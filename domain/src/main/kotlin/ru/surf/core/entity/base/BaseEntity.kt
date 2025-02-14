package ru.surf.core.entity.base

import org.springframework.data.util.ProxyUtils
import java.io.Serializable
import java.util.*
import jakarta.persistence.*

@MappedSuperclass
abstract class BaseEntity<T : Serializable> {

    abstract val id: T?

    override fun equals(other: Any?): Boolean {
        if (null == other) return false
        if (this === other) return true
        if (javaClass != ProxyUtils.getUserClass(other)) return false

        val that = other as BaseEntity<*>
        return Objects.equals(id, that.id)
    }

    override fun hashCode(): Int {
        var hashCode = 17
        hashCode += when (id) {
            null -> 0
            else -> id.hashCode() * 31
        }
        return hashCode
    }

}