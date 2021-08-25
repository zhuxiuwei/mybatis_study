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
        }finally {
            session.close();
        }
     }


}
