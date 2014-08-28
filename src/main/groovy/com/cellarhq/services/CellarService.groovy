package com.cellarhq.services

import static com.cellarhq.generated.Tables.*
import static ratpack.rx.RxRatpack.observe
import static ratpack.rx.RxRatpack.observeEach

import com.cellarhq.domain.Cellar
import com.cellarhq.generated.tables.records.CellarRecord
import com.google.inject.Inject
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.jooq.DSLContext
import org.jooq.SelectJoinStep
import org.pac4j.oauth.profile.twitter.TwitterProfile
import ratpack.exec.ExecControl

import javax.sql.DataSource
import java.sql.Timestamp
import java.time.LocalDateTime

@Slf4j
@CompileStatic
class CellarService extends BaseJooqService {

    @Inject
    CellarService(DataSource dataSource, ExecControl execControl) {
        super(dataSource, execControl)
    }

    rx.Observable<Cellar> save(Cellar cellar) {
        observe(execControl.blocking {
            jooq { DSLContext create ->
                CellarRecord cellarRecord = create.newRecord(CELLAR, cellar)

                if (cellarRecord.id) {
                    cellarRecord.update()
                } else {
                    cellarRecord.reset(CELLAR.ID)
                    cellarRecord.store()
                }

                cellarRecord.into(Cellar)
            }
        }).asObservable()
    }

    rx.Observable<Cellar> find(Long id) {
        observe(execControl.blocking {
            jooq { DSLContext create ->
                create.select()
                    .from(CELLAR)
                    .where(CELLAR.ID.eq(id))
                    .fetchOneInto(Cellar)
            }
        }).asObservable()
    }

    rx.Observable<Cellar> findBySlug(String slug) {
        observe(execControl.blocking {
            jooq { DSLContext create ->
                create.select()
                        .from(CELLAR)
                        .where(CELLAR.SCREEN_NAME.eq(slug))
                        .fetchOneInto(Cellar)
            }
        }).asObservable()
    }

    rx.Observable<Cellar> all(int numberOfRows = 20, int offset = 0) {
        observeEach(execControl.blocking {
            jooq { DSLContext create ->
                create.select()
                        .from(CELLAR)
                        .limit(offset, numberOfRows)
                        .fetchInto(Cellar)
            }
        })
    }

    rx.Observable<Cellar> search(String searchTerm, int numberOfRows = 20, int offset = 0) {
        observe(execControl.blocking {
            jooq { DSLContext create ->
                create.select()
                    .from(CELLAR)
                    .where(CELLAR.DISPLAY_NAME.likeIgnoreCase("%${searchTerm}%"))
                    .limit(offset, numberOfRows)
                    .fetchInto(Cellar)
            }
        })
    }

    rx.Observable<Integer> count(String searchTerm = null) {
        observe(execControl.blocking {
            jooq { DSLContext create ->
                SelectJoinStep select = create.selectCount()
                        .from(CELLAR)

                if (searchTerm) {
                    select.where(CELLAR.DISPLAY_NAME.likeIgnoreCase("%${searchTerm}%"))
                }

                select.fetchOneInto(Integer)
            }
        })
    }

    Cellar getBlocking(Long id) {
        (Cellar) jooq { DSLContext create ->
            create.select()
                    .from(CELLAR)
                    .where(CELLAR.ID.eq(id))
                    .fetchOneInto(Cellar)
        }
    }

    Cellar saveBlocking(Cellar cellar) {
        jooq { DSLContext create ->
            CellarRecord cellarRecord = create.newRecord(CELLAR, cellar)
            if (cellar.id) {
                create.executeUpdate(cellarRecord)
            } else {
                create.executeInsert(cellarRecord)
            }
            cellarRecord.into(Cellar)
        }
    }

    void updateLoginStats(Cellar cellar, TwitterProfile twitterProfile = null) {
        cellar.lastLogin = Timestamp.valueOf(LocalDateTime.now())
        if (cellar.updateFromNetwork && twitterProfile) {
            cellar.with {
                displayName = twitterProfile.displayName
                location = twitterProfile.location
                website = twitterProfile.profileUrl
                bio = twitterProfile.description
            }
        }
        saveBlocking(cellar)
    }
}
