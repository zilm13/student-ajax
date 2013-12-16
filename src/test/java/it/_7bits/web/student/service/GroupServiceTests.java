package it._7bits.web.student.service;

import it._7bits.web.student.config.DaoConfig;
import it._7bits.web.student.config.DefaultValuesConfig;
import it._7bits.web.student.config.HibernateJpaConfig;
import it._7bits.web.student.config.ServiceConfig;
import it._7bits.web.student.domain.Department;
import it._7bits.web.student.domain.Group;
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
public class GroupServiceTests {

    private final static String DATASOURCE_URL = "jdbc:mysql://127.0.0.1:3306/students?useUnicode=true&amp;characterEncoding=utf-8";
    private final static String DATASOURCE_USERNAME = "students";
    private final static String DATASOURCE_PASSWORD = "studentspass";
    private final static String DATASOURCE_DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
    private final static String DATASOURCE_JNDI_NAME = "java:comp/env/jdbc/MyDS";

    @Autowired
    IGroupService groupService;
    @Autowired
    IDepartmentService departmentService;


    public GroupServiceTests() {
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
        final String UNIQUE_GROUP_NAME = "XXMetahondricalXX";
        final Long DEPARTMENT_ID = 1L;

        Department department = departmentService.findDepartmentById (DEPARTMENT_ID);
        Group group = new Group();
        group.setGroupName (UNIQUE_GROUP_NAME);
        group.setDepartment (department);

        int size = groupService.findAllGroups().size();
        groupService.addGroup (group);
        List<Group> groupList = groupService.findAllGroups();
        Assert.assertEquals (groupList.size(), size + 1);
        for (Group curGroup : groupList) {
            if (curGroup.getGroupName().equals(UNIQUE_GROUP_NAME)) {
                groupService.deleteGroup (curGroup);
            }
        }
        assertEquals (groupService.findAllGroups().size(), size);
    }
}