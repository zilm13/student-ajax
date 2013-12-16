package it._7bits.web.student.web.form;

import it._7bits.web.student.domain.Group;
import it._7bits.web.student.domain.SubDepartment;

/**
 * JavaBean Class for Student Form
 */
public class StudentForm {
    private Long id;
    private String firstName;
    private String lastName;
    private Group group;
    private SubDepartment subDepartment;
    private boolean isHead;
    private boolean forceHead;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public SubDepartment getSubDepartment() {
        return subDepartment;
    }

    public void setSubDepartment(SubDepartment subDepartment) {
        this.subDepartment = subDepartment;
    }

    public boolean getIsHead() {
        return isHead;
    }

    public void setIsHead(boolean isHead) {
        this.isHead = isHead;
    }

    public boolean getForceHead() {
        return forceHead;
    }

    public void setForceHead(boolean forceHead) {
        this.forceHead = forceHead;
    }

}

