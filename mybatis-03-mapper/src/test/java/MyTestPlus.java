import com.xiuwei.POJO.Employee;
import com.xiuwei.dao.EmployeeMapper;
import com.xiuwei.dao.EmployeeMapperPlus;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class MyTestPlus {

    private SqlSession getSession() throws IOException {
        String resources = "mybatis-config.xml";
        InputStream in = Resources.getResourceAsStream(resources);
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(in);
        return sessionFactory.openSession();
    }


    //传参 - param测试
    @Test
    public void testResultMap() throws IOException {
        SqlSession session = getSession();
        try {
            EmployeeMapperPlus mapper = session.getMapper(EmployeeMapperPlus.class);
            Employee employee = mapper.getEmpById(1);
            System.out.println(employee);   //Employee{id=1, lastName='tom', gender='0', email='tom@126.com'}
        }finally {
            session.close();
        }
    }

    //第30讲：关联查询：查询员工，同时查关联的部门表。使用级联属性封装结果。
    @Test
    public void testGetEmpAndDept() throws IOException {
        SqlSession session = getSession();
        try {
            EmployeeMapperPlus mapper = session.getMapper(EmployeeMapperPlus.class);
            Employee employee = mapper.getEmpAndDept(1);
            System.out.println(employee);   //Employee{id=1, lastName='tom', gender='0', email='null', department=Department{id=1, departmentName='RD'}}
        }finally {
            session.close();
        }
    }

    //第31讲：关联查询：查询员工，同时查关联的部门表。使用association封装关联对象。
    @Test
    public void testGetEmpAndDept2() throws IOException {
        SqlSession session = getSession();
        try {
            EmployeeMapperPlus mapper = session.getMapper(EmployeeMapperPlus.class);
            Employee employee = mapper.getEmpAndDept2(1);
            System.out.println(employee);   //Employee{id=1, lastName='tom', gender='0', email='null', department=Department{id=1, departmentName='RD'}}
        }finally {
            session.close();
        }
    }
}
