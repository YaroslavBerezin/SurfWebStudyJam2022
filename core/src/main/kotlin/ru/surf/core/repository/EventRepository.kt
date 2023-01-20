package ru.surf.core.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.surf.core.entity.Event
import java.util.UUID

interface EventRepository : JpaRepository<Event, UUID> {
}