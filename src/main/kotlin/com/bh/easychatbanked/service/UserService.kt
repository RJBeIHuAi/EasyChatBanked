package com.bh.easychatbanked.service

import com.bh.easychatbanked.eneity.User
import com.bh.easychatbanked.repository.UserRepository
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.web.csrf.CsrfToken
import org.springframework.security.web.csrf.CsrfTokenRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    @Autowired
    private val userRepository: UserRepository,
    private val csrfTokenRepository: CsrfTokenRepository,
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
    fun getCsrfToken(request: HttpServletRequest): CsrfToken? {
        return csrfTokenRepository.loadToken(request)
    }

    fun getUserByAccount(account: String): User? {
        return userRepository.findByAccount(account)
    }
}