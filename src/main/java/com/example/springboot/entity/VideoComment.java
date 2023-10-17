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
@TableName("video_comment")
public class VideoComment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Alias("ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 评论内容
     */
    @Alias("评论内容")
    private String content;

    /**
     * 用户
     */
    @Alias("用户")
    private Integer userId;

    /**
     * 视频
     */
    @Alias("视频")
    private Integer videoId;

    /**
     * 父级评论ID
     */
    @Alias("父级评论ID")
    private Integer pid;

    /**
     * 评论时间
     */
    @Alias("评论时间")
    private String time;


    @TableField(exist = false)
    private List<VideoComment> children;

    @TableField(exist = false)
    private String img;

    @TableField(exist = false)
    private String username;

    @TableField(exist = false)
    private String videoName;

    @TableField(exist = false)
    private String parentName;

}
