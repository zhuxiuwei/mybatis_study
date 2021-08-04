package com.xiuwei.dao;

import com.xiuwei.POJO.Employee;

public interface EmployeeMapper {

    /**
     *  可以为 增删改 定义以下返回值（无需修改mapper文件，不用加resultType，也没有resultType这个标签）
     *  Boolean(boolean): 增删改是否成功
     *  Long(long)/Integer(int): 增删改影响的行数
     */
    Employee getEmpById(Integer id);
    Long addEmp(Employee employee);
    Boolean updateEmp(Employee employee);
    Integer  deleteEmp(Integer id);
}
