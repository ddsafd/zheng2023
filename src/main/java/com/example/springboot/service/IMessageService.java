package com.example.springboot.service;

import com.example.springboot.entity.Message;
import java.util.List;

public interface IMessageService {
    List<Message> getAllMessages();
    Message getMessageById(Long id);
    void addMessage(Message message);
    void updateMessage(Message message);
    void deleteMessage(Long id);
}
