package com.example.kekoufontandroid.domain.dto;

import lombok.Data;

/**
 * @author ShuangLian
 * @date 2022/1/17 16:47
 */
@Data
public class CourseSearchDTO {
    /**
     * 课程Id
     */
    private Integer courseId;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 所属学科名称
     */
    private String subjectName;
}
