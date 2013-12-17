package it._7bits.web.student.domain;


import java.io.Serializable;

/**
 * Group POJO
 */
public class Group implements Serializable{
    private Long id;
    private String groupName;
    private Department department;

    public Group() {}
    public Group (Group group) {
        this.id = group.getId();
        this.groupName = group.getGroupName();
        this.department = group.getDepartment();
    }

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
