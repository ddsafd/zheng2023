package com.example.springboot.entity;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 竞赛收藏表
 * </p>
 *
 * @author
 * @since
 */
@Getter
@Setter
@TableName("video_collect")
public class VideoCollect implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Alias("ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 文章
     */
    @Alias("文章")
    private Integer videoId;

    /**
     * 用户
     */
    @Alias("用户")
    private Integer userId;

    /**
     * 添加时间
     */
    @Alias("添加时间")
    private String time;

    @TableField(exist = false)
    private String userName;

    @TableField(exist = false)
    private String videoName;

    @TableField(exist = false)
    private Video video;

}
