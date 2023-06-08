package com.bh.easychatbanked.repository

import com.bh.easychatbanked.eneity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByAccount(account: String): User?
    fun findByUsername(username: String): User?
}