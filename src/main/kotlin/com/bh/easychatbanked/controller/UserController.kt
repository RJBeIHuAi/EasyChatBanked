package com.bh.easychatbanked.controller

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
    fun registerUser(@RequestBody registrationRequest: RegistrationRequest): ResponseEntity<ApiResponse> {
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
            val response = ApiResponse(HttpStatus.BAD_REQUEST.value(), "Account already exists.")
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response)
        }

        // 调用 UserService 注册用户
        userService.registerUser(account, username, password, email, phone)

        val response = ApiResponse(HttpStatus.CREATED.value(), "User registered successfully.")
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @GetMapping("/find/{id}")
    fun getUserById(@PathVariable id: Long?): ResponseEntity<ApiResponse> {
        val user = userService.getUserById(id)
        return if (user != null) {
            val response = ApiResponse(HttpStatus.OK.value(), "User found.")
            ResponseEntity.ok(response)
        } else {
            val response = ApiResponse(HttpStatus.NOT_FOUND.value(), "User not found.")
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(response)
        }
    }

    @DeleteMapping("/del/{id}")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<ApiResponse> {
        return try {
            userService.deleteUserById(id)
            ResponseEntity.ok(ApiResponse(200, "User deleted successfully."))
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse(500, "Failed to delete user."))
        }
    }
    @PatchMapping("/update/{id}")
    fun updateUser(
        @PathVariable id: Long,
        @RequestBody updatedUser: UserUpdateRequest
    ): ResponseEntity<ApiResponse> {
        val success = userService.updateUser(id, updatedUser)
        return if (success) {
            val response = ApiResponse(HttpStatus.OK.value(), "User updated successfully.")
            ResponseEntity.ok(response)
        } else {
            val response = ApiResponse(HttpStatus.NOT_FOUND.value(), "User not found.")
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(response)
        }
    }

    @GetMapping("/csrf")
    fun getCsrfToken(request: HttpServletRequest): String ? {
        return (request.getAttribute("_csrf") as CsrfToken).token
    }

}

data class ApiResponse(val code: Int, val message: String)
