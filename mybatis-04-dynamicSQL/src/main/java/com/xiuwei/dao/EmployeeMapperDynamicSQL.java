package com.xiuwei.dao;

import com.xiuwei.POJO.Employee;

import java.util.List;

public interface EmployeeMapperDynamicSQL {
    //查询条件employee里，携带了哪个查询条件，返回结果就带上这个字段的值
    List<Employee>  getEmpsByConditionIf(Employee employee);
}
