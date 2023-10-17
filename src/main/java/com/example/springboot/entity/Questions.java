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
public class Questions implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Alias("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 试题名称
     */
    @Alias("试题名称")
    private String name;

    /**
     * 描述
     */
    @Alias("描述")
    private String content;

    /**
     * 答案
     */
    @Alias("答案")
    private String answer;

    /**
     * 选项A
     */
    @Alias("选项A")
    private String choiceA;

    /**
     * 选项B
     */
    @Alias("选项B")
    private String choiceB;

    /**
     * 选项C
     */
    @Alias("选项C")
    private String choiceC;

    /**
     * 选项D
     */
    @Alias("选项D")
    private String choiceD;

    /**
     * 解析
     */
    @Alias("解析")
    private String explaination;

    /**
     * 分值
     */
    @Alias("分值")
    private String scores;

    /**
     * 种类
     */
    @Alias("种类")
    private String type;


}
