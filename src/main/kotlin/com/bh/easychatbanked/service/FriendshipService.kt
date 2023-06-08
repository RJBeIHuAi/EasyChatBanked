package com.bh.easychatbanked.service

import com.bh.easychatbanked.eneity.FriendRequest
import com.bh.easychatbanked.eneity.User
import com.bh.easychatbanked.enum.FriendRequestStatus
import com.bh.easychatbanked.repository.FriendRequestRepository
import com.bh.easychatbanked.repository.UserRepository
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.web.csrf.CsrfToken
import org.springframework.security.web.csrf.CsrfTokenRepository
import org.springframework.stereotype.Service

@Service
class FriendshipService (
    @Autowired
    private val userRepository: UserRepository,
    private val friendRequestRepository: FriendRequestRepository,
){
    fun sendFriendRequest(senderAccount: String, receiverAccount: String) {
        val sender = userRepository.findByAccount(senderAccount)
        val receiver = userRepository.findByAccount(receiverAccount)

        if (sender != null && receiver != null) {
            val existingRequest = friendRequestRepository.findBySenderAndReceiver(sender, receiver)
            if (existingRequest == null) {
                val friendRequest = FriendRequest(sender = sender, receiver = receiver)
                friendRequestRepository.save(friendRequest)
            }
        }
    }
    fun acceptFriendRequest(requestId: Long) {
        val friendRequest = friendRequestRepository.findById(requestId).orElse(null)
        if (friendRequest != null && friendRequest.status == FriendRequestStatus.PENDING) {
            friendRequest.status = FriendRequestStatus.ACCEPTED
            friendRequestRepository.save(friendRequest)

            val sender = friendRequest.sender
            val receiver = friendRequest.receiver
            sender.friends.add(receiver)
            receiver.friends.add(sender)
            userRepository.save(sender)
            userRepository.save(receiver)
        }
    }

    fun rejectFriendRequest(requestId: Long) {
        val friendRequest = friendRequestRepository.findById(requestId).orElse(null)
        if (friendRequest != null && friendRequest.status == FriendRequestStatus.PENDING) {
            friendRequest.status = FriendRequestStatus.REJECTED
            friendRequestRepository.save(friendRequest)
        }
    }

    fun getPendingFriendRequestsWithSender(user: User): List<User> {
        val pendingRequests = friendRequestRepository.findByReceiverAndStatus(user, FriendRequestStatus.PENDING)
        return pendingRequests.map { it.sender }
    }

    fun getFriends(user: User): List<User> {
        return user.friends
    }

    fun removeFriend(user: User, friend: User) {
        user.friends.remove(friend)
        friend.friends.remove(user)
        userRepository.save(user)
        userRepository.save(friend)
    }
}