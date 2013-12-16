package it._7bits.web.student.config;

import it._7bits.web.student.service.IDefaultValuesService;
import it._7bits.web.student.service.impl.DefaultValuesServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Default Values setup
 */
@Configuration
public class DefaultValuesConfig {

    private static final String DEFAULT_DEPARTMENT_RETURN_URI="/department/view";
    private static final String DEFAULT_GROUP_RETURN_URI="/group/view";
    private static final String DEFAULT_STUDENT_RETURN_URI="/student/view";
    private static final String DEFAULT_SUBDEPARTMENT_RETURN_URI="/subdepartment/view";
    private static final String BASE_URL="http://localhost:8100/student-ajax3";

    @Bean
    public IDefaultValuesService defaultValuesService() {
        IDefaultValuesService defaultValuesService = new DefaultValuesServiceImpl();
        defaultValuesService.setBaseUrl (BASE_URL);
        defaultValuesService.setDepartmentReturnUri (DEFAULT_DEPARTMENT_RETURN_URI);
        defaultValuesService.setGroupReturnUri (DEFAULT_GROUP_RETURN_URI);
        defaultValuesService.setSubDepartmentReturnUri (DEFAULT_SUBDEPARTMENT_RETURN_URI);
        defaultValuesService.setStudentReturnUri (DEFAULT_STUDENT_RETURN_URI);
        return defaultValuesService;
    }

}
