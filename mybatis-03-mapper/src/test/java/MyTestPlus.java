import com.xiuwei.POJO.Department;
import com.xiuwei.POJO.Employee;
import com.xiuwei.dao.DepartmentMapper;
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
import java.util.List;
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

    //第32讲：关联查询：查询员工，同时查关联的部门表。使用association 实现分步查询。
    @Test
    public void testGetEmpByIdStep() throws IOException {
        SqlSession session = getSession();
        try {
            EmployeeMapperPlus mapper = session.getMapper(EmployeeMapperPlus.class);
            Employee employee = mapper.getEmpByIdStep(1);
            System.out.println(employee);   //Employee{id=1, lastName='tom', gender='0', email='null', department=Department{id=1, departmentName='RD'}}
        }finally {
            session.close();
        }
    }

    //第33讲：关联查询，分部查询：延迟加载。
    @Test
    public void testGetEmpByIdStepLazyLoad() throws IOException {
        SqlSession session = getSession();
        try {
            EmployeeMapperPlus mapper = session.getMapper(EmployeeMapperPlus.class);
            Employee employee = mapper.getEmpByIdStep(1);
            System.out.println(employee.getId());   //打印1。 执行这部时，不需要加载dept，debug信息里应该只执行一条sql:select * from tbl_employee where id=?
            System.out.println(employee.getDepartment());   //打印dept对象。 执行这部时，会延迟加载dept, debug看到第二条SQL：select id, dept_name departmentName from tbl_dept where id=?
        }finally {
            session.close();
        }
    }

    //第34讲：关联查询，collection定义关联集合的封装 - 查部门时，把全部员工也查出来(嵌套查询方式)
    @Test
    public void testGetDeptByIdWithEmployees() throws IOException {
        SqlSession session = getSession();
        try {
            DepartmentMapper mapper = session.getMapper(DepartmentMapper.class);
            Department department = mapper.getDeptByIdWithEmployees(1);
            System.out.println(department);             //Department{id=1, departmentName='RD'}
            System.out.println(department.getEmps());   //[Employee{id=1, lastName='tom', gender='0', email='tom@126.com', department=null}, Employee{id=5, lastName='jerry', gender='1', email='jerry@qq.com', department=null}]
        }finally {
            session.close();
        }
    }

    @Test
    public void testGetEmpsByDeptId() throws IOException {
        SqlSession session = getSession();
        try {
            EmployeeMapperPlus mapper = session.getMapper(EmployeeMapperPlus.class);
            List<Employee> emps = mapper.getEmpsByDeptId(1);
            System.out.println(emps);
        }finally {
            session.close();
        }
    }

    //第35讲：关联查询，collection定义关联集合的封装 - 查部门时，把全部员工也查出来(分步查询&延迟加载方式)
    @Test
    public void testGetDeptByIdWithEmployeesStep() throws IOException {
        SqlSession session = getSession();
        try {
            DepartmentMapper mapper = session.getMapper(DepartmentMapper.class);
            Department department = mapper.getDeptByIdWithEmployeesStep(1);
            System.out.println(department.getDepartmentName());      //RD
            //如果开启了懒加载，如果注释调下一行，上一行会只执行一个SQL。
            System.out.println(department.getEmps());   //[Employee{id=1, lastName='tom', gender='0', email='tom@126.com', department=null}, Employee{id=5, lastName='jerry', gender='1', email='jerry@qq.com', department=null}]
        }finally {
            session.close();
        }
    }

    //第37讲：discriminator：鉴别器
    @Test
    public void testetEmpByIdTestDiscriminator() throws IOException {
        SqlSession session = getSession();
        try {
            EmployeeMapperPlus mapper = session.getMapper(EmployeeMapperPlus.class);
            Employee employee = mapper.getEmpByIdTestDiscriminator(1);
            //女生，结果中包含部门信息
            System.out.println(employee);   //Employee{id=1, lastName='tom', gender='0', email='tom@126.com', department=Department{id=1, departmentName='RD'}}
            //男生，结果中不包含部门信息，并且email=last_name值。
            employee = mapper.getEmpByIdTestDiscriminator(3);   //Employee{id=3, lastName='jerry_update3', gender='1', email='jerry_update3', department=null}
            System.out.println(employee);

        }finally {
            session.close();
        }
    }
}
