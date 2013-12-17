package it._7bits.web.student.domain;

import java.io.Serializable;

/**
 * SubDepartment POJO
 */
public class SubDepartment implements Serializable {
    private Long id;
    private String subDepartmentName;
    private Department department;

    public SubDepartment() {}

    public SubDepartment (SubDepartment subDepartment) {
        this.id = subDepartment.getId();
        this.subDepartmentName = subDepartment.getSubDepartmentName();
        this.department = subDepartment.getDepartment();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubDepartmentName() {
        return subDepartmentName;
    }

    public void setSubDepartmentName(String subDepartmentName) {
        this.subDepartmentName = subDepartmentName;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
