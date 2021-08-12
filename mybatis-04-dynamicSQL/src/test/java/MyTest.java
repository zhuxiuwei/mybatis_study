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
    //#39： 查询条件employee里，携带了哪个查询条件，返回结果就带上这个字段的值
    public void testGetEmpsByConditionIf() throws IOException {
        SqlSession session = getSession();
        try {
            EmployeeMapperDynamicSQL mapper = session.getMapper(EmployeeMapperDynamicSQL.class);
            List<Employee> employees = mapper.getEmpsByConditionIf(new Employee(null, "%to%", null, null));
            System.out.println(employees);  //[Employee{id=1, lastName='tom', gender='0', email='tom@126.com', department=null}]
            /**
             * 观察Log里真实执行的SQL：select * from tbl_employee where id=? and last_name like ?
             * 因为email和gender都是null, sql里就没包含这些字段。
            */
        }finally {
            session.close();
        }
    }

    @Test
    //#40： 用if的同时，用where标签，解决where 后面and/or拼接可能存在的问题。
    public void testGetEmpsByConditionIfUsingWhere() throws IOException {
        SqlSession session = getSession();
        try {
            EmployeeMapperDynamicSQL mapper = session.getMapper(EmployeeMapperDynamicSQL.class);
            List<Employee> employees = mapper.getEmpsByConditionIfUsingWhere(new Employee(null, "%to%", null, null));
            System.out.println(employees);  //[Employee{id=1, lastName='tom', gender='0', email='tom@126.com', department=null}]
            /**
             * 观察Log里真实执行的SQL，是语法正确的：select * from tbl_employee WHERE last_name like ?
             * 即使第一个参数(id)为空，也没有错误拼接成 select * from tbl_employee WHERE and last_name like ?
             */
        }finally {
            session.close();
        }
    }
}
