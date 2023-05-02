package ru.surf.mail.service.strategy

import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component
import org.thymeleaf.spring6.SpringTemplateEngine
import ru.surf.core.kafkaEvents.EmailType
import ru.surf.core.kafkaEvents.MailEvent
import ru.surf.mail.model.Template

@Component
class DefenceCancelNotificationStrategy(
        private val javaMailSender: JavaMailSender,
        private val springTemplateEngine: SpringTemplateEngine
) : EmailSendStrategy {

    override fun emailType(): EmailType = EmailType.DEFENCE_CANCEL_NOTIFICATION

    override fun sendEmail(email: MailEvent) =
            createMimeMessage(
                    email,
                    Template.DEFENCE_CANCEL_NOTIFICATION.html,
                    javaMailSender,
                    springTemplateEngine
            ).run {
                javaMailSender.send(this)
            }

}