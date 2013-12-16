package it._7bits.web.student.config;

import it._7bits.web.student.dao.*;
import it._7bits.web.student.dao.hibernate.*;
import it._7bits.web.student.dao.hibernate.entity.DepartmentEntity;
import it._7bits.web.student.dao.hibernate.entity.GroupEntity;
import it._7bits.web.student.dao.hibernate.entity.StudentEntity;
import it._7bits.web.student.dao.hibernate.entity.SubDepartmentEntity;
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
    public IDepartmentDao departmentDao() {
        return new DepartmentDaoJpa();
    }

    /**
     * Setting up DAO for SubDepartment
     * @return SubDepartment DAO Bean
     */
    @Bean
    public ISubDepartmentDao subDepartmentDao() {
        return new SubDepartmentDaoJpa();
    }

    /**
     * Setting up DAO for Group
     * @return Group DAO Bean
     */
    @Bean
    public IGroupDao groupDao() {
        return new GroupDaoJpa();
    }

    /**
     * Setting up DAO for Student
     * @return Student DAO Bean
     */
    @Bean
    public IStudentDao studentDao() {
        return new StudentDaoJpa();
    }
}
