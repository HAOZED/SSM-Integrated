package com.zedh.test;

import com.zedh.service.CourseService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class testSpring {
    @Test
    public void test(){
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        CourseService courseService = (CourseService) ac.getBean("CourseService");
        System.out.println("开始执行findAll!");
        courseService.findAll();
    }

}
