package it._7bits.web.student.web.form;

import it._7bits.web.student.domain.Department;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 *  JavaBean Class for Group Form
 */
public class GroupForm {
    private Long id;
    @NotEmpty
    private String groupName;
    @NotNull
    private Department department;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
