###总思想：先创建好Spring，然后用Spring整合SpringMVC，然后用Spring整合MyBatis，最终实现SSM的整合

#####1、配置pom.xml，到入相关的jar包
        特别的点：
            <!--下面的配置是为了应对Cause: java.io.IOException: Could not find resource https://blog.csdn.net/wt_better/article/details/90261220-->
            <!--https://blog.csdn.net/wt_better/article/details/90261220?depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromBaidu-1&utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromBaidu-1-->
            <resources>
                <resource>
                    <directory>src/main/java</directory>
                    <includes>
                        <include>**/*.xml</include>
                    </includes>
                </resource>
            </resources>


#####2、创建好web项目，然后把Java、resources文件放入main文件夹下，并右键mark好，java-Source Boot，resources-resources boot

##<一、创建并测试spring>
#####3、右键 -> new -> xml configuration file -> spring config，以创建spring的配置文件 -> applicationContext.xml
#####4、创建com/zedh/po  /controller(表现层 @Controller)  /service(业务层 @Service)  /dao(持久层 @Resposity)
#####5、创建JavaBean对象，即创建po/Course.java；创建Service/CourseService.java(Interface)，创建Service/CourseServiceImpl/CourseServiceImpl.java(启用@Service(value="CourseService"))
#####6、配置applicationContext.xml:开启注解扫描，并且不扫描@Controller，以区分业务层和表现层，因为Spring是业务层，SpringMVC才用于表现层
    //code:
    <context:component-scan base-package="com.zedh">
        <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
    </context:component-scan>
#####7、创建com/zedh/test/testSpring.java
    //code:
            public void test(){
                    ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
                    CourseService courseService = (CourseService) ac.getBean("CourseService");
                    System.out.println("开始执行findAll!");
                    courseService.findAll();
                }
   开始单元测试

##<二、Spring整合SpringMVC框架>
#####8、创建springmvc.xml
    配置4个
    a.开启注解扫描，除@Controller外的都不扫描
        <context:component-scan base-package="com.zedh">
            <context:include-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
        </context:component-scan>
    b.配置视图解析器
        <bean id="viewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
            <property name="prefix" value="/" />
            <property name="suffix" value=".jsp"/>
        </bean>
    c.过滤静态资源
        <mvc:resources mapping="/css/**" location="/css/"/>
        <mvc:resources mapping="/images/**" location="/images/"/>
        <mvc:resources mapping="/js/**" location="/js/"/>
    d.开启springmvc注解
        <mvc:annotation-driven/>

#####9、配置web.xml

    a.配置servlet和servlet-mapping标签
       <servlet>
        <servlet-name>springmvc</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
          <param-name>contextConfigLocation</param-name>
          <param-value>WEB-INF/classes/springmvc.xml</param-value>
        </init-param>
      </servlet>
      <servlet-mapping>
        <servlet-name>springmvc</servlet-name>
        <url-pattern>*.do</url-pattern>
      </servlet-mapping>

    b.开启监听:spring的监听器，默认只加载/WEB-INF/classes/applicationContext.xml的配置文件
        <listener>
            <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
        </listener>
        !!!由于监听器默认加载的路径是/WEB-INF，把配置文件放到这里不便于管理配置文件，因此，可以设置配置文件的路径
        <context-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:applicationContext.xml</param-value>
        </context-param>

    c.开启编码的过滤器，以解决中文乱码问题
        <filter>
            <filter-name>characterEncodingFilter</filter-name>
            <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
            <init-param>
                <param-name>encoding</param-name>
                <param-value>UTF-8</param-value>
            </init-param>
        </filter>
        <filter-mapping>
            <filter-name>characterEncodingFilter</filter-name>
            <url-pattern>*.do</url-pattern>
        </filter-mapping>

#####10、新建一个index.jsp，然后写一个测试链接
        <a href="findAll.do">findAll.do</a>

#####11、响应链接:总过程index.jsp  ->  Controller(CourseController)  ->  Service(CourseServiceImpl)  ->  Dao(CourseDao)  ->  Service  ->  Controller  ->  list.jsp
    a.新建controller/CourseController.java(@Controller)
        @Controller
        public class CourseController {
            /*自动注入*/
            @Autowired
            private CourseService courseService;
            @RequestMapping(value = "/findAll.do")
            public String findAll(Model model){
                System.out.println("表现层：findAll");
                courseService.findAll();
                return "list";
            }
        }
    b.在CourseServiceImpl.java，自动注入CourseDao
            @Service(value = "CourseService")
            public class CourseServiceImpl implements CourseService {
                @Autowired
                private CourseDao courseDao;
                @Override
                public List<Course> findAll() {
                    System.out.println("业务层：findAll");
                    return null;
                }
            }
    c.在CourseDao
            @Repository
            public interface CourseDao {
                @Select("select * from course")
                public List<Course> findAll();
            }
#####12、Edit Configuration增加Tomcat
    a.开始测试
    b.打印：{
        表现层：findAll
        业务层：findAll
        }
      即表示整合成功

由于现在还没有添加数据库，只是在表现层和业务层进行整合，所以还没有读取数据库的数据，用sout()来展示逻辑则更加容易

##<三、创建MyBatis>
#####13、创建mybatis-config.xml，并配置好
    <?xml version="1.0" encoding="utf-8" ?>
    <!DOCTYPE configuration
            PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
            "http://mybatis.org/dtd/mybatis-3-config.dtd">
    <configuration>
        <typeAliases>
            <package name="com.zedh.po"/>
        </typeAliases>
        <environments default="mysql">
            <environment id="mysql">
                <transactionManager type="JDBC"></transactionManager>
                <dataSource type="POOLED">
                    <property name="driver" value="com.mysql.jdbc.Driver"/>
                    <property name="url" value="jdbc:mysql://localhost:3306/ssm"/>
                    <property name="username" value="root"/>
                    <property name="password" value="123"/>
                </dataSource>
            </environment>
        </environments>

        <mappers>
            <mapper resource="com/zedh/mapper/CourseMapper.xml"/>
        </mappers>

    </configuration>

#####14、创建mapper/CourseMapper.xml
    <?xml version="1.0" encoding="utf-8" ?>
    <!DOCTYPE mapper
            PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
            "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
    <mapper namespace="com.zedh.dao.CourseDao">
        <select id="findAll" resultType="Course">
            select * from course
        </select>
    </mapper>

#####15、开始测试MyBatis
    public class testMybatis {
        private SqlSession sqlSession;
        @Before
        public void testBefore() throws IOException {
            InputStream ins = Resources.getResourceAsStream("mybatis-config.xml");
            /*
            SqlSessionFactoryBuilder ssfb  = new SqlSessionFactoryBuilder();
            SqlSessionFactory factory = ssfb.build(ins);
            此操作可以简化为一步
            */
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

    打印成功则表示MyBatis测试成功

##<四、Spring整合MyBatis>
#####16、配置applicationContext.xml
       A】spring整合mybatis框架
       a1引入数据源  BasicDataSource
       a2配置sqlsessionfactory    SqlSessionFactoryBean
       a3配置dao所在的包  MapperScannerConfigurer


       B】配置spring框架声明式事务管理
       b1配置事务管理器
       b2配置事务通知
       b3配置aop增强

    <!--spring整合mybatis框架-->
    <!--1引入数据源-->
    <bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource">
        <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
        <property name="url" value="jdbc:mysql://localhost:3306/ssm"/>
        <property name="username" value="root"/>
        <property name="password" value="123"/>
    </bean>
    <!--2配置sqlsessionfactory-->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource"/>
    </bean>
    <!--3配置dao所在的b包-->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="basePackage" value="com.zedh.dao"/>
    </bean>


    <!--配置spring框架声明式事务管理-->
    <!--1配置事务管理器-->
    <bean id="dataSourceTransactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"/>
    </bean>
    <!--2配置事务通知-->
    <!--解析tx:advice  https://blog.csdn.net/rong_wz/article/details/53787648?depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1&utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1-->
    <tx:advice id="myAdvice" transaction-manager="dataSourceTransactionManager">
        <tx:attributes>
            <!--find开头的事务开启只读，这种执行效率会比read-write的Connection高-->
            <tx:method name="find*" read-only="true"/>
            <!--除了find开头的事务隔离   DEFAULT：采用数据库默认隔离级别-->
            <tx:method name="*" isolation="DEFAULT"/>
        </tx:attributes>
    </tx:advice>
    <!--3配置aop增强-->
    <aop:config>
        <aop:advisor advice-ref="myAdvice" pointcut="execution(* com.zedh.service.CourseServiceImpl.*(..))"></aop:advisor>
    </aop:config>

#####17、测试（这一步的测试继续在依靠CourseDao有@Select("select * from course")的前提下）
    在Tomcat上进行测试
    能跳转到list并打印所有内容则表示初步整合成功

#####18、整合优化：目的是把和数据库进行交互的过程交给*mapper.xml文件来做
    a.把CourseMapper.xml放到Resources/mapper下
    b.更新applicationContext.xml/bean-sqlsessionfactorybean的配置
        <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
            <property name="dataSource" ref="dataSource"/>
            <!--新增！！！！！！！整合优化时添加，classpath即部署到tomcat之后的resource-->
            <property name="configLocation" value="classpath:mybatis-config.xml"/>
            <property name="mapperLocations" value="classpath:mapper/*.xml"/>
        </bean>
    c.更新applicationContext.xml/bean-MapperScannerConfigurer的配置
            <bean id="mapperscanner" class="org.mybatis.spring.mapper.MapperScannerConfigurer">
                <!--整合优化时添加-->
                <!--!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!-->
                <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"></property>
                <property name="basePackage" value="com.zedh.dao"/>
            </bean>
    d.测试前，需要把mybatis-config.xml中数据源和mappers标签注解掉，以免和applicationContext.xml一起让单元测试失效
    e.创建testSpringMyBatis.java测试
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
        }

        能够正常打印代表整合的优化成功


#####19、在Tomcat上进行测试，点击findall.do，跳转到list并打印所有信息则表示ssm整合成功2/3



####接下来，则测试Insert操作，能成功插入，就表示几乎完成了ssm的整合
#####20、resources/mapper/CourseMapper.xml的配置
         <!--insert的返回值为0/1-->
         <insert id="Insert">
             insert into course (cname,clocal) values (#{cname},#{clocal})
         </insert>

#####21、在CourseDao中新增public int Insert(Course course)方法
    public int Insert(Course course);
#####22、在CourseService中新增方法
    public int Insert(Course course);
    然后在CourseServiceImpl中新增方法
    public int Insert(Course course) {
            System.out.println("业务层：Insert");
            return courseDao.Insert(course);
    }
#####23、在testSpringMybatis中测试Insert
    @Test
    public void insert(){
        Course course = new Course(0,"课程名","位置名");
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        CourseDao courseDao = ac.getBean(CourseDao.class);
        int insert = courseDao.Insert(course);
        System.out.println(insert);
    }
    打印测试结果为“1”则表示 插入成功
#####24、在index.jsp中加入表单，用以向CourseController传值
    <form action="Insert.do" method="post">
        <tr>
            <td>课程名</td>
            <td><input type="text" name="cname"></td>
        </tr>
        <tr>
            <td>课程位置</td>
            <td><input type="text" name="clocal"></td>
        </tr>
        <tr>
            <td><button type="submit" value="添加">添加</button></td>
        </tr>
    </form>
#####25、在CourseController中加入Insert.do的@RequestingMapping(value = "/Insert.do")
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

        此处用了转发的方法，response.sendRedirect(request.getContextPath()+"/findAll.do")，转发到findAll.do，查询数据库后，通过model.addAttribute("list",list)，向list.jsp传输数据

#####26、把项目部署到Tomcat中进行测试，打印出完整结果，就表示ssm的整合成功了！！！