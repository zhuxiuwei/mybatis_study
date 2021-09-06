import com.xiuwei.POJO.Employee;
import com.xiuwei.POJO.EmployeeExample;
import com.xiuwei.dao.EmployeeMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MyTest {

    private SqlSessionFactory getSessionFactory() throws IOException {
        String resources = "mybatis-config.xml";
        InputStream in = Resources.getResourceAsStream(resources);
        return new SqlSessionFactoryBuilder().build(in);
    }

    //测试简单版本增删改查
//    @Test
//    public void testSimpleCode() throws IOException {
//        SqlSessionFactory sessionFactory = getSessionFactory();
//        SqlSession session = sessionFactory.openSession();
//
//        try{
//            EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
//            List<Employee> emps = mapper.selectAll();
//            for(Employee employee: emps)
//                System.out.println(employee);
//        }finally {
//            session.close();
//        }
//    }

    //测试复杂版本增删改查
    @Test
    public void testMyBatis3Code() throws IOException {
        SqlSessionFactory sessionFactory = getSessionFactory();
        SqlSession session = sessionFactory.openSession();

        try{
            EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
            //xxxExample就是封装查询条件的
            //1. 查询所有
            List<Employee> employees = mapper.selectByExample(null);
            for(Employee employee: employees)
                System.out.println(employee);

            //2. 查询 （ 员工姓名有e字幕的 & 性别=1的） or 邮箱包含xx的
            //封装查询条件的example
            EmployeeExample example = new EmployeeExample();
            //创建Criteria，这个Criteria拼装查询条件
            EmployeeExample.Criteria criteria = example.createCriteria();
            criteria.andLastNameLike("%e%").andGenderEqualTo("1");
            //或条件的拼接
            EmployeeExample.Criteria criteria2 = example.createCriteria();
            criteria2.andEmailLike("%xx%");
            example.or(criteria2);

            List<Employee> employees1 = mapper.selectByExample(example);
            for(Employee employee: employees1)
                System.out.println(employee);

            //观察SQL:
//              ==> Preparing: select id, last_name, gender, email, d_id from tbl_employee WHERE ( last_name like ? and gender = ? ) or( email like ? )
//              ==> Parameters: %e%(String), 1(String), %xx%(String)
        }finally {
            session.close();
        }
    }
}
