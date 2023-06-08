package com.bh.easychatbanked.repository

import com.bh.easychatbanked.eneity.FriendRequest
import com.bh.easychatbanked.eneity.User
import com.bh.easychatbanked.enum.FriendRequestStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FriendRequestRepository :JpaRepository<FriendRequest, Long> {
    fun findBySenderAndReceiver(sender: User, receiver: User): FriendRequest?
    fun findByReceiverAndStatus(receiver: User, status: FriendRequestStatus): List<FriendRequest>
}