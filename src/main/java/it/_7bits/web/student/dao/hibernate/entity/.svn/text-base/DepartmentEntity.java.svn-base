package it._7bits.web.student.dao.hibernate.entity;


import it._7bits.web.student.domain.Department;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Department Hibernate Entity Class
 */
@Entity
@Table(name="Department")
public class DepartmentEntity extends Department {

    /**
     * Default constructor, necessary for constructors resolver
     */
    public DepartmentEntity () {}

    /**
     * Wrapping POJO into Entity
     * @param department Department POJO
     */
    public DepartmentEntity (Department department) {
        super.setId (department.getId());
        super.setDepartmentName (department.getDepartmentName());
        super.setDeanFirstName (department.getDeanFirstName());
        super.setDeanLastName (department.getDeanLastName());
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
    public String getDepartmentName() {
        return super.getDepartmentName();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void setDepartmentName(String departmentName) {
        super.setDepartmentName(departmentName);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public String getDeanFirstName() {
        return super.getDeanFirstName();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void setDeanFirstName(String deanFirstName) {
        super.setDeanFirstName(deanFirstName);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public String getDeanLastName() {
        return super.getDeanLastName();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void setDeanLastName(String deanLastName) {
        super.setDeanLastName(deanLastName);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
