package com.zedh.service.CourseServiceImpl;

import com.zedh.dao.CourseDao;
import com.zedh.po.Course;
import com.zedh.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "CourseService")
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseDao courseDao;

    @Override
    public List<Course> findAll() {
        System.out.println("业务层：findAll");
        return courseDao.findAll();
    }

    @Override
    public int Insert(Course course) {
        System.out.println("业务层：Insert");
        return courseDao.Insert(course);
    }


}
