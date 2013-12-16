package it._7bits.web.student.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.naming.NamingException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { DaoConfig.class, DefaultValuesConfig.class,
        HibernateJpaConfig.class, ServiceConfig.class, WebConfig.class })
public class WebDomainIntegrationTests {
    private MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    public WebDomainIntegrationTests() {
        final SimpleNamingContextBuilder builder = new SimpleNamingContextBuilder();
        BasicDataSource ds = new BasicDataSource();
        ds.setUrl("jdbc:mysql://127.0.0.1:3306/students?useUnicode=true&amp;characterEncoding=utf-8");
        ds.setUsername("students");
        ds.setPassword ("studentspass");
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        builder.bind("java:comp/env/jdbc/MyDS", ds);
        try {
            builder.activate();
        } catch (NamingException e) {
            //will broke test
        }
    }

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void departmentView() throws Exception {
        mockMvc.perform(get("/department/view"))
                .andDo(print())
                .andExpect(view().name("department/view"))
                .andExpect(model().attributeExists("departments"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/department/view.jsp"));
    }
}
