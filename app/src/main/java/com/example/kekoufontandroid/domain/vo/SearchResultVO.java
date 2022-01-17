package com.example.kekoufontandroid.domain.vo;

import com.example.kekoufontandroid.domain.dto.CourseSearchDTO;
import com.example.kekoufontandroid.domain.dto.SubjectSearchDTO;

import java.util.List;

import lombok.Data;

/**
 * @author ShuangLian
 * @date 2022/1/17 16:43
 */
@Data
public class SearchResultVO {
    List<CourseSearchDTO> courseList;
    List<SubjectSearchDTO> subjectList;

}
