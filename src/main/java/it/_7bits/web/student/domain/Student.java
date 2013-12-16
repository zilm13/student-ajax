package it._7bits.web.student.domain;

import java.io.Serializable;

/**
 * Student POJO
 */
public class Student implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private Group group;
    private SubDepartment subDepartment;
    private boolean isHead;


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

    public void setIsHead(boolean head) {
        isHead = head;
    }
}