package com.example.springboot.controller;

import com.example.springboot.entity.Message;
import com.example.springboot.service.IMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private IMessageService messageService;

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllMessages() {
        try {
            List<Message> messages = messageService.getAllMessages();
            return ResponseEntity.ok(messages);
        } catch (Exception e) {
            logger.error("Error fetching all messages", e);
            return ResponseEntity.status(500).body("Error fetching all messages");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMessageById(@PathVariable Long id) {
        try {
            Message message = messageService.getMessageById(id);
            if (message != null) {
                return ResponseEntity.ok(message);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error fetching message with ID: " + id, e);
            return ResponseEntity.status(500).body("Error fetching message with ID: " + id);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addMessage(@RequestBody Message message) {
        try {
            messageService.addMessage(message);
            return ResponseEntity.ok("Message added successfully");
        } catch (Exception e) {
            logger.error("Error adding message", e);
            return ResponseEntity.status(500).body("Error adding message");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateMessage(@RequestBody Message message) {
        try {
            messageService.updateMessage(message);
            return ResponseEntity.ok("Message updated successfully");
        } catch (Exception e) {
            logger.error("Error updating message", e);
            return ResponseEntity.status(500).body("Error updating message");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMessage(@PathVariable Long id) {
        try {
            messageService.deleteMessage(id);
            return ResponseEntity.ok("Message deleted successfully");
        } catch (Exception e) {
            logger.error("Error deleting message with ID: " + id, e);
            return ResponseEntity.status(500).body("Error deleting message with ID: " + id);
        }
    }
}
