package com.xiuwei.dao;

import com.xiuwei.POJO.Employee;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface EmployeeMapperPlus {
    Employee getEmpById(Integer id);

    //第30讲：关联查询：查询员工，同时查关联的部门表。使用级联属性封装结果。
    Employee getEmpAndDept(Integer id);

    //第31讲：关联查询：查询员工，同时查关联的部门表。使用association封装关联对象
    Employee getEmpAndDept2(Integer id);

    //第32讲：关联查询：查询员工，同时查关联的部门表。使用association 分步查询。
    Employee getEmpByIdStep(Integer id);

    //第35讲用到
    List<Employee> getEmpsByDeptId(Integer did);
}
