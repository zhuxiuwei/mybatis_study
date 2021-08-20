package com.xiuwei.dao;

import com.xiuwei.POJO.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeMapperDynamicSQL {
    //#39： 查询条件employee里，携带了哪个查询条件，返回结果就带上这个字段的值
    List<Employee>  getEmpsByConditionIf(Employee employee);

    //#40： 用if的同时，用where标签，解决where 后面and/or拼接可能存在的问题。
    List<Employee>  getEmpsByConditionIfUsingWhere(Employee employee);

    //#42：choose测试:如果有id就用ID查，如果有lastName就用lastName查.....二者只用一个
    List<Employee>  getEmpsByConditionChoose(Employee employee);

    //#43: set与if结合的动态更新 - 传了哪列，就更新哪列，其他列不更新。(之前「mybatis-03-mapper」的'updateEmp'是全字段更新
    void updateEmp(Employee employee);

    //#44: foreach循环遍历集合，拼SQL
    List<Employee>  getEmpsByConditionForeach(@Param("ids") List<Integer> ids);

    //#45: foreach批量添加
    void  addEmps(@Param("emps") List<Employee> emps);

}
