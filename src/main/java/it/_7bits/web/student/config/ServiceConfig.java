package it._7bits.web.student.config;

import it._7bits.web.student.service.IDepartmentService;
import it._7bits.web.student.service.IGroupService;
import it._7bits.web.student.service.IStudentService;
import it._7bits.web.student.service.ISubDepartmentService;
import it._7bits.web.student.service.impl.DepartmentServiceImpl;
import it._7bits.web.student.service.impl.GroupServiceImpl;
import it._7bits.web.student.service.impl.StudentServiceImpl;
import it._7bits.web.student.service.impl.SubDepartmentServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Service Configuration
 */
@Configuration
public class ServiceConfig {

    /**
     * Setting up Service for Department
     * @return Department service
     */
    @Bean
    public IDepartmentService departmentService() {
        return new DepartmentServiceImpl();
    }

    /**
     * Setting up service for Group
     * @return Group service
     */
    @Bean
    public IGroupService groupService() {
        return new GroupServiceImpl();
    }

    /**
     * Setting up service for SubDepartment
     * @return SubDepartment service
     */
    @Bean
    public ISubDepartmentService subDepartmentService() {
        return new SubDepartmentServiceImpl();
    }

    /**
     * Setting up service for Student
     * @return Student service
     */
    @Bean
    public IStudentService studentService() {
        return new StudentServiceImpl();
    }


}