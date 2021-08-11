package com.xiuwei.dao;

import com.xiuwei.POJO.Department;

public interface DepartmentMapper {
    Department getDeptById(Integer id);

    //（第34讲）增强版的查询，查部门时，把全部员工也查出来
    Department getDeptByIdWithEmployees(Integer id);

}
