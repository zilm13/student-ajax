package it._7bits.web.student.service;

import it._7bits.web.student.config.DaoConfig;
import it._7bits.web.student.config.DefaultValuesConfig;
import it._7bits.web.student.config.HibernateJpaConfig;
import it._7bits.web.student.config.ServiceConfig;
import it._7bits.web.student.domain.Department;
import it._7bits.web.student.domain.SubDepartment;
import junit.framework.Assert;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
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
public class SubDepartmentServiceTests {

    private final static String DATASOURCE_URL = "jdbc:mysql://127.0.0.1:3306/students?useUnicode=true&amp;characterEncoding=utf-8";
    private final static String DATASOURCE_USERNAME = "students";
    private final static String DATASOURCE_PASSWORD = "studentspass";
    private final static String DATASOURCE_DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
    private final static String DATASOURCE_JNDI_NAME = "java:comp/env/jdbc/MyDS";
    protected final Logger LOG = Logger.getLogger(getClass());

    @Autowired
    ISubDepartmentService subDepartmentService;
    @Autowired
    IDepartmentService departmentService;


    public SubDepartmentServiceTests() {
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
    public void AddDeleteSubDepartmentTest () throws Exception{
        final String UNIQUE_SUBDEPARTMENT_NAME = "XXMetahondricalXX";
        final Long DEPARTMENT_ID = 1L;

        Department department = departmentService.findDepartmentById (DEPARTMENT_ID);
        LOG.info(department.getDepartmentName());
        SubDepartment subDepartment = new SubDepartment();
        subDepartment.setSubDepartmentName (UNIQUE_SUBDEPARTMENT_NAME);
        subDepartment.setDepartment (department);

        int size = subDepartmentService.findAllSubDepartments().size();
        subDepartmentService.addSubDepartment(subDepartment);
        List<SubDepartment> subDepartmentList = subDepartmentService.findAllSubDepartments();
        Assert.assertEquals(subDepartmentList.size(), size + 1);
        for (SubDepartment curSub : subDepartmentList) {
            if (curSub.getSubDepartmentName().equals(UNIQUE_SUBDEPARTMENT_NAME)) {
                subDepartmentService.deleteSubDepartment(curSub);
            }
        }
        assertEquals(subDepartmentService.findAllSubDepartments().size(), size);
    }
}
