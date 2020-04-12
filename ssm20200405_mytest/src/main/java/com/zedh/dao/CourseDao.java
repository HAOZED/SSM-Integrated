package com.zedh.dao;

import com.zedh.po.Course;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseDao {

    /*@Select("select * from course")*/
    public List<Course> findAll();
    public int Insert(Course course);

}
