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
public class Type implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Alias("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 试题类别名称
     */
    @Alias("试题类别名称")
    private String name;

    /**
     * 描述
     */
    @Alias("描述")
    private String content;

    /**
     * 封面
     */
    @Alias("封面")
    private String img;


}
