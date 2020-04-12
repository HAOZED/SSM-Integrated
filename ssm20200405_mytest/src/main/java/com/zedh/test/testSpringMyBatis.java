package com.zedh.test;

import com.zedh.dao.CourseDao;
import com.zedh.po.Course;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class testSpringMyBatis {
    @Test
    public void test(){
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        CourseDao courseDao = ac.getBean(CourseDao.class);
        List<Course> list = courseDao.findAll();
        for(Course course:list){
            System.out.println(course);
        }
    }
    @Test
    public void insert(){
        Course course = new Course(0,"课程名","位置名");
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        CourseDao courseDao = ac.getBean(CourseDao.class);
        int insert = courseDao.Insert(course);
        System.out.println(insert);
    }

}
