package com.bh.easychatbanked.eneity

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(unique = true)
    val account: String,

    var username: String,

    var password: String,

    var email: String,

    var phone: String,

    val avatar: String = "default-avatar.jpg",

    val createTime: LocalDateTime = LocalDateTime.now(),

    @ManyToMany
    @JoinTable(
    name = "user_friends",
    joinColumns = [JoinColumn(name = "user_id")],
    inverseJoinColumns = [JoinColumn(name = "friend_id")]
    )
    @JsonIgnore
    val friends: MutableList<User> = mutableListOf()

)





