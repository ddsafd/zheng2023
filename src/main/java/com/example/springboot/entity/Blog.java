package com.example.springboot.entity;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.IdType;
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
public class Blog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Alias("ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Alias("标题")
    private String name;

    @Alias("内容")
    private String content;

    @Alias("发布人")
    private String user;

    @Alias("发布时间")
    private String time;

    @Alias("封面")
    private String img;

    @Alias("类别")
    private String blogType;

    private Integer pageviews;

    private Integer userId;

}
