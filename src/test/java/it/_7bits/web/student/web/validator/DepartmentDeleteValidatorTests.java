package it._7bits.web.student.web.validator;


import it._7bits.web.student.domain.Department;
import it._7bits.web.student.service.IDepartmentService;
import it._7bits.web.student.web.form.DepartmentForm;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doAnswer;

/**
 * DepartmentDeleteValidator Tests
 */
public class DepartmentDeleteValidatorTests {

    @InjectMocks
    DepartmentDeleteValidator departmentDeleteValidator;
    @Mock
    IDepartmentService departmentService;

    final static Long DEPARTMENT1_ID = 1L;
    final static String DEPARTMENT1_NAME = "Математический";
    final static String DEPARTMENT1_DEAN_NAME = "Андрей";
    final static String DEPARTMENT1_DEAN_LASTNAME = "Иванов";
    final static Long DEPARTMENT_SUCCESS_ID = 1L;
    final static Long DEPARTMENT_NULL_ID = null;
    final static Long DEPARTMENT_FAIL_ID = 2L;
    final static String DEPARTMENT_SUCCESS_NAME = "Математический";
    final static String DEPARTMENT_SUCCESS_DEAN_NAME = "Андрей";
    final static String DEPARTMENT_SUCCESS_DEAN_LASTNAME = "Иванов";
    final static String DEPARTMENT_SUCCESS_EMPTY_NAME = "";
    final static String DEPARTMENT_SUCCESS_EMPTY_DEAN_NAME = "";
    final static String DEPARTMENT_SUCCESS_EMPTY_DEAN_LASTNAME = "";
    final static String DEPARTMENT_MODEL_NAME = "departmentForm";
    protected final Logger LOG = Logger.getLogger(getClass());

    /**
     * Initial setup
     * All mocks setup here
     * @throws Exception if smth goes wrong
     */
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        departmentDeleteValidator.setDepartmentService (departmentService);
        final Department department1 = new Department();
        department1.setId(DEPARTMENT1_ID);
        department1.setDepartmentName(DEPARTMENT1_NAME);
        department1.setDeanFirstName(DEPARTMENT1_DEAN_NAME);
        department1.setDeanLastName(DEPARTMENT1_DEAN_LASTNAME);

        // Mock setup for Department Service
        doAnswer (new Answer<Department>() {
            public Department answer (InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                LOG.debug ("Department id is " + args[0]);
                if (args[0].equals (DEPARTMENT1_ID)) {
                    LOG.debug ("Department returned");
                    return department1;
                } else {
                    LOG.debug ("Department not returned");
                    return null;
                }
            }
        }).when (departmentService).
                findDepartmentById (anyLong());
    }

    /**
     * Test if DepartmentDeleteValidator not fails on correct input
     */
    @Test
    public void departmentDeleteValidatorSuccess() {
        DepartmentForm departmentForm =  new DepartmentForm();
        departmentForm.setId( DEPARTMENT_SUCCESS_ID);
        departmentForm.setDepartmentName (DEPARTMENT_SUCCESS_NAME);
        departmentForm.setDeanFirstName (DEPARTMENT_SUCCESS_DEAN_NAME);
        departmentForm.setDeanLastName (DEPARTMENT_SUCCESS_DEAN_LASTNAME);
        // We need some implementation of BindingResult here
        BindingResult result = new BindException(departmentForm, DEPARTMENT_MODEL_NAME);
        departmentDeleteValidator.validate (departmentForm, result);
        assert (!result.hasErrors());
    }

    /**
     * Test if DepartmentDeleteValidator fails on null id
     */
    @Test
    public void departmentDeleteValidatorFailNullId() {
        DepartmentForm departmentForm =  new DepartmentForm();
        departmentForm.setId( DEPARTMENT_NULL_ID);
        departmentForm.setDepartmentName (DEPARTMENT_SUCCESS_NAME);
        departmentForm.setDeanFirstName (DEPARTMENT_SUCCESS_DEAN_NAME);
        departmentForm.setDeanLastName (DEPARTMENT_SUCCESS_DEAN_LASTNAME);
        // We need some implementation of BindingResult here
        BindingResult result = new BindException(departmentForm, DEPARTMENT_MODEL_NAME);
        departmentDeleteValidator.validate (departmentForm, result);
        assert (result.hasErrors());
    }

    /**
     * Test if DepartmentDeleteValidator fails on wrong id
     */
    @Test
    public void departmentDeleteValidatorFailId() {
        DepartmentForm departmentForm =  new DepartmentForm();
        departmentForm.setId( DEPARTMENT_FAIL_ID);
        departmentForm.setDepartmentName (DEPARTMENT_SUCCESS_NAME);
        departmentForm.setDeanFirstName (DEPARTMENT_SUCCESS_DEAN_NAME);
        departmentForm.setDeanLastName (DEPARTMENT_SUCCESS_DEAN_LASTNAME);
        // We need some implementation of BindingResult here
        BindingResult result = new BindException(departmentForm, DEPARTMENT_MODEL_NAME);
        departmentDeleteValidator.validate (departmentForm, result);
        assert (result.hasErrors());
    }

    /**
     * Test if DepartmentDeleteValidator not fails on all empty fields except id
     */
    @Test
    public void departmentDeleteValidatorNotFailEmpty() {
        DepartmentForm departmentForm =  new DepartmentForm();
        departmentForm.setId( DEPARTMENT_SUCCESS_ID);
        departmentForm.setDepartmentName (DEPARTMENT_SUCCESS_EMPTY_NAME);
        departmentForm.setDeanFirstName (DEPARTMENT_SUCCESS_EMPTY_DEAN_NAME);
        departmentForm.setDeanLastName (DEPARTMENT_SUCCESS_EMPTY_DEAN_LASTNAME);
        // We need some implementation of BindingResult here
        BindingResult result = new BindException(departmentForm, DEPARTMENT_MODEL_NAME);
        departmentDeleteValidator.validate (departmentForm, result);
        assert (!result.hasErrors());
    }
}
