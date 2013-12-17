package it._7bits.web.student.web.controller;

import it._7bits.web.student.domain.Cookah;
import it._7bits.web.student.domain.Department;
import it._7bits.web.student.service.IDefaultValuesService;
import it._7bits.web.student.service.IDepartmentService;
import it._7bits.web.student.service.ServiceGeneralException;
import it._7bits.web.student.web.form.DepartmentForm;
import it._7bits.web.student.web.validator.DepartmentDeleteValidator;
import it._7bits.web.student.web.validator.DepartmentEditValidator;
import junit.framework.TestCase;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * Tests for Department Controller
 */
public class DepartmentControllerTests extends TestCase {

    MockMvc mockMvc;

    private Cookah cookah;
    @Mock
    private IDefaultValuesService defaultValuesService;
    @InjectMocks
    DepartmentController controller;
    @Mock
    IDepartmentService departmentService;
    @Mock
    MessageSource messageSource;
    @Mock
    DepartmentEditValidator departmentEditValidator;
    @Mock
    DepartmentDeleteValidator departmentDeleteValidator;

    final static String BASE_URL = "http://test.com";
    final static String DEPARTMENT_VIEW_URI = "/department/view";
    final static String DEPARTMENT_VIEW_VIEW_NAME = "department/view";
    final static Long DEPARTMENT1_ID = 1L;
    final static String DEPARTMENT1_NAME = "Математический";
    final static String DEPARTMENT1_DEAN_NAME = "Андрей";
    final static String DEPARTMENT1_DEAN_LASTNAME = "Иванов";
    final static String DEPARTMENT_ADD_URI = "/department/add";
    final static String DEPARTMENT_ADD_VIEW_NAME = "department/add";
    final static String DEPARTMENT_ADD_PARAM_NAME = "Физический";
    final static String DEPARTMENT_ADD_PARAM_DEAN_NAME = "Иван";
    final static String DEPARTMENT_ADD_PARAM_DEAN_LASTNAME = "Петров";
    final static String ATTRIBUTE_COOKIE = "cookah";
    final static String ATTRIBUTE_DEPARTMENTS = "departments";
    final static String ATTRIBUTE_DEPARTMENT_FORM = "departmentForm";
    final static String PARAM_NAME_DEPARTMENT_ID = "id";
    final static String PARAM_NAME_DEPARTMENT_NAME = "departmentName";
    final static String PARAM_NAME_DEAN_FIRSTNAME = "deanFirstName";
    final static String PARAM_NAME_DEAN_LASTNAME = "deanLastName";
    final static String DEPARTMENT_EDIT_PARAM_ID = "1";
    final static String DEPARTMENT_EDIT_PARAM_NAME = "Метафизический";
    final static String DEPARTMENT_EDIT_PARAM_DEAN_NAME = "Апостол";
    final static String DEPARTMENT_EDIT_PARAM_DEAN_LASTNAME = "Петров";
    final static String DEPARTMENT_EDIT_URI_FAIL = "/department/edit/2";
    final static String DEPARTMENT_EDIT_URI_SUCCESS = "/department/edit/1";
    final static String DEPARTMENT_EDIT_VIEW_NAME = "department/edit";
    final static String DEPARTMENT_DELETE_URI_FAIL = "/department/delete/2";
    final static String DEPARTMENT_DELETE_URI_SUCCESS = "/department/delete/1";
    final static String DEPARTMENT_DELETE_VIEW_NAME = "department/delete";
    final static String DEPARTMENT_DELETE_FAIL_PARAM_ID = "222";
    final static String DEPARTMENT_DELETE_PARAM_ID = "1";

    protected final Logger LOG = Logger.getLogger(getClass());

    private List<Department> departmentList;

    /**
     * Initial setup take place here
     * All Mocks setup here
     * @throws Exception if smth fails
     */
    @Before
    public void setUp () throws Exception {

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        // Populating department List
        departmentList = new ArrayList<Department>();
        final Department department1 = new Department();
        department1.setId(DEPARTMENT1_ID);
        department1.setDepartmentName(DEPARTMENT1_NAME);
        department1.setDeanFirstName(DEPARTMENT1_DEAN_NAME);
        department1.setDeanLastName(DEPARTMENT1_DEAN_LASTNAME);
        departmentList.add(department1);

        cookah = new Cookah();
        cookah.setReturnPath (BASE_URL + DEPARTMENT_VIEW_URI);
        cookah.setReturnUri (DEPARTMENT_VIEW_URI);
        controller.setCookah (cookah);

        //
        // Mocks setup
        //
        when(departmentService.findAllDepartments())
                .thenReturn(departmentList);

        // Only one department can be found by DepartmentService
        doAnswer(new Answer<Department>() {
            public Department answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                LOG.debug("Department id is " + args[0]);
                if (args[0].equals(DEPARTMENT1_ID)) {
                    LOG.debug("Department returned");
                    return department1;
                } else {
                    LOG.debug("Department not returned");
                    return null;
                }
            }
        }).when(departmentService).
                findDepartmentById(anyLong());

        doNothing()
                .when (departmentService)
                .addDepartment(any(Department.class));
        when (defaultValuesService.getBaseUrl()).
                thenReturn(BASE_URL);
        when (defaultValuesService. getDepartmentReturnUri()).
                thenReturn(DEPARTMENT_VIEW_URI);

        // Department Edit Validator Mock
        // Checks only departmentName, rejects if empty
        doAnswer(new Answer<Object>() {
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                LOG.debug ("DepartmentEditValidatorTest: " + args[0]);
                DepartmentForm departmentForm = (DepartmentForm) args[0];
                LOG.debug ("DepartmentEditValidatorTest: " + departmentForm);
                LOG.debug ("DepartmentEditValidatorTest id:" + departmentForm.getId());
                BindingResult bindingResult = (BindingResult) args[1];
                if (!departmentForm.getDepartmentName().isEmpty()) {
                    LOG.debug ("DepartmentEditValidatorTest: Not rejecting");
                    return null;
                } else {
                    LOG.debug ("DepartmentEditValidatorTest: Rejecting");
                    bindingResult.rejectValue("departmentName","Test reject if id is not 1");
                    return null;
                }
            }
        }).when(departmentEditValidator).validate(anyObject(), any(org.springframework.validation.Errors.class));

        // Department Delete Validator Mock
        // Checks if department not null, otherwise throw ServiceGeneralException
        doAnswer(new Answer<Object>() {
            public Object answer(InvocationOnMock invocation) throws Exception {
                Object[] args = invocation.getArguments();
                Department department = (Department) args[0];
                if (department != null) {
                    LOG.debug ("DepartmentDelete: Not rejecting");
                    return null;
                } else {
                    LOG.debug ("DepartmentDelete: Rejecting");
                    throw new ServiceGeneralException("",new Exception());
                }
            }
        }).when(departmentService).deleteDepartment(any(Department.class));
    }

    /**
     * Testing if "department view" controller works fine
     * @throws Exception if smth. fails
     */
    @Test
    public void testDepartmentView() throws Exception {

        mockMvc.perform (get(DEPARTMENT_VIEW_URI)
                .accept (MediaType.TEXT_HTML))
                .andExpect (status().isOk())
                .andExpect(view().name(DEPARTMENT_VIEW_VIEW_NAME))
                .andExpect (model().attribute(ATTRIBUTE_DEPARTMENTS, departmentList))
                .andExpect (model().attributeExists(ATTRIBUTE_COOKIE))
                .andExpect (model().attribute(ATTRIBUTE_COOKIE, cookah));
    }

    /**
     * Testing if "department add" controller requested with GET method works fine
     * @throws Exception if smth. fails
     */
    @Test
    public void testDepartmentAddGet() throws Exception {

        mockMvc.perform(get(DEPARTMENT_ADD_URI)
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect (view().name(DEPARTMENT_ADD_VIEW_NAME))
                .andExpect (model().attributeExists(ATTRIBUTE_DEPARTMENT_FORM))
                .andExpect(model().attribute(ATTRIBUTE_COOKIE, cookah));
    }

    /**
     * Testing if "department add" controller fails with POST method
     * if using not filled form
     * @throws Exception if smth. fails
     */
    @Test
    public void testDepartmentAddPostFail() throws Exception {

        mockMvc.perform(post(DEPARTMENT_ADD_URI)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param(PARAM_NAME_DEPARTMENT_NAME, ""))
                .andExpect (status().isOk())
                .andExpect (view().name(DEPARTMENT_ADD_VIEW_NAME))
        .andExpect(model().attributeHasErrors(ATTRIBUTE_DEPARTMENT_FORM));
    }

    /**
     * Testing if "department add" controller works fine with POST method
     * if for is filled properly
     * @throws Exception if smth fails.
     */
    @Test
    public void testDepartmentAddPostSuccess() throws Exception {

        mockMvc.perform(post(DEPARTMENT_ADD_URI)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param(PARAM_NAME_DEPARTMENT_NAME, DEPARTMENT_ADD_PARAM_NAME)
                .param(PARAM_NAME_DEAN_FIRSTNAME, DEPARTMENT_ADD_PARAM_DEAN_NAME)
                .param(PARAM_NAME_DEAN_LASTNAME, DEPARTMENT_ADD_PARAM_DEAN_LASTNAME))
                .andExpect (status().isMovedTemporarily())
                .andExpect(view().name("redirect:" + BASE_URL + DEPARTMENT_VIEW_URI));
    }

    /**
     * Testing if "department edit" controller fails when requested with GET method
     * using wrong id
     * @throws Exception if smth. fails
     */
    @Test
    public void testDepartmentEditGetFail() throws Exception {

        mockMvc.perform (get(DEPARTMENT_EDIT_URI_FAIL)
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect (view().name(DEPARTMENT_EDIT_VIEW_NAME))
                .andExpect (model().attributeDoesNotExist(ATTRIBUTE_DEPARTMENT_FORM));
    }

    /**
     * Testing if "department edit" controller works fine GET
     * with appropriate Url requested
     * @throws Exception if smth. fails
     */
    @Test
    public void testDepartmentEditGetSuccess() throws Exception {

        mockMvc.perform (get(DEPARTMENT_EDIT_URI_SUCCESS)
                .accept(MediaType.TEXT_HTML))
                .andExpect (status().isOk())
                .andExpect (view().name(DEPARTMENT_EDIT_VIEW_NAME))
                .andExpect (model().attributeExists(ATTRIBUTE_DEPARTMENT_FORM));
    }

    /**
     * Testing if "department edit" controller fails fine with POST method if id not exits 
     * @throws Exception if smth. fails
     */
    @Test
    public void testDepartmentEditPostFail() throws Exception {

        mockMvc.perform(post(DEPARTMENT_EDIT_URI_SUCCESS)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param(PARAM_NAME_DEPARTMENT_ID, DEPARTMENT_EDIT_PARAM_ID)
                .param(PARAM_NAME_DEPARTMENT_NAME, "")
                .param(PARAM_NAME_DEAN_FIRSTNAME, DEPARTMENT_EDIT_PARAM_DEAN_NAME)
                .param(PARAM_NAME_DEAN_LASTNAME, DEPARTMENT_EDIT_PARAM_DEAN_LASTNAME))
                .andExpect (view().name(DEPARTMENT_EDIT_VIEW_NAME))
                .andExpect(status().isOk());

    }

    /**
     * Testing if "department edit" controller works fine with POST method
     * @throws Exception if smth. fails
     */
    @Test
    public void testDepartmentEditPostSuccess() throws Exception {

        mockMvc.perform(post(DEPARTMENT_EDIT_URI_SUCCESS)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param(PARAM_NAME_DEPARTMENT_ID, DEPARTMENT_EDIT_PARAM_ID)
                .param(PARAM_NAME_DEPARTMENT_NAME, DEPARTMENT_EDIT_PARAM_NAME)
                .param(PARAM_NAME_DEAN_FIRSTNAME, DEPARTMENT_EDIT_PARAM_DEAN_NAME)
                .param(PARAM_NAME_DEAN_LASTNAME, DEPARTMENT_EDIT_PARAM_DEAN_LASTNAME))
                .andExpect(status().isMovedTemporarily())
                .andExpect(view().name("redirect:" + BASE_URL + DEPARTMENT_VIEW_URI));
    }

    /**
     * Testing if "department delete" controller fails whe requested with bad id param
     * @throws Exception if smth fails
     */
    @Test
    public void testDepartmentDeleteGetFail() throws Exception {

        mockMvc.perform (get(DEPARTMENT_DELETE_URI_FAIL)
                .accept(MediaType.TEXT_HTML))
                .andExpect (status().isOk())
                .andExpect (view().name(DEPARTMENT_DELETE_VIEW_NAME))
                .andExpect (model().attributeDoesNotExist(ATTRIBUTE_DEPARTMENT_FORM));
    }


    /**
     * Testing if "department delete" controller works fine when requested with gopd id param
     * @throws Exception if smth. fails
     */
    @Test
    public void testDepartmentDeleteGetSuccess() throws Exception {

        mockMvc.perform (get(DEPARTMENT_DELETE_URI_SUCCESS)
                .accept(MediaType.TEXT_HTML))
                .andExpect (status().isOk())
                .andExpect (view().name(DEPARTMENT_DELETE_VIEW_NAME))
                .andExpect (model().attributeExists(ATTRIBUTE_DEPARTMENT_FORM));
    }

    /**
     * Testing if "department delete" fails correctly with POST method if id not exists
     * @throws Exception if smth. fails
     */
    @Test
    public void testDepartmentDeletePostFail() throws Exception {

        mockMvc.perform(post(DEPARTMENT_DELETE_URI_SUCCESS)
                .contentType (MediaType.APPLICATION_FORM_URLENCODED)
                .param (PARAM_NAME_DEPARTMENT_ID, DEPARTMENT_DELETE_FAIL_PARAM_ID))
                .andExpect(status().isMovedTemporarily())
                .andExpect(view().name("redirect:" + BASE_URL + DEPARTMENT_VIEW_URI));
    }

    /**
     * Testing if "department delete" works fine with POST method
     * @throws Exception if smth. fails
     */
    @Test
    public void testDepartmentDeletePostSuccess() throws Exception {

        mockMvc.perform(post(DEPARTMENT_DELETE_URI_SUCCESS)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param(PARAM_NAME_DEPARTMENT_ID, DEPARTMENT_DELETE_PARAM_ID))
                .andExpect(status().isMovedTemporarily())
                .andExpect(view().name("redirect:" + BASE_URL + DEPARTMENT_VIEW_URI));
    } 
}
