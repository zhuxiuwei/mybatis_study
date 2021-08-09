package com.xiuwei.dao;

import com.xiuwei.POJO.Employee;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface EmployeeMapperPlus {
    Employee getEmpById(Integer id);
}
