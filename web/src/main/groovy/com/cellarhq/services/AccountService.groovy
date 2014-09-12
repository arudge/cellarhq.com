package com.cellarhq.services

import static com.cellarhq.generated.Tables.*

import com.cellarhq.auth.Role
import com.cellarhq.domain.OAuthClient
import com.cellarhq.domain.Cellar
import com.cellarhq.domain.EmailAccount
import com.cellarhq.domain.OAuthAccount
import com.cellarhq.domain.Photo
import com.cellarhq.generated.tables.records.AccountEmailRecord
import com.cellarhq.generated.tables.records.AccountOauthRecord
import com.cellarhq.generated.tables.records.CellarRecord
import com.cellarhq.generated.tables.records.CellarRoleRecord
import com.cellarhq.generated.tables.records.PasswordChangeRequestRecord
import com.cellarhq.generated.tables.records.PhotoRecord
import com.cellarhq.services.photo.PhotoService
import com.cellarhq.services.photo.writer.PhotoWriteFailedException
import com.cellarhq.services.photo.model.ResizeCommand
import com.cellarhq.util.LogUtil
import com.google.inject.Inject
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.jooq.DSLContext
import org.jooq.Record
import org.mindrot.jbcrypt.BCrypt
import ratpack.exec.ExecControl
import ratpack.form.UploadedFile

import javax.sql.DataSource
import java.sql.Timestamp
import java.time.LocalDateTime

@Slf4j
@CompileStatic
class AccountService extends BaseJooqService {

    private final static int BCRYPT_LOG_ROUNDS = 16

    private final PhotoService photoService

    @Inject
    AccountService(DataSource dataSource, ExecControl execControl, PhotoService photoService) {
        super(dataSource, execControl)
        this.photoService = photoService
    }

    EmailAccount findByCredentials(String username, String password) {
        EmailAccount emailAccount = findByEmail(username)
        if (emailAccount && BCrypt.checkpw(password, emailAccount.password)) {
            return emailAccount
        }
        return null
    }

    OAuthAccount findByCredentials(String username, OAuthClient client = OAuthClient.TWITTER) {
        (OAuthAccount) jooq { DSLContext create ->
            Record result = create.select()
                    .from(ACCOUNT_OAUTH)
                    .join(CELLAR).onKey()
                    .where(ACCOUNT_OAUTH.CLIENT.eq(client.toString()))
                        .and(ACCOUNT_OAUTH.USERNAME.eq(username))
                    .fetchOne()

            if (result) {
                OAuthAccount oAuthAccount = result.into(OAuthAccount)
                oAuthAccount.cellar = result.into(Cellar)
                return oAuthAccount
            }
            return null
        }
    }

    EmailAccount findByEmail(String email) {
        (EmailAccount) jooq { DSLContext create ->
            create.select()
                .from(ACCOUNT_EMAIL)
                .where(ACCOUNT_EMAIL.EMAIL.eq(email))
                .fetchOneInto(EmailAccount)
        }
    }

    EmailAccount findByEmailWithCellar(String email) {
        (EmailAccount) jooq { DSLContext create ->
            Record result = create.select()
                    .from(ACCOUNT_EMAIL)
                    .join(CELLAR).onKey()
                    .where(ACCOUNT_EMAIL.EMAIL.eq(email))
                    .fetchOne()

            if (result) {
                EmailAccount emailAccount = result.into(EmailAccount)
                emailAccount.cellar = result.into(Cellar)
                return emailAccount
            }
            return null
        }
    }

    EmailAccount create(EmailAccount emailAccount, UploadedFile picture) {
        emailAccount.password = BCrypt.hashpw(emailAccount.password, BCrypt.gensalt(BCRYPT_LOG_ROUNDS))
        (EmailAccount) jooq { DSLContext create ->
            create.transactionResult {
                CellarRecord cellarRecord = create.newRecord(CELLAR, emailAccount.cellar)

                if (picture) {
                    PhotoRecord photoRecord = new PhotoRecordBuilder(picture).makePhoto(create)
                    if (photoRecord) {
                        photoRecord.description = Photo.DESCRIPTION_SETTINGS_UPLOAD
                        photoRecord.store()
                        cellarRecord.photoId = photoRecord.id
                    }
                }

                cellarRecord.reset(CELLAR.ID)
                cellarRecord.store()
                emailAccount.cellarId = cellarRecord.id

                bindRoleToCellar(create, Role.MEMBER, cellarRecord.id)

                AccountEmailRecord accountEmailRecord = create.newRecord(ACCOUNT_EMAIL, emailAccount)
                accountEmailRecord.reset(ACCOUNT_EMAIL.ID)
                accountEmailRecord.store()

                EmailAccount resultEmailAccount = accountEmailRecord.into(EmailAccount)
                resultEmailAccount.cellar = cellarRecord.into(Cellar)
                resultEmailAccount.id ? resultEmailAccount : null
            }
        }
    }

    OAuthAccount create(OAuthAccount oAuthAccount, String pictureUrl) {
        (OAuthAccount) jooq { DSLContext create ->
            create.transactionResult {
                CellarRecord cellarRecord = create.newRecord(CELLAR, oAuthAccount.cellar)

                if (pictureUrl) {
                    PhotoRecord photoRecord = new PhotoRecordBuilder(pictureUrl).makePhoto(create)
                    if (photoRecord) {
                        photoRecord.description = Photo.DESCRIPTION_TWITTER_UPLOAD
                        photoRecord.store()
                        cellarRecord.photoId = photoRecord.id
                    }
                }

                cellarRecord.reset(CELLAR.ID)
                cellarRecord.store()
                oAuthAccount.cellarId = cellarRecord.id

                bindRoleToCellar(create, Role.MEMBER, cellarRecord.id)

                AccountOauthRecord record = create.newRecord(ACCOUNT_OAUTH, oAuthAccount)
                record.reset(ACCOUNT_OAUTH.ID)
                record.store()

                OAuthAccount resultOAuthAccount = record.into(OAuthAccount)
                resultOAuthAccount.cellar = cellarRecord.into(Cellar)
                resultOAuthAccount.id ? resultOAuthAccount : null
            }
        }
    }

    private void bindRoleToCellar(DSLContext create, Role role, long cellarId) {
        CellarRoleRecord cellarRoleRecord = create.newRecord(CELLAR_ROLE)
        cellarRoleRecord.cellarId = cellarId
        cellarRoleRecord.role = role.toString()
        cellarRoleRecord.store()
    }

    String startPasswordRecovery(EmailAccount email) {
        String uuid = UUID.randomUUID().toString().replaceAll(/\W/, '')
        jooq { DSLContext create ->
            PasswordChangeRequestRecord record = create.newRecord(PASSWORD_CHANGE_REQUEST)
            record.id = uuid
            record.accountEmailId = email.id
            record.createdDate = Timestamp.valueOf(LocalDateTime.now())
            record.store()
        }
        return uuid
    }

    EmailAccount findByPasswordChangeRequestHash(String hash) {
        jooq { DSLContext create ->
            create.select(ACCOUNT_EMAIL.fields())
                    .from(ACCOUNT_EMAIL)
                    .join(PASSWORD_CHANGE_REQUEST).onKey()
                    .where(PASSWORD_CHANGE_REQUEST.ID.eq(hash))
                    .fetchOne()?.into(EmailAccount)
        }
    }

    void changePassword(EmailAccount emailAccount, String requestUuid) {
        String password = BCrypt.hashpw(emailAccount.password, BCrypt.gensalt(BCRYPT_LOG_ROUNDS))
        jooq { DSLContext create ->
            create.transaction {
                create.update(ACCOUNT_EMAIL)
                        .set(ACCOUNT_EMAIL.PASSWORD, password)
                        .where(ACCOUNT_EMAIL.ID.eq(emailAccount.id))
                        .execute()

                create.delete(PASSWORD_CHANGE_REQUEST).where(PASSWORD_CHANGE_REQUEST.ID.eq(requestUuid)).execute()
            }
        }
    }

    private class PhotoRecordBuilder {

        private final UploadedFile file
        private final String url

        PhotoRecordBuilder(UploadedFile file) {
            this.file = file
            this.url = null
        }

        PhotoRecordBuilder(String url) {
            this.url = url
            this.file = null
        }

        PhotoRecord makePhoto(DSLContext create) {
            List<ResizeCommand> resizeCommands = [
                    new ResizeCommand(Photo.Size.THUMB, 64),
                    new ResizeCommand(Photo.Size.LARGE, 256)
            ]
            try {
                return file ?
                        photoService.createPhotoRecord(create, Photo.Type.CELLAR, file, resizeCommands) :
                        photoService.createPhotoRecord(create, Photo.Type.CELLAR, url, resizeCommands)
            } catch (PhotoWriteFailedException e) {
                log.error(LogUtil.toLog('AccountService', [
                        msg: 'Photo write failed while creating an account'
                ]))
            }
            return null
        }
    }
}