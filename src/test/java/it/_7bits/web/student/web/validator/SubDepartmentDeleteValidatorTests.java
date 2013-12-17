package it._7bits.web.student.web.validator;

import it._7bits.web.student.domain.Department;
import it._7bits.web.student.domain.SubDepartment;
import it._7bits.web.student.service.ISubDepartmentService;
import it._7bits.web.student.web.form.SubDepartmentForm;
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
 * SubDepartmentDeleteValidator Tests
 */
public class SubDepartmentDeleteValidatorTests {

    @InjectMocks
    SubDepartmentDeleteValidator subDepartmentDeleteValidator;
    @Mock
    ISubDepartmentService subDepartmentService;

    final static Long SUB1_ID = 1L;
    final static String SUB1_NAME = "Прикладной алгебры";
    final static Long DEPARTMENT1_ID = 1L;
    final static String DEPARTMENT1_NAME = "Математический";
    final static String DEPARTMENT1_DEAN_NAME = "Андрей";
    final static String DEPARTMENT1_DEAN_LASTNAME = "Иванов";
    final static Long SUB_SUCCESS_ID = 1L;
    final static Long SUB_NULL_ID = null;
    final static Long SUB_FAIL_ID = 2L;
    final static String SUB_SUCCESS_NAME = "Геометрии";
    Department SUB_SUCCESS_DEPARTMENT;
    final static String SUB_SUCCESS_EMPTY_NAME = "";
    final static Department SUB_SUCCESS_EMPTY_DEPARTMENT = null;
    final static String SUB_MODEL_NAME = "subDepartmentForm";
    protected final Logger LOG = Logger.getLogger(getClass());

    /**
     * Initial setup
     * All mocks setup here
     * @throws Exception if smth goes wrong
     */
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        subDepartmentDeleteValidator.setSubDepartmentService(subDepartmentService);
        final Department department1 = new Department();
        department1.setId (DEPARTMENT1_ID);
        department1.setDepartmentName (DEPARTMENT1_NAME);
        department1.setDeanFirstName (DEPARTMENT1_DEAN_NAME);
        department1.setDeanLastName (DEPARTMENT1_DEAN_LASTNAME);
        SUB_SUCCESS_DEPARTMENT = department1;
        final SubDepartment subDepartment1 = new SubDepartment();
        subDepartment1.setId (SUB1_ID);
        subDepartment1.setSubDepartmentName(SUB1_NAME);
        subDepartment1.setDepartment (department1);

        // Mock setup for SubDepartment Service
        doAnswer (new Answer<SubDepartment>() {
            public SubDepartment answer (InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                LOG.debug ("SubDepartment id is " + args[0]);
                if (args[0].equals (SUB1_ID)) {
                    LOG.debug ("SubDepartment returned");
                    return subDepartment1;
                } else {
                    LOG.debug ("SubDepartment not returned");
                    return null;
                }
            }
        }).when (subDepartmentService).
                findSubDepartmentById(anyLong());
    }

    /**
     * Test if SubDepartmentDeleteValidator not fails on correct input
     */
    @Test
    public void subDeleteValidatorSuccess() {
        SubDepartmentForm subDepartmentForm =  new SubDepartmentForm();
        subDepartmentForm.setId (SUB_SUCCESS_ID);
        subDepartmentForm.setSubDepartmentName(SUB_SUCCESS_NAME);
        subDepartmentForm.setDepartment (SUB_SUCCESS_DEPARTMENT);
        // We need some implementation of BindingResult here
        BindingResult result = new BindException(subDepartmentForm, SUB_MODEL_NAME);
        subDepartmentDeleteValidator.validate (subDepartmentForm, result);
        assert (!result.hasErrors());
    }

    /**
     * Test if SubDepartmentDeleteValidator fails on null id
     */
    @Test
    public void subDeleteValidatorFailNullId() {
        SubDepartmentForm subDepartmentForm =  new SubDepartmentForm();
        subDepartmentForm.setId (SUB_NULL_ID);
        subDepartmentForm.setSubDepartmentName (SUB_SUCCESS_NAME);
        subDepartmentForm.setDepartment (SUB_SUCCESS_DEPARTMENT);
        // We need some implementation of BindingResult here
        BindingResult result = new BindException(subDepartmentForm, SUB_MODEL_NAME);
        subDepartmentDeleteValidator.validate (subDepartmentForm, result);
        assert (result.hasErrors());
    }

    /**
     * Test if SubDepartmentDeleteValidator fails on wrong id
     */
    @Test
    public void subDeleteValidatorFailId() {
        SubDepartmentForm subDepartmentForm =  new SubDepartmentForm();
        subDepartmentForm.setId (SUB_FAIL_ID);
        subDepartmentForm.setSubDepartmentName (SUB_SUCCESS_NAME);
        subDepartmentForm.setDepartment (SUB_SUCCESS_DEPARTMENT);
        // We need some implementation of BindingResult here
        BindingResult result = new BindException(subDepartmentForm, SUB_MODEL_NAME);
        subDepartmentDeleteValidator.validate (subDepartmentForm, result);
        assert (result.hasErrors());
    }


    /**
     * Test if SubDepartmentDeleteValidator not cares about empty fields except id
     */
    @Test
    public void subDeleteValidatorSuccessEmpty() {
        SubDepartmentForm subDepartmentForm =  new SubDepartmentForm();
        subDepartmentForm.setId (SUB_SUCCESS_ID);
        subDepartmentForm.setSubDepartmentName (SUB_SUCCESS_EMPTY_NAME);
        subDepartmentForm.setDepartment (SUB_SUCCESS_EMPTY_DEPARTMENT);
        // We need some implementation of BindingResult here
        BindingResult result = new BindException(subDepartmentForm, SUB_MODEL_NAME);
        subDepartmentDeleteValidator.validate (subDepartmentForm, result);
        assert (!result.hasErrors());
    }
}
