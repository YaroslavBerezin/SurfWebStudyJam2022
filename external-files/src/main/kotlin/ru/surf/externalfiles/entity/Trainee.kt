package ru.surf.externalfiles.entity

import ru.surf.externalfiles.entity.base.UUIDBasedEntity
import java.util.*
import javax.persistence.*

@Table(name = "trainees")
@Entity
class Trainee(

    @Id
    @Column(name = "id")
    override val id: UUID = UUID.randomUUID(),

    @Column(name = "score")
    var score: Int? = null,

    @Column(name = "is_active")
    var isActive: Boolean = false,

    @ManyToOne(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id")
    val candidate: Candidate = Candidate(),

    @ManyToOne(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    val event: Event = Event(),

    @ManyToOne(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    val team: Team = Team(),

    @OneToOne(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    val account: Account = Account(),

    @OneToMany(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY, mappedBy = "traineeReceiver")
    val trainees: List<TraineeFeedback> = emptyList(),

    ) : UUIDBasedEntity(id) {

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , score = $score , isActive = $isActive )"
    }

}