package ru.surf.core.kafkaEvents

data class CandidateEventNotification(
    override val emailType: EmailType,
    override val emailTo: String,
    override val subject: String,

    val firstName: String,
    val lastName: String,
    val eventsName: Set<String>
) : IMailEvent {
    override fun params(): Map<String, *> {
        return mapOf(
            "firstName" to firstName,
            "lastName" to lastName,
            "eventsName" to eventsName
        )
    }
}