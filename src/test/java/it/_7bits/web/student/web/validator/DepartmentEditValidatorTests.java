package it._7bits.web.student.web.validator;

import it._7bits.web.student.dao.IDepartmentDao;
import it._7bits.web.student.service.ISubDepartmentService;
import it._7bits.web.student.service.ServiceGeneralException;
import it._7bits.web.student.web.form.DepartmentForm;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;

import static org.mockito.Mockito.*;
/**
 * Created by zilm on 12/16/13.
 */
public class DepartmentEditValidatorTests {
    @Mock
    DepartmentEditValidator departmentEditValidator;
    @Mock
    ISubDepartmentService departmentService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        //doThrow(new ServiceGeneralException("",new Exception())).when(departmentEditValidator).validate(anyObject(),any(org.springframework.validation.Errors.class));
    }

    @Test
    public void departmentEditValidatorSuccess() {
        DepartmentForm departmentForm =  new DepartmentForm();
        BindingResult result = mock(BindingResult.class);
        departmentEditValidator.validate(departmentForm,result);
    }

}
