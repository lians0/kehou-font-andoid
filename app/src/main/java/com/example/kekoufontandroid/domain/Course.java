package com.example.kekoufontandroid.domain;

import java.util.Date;

import lombok.Data;

@Data
public class Course {
    /**
     * 课程id
     */
    private Integer courseId;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 所属学科
     */
    private Integer subjectId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}