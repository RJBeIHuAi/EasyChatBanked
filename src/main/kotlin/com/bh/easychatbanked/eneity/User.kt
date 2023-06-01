package com.bh.easychatbanked.eneity

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

    val username: String,

    val password: String,

    val email: String,

    val phone: String,

    val avatar: String = "default-avatar.jpg",

    val createTime: LocalDateTime = LocalDateTime.now()
)

sealed class RegistrationResult<out T> {
    data class Success<out T>(val message: String) : RegistrationResult<T>()
    data class DuplicateAccount<out T>(val code: Int, val message: String) : RegistrationResult<T>()
    data class Failure<out T>(val code: Int, val message: String) : RegistrationResult<T>()
}






