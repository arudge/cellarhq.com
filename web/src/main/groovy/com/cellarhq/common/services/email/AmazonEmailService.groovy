package com.cellarhq.common.services.email

import com.amazonaws.auth.AWSCredentials
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient
import com.amazonaws.services.simpleemail.model.*
import com.cellarhq.util.LogUtil
import com.google.inject.Inject
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

/**
 * @todo Refactor to be non-blocking.
 */
@Slf4j
@CompileStatic
class AmazonEmailService implements EmailService {

  private final AmazonSimpleEmailServiceClient client

  @Inject
  AmazonEmailService(AWSCredentials credentials) {
    client = new AmazonSimpleEmailServiceClient(credentials)
  }

  @Override
  void sendEmail(String to, String subject, String body) {
    sendEmail(DEFAULT_FROM, to, subject, body)
  }

  @Override
  void sendEmail(String from, String to, String subject, String body) {
    SendEmailResult result = client.sendEmail(
      new SendEmailRequest(
        source: from,
        destination: new Destination([to]),
        message: new Message(
          subject: new Content(subject),
          body: new Body(new Content(body))
        )
      )
    )
    log.info(LogUtil.toLog('Sent email via amazon', [
      from     : from,
      subject  : subject,
      to       : to,
      body     : body,
      messageId: result.messageId
    ]))
  }
}
