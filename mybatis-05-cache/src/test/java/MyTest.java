import com.xiuwei.POJO.Employee;
import com.xiuwei.dao.EmployeeMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class MyTest {

    private SqlSession getSession() throws IOException {
        String resources = "mybatis-config.xml";
        InputStream in = Resources.getResourceAsStream(resources);
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(in);
        return sessionFactory.openSession();
    }

    //#52 体验一级缓存
    @Test
    public void test1stLevelCache() throws IOException {
        SqlSession session = getSession();

        try{
            EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
            Employee employee = mapper.getEmpById(1);
            System.out.println(employee);

            System.out.println("--------------------------");
            Employee employee2 = mapper.getEmpById(1);
            System.out.println(employee2);  //从后面的log看，sql一共只执行过一次，说明第二次查的缓存。
            System.out.println(employee == employee2);  //true。 两个对象相等。

            System.out.println("-------------测试一样的Sql，不同的select id 是否复用缓存-------------");
            Employee employee3 = mapper.getEmpById2(1); //sql和getEmpById一样
            System.out.println(employee3);  //从后面的log看，sql这里又执行了，说明 没有复用缓存。
            System.out.println(employee3 == employee);  //false
        }finally {
            session.close();
        }
    }


}
