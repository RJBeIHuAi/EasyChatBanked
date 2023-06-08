package com.bh.easychatbanked.controller

import com.bh.easychatbanked.eneity.User
import com.bh.easychatbanked.repository.UserRepository
import com.bh.easychatbanked.service.FriendshipService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.web.csrf.CsrfToken
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/friends")
class FriendController (
    @Autowired
    private val friendshipService: FriendshipService,
    private val userRepository: UserRepository
){

    //发送申请
    @PostMapping("/request")
    fun sendFriendRequest(@RequestParam senderAccount: String, @RequestParam receiverAccount: String) {
        friendshipService.sendFriendRequest(senderAccount, receiverAccount)
    }

    //接受申请
    @PostMapping("/request/accept")
    fun acceptFriendRequest(@RequestParam requestId: Long) {
        friendshipService.acceptFriendRequest(requestId)
    }

    // 拒绝申请
    @PostMapping("/request/reject")
    fun rejectFriendRequest(@RequestParam requestId: Long) {
        friendshipService.rejectFriendRequest(requestId)
    }

    //获取发送者信息 及 request
    @GetMapping("/requests/pending")
    fun getPendingFriendRequests(@RequestParam receiverId: Long): List<User> {
        val user = userRepository.findById(receiverId).orElse(null)
        return if (user != null) {
            friendshipService.getPendingFriendRequestsWithSender(user)
        } else {
            emptyList()
        }
    }


    @GetMapping("/fid/{userId}")
    fun getFriends(@PathVariable userId: Long): List<User> {
        val user = userRepository.findById(userId).orElse(null)
        return if (user != null) {
            friendshipService.getFriends(user)
        } else {
            emptyList()
        }
    }
    //移除好友关系
    @PostMapping("/remove")
    fun removeFriend(@RequestParam username: String, @RequestParam friendUsername: String) {
        val user = userRepository.findByUsername(username)
        val friend = userRepository.findByUsername(friendUsername)
        if (user != null && friend != null) {
            friendshipService.removeFriend(user, friend)
        }
    }

    @GetMapping("/csrf")
    fun getCsrfToken(request: HttpServletRequest): String ? {
        return (request.getAttribute("_csrf") as CsrfToken).token
    }
}