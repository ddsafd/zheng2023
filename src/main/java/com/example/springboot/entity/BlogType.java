package com.example.springboot.entity;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("blog_type")
public class BlogType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Alias("ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Alias("类别")
    private String name;

    @Alias("描述")
    private String content;


}
