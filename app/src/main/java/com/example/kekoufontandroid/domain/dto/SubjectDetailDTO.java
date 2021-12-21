package com.example.kekoufontandroid.domain.dto;

import com.example.kekoufontandroid.domain.Record;

import java.util.List;

import lombok.Data;

@Data
public class SubjectDetailDTO {
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

    List<Record> recordList;


}
