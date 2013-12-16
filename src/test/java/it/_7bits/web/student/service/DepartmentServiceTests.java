package it._7bits.web.student.service;

import it._7bits.web.student.config.DaoConfig;
import it._7bits.web.student.config.DefaultValuesConfig;
import it._7bits.web.student.config.HibernateJpaConfig;
import it._7bits.web.student.config.ServiceConfig;
import it._7bits.web.student.domain.Department;
import it._7bits.web.student.service.IDepartmentService;
import it._7bits.web.student.service.ServiceGeneralException;
import junit.framework.Assert;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.List;

import static junit.framework.TestCase.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DaoConfig.class, DefaultValuesConfig.class,
        HibernateJpaConfig.class, ServiceConfig.class})
public class DepartmentServiceTests {

    private final static String DATASOURCE_URL = "jdbc:mysql://127.0.0.1:3306/students?useUnicode=true&amp;characterEncoding=utf-8";
    private final static String DATASOURCE_USERNAME = "students";
    private final static String DATASOURCE_PASSWORD = "studentspass";
    private final static String DATASOURCE_DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
    private final static String DATASOURCE_JNDI_NAME = "java:comp/env/jdbc/MyDS";

    @Autowired
    IDepartmentService departmentService;


    public DepartmentServiceTests() {
        final SimpleNamingContextBuilder builder = new SimpleNamingContextBuilder();
        BasicDataSource ds = new BasicDataSource();
        ds.setUrl (DATASOURCE_URL);
        ds.setUsername (DATASOURCE_USERNAME);
        ds.setPassword  (DATASOURCE_PASSWORD);
        ds.setDriverClassName (DATASOURCE_DRIVER_CLASS_NAME);
        builder.bind (DATASOURCE_JNDI_NAME, ds);
        try {
            builder.activate();
        } catch (NamingException e) {
            //will broke test
        }
    }

    @Test
    public void AddDeleteDepartmentTest () throws ServiceGeneralException{
        final String UNIQUE_DEPARTMENT_NAME = "XXMetahondricalXX";
        final String DEAN_NAME = "Ivan";
        final String DEAN_LASTNAME = "Poluvanov";

        Department department = new Department();
        department.setDepartmentName (UNIQUE_DEPARTMENT_NAME);
        department.setDeanFirstName(DEAN_NAME);
        department.setDeanLastName (DEAN_LASTNAME);
        int size = departmentService.findAllDepartments().size();
        departmentService.addDepartment (department);
        List<Department> departmentList = departmentService.findAllDepartments();
        assertEquals (departmentList.size(), size + 1);
        for (Department curDep : departmentList) {
            if (curDep.getDepartmentName().equals(UNIQUE_DEPARTMENT_NAME)) {
                departmentService.deleteDepartment (curDep);
            }
        }
        assertEquals(departmentService.findAllDepartments().size(), size);
    }
}
