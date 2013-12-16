package it._7bits.web.student.web.form;

import it._7bits.web.student.domain.Department;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 *  JavaBean Class for SubDepartment Form
 */
public class SubDepartmentForm {
    private Long id;
    @NotEmpty
    private String subDepartmentName;
    @NotNull
    private Department department;

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
