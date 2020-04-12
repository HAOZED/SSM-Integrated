package com.zedh.test;

import com.zedh.dao.CourseDao;
import com.zedh.po.Course;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class testMybatis {
    private SqlSession sqlSession;
    @Before
    public void testBefore() throws IOException {
        InputStream ins = Resources.getResourceAsStream("mybatis-config.xml");
        /*SqlSessionFactoryBuilder ssfb  = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = ssfb.build(ins);*/
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(ins);
        sqlSession = factory.openSession();
    }
    @Test
    public void test(){
        CourseDao mapper = sqlSession.getMapper(CourseDao.class);
        List<Course> list = mapper.findAll();
        for(Course course:list){
            System.out.println(course);
        }
    }
}
