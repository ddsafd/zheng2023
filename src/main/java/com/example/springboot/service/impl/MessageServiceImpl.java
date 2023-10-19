package com.example.springboot.service.impl;

import com.example.springboot.entity.Message;
import com.example.springboot.mapper.MessageMapper;
import com.example.springboot.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements IMessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Override
    public List<Message> getAllMessages() {
        return messageMapper.getAllMessages();
    }

    @Override
    public Message getMessageById(Long id) {
        // Implement this method based on your requirements
        return null;
    }

    @Override
    public void addMessage(Message message) {
        messageMapper.addMessage(message);
    }

    @Override
    public void updateMessage(Message message) {
        messageMapper.updateMessage(message);
    }

    @Override
    public void deleteMessage(Long id) {
        messageMapper.deleteMessage(id);
    }
}
