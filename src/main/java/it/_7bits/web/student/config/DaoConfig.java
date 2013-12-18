package it._7bits.web.student.config;

import it._7bits.web.student.dao.IEntityDao;
import it._7bits.web.student.dao.hibernate.*;
import it._7bits.web.student.dao.hibernate.entity.DepartmentEntity;
import it._7bits.web.student.dao.hibernate.entity.GroupEntity;
import it._7bits.web.student.dao.hibernate.entity.StudentEntity;
import it._7bits.web.student.dao.hibernate.entity.SubDepartmentEntity;
import it._7bits.web.student.domain.Department;
import it._7bits.web.student.domain.Group;
import it._7bits.web.student.domain.Student;
import it._7bits.web.student.domain.SubDepartment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * DAO Configuration
 */
@Configuration
public class DaoConfig {
    /**
     * Setting up DAO for Department
     * @return Department DAO Bean
     */
    @Bean
    public IEntityDao departmentDao() {
        return new DepartmentDaoJpa (DepartmentEntity.class, "DepartmentEntity", Department.class);
    }

    /**
     * Setting up DAO for SubDepartment
     * @return SubDepartment DAO Bean
     */
    @Bean
    public IEntityDao subDepartmentDao() {
        return new SubDepartmentDaoJpa (SubDepartmentEntity.class, "SubDepartmentEntity", SubDepartment.class);
    }

    /**
     * Setting up DAO for Group
     * @return Group DAO Bean
     */
    @Bean
    public IEntityDao groupDao() {
        return new GroupDaoJpa (GroupEntity.class, "GroupEntity", Group.class);
    }

    /**
     * Setting up DAO for Student
     * @return Student DAO Bean
     */
    @Bean
    public IEntityDao studentDao() {
        return new StudentDaoJpa (StudentEntity.class, "StudentEntity", Student.class);
    }
}
