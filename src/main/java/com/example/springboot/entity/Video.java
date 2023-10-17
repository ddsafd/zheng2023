package com.example.springboot.entity;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since
 */
@Getter
@Setter
public class Video implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Alias("ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 标题
     */
    @Alias("标题")
    private String name;

    /**
     * 内容
     */
    @Alias("内容")
    private String content;

    /**
     * 发布人
     */
    @Alias("发布人")
    private String user;

    /**
     * 发布时间
     */
    @Alias("发布时间")
    private String time;

    /**
     * 封面
     */
    @Alias("封面")
    private String img;

    /**
     * 视频
     */
    @Alias("视频")
    private String file;


    @TableField(exist = false)
    private Long collectCount;
}
