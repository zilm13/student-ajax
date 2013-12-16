package it._7bits.web.student.dao.hibernate.entity;


import it._7bits.web.student.domain.Department;
import it._7bits.web.student.domain.SubDepartment;

import javax.persistence.*;

/**
 * SubDepartment Hibernate Entity Class
 */
@Entity
@Table(name="SubDepartment")
public class SubDepartmentEntity extends SubDepartment {
    private DepartmentEntity departmentEntity;

    /**
     * Default constructor with no arguments, necessary for constructors resolver
     */
    public SubDepartmentEntity() {}

    /**
     * Wrapping POJO into Entity
     * @param subDepartment SubDepartment POJO
     */
    public SubDepartmentEntity (SubDepartment subDepartment) {
        super.setId (subDepartment.getId());
        super.setSubDepartmentName (subDepartment.getSubDepartmentName());
        super.setDepartment (subDepartment.getDepartment());
        if (subDepartment.getDepartment() != null) {
            departmentEntity = new DepartmentEntity (subDepartment.getDepartment());
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
    public String getSubDepartmentName() {
        return super.getSubDepartmentName();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void setSubDepartmentName(String subDepartmentName) {
        super.setSubDepartmentName(subDepartmentName);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    @Transient
    public Department getDepartment() {
        return super.getDepartment();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void setDepartment(Department department) {
        super.setDepartment(department);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @ManyToOne(fetch=FetchType.EAGER) @JoinColumn(name="departmentId")
    public DepartmentEntity getDepartmentEntity() {
        return departmentEntity;
    }

    public void setDepartmentEntity(DepartmentEntity departmentEntity) {
        super.setDepartment (departmentEntity);
        this.departmentEntity = departmentEntity;
    }
}
