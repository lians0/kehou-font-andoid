package com.example.kekoufontandroid.domain.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author ShuangLian
 * @date 2022/1/13 16:55
 */
@Data
public class CourseAndIsJoinVO {
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
    /**
     * 是否参与
     */
    private Boolean isJoin;
}
