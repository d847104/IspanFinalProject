package com.ispan.warashibe.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ispan.warashibe.model.ChatRoom;
import com.ispan.warashibe.model.Members;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    
    Optional<ChatRoom> findByParticipant1AndParticipant2(Members participant1, Members participant2);
    
    Optional<ChatRoom> findByParticipant2AndParticipant1(Members participant2, Members participant1);
}
