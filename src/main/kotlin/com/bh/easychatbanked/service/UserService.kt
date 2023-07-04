package com.bh.easychatbanked.service

import com.bh.easychatbanked.eneity.User
import com.bh.easychatbanked.repository.UserRepository
import com.bh.easychatbanked.request.UserUpdateRequest
import org.springframework.beans.factory.annotation.Autowired

import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class UserService(
    @Autowired
    private val userRepository: UserRepository,
) {
    fun registerUser(account: String, username: String, password: String, email: String, phone: String) {

        val user = User(
            account = account,
            username = username,
            password = password,
            email = email,
            phone = phone
        )
        userRepository.save(user)
    }

    fun getUserById(id: Long?): User? {
        return userRepository.findById(id!!).orElse(null)
    }

    fun getUserByAccount(account: String): User? {
        return userRepository.findByAccount(account)
    }
    fun deleteUserById(id: Long) {
        userRepository.deleteById(id)
    }
    fun updateUser(id: Long, updatedUser: UserUpdateRequest): Boolean {
        val user = userRepository.findById(id).orElse(null)
        // 判断是否在创建提交时间的30天内
        val createTime = user.createTime
        val currentDate = LocalDateTime.now()
        val thirtyDaysAgo = currentDate.minusDays(30)
        if (createTime.isBefore(thirtyDaysAgo)) {
            throw IllegalArgumentException("Modification not allowed after 30 days.")
        }
        // 更新用户信息
        updatedUser.username?.let { user.username = it }
        updatedUser.password?.let { user.password = it }
        updatedUser.email?.let { user.email = it }
        updatedUser.phone?.let { user.phone = it }

        userRepository.save(user)
        return true
    }

}