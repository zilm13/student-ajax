package it._7bits.web.student.service;

import it._7bits.web.student.config.DaoConfig;
import it._7bits.web.student.config.DefaultValuesConfig;
import it._7bits.web.student.config.HibernateJpaConfig;
import it._7bits.web.student.config.ServiceConfig;
import it._7bits.web.student.domain.Group;
import it._7bits.web.student.domain.Student;
import it._7bits.web.student.domain.SubDepartment;
import junit.framework.Assert;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.naming.NamingException;
import java.util.List;

import static junit.framework.TestCase.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DaoConfig.class, DefaultValuesConfig.class,
        HibernateJpaConfig.class, ServiceConfig.class})
public class StudentServiceTests {

    private final static String DATASOURCE_URL = "jdbc:mysql://127.0.0.1:3306/students?useUnicode=true&amp;characterEncoding=utf-8";
    private final static String DATASOURCE_USERNAME = "students";
    private final static String DATASOURCE_PASSWORD = "studentspass";
    private final static String DATASOURCE_DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
    private final static String DATASOURCE_JNDI_NAME = "java:comp/env/jdbc/MyDS";

    @Autowired
    IGroupService groupService;
    @Autowired
    ISubDepartmentService subDepartmentService;
    @Autowired
    IStudentService studentService;


    public StudentServiceTests() {
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
    public void AddDeleteStudentTest () throws ServiceGeneralException{
        final String UNIQUE_STUDENT_NAME = "XXMetahondricalXX";
        final String STUDENT_LASTNAME = "Polovanov";
        final boolean STUDENT_IS_HEAD = false;

        final Long SUB_DEPARTMENT_ID = 1L;
        final Long GROUP_ID = 1L;

        SubDepartment subDepartment = subDepartmentService.findSubDepartmentById(SUB_DEPARTMENT_ID);
        Group group = groupService.findGroupById (GROUP_ID);
        Student student = new Student();
        student.setFirstName (UNIQUE_STUDENT_NAME);
        student.setLastName (STUDENT_LASTNAME);
        student.setGroup (group);
        student.setSubDepartment (subDepartment);
        student.setIsHead (STUDENT_IS_HEAD);

        int size = studentService.findAllStudents().size();
        studentService.addStudent (student);
        List<Student> studentList = studentService.findAllStudents();
        Assert.assertEquals(studentList.size(), size + 1);
        for (Student curStudent : studentList) {
            if (curStudent.getFirstName().equals(UNIQUE_STUDENT_NAME)) {
                studentService.deleteStudent (curStudent);
            }
        }
        assertEquals (studentService.findAllStudents().size(), size);
    }
}