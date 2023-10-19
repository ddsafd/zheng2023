package com.example.springboot.mapper;

import com.example.springboot.entity.Message;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MessageMapper {

    @Select("SELECT * FROM message")
    List<Message> getAllMessages();

    @Insert("INSERT INTO message(userId, content) VALUES(#{userId}, #{content})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void addMessage(Message message);

    @Update("UPDATE message SET content = #{content}, isAnswered = #{isAnswered}, answer = #{answer}, answerUserId = #{answerUserId} WHERE id = #{id}")
    void updateMessage(Message message);

    @Delete("DELETE FROM message WHERE id = #{id}")
    void deleteMessage(Long id);
}

