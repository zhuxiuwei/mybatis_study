import com.xiuwei.POJO.Employee;
import com.xiuwei.dao.EmployeeMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

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
            Employee employee1 = new Employee(null, "jerry", "1", "jerry@qq.com");  //自增id，所以id设置Null
            mapper.addEmp(employee1);
            System.out.println(employee1.getId());  //如果mapper的insert标签里配置了useGeneratedKeys="true" keyProperty="id"，就能正确获取插入后的自增id

            //测试修改
//            Employee employee1 = new Employee(3, "jerry_update3", "1", "jerry_update@qq.com");  //修改id=3的记录
//            boolean succeed = mapper.updateEmp(employee1);
//            System.out.println(succeed);

            //测试删除
//            mapper.deleteEmp(4);

            //2. 手动提交
            session.commit();

        }finally {
            session.close();
        }
    }

    //传参 - param测试
    @Test
    public void testSelectByParam() throws IOException {
        SqlSession session = getSession();
        try {
            EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
            Employee employee = mapper.getEmpByParams(1, "tom", "0");
            System.out.println(employee);   //Employee{id=1, lastName='tom', gender='0', email='tom@126.com'}
        }finally {
            session.close();
        }
    }

    //传参 - map时
    @Test
    public void testSelectByMap() throws IOException {
        SqlSession session = getSession();
        try {
            EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
            Map<String, Object> args = new HashMap<String, Object>();
            args.put("id", 1);
            args.put("lName", "tom");   //注意，lName和mapper里 "#{lName}" 写法一致
            args.put("foo", "bar");     //无效参数，不影响结果
            args.put("tableName", "tbl_employee");  /** 用于测试 #{}取值与 ${}取值的区别。 */

            Employee employee = mapper.getEmpByMap(args);
            System.out.println(employee);   //Employee{id=1, lastName='tom', gender='0', email='tom@126.com'}
        }finally {
            session.close();
        }
    }

    //传参 - pojo时
    @Test
    public void testSelectByPOJO() throws IOException {
        SqlSession session = getSession();
        try {
            EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
            Employee employee = new Employee(1, "Tom", "Foo", "Bar");
            Employee res = mapper.getEmpByPojo(employee);
            System.out.println(res);   //Employee{id=1, lastName='tom', gender='0', email='tom@126.com'}
        }finally {
            session.close();
        }
    }

    //返回map的测试
    @Test
    public void testSelectReturnMap() throws IOException {
        SqlSession session = getSession();
        try {
            EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
            //返回 一条记录 的map。 key: db列名， value: db的值
            Map<String, Object> res = mapper.getEmpByIdReturnMap(1);
            System.out.println(res);   //{gender=0, last_name=tom, id=1, email=tom@126.com}

            //返回 多条记录 的map。 key: id， value: employee javebean
            Map<Integer, Employee> res2 = mapper.getEmpsByLikeLastNameReturnMap("%jer%");   //注意还没有找到like的百分号，在mapper sql里写的正确方式，所以百分号写这了。
            System.out.println(res2);   //{3=Employee{id=3, lastName='jerry_update3', gender='1', email='jerry_update@qq.com', department=null}, 5=Employee{id=5, lastName='jerry', gender='1', email='jerry@qq.com', department=null}, 6=Employee{id=6, lastName='jerry', gender='1', email='jerry@qq.com', department=null}}
        }finally {
            session.close();
        }
    }
}
