package com.example.kekoufontandroid.domain;

import java.util.Date;

import lombok.Data;

/**
 * 上课记录表
 * @TableName record
 */
@Data
public class Record {
    /**
     * 记录id
     */
    private Integer recordId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 学科id
     */
    private Integer subjectId;

    /**
     * 记录描述
     */
    private String recordDesc;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}