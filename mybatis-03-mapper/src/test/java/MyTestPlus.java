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


}
