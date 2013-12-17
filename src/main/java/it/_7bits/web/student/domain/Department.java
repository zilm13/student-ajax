package it._7bits.web.student.domain;


import java.io.Serializable;

/**
 * Department POJO
 */

public class Department implements Serializable {
    private Long id;
    private String departmentName;
    private String deanFirstName;
    private String deanLastName;

    public Department() {}

    public Department (Department department) {
        this.id = department.getId();
        this.departmentName = department.getDepartmentName();
        this.deanFirstName = department.getDeanFirstName();
        this.deanLastName = department.getDeanLastName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDeanFirstName() {
        return deanFirstName;
    }

    public void setDeanFirstName(String deanFirstName) {
        this.deanFirstName = deanFirstName;
    }

    public String getDeanLastName() {
        return deanLastName;
    }

    public void setDeanLastName(String deanLastName) {
        this.deanLastName = deanLastName;
    }
}
