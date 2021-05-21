package com.sweater.service;

import com.sweater.domain.Message;
import com.sweater.domain.User;
import com.sweater.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public Iterable<Message> findByAuthor(User author) {
        return messageRepository.findByAuthor(author);
    }

    public Iterable<Message> findByTag(String filter) {
        return messageRepository.findByTag(filter);
    }

    public Iterable<Message> findAll() {
        return  messageRepository.findAll();
    }

    public void save(Message message) {
        messageRepository.save(message);
    }
}

