package com.bh.easychatbanked.request

import com.bh.easychatbanked.eneity.FriendRequest
import com.bh.easychatbanked.eneity.User

data class RegistrationRequest(
    val account: String,
    val username: String,
    val password: String,
    val email: String,
    val phone: String
)

data class UserUpdateRequest(
    val account: String? = null,
    val username: String? = null,
    val password: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val avatar: String? = null,
)

