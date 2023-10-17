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
 * 竞赛收藏表
 * </p>
 *
 * @author
 * @since
 */
@Getter
@Setter
public class Collect implements Serializable {

    private static final long serialVersionUID = 1L;

    @Alias("ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Alias("文章")
    private Integer articleId;

    @Alias("用户")
    private Integer userId;

    @Alias("添加时间")
    private String time;

    @TableField(exist = false)
    private String userName;

    @TableField(exist = false)
    private String articleName;

    @TableField(exist = false)
    private Article article;

}
