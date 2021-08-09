package com.xiuwei.dao;

import com.xiuwei.POJO.Employee;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface EmployeeMapper {

    /**
     *  可以为 增删改 定义以下返回值（无需修改mapper文件，不用加resultType，也没有resultType这个标签）
     *      Boolean(boolean): 增删改是否成功。如果影响的行数>=1，即为true。
     *      Long(long)/Integer(int): 增删改影响的行数
     */
    Employee getEmpById(Integer id);
    Long addEmp(Employee employee);
    Boolean updateEmp(Employee employee);
    Integer deleteEmp(Integer id);

    //传参测试 - param, map, pojo
    Employee getEmpByParams(@Param("id") Integer id, @Param("lName") String lastName, String gender);
    Employee getEmpByMap(Map<String, Object> argsMap);
    Employee getEmpByPojo(Employee employee);

    //返回 一条记录 的map
    Map<String, Object> getEmpByIdReturnMap(Integer id);

    //返回 多条记录 的map。 key: id， value: employee javebean
    @MapKey("id")  //告诉Mybatis 封装map时，使用哪个属性 作为主键。
    Map<Integer, Employee> getEmpsByLikeLastNameReturnMap(@Param("lastName") String lastName);


}
