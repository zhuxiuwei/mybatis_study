import com.xiuwei.POJO.Employee;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import com.xiuwei.dao.EmployeeMapperDynamicSQL;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
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
        }finally {
            session.close();
        }
    }

    @Test
    //#42：choose测试:如果有id就用ID查，如果有lastName就用lastName查.....二者只用一个
    public void testGetEmpsByConditionChoose() throws IOException {
        SqlSession session = getSession();
        try {
            EmployeeMapperDynamicSQL mapper = session.getMapper(EmployeeMapperDynamicSQL.class);
            List<Employee> employees = mapper.getEmpsByConditionChoose(new Employee(null, "%to%", null, null));
            System.out.println(employees);  //[Employee{id=1, lastName='tom', gender='0', email='tom@126.com', department=null}]

            //传了2个条件，只用id查
            employees = mapper.getEmpsByConditionChoose(new Employee(3, "%to%", null, null));
            System.out.println(employees);  //[Employee{id=3, lastName='jerry_update3', gender='1', email='jerry_update@qq.com', department=null}]

            //不传条件，全查
            employees = mapper.getEmpsByConditionChoose(new Employee(null, null, null, null));
            System.out.println(employees);  //返回全部
        }finally {
            session.close();
        }
    }

    @Test
    //#43：set测试: set与if结合的动态更新 - 传了哪列，就更新哪列，其他列不更新。(之前「mybatis-03-mapper」的'updateEmp'是全字段更新
    public void testUpdateEmp() throws IOException {
        SqlSession session = getSession();
        try {
            EmployeeMapperDynamicSQL mapper = session.getMapper(EmployeeMapperDynamicSQL.class);
            //效果：只更新lastName。 email、gender都不更新。 而且sql不会拼错。
            mapper.updateEmp(new Employee(6, "jerry222222", null, null));
            session.commit();
        }finally {
            session.close();
        }
    }

    @Test
    //#44: foreach循环遍历集合，拼SQL
    public void testGetEmpsByConditionForeach() throws IOException {
        SqlSession session = getSession();
        try {
            EmployeeMapperDynamicSQL mapper = session.getMapper(EmployeeMapperDynamicSQL.class);
            List<Employee> employees = mapper.getEmpsByConditionForeach(Arrays.asList(1, 2, 3, 4)); //效果：根据传入的list，拼SQL
            //观察sql是正确的： select * from tbl_employee where id in( ? , ? , ? , ? ) ==> Parameters: 1(Integer), 2(Integer), 3(Integer), 4(Integer)
            System.out.println(employees);  //[Employee{id=1, lastName='tom', gender='0', email='tom@126.com', department=null}, Employee{id=3, lastName='jerry_update3', gender='1', email='jerry_update@qq.com', department=null}]
        }finally {
            session.close();
        }
    }
}