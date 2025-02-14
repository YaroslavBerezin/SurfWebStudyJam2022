package ru.surf.core.kafkaEvents

data class GeneralNotificationDto(
    override val emailType: EmailType,
    override val emailTo: String,
    override val subject: String,
    val notificationParams: Map<String, *>
) : MailEvent {
    override fun params(): Map<String, *> {
        return notificationParams
    }
}