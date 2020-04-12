package com.zedh.controller;

import com.zedh.po.Course;
import com.zedh.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class CourseController {
    @Autowired
    private CourseService courseService;

    @RequestMapping(value = "/findAll.do")
    public String findAll(Model model){
        System.out.println("表现层：findAll");
        List<Course> courseList = courseService.findAll();
        model.addAttribute("list",courseList);
        return "list";
    }


    @RequestMapping(value = "/Insert.do")
    public void Insert(Course course, HttpServletResponse response, HttpServletRequest request) throws IOException {
        System.out.println("表现层：insert");
        int insert = courseService.Insert(course);
        System.out.println(insert);
        if (insert == 1){
            response.sendRedirect(request.getContextPath()+"/findAll.do");
        }else{
            System.out.println("插入失败!!");
        }
    }
}
