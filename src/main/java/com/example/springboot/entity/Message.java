package com.example.springboot.entity;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@TableName("message")
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @Alias("ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Alias("用户ID")
    private Integer userId;

    @Alias("留言内容")
    private String content;

    @Alias("是否回答")
    private Boolean isAnswered;

    @Alias("回答内容")
    private String answer;

    @TableField(exist = false)
    private Long collectCount;
}
