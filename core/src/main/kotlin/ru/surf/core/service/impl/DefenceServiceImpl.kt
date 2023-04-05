package ru.surf.core.service.impl

import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.surf.core.dto.defence.PostRequestDefenceDto
import ru.surf.core.dto.defence.PostResponseDefenceDto
import ru.surf.core.entity.Candidate
import ru.surf.core.entity.Defence
import ru.surf.core.entity.SurfEmployee
import ru.surf.core.entity.Trainee
import ru.surf.core.exception.defence.DefenceCreationFailedException
import ru.surf.core.exception.defence.DefenceNotFoundByIdException
import ru.surf.core.exception.defence.DefenceNotFoundByMeetingIdException
import ru.surf.core.kafkaEvents.meeting.CancelDefenceMeetingEvent
import ru.surf.core.kafkaEvents.meeting.CreateDefenceMeetingEvent
import ru.surf.core.mapper.defence.DefenceMapper
import ru.surf.core.repository.DefenceRepository
import ru.surf.core.repository.TraineeRepository
import ru.surf.core.service.DefenceService
import ru.surf.core.service.EventService
import ru.surf.core.service.KafkaService
import ru.surf.meeting.service.ZoomIntegrationService
import java.util.*

@Service
class DefenceServiceImpl(
    private val defenceMapper: DefenceMapper,
    private val kafkaService: KafkaService,
    private val defenceRepository: DefenceRepository,
    private val eventService: EventService,
    private val zoomIntegrationService: ZoomIntegrationService,
    private val traineeRepository: TraineeRepository,
) : DefenceService {

    companion object Logger {
        val logger = LoggerFactory.getLogger(DefenceServiceImpl::class.java)
    }

    override fun createDefence(postRequestDefenceDto: PostRequestDefenceDto): PostResponseDefenceDto =
        with(defenceMapper.convertFromPostRequestDefenceDtoToDefenceEntity(postRequestDefenceDto)) {
            val zoomMeeting = try {
                zoomIntegrationService.createZoomMeeting(postRequestDefenceDto.zoomCreateMeetingRequestDto)
            } catch (e: Exception) {
                throw DefenceCreationFailedException()
            }
            zoomLink = zoomMeeting.joinUrl
            zoomMeetingId = zoomMeeting.id
            kafkaService.sendCoreEvent(
                CreateDefenceMeetingEvent(
                    title = postRequestDefenceDto.title,
                    description = postRequestDefenceDto.description,
                    date = postRequestDefenceDto.date,
                    duration = postRequestDefenceDto.zoomCreateMeetingRequestDto.duration,
                    candidateParticipants = traineeRepository.findAllByIdIn(postRequestDefenceDto.traineeIds),
                    // TODO: 17.03.2023 Этот список будет генерироваться другим методом
                    surfParticipants = listOf(SurfEmployee(name = "")),
                    zoomLink = zoomLink,
                    eventName = eventService.getEvent(postRequestDefenceDto.eventId).title,
                )
            )
            defenceRepository.save(this).also {
                logger.info("Saved entity with data $it")
            }.run {
                defenceMapper.convertFromDefenceEntityToPostResponseEntityDto(this)
            }
        }

    override fun deleteDefence(id: UUID) {
        val defenceFromDb = findDefence(id)
        kafkaService.sendCoreEvent(
            CancelDefenceMeetingEvent(
                title = defenceFromDb.title,
                description = defenceFromDb.description,
                date = defenceFromDb.date,
                // TODO: 17.03.2023 Этот список будет доставаться из бд
                traineeParticipants = listOf(
                    Trainee(
                        Candidate(
                        )
                    )
                ),
                // TODO: 25.03.2023 Достать все всех работников в соответствии с командой позже
                surfParticipants = listOf(SurfEmployee(name = "")),
                zoomMeetingId = defenceFromDb.zoomMeetingId
            )
        )
        defenceRepository.delete(defenceFromDb)
    }

    override fun findDefence(id: UUID): Defence {
        val defenceFromDb = defenceRepository.findByIdOrNull(id) ?: throw DefenceNotFoundByIdException(id)
        logger.info("Successfully getting defence with id $id")
        return defenceFromDb
    }

    override fun deleteDefenceByMeetingId(zoomMeetingId: Long) =
        defenceRepository.delete(findDefenceByMeetingId(zoomMeetingId)).run {
            logger.info("Successfully delete defence with meeting id $zoomMeetingId")
        }

    override fun findDefenceByMeetingId(zoomMeetingId: Long): Defence {
        val defenceFromDb = (defenceRepository.getDefenceByZoomMeetingId(zoomMeetingId)
            ?: throw DefenceNotFoundByMeetingIdException(zoomMeetingId))
        logger.info("Successfully getting defence with meeting id $zoomMeetingId")
        return defenceFromDb
    }

}

