package com.xiuwei.POJO;

public class Department {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_dept.id
     *
     * @mbg.generated Mon Sep 06 23:41:40 CST 2021
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_dept.dept_name
     *
     * @mbg.generated Mon Sep 06 23:41:40 CST 2021
     */
    private String deptName;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_dept.id
     *
     * @return the value of tbl_dept.id
     *
     * @mbg.generated Mon Sep 06 23:41:40 CST 2021
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_dept.id
     *
     * @param id the value for tbl_dept.id
     *
     * @mbg.generated Mon Sep 06 23:41:40 CST 2021
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_dept.dept_name
     *
     * @return the value of tbl_dept.dept_name
     *
     * @mbg.generated Mon Sep 06 23:41:40 CST 2021
     */
    public String getDeptName() {
        return deptName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_dept.dept_name
     *
     * @param deptName the value for tbl_dept.dept_name
     *
     * @mbg.generated Mon Sep 06 23:41:40 CST 2021
     */
    public void setDeptName(String deptName) {
        this.deptName = deptName == null ? null : deptName.trim();
    }
}