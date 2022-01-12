package com.example.kekoufontandroid.domain;

import java.io.Serializable;

import lombok.Data;

/**
 * 课程评价
 * @TableName comment_course
 */

@Data
public class CommentCourse implements Serializable {
    /**
     * 课程评价id
     */

    private Integer commentId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 学科id
     */
    private Integer subjectId;

    /**
     * 学期id
     */
    private Integer semesterId;

    /**
     * 考试形式
     */
    private String testType;

    /**
     * 课程评价
     */
    private String commentText;

}