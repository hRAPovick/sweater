package com.sweater.repository;

import com.sweater.domain.Message;
import com.sweater.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface MessageRepository extends CrudRepository<Message, Long> {

    Iterable<Message> findByTag(String tag);
    Iterable<Message> findByAuthor(User author);
}
