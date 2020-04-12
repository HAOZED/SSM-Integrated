package com.zedh.service;

import com.zedh.po.Course;

import java.util.List;

public interface CourseService {
    public List<Course> findAll();
    public int Insert(Course course);
}
