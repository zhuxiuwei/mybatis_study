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
    public void testCRUD() throws IOException {
        //1. 获取的session，没有自动提交
        SqlSession session = getSession();
        try {
            EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);

            //测试查询
            Employee employee = mapper.getEmpById(1);
            System.out.println(employee);   //Employee{id=1, lastName='tom', gender='0', email='tom@126.com'}

            //测试添加
//            Employee employee1 = new Employee(null, "jerry", "1", "jerry@qq.com");  //自增id，所以id设置Null
//            mapper.addEmp(employee1);

            //测试修改
            Employee employee1 = new Employee(3, "jerry_update3", "1", "jerry_update@qq.com");  //修改id=3的记录
            boolean succeed = mapper.updateEmp(employee1);
            System.out.println(succeed);

            //测试删除
//            mapper.deleteEmp(4);

            //2. 手动提交
            session.commit();

        }finally {
            session.close();
        }
    }
}
