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

    private SqlSessionFactory getSessionFactory() throws IOException {
        String resources = "mybatis-config.xml";
        InputStream in = Resources.getResourceAsStream(resources);
        return new SqlSessionFactoryBuilder().build(in);
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

    //55 测试二级缓存
    @Test
    public void test2ndLevelCache() throws IOException {
        /**
         * 注意必须从同一个SqlSessionFactory，创建出来的2个session，才能共享2级缓存
         * 如果是getSession()生成的2个session，是通过不同的sqlSessionFactory创建的，就不能共享2级缓存
         * */
        SqlSessionFactory sessionFactory = getSessionFactory();
        SqlSession session = sessionFactory.openSession();
        SqlSession session2 = sessionFactory.openSession();
        try{
            EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
            Employee employee = mapper.getEmpById(1);
            System.out.println(employee);
            /**
             * 必须关闭或提交session，数据才会放入  二级缓存。所以必须执行下commit/close，二级缓存才会生效。
             */
            session.commit();   //session.close(); 也行

//            Thread.sleep(6000); //在sleep阶段，可以尝试手工修改id=1的数据。下面第二次查询走缓存，依然读取到的是旧数据。
            EmployeeMapper mapper2 = session2.getMapper(EmployeeMapper.class);

//            System.out.println("==========now insert =========");     //测试flushCache=true/false时，对一/二级缓存的影响。
//            mapper2.addEmp(new Employee(null, "jerry", "1", "jerry@qq.com"));

            System.out.println("==========now select again=========");
            Employee employee2 = mapper2.getEmpById(1);

            /**
             * 从log看，sql只执行了一次。
             * 然后会有： Cache Hit Ratio [com.xiuwei.dao.EmployeeMapper]: 0.5    <--- 缓存命中率50%
             */
            System.out.println(employee2);
            System.out.println(employee == employee2);  //不知道为啥，复用缓存后，这里也是false。
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            session2.close();
        }
    }

}
