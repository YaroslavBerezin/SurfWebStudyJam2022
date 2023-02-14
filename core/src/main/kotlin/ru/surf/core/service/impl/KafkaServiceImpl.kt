package ru.surf.core.service.impl

import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.KafkaException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaProducerException
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import ru.surf.core.configuration.KafkaTopicConfiguration
import ru.surf.core.event.ReceivingRequestKafkaEvent
import ru.surf.core.exception.ExceptionType
import ru.surf.core.service.KafkaService

@Service
class KafkaServiceImpl(
    private val kafkaTemplate: KafkaTemplate<String, ReceivingRequestKafkaEvent>,
) : KafkaService {

    companion object KafkaLogger {
        val logger: Logger = LoggerFactory.getLogger(KafkaServiceImpl::class.java)
    }

    override fun sendReceivingRequestEvent(receivingRequestKafkaEvent: ReceivingRequestKafkaEvent) {
        val requestKafkaEventRecord =
            ProducerRecord<String, ReceivingRequestKafkaEvent>(
                KafkaTopicConfiguration.TOPICS.RECEIVING_REQUEST_TOPIC,
                receivingRequestKafkaEvent
            )
        kafkaTemplate.send(requestKafkaEventRecord).completable().whenComplete { result, ex ->
            when (ex == null) {
                true -> logger.debug("Successfully send $receivingRequestKafkaEvent to ${KafkaTopicConfiguration.TOPICS.RECEIVING_REQUEST_TOPIC}")
                false -> {
                    logger.error("Message sending failed with data $result")
                    throw KafkaProducerException(
                        requestKafkaEventRecord,
                        ExceptionType.SERVICE_EXCEPTION.toString(),
                        KafkaException()
                    )
                }
            }
        }
    }

}