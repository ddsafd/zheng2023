package com.example.springboot.controller;

import com.example.springboot.entity.Message;
import com.example.springboot.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/message")
@CrossOrigin(origins = "http://localhost:8080")  // 允许来自8080端口的跨域请求
public class MessageController {

    @Autowired
    private IMessageService messageService;

    @GetMapping("/getAll")
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }

    @GetMapping("/{id}")
    public Message getMessageById(@PathVariable Long id) {
        return messageService.getMessageById(id);
    }

    @PostMapping("/add")
    public void addMessage(@RequestBody Message message) {
        messageService.addMessage(message);
    }

    @PutMapping("/update")
    public void updateMessage(@RequestBody Message message) {
        messageService.updateMessage(message);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);
    }
}
