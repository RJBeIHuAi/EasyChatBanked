package com.bh.easychatbanked.controller

import com.bh.easychatbanked.eneity.User
import com.bh.easychatbanked.request.RegistrationRequest
import com.bh.easychatbanked.request.UserUpdateRequest
import com.bh.easychatbanked.service.UserService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.web.csrf.CsrfToken
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(
    @Autowired
    private val userService: UserService,
){
    @PostMapping("/register")
    fun registerUser(@RequestBody registrationRequest: RegistrationRequest): ResponseEntity<String> {
        // 从请求体中获取用户注册信息
        val account = registrationRequest.account
        val username = registrationRequest.username
        val password = registrationRequest.password
        val email = registrationRequest.email
        val phone = registrationRequest.phone

        // 检查账号是否存在
        val existingUser = userService.getUserByAccount(account)
        if (existingUser != null) {
            // 账号已经存在，返回相应的错误信息
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account already exists.")
        }

        // 调用 UserService 注册用户
        userService.registerUser(account, username, password, email, phone)

        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully.")
    }

    @GetMapping("/find/{id}")
    fun getUserById(@PathVariable id: Long?): User? {
        return userService.getUserById(id)
    }

    @DeleteMapping("/del/{id}")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<String> {
        userService.deleteUserById(id)
        return ResponseEntity.status(HttpStatus.CREATED).body("User deleted successfully.")
    }
    @PatchMapping("/update/{id}")
    fun updateUser(
        @PathVariable id: Long,
        @RequestBody updatedUser: UserUpdateRequest
    ): ResponseEntity<String> {
        val success = userService.updateUser(id, updatedUser)
        return if (success) {
            ResponseEntity.ok("User updated successfully.")
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.")
        }
    }

    @GetMapping("/csrf")
    fun getCsrfToken(request: HttpServletRequest): String ? {
        return (request.getAttribute("_csrf") as CsrfToken).token
    }

}