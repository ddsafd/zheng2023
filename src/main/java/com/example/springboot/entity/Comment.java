package com.example.springboot.entity;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 评论表
 * </p>
 *
 * @author
 * @since
 */
@Getter
@Setter
@TableName("comment")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Alias("ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Alias("评论内容")
    private String content;

    @Alias("用户")
    private Integer userId;

    @Alias("文章")
    private Integer articleId;

    @Alias("父级评论ID")
    private Integer pid;

    @Alias("评论时间")
    private String time;

    @TableField(exist = false)
    private List<Comment> children;

    @TableField(exist = false)
    private String img;

    @TableField(exist = false)
    private String username;

    @TableField(exist = false)
    private String articleName;

    @TableField(exist = false)
    private String parentName;

}
