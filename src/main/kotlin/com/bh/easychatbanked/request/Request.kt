package com.bh.easychatbanked.request

data class RegistrationRequest(
    val account: String,
    val username: String,
    val password: String,
    val email: String,
    val phone: String
)