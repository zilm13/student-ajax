package it._7bits.web.student.dao.hibernate.entity;


import it._7bits.web.student.domain.Group;
import it._7bits.web.student.domain.Student;
import it._7bits.web.student.domain.SubDepartment;

import javax.persistence.*;

/**
 * Student Hibernate Entity Class
 */
@Entity
@Table(name="Student")
public class StudentEntity extends Student {
    private GroupEntity groupEntity;
    private SubDepartmentEntity subDepartmentEntity;

    /**
     * Default constructor with no arguments, necessary for constructors resolver
     */
    public StudentEntity() {}

    /**
     * Wrapping POJO into Entity
     * @param student Student POJO
     */
    public StudentEntity(Student student) {
        super.setId (student.getId());
        super.setFirstName (student.getFirstName());
        super.setLastName (student.getLastName());
        super.setGroup (student.getGroup());
        super.setSubDepartment (student.getSubDepartment());
        super.setIsHead (student.getIsHead());
        if (student.getGroup() != null) {
            groupEntity = new GroupEntity (student.getGroup());
        }
        if (student.getSubDepartment() != null) {
            subDepartmentEntity = new SubDepartmentEntity (student.getSubDepartment());
        }
    }

    @Id
    @GeneratedValue
    @Override
    public Long getId() {
        return super.getId();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void setId(Long id) {
        super.setId(id);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public String getFirstName() {
        return super.getFirstName();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void setFirstName(String firstName) {
        super.setFirstName(firstName);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public String getLastName() {
        return super.getLastName();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void setLastName(String lastName) {
        super.setLastName(lastName);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public boolean getIsHead() {
        return super.getIsHead();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void setIsHead(boolean head) {
        super.setIsHead(head);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    @Transient
    public Group getGroup() {
        return super.getGroup();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void setGroup(Group group) {
        super.setGroup(group);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    @Transient
    public SubDepartment getSubDepartment() {
        return super.getSubDepartment();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void setSubDepartment(SubDepartment subDepartment) {
        super.setSubDepartment(subDepartment);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @ManyToOne(fetch=FetchType.EAGER) @JoinColumn(name="groupId" )
    public GroupEntity getGroupEntity() {
        return groupEntity;
    }

    public void setGroupEntity(GroupEntity groupEntity) {
        if (groupEntity != null) {
            Group group = new Group (groupEntity);
            super.setGroup (group);
        }
        this.groupEntity = groupEntity;
    }

    @ManyToOne(fetch=FetchType.EAGER) @JoinColumn(name="subDepartmentId" )
    public SubDepartmentEntity getSubDepartmentEntity() {
        return subDepartmentEntity;
    }

    public void setSubDepartmentEntity(SubDepartmentEntity subDepartmentEntity) {
        if (subDepartmentEntity != null){
            SubDepartment subDepartment = new SubDepartment (subDepartmentEntity);
            super.setSubDepartment (subDepartment);
        }
        this.subDepartmentEntity = subDepartmentEntity;
    }
}
