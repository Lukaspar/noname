package com.lukaspar.ep.authentication.repository;

import com.lukaspar.ep.authentication.model.Message;
import com.lukaspar.ep.authentication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByReceiver(User receiver);
}
