package com.bh.easychatbanked.eneity

import com.bh.easychatbanked.enum.FriendRequestStatus
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*


@Entity
@Table(name = "friend_requests")
data class FriendRequest(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JsonIgnore
    val sender: User,

    @ManyToOne
    @JsonIgnore
    val receiver: User,

    @Enumerated(EnumType.STRING)
    var status: FriendRequestStatus = FriendRequestStatus.PENDING
)
