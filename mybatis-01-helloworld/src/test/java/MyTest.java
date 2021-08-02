import com.xiuwei.POJO.Employee;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class MyTest {

    /**
     * 1、根据xml配置文件（全局配置文件）创建一个SqlSessionFactory对象 有数据源一些运行环境信息
     * 2、sql映射文件；配置了每一个sql，以及sql的封装规则等。
     * 3、将sql映射文件注册在全局配置文件中
     * 4、写代码：
     * 		1）、根据全局配置文件得到SqlSessionFactory；
     * 		2）、使用sqlSession工厂，获取到sqlSession对象使用他来执行增删改查
     * 			一个sqlSession就是代表和数据库的一次会话，用完关闭
     * 		3）、使用sql的唯一标志来告诉MyBatis执行哪个sql。sql都是保存在sql映射文件中的。
     *
     * @throws IOException
     */
    @Test
    public void helloWorldTest() throws IOException {
        /**
         * 以下四行可封装为工具类
         * 1. 根据配置文件，创建一个SqlSessionFactory对象
         */
        String resources = "mybatis-config.xml";
        InputStream in = Resources.getResourceAsStream(resources);
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(in);
//        SqlSession session = sessionFactory.openSession(true);  //true: 自动提交事务

        //2. 用sqlSession,执行一个已经映射了的sql
        SqlSession session = sessionFactory.openSession();
        //参数1： sql的唯一标示,即mapper xml里的id。为了避免冲突，建议是namespace.id
        //参数2： 执行sql要用的参数
        try {
            Employee employee = session.selectOne("com.xiuwei.mybatis.EmployeeMapper.selectEmp", 1);
            System.out.println(employee);   //Employee{id=1, lastName='tom', gender='0', email='tom@126.com'}
        }finally {
            session.close();
        }

    }
}
