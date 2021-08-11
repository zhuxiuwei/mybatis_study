package com.xiuwei.dao;

import com.xiuwei.POJO.Department;

public interface DepartmentMapper {
    Department getDeptById(Integer id);

    //（第34讲）增强版的查询，查部门时，把全部员工也查出来（嵌套结果集的方式）
    Department getDeptByIdWithEmployees(Integer id);

    //（第35讲）增强版的查询，查部门时，把全部员工也查出来（分步骤查询&延迟加载方式）
    Department getDeptByIdWithEmployeesStep(Integer id);

}
