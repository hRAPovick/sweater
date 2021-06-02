package com.sweater.service;

import com.sweater.domain.Message;
import com.sweater.domain.User;
import com.sweater.domain.dto.MessageDto;
import com.sweater.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public Page<MessageDto> findByAuthor(User author, User user, Pageable pageable) {
        return messageRepository.findByAuthor(author, user, pageable);
    }

    public Page<MessageDto> findByTag(String filter, Pageable pageable) {
        return messageRepository.findByTag(filter, pageable);
    }

    public Page<MessageDto> findAll(Pageable pageable, User user) {
        return  messageRepository.findAll(pageable, user);
    }

    public void save(Message message) {
        messageRepository.save(message);
    }
}

