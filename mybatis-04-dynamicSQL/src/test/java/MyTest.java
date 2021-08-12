import com.xiuwei.POJO.Employee;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import com.xiuwei.dao.EmployeeMapperDynamicSQL;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MyTest {

    private SqlSession getSession() throws IOException {
        String resources = "mybatis-config.xml";
        InputStream in = Resources.getResourceAsStream(resources);
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(in);
        return sessionFactory.openSession();
    }

    @Test
    public void testGetEmpsByConditionIf() throws IOException {
        SqlSession session = getSession();
        try {
            EmployeeMapperDynamicSQL mapper = session.getMapper(EmployeeMapperDynamicSQL.class);
            List<Employee> employees = mapper.getEmpsByConditionIf(new Employee(1, "%to%", null, null));
            System.out.println(employees);  //[Employee{id=1, lastName='tom', gender='0', email='tom@126.com', department=null}]
            /**
             * 观察Log里的SQL：select * from tbl_employee where id=? and last_name like ?
             * 因为email和gender都是null, sql里就没包含这些字段。
            */
        }finally {
            session.close();
        }
    }
}
