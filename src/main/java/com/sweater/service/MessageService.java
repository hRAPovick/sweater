package com.sweater.service;

import com.sweater.domain.Message;
import com.sweater.domain.User;
import com.sweater.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public Page<Message> findByAuthor(User author, Pageable pageable) {
        return messageRepository.findByAuthor(author, pageable);
    }

    public Page<Message> findByTag(String filter, Pageable pageable) {
        return messageRepository.findByTag(filter, pageable);
    }

    public Page<Message> findAll(Pageable pageable) {
        return  messageRepository.findAll(pageable);
    }

    public void save(Message message) {
        messageRepository.save(message);
    }
}

