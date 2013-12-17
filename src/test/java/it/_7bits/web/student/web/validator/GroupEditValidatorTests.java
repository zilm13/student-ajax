package it._7bits.web.student.web.validator;

import it._7bits.web.student.domain.Department;
import it._7bits.web.student.domain.Group;
import it._7bits.web.student.service.IGroupService;
import it._7bits.web.student.web.form.GroupForm;
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
 * GroupEditValidator Tests
 */
public class GroupEditValidatorTests {

    @InjectMocks
    GroupEditValidator groupEditValidator;
    @Mock
    IGroupService groupService;

    final static Long GROUP1_ID = 1L;
    final static String GROUP1_NAME = "ММ-06";
    final static Long DEPARTMENT1_ID = 1L;
    final static String DEPARTMENT1_NAME = "Математический";
    final static String DEPARTMENT1_DEAN_NAME = "Андрей";
    final static String DEPARTMENT1_DEAN_LASTNAME = "Иванов";
    final static Long GROUP_SUCCESS_ID = 1L;
    final static Long GROUP_FAIL_NULL_ID = null;
    final static Long GROUP_FAIL_ID = 2L;
    final static String GROUP_SUCCESS_NAME = "ММ-33";
    Department GROUP_SUCCESS_DEPARTMENT;
    final static String GROUP_FAIL_NAME = "";
    final static Department GROUP_FAIL_DEPARTMENT = null;
    final static String GROUP_MODEL_NAME = "groupForm";
    protected final Logger LOG = Logger.getLogger(getClass());

    /**
     * Initial setup
     * All mocks setup here
     * @throws Exception if smth goes wrong
     */
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        groupEditValidator.setGroupService(groupService);
        final Department department1 = new Department();
        department1.setId (DEPARTMENT1_ID);
        department1.setDepartmentName (DEPARTMENT1_NAME);
        department1.setDeanFirstName (DEPARTMENT1_DEAN_NAME);
        department1.setDeanLastName (DEPARTMENT1_DEAN_LASTNAME);
        GROUP_SUCCESS_DEPARTMENT = department1;
        final Group group1 = new Group();
        group1.setId (GROUP1_ID);
        group1.setGroupName (GROUP1_NAME);
        group1.setDepartment (department1);

        // Mock setup for Group Service
        doAnswer (new Answer<Group>() {
            public Group answer (InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                LOG.debug ("Group id is " + args[0]);
                if (args[0].equals (GROUP1_ID)) {
                    LOG.debug ("Group returned");
                    return group1;
                } else {
                    LOG.debug ("Group not returned");
                    return null;
                }
            }
        }).when (groupService).
                findGroupById (anyLong());
    }

    /**
     * Test if GroupEditValidator not fails on correct input
     */
    @Test
    public void groupEditValidatorSuccess() {
        GroupForm groupForm =  new GroupForm();
        groupForm.setId (GROUP_SUCCESS_ID);
        groupForm.setGroupName (GROUP_SUCCESS_NAME);
        groupForm.setDepartment (GROUP_SUCCESS_DEPARTMENT);
        // We need some implementation of BindingResult here
        BindingResult result = new BindException(groupForm, GROUP_MODEL_NAME);
        groupEditValidator.validate (groupForm, result);
        assert (!result.hasErrors());
    }

    /**
     * Test if GroupEditValidator fails on null id
     */
    @Test
    public void groupEditValidatorFailNullId() {
        GroupForm groupForm =  new GroupForm();
        groupForm.setId (GROUP_FAIL_NULL_ID);
        groupForm.setGroupName (GROUP_SUCCESS_NAME);
        groupForm.setDepartment (GROUP_SUCCESS_DEPARTMENT);
        // We need some implementation of BindingResult here
        BindingResult result = new BindException(groupForm, GROUP_MODEL_NAME);
        groupEditValidator.validate (groupForm, result);
        assert (result.hasErrors());
    }

    /**
     * Test if GroupEditValidator fails on wrong id
     */
    @Test
    public void groupEditValidatorFailId() {
        GroupForm groupForm =  new GroupForm();
        groupForm.setId (GROUP_FAIL_ID);
        groupForm.setGroupName (GROUP_SUCCESS_NAME);
        groupForm.setDepartment (GROUP_SUCCESS_DEPARTMENT);
        // We need some implementation of BindingResult here
        BindingResult result = new BindException(groupForm, GROUP_MODEL_NAME);
        groupEditValidator.validate (groupForm, result);
        assert (result.hasErrors());
    }

    /**
     * Test if GroupEditValidator fails on wrong name
     */
    @Test
    public void groupEditValidatorFailName() {
        GroupForm groupForm =  new GroupForm();
        groupForm.setId (GROUP_SUCCESS_ID);
        groupForm.setGroupName (GROUP_FAIL_NAME);
        groupForm.setDepartment (GROUP_SUCCESS_DEPARTMENT);
        // We need some implementation of BindingResult here
        BindingResult result = new BindException(groupForm, GROUP_MODEL_NAME);
        groupEditValidator.validate (groupForm, result);
        assert (result.hasErrors());
    }

    /**
     * Test if GroupEditValidator fails on wrong department
     */
    @Test
    public void groupEditValidatorFailDep() {
        GroupForm groupForm =  new GroupForm();
        groupForm.setId (GROUP_SUCCESS_ID);
        groupForm.setGroupName (GROUP_SUCCESS_NAME);
        groupForm.setDepartment (GROUP_FAIL_DEPARTMENT);
        // We need some implementation of BindingResult here
        BindingResult result = new BindException(groupForm, GROUP_MODEL_NAME);
        groupEditValidator.validate (groupForm, result);
        assert (result.hasErrors());
    }
}
