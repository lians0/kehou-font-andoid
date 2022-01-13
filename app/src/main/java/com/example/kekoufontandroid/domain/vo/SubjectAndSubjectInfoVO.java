package com.example.kekoufontandroid.domain.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author ShuangLian
 * @date 2021/12/20 19:28
 */
@Data
public class SubjectAndSubjectInfoVO {

    private String teacher;
    private String subjectName;
    private String subjectDesc;
    private String subjectIcon;

    /**
     * 参与人总数
     */
    private int joinTotal;
    /**
     * 该科目的总课程数
     */
    private int courseTotal;
    /**
     * 当前用具是否已参加课程
     */
    private boolean isJoin;
    /**
     * 当前课程的所有课程
     */
    private List<CourseVO> courseList;

    @Data
    public static class CourseVO {

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

        private boolean isJoin;

    }
}
