package it._7bits.web.student.dao.hibernate.entity;


import it._7bits.web.student.domain.Department;
import it._7bits.web.student.domain.Group;

import javax.persistence.*;

/**
 * Group Hibernate Entity Class
 */
@Entity
@Table(name="GroupT")
public class GroupEntity extends Group {
    private DepartmentEntity departmentEntity;

    /**
     * Default constructor with blank arguments, necessary constructors resolver
     */
    public GroupEntity() {}

    /**
     * Wrapping POJO into Entity
     * @param group Group POJO
     */
    public GroupEntity (Group group) {
        super.setId (group.getId());
        super.setGroupName (group.getGroupName());
        super.setDepartment(group.getDepartment());
        if (group.getDepartment() != null) {
            departmentEntity = new DepartmentEntity (group.getDepartment());
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
    public String getGroupName() {
        return super.getGroupName();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void setGroupName(String groupName) {
        super.setGroupName(groupName);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    @Transient
    public Department getDepartment() {
        return super.getDepartment();
    }

    @Override
    public void setDepartment(Department department) {
        super.setDepartment(department);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @ManyToOne(fetch=FetchType.EAGER) @JoinColumn(name="departmentId" )
    public DepartmentEntity getDepartmentEntity() {
        return departmentEntity;
    }

    public void setDepartmentEntity(DepartmentEntity departmentEntity) {
        super.setDepartment (departmentEntity);
        this.departmentEntity = departmentEntity;
    }
}
