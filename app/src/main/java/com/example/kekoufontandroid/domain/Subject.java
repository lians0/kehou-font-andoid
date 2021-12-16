package com.example.kekoufontandroid.domain;

import java.util.Date;

import lombok.Data;

@Data
public class Subject  {
    /**
     * 科目id
     */
    private Integer subjectId;

    /**
     * 科目名称
     */
    private String subjectName;

    /**
     * 图标
     */
    private String icon;

    /**
     * 科目描述
     */
    private String subjectDesc;

    /**
     * 类别
     */
    private String category;

    /**
     * 创建者id
     */
    private Integer creatorId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}