package it._7bits.web.student.web.validator;

import it._7bits.web.student.domain.Department;
import it._7bits.web.student.domain.Group;
import it._7bits.web.student.domain.Student;
import it._7bits.web.student.domain.SubDepartment;
import it._7bits.web.student.service.IStudentService;
import it._7bits.web.student.web.form.StudentForm;
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
 * StudentDeleteValidator Tests
 */
public class StudentDeleteValidatorTests {

    @InjectMocks
    StudentDeleteValidator studentDeleteValidator;
    @Mock
    IStudentService studentService;

    final static Long SUB1_ID = 1L;
    final static String SUB1_NAME = "Прикладной алгебры";
    final static Long GROUP1_ID = 1L;
    final static String GROUP1_NAME = "ММ-05";
    final static Long DEPARTMENT1_ID = 1L;
    final static String DEPARTMENT1_NAME = "Математический";
    final static String DEPARTMENT1_DEAN_NAME = "Андрей";
    final static String DEPARTMENT1_DEAN_LASTNAME = "Иванов";
    final static Long STUDENT1_ID = 1L;
    final static String STUDENT1_NAME = "Иван";
    final static String STUDENT1_LASTNAME = "Иванов";
    final static boolean STUDENT1_ISHEAD = false;
    final static Long STUDENT_SUCCESS_ID = 1L;
    final static Long STUDENT_FAIL_NULL_ID = null;
    final static Long STUDENT_FAIL_ID = 2L;
    final static String STUDENT_SUCCESS_NAME = "Иван";
    final static String STUDENT_SUCCESS_LASTNAME = "Иванов";
    final static boolean STUDENT_SUCCESS_ISHEAD = false;
    final static boolean STUDENT_SUCCESS_FORCEHEAD = false;
    SubDepartment STUDENT_SUCCESS_SUB;
    Group STUDENT_SUCCESS_GROUP;
    final static String STUDENT_SUCCESS_EMPTY_NAME = "";
    final static String STUDENT_SUCCESS_EMPTY_LASTNAME = "";
    final static SubDepartment STUDENT_SUCCESS_EMPTY_SUB = null;
    final static Group STUDENT_SUCCESS_EMPTY_GROUP = null;
    final static String STUDENT_MODEL_NAME = "studentForm";
    protected final Logger LOG = Logger.getLogger(getClass());

    /**
     * Initial setup
     * All mocks setup here
     * @throws Exception if smth goes wrong
     */
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        studentDeleteValidator.setStudentService(studentService);
        final Department department1 = new Department();
        department1.setId (DEPARTMENT1_ID);
        department1.setDepartmentName (DEPARTMENT1_NAME);
        department1.setDeanFirstName (DEPARTMENT1_DEAN_NAME);
        department1.setDeanLastName (DEPARTMENT1_DEAN_LASTNAME);
        final Group group1 = new Group();
        group1.setId (GROUP1_ID);
        group1.setGroupName (GROUP1_NAME);
        group1.setDepartment (department1);
        STUDENT_SUCCESS_GROUP = group1;
        final SubDepartment subDepartment1 = new SubDepartment();
        subDepartment1.setId (SUB1_ID);
        subDepartment1.setSubDepartmentName (SUB1_NAME);
        subDepartment1.setDepartment (department1);
        STUDENT_SUCCESS_SUB = subDepartment1;
        final Student student1 = new Student();
        student1.setId(STUDENT1_ID);
        student1.setFirstName(STUDENT1_NAME);
        student1.setLastName(STUDENT1_LASTNAME);
        student1.setGroup(group1);
        student1.setSubDepartment(subDepartment1);
        student1.setIsHead(STUDENT1_ISHEAD);

        // Mock setup for Student Service
        doAnswer (new Answer<Student>() {
            public Student answer (InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                LOG.debug ("Student id is " + args[0]);
                if (args[0].equals (STUDENT1_ID)) {
                    LOG.debug ("Student returned");
                    return student1;
                } else {
                    LOG.debug ("Student not returned");
                    return null;
                }
            }
        }).when (studentService).
                findStudentById(anyLong());
    }

    /**
     * Test if StudentDeleteValidator not fails on correct input
     */
    @Test
    public void studentDeleteValidatorSuccess() {
        StudentForm studentForm =  new StudentForm();
        studentForm.setId (STUDENT_SUCCESS_ID);
        studentForm.setFirstName (STUDENT_SUCCESS_NAME);
        studentForm.setLastName (STUDENT_SUCCESS_LASTNAME);
        studentForm.setGroup (STUDENT_SUCCESS_GROUP);
        studentForm.setSubDepartment (STUDENT_SUCCESS_SUB);
        studentForm.setIsHead (STUDENT_SUCCESS_ISHEAD);
        studentForm.setForceHead (STUDENT_SUCCESS_FORCEHEAD);
        // We need some implementation of BindingResult here
        BindingResult result = new BindException(studentForm, STUDENT_MODEL_NAME);
        studentDeleteValidator.validate (studentForm, result);
        assert (!result.hasErrors());
    }

    /**
     * Test if StudentDeleteValidator fails on null id
     */
    @Test
    public void studentDeleteValidatorFailNullId() {
        StudentForm studentForm =  new StudentForm();
        studentForm.setId (STUDENT_FAIL_NULL_ID);
        studentForm.setFirstName (STUDENT_SUCCESS_NAME);
        studentForm.setLastName (STUDENT_SUCCESS_LASTNAME);
        studentForm.setGroup (STUDENT_SUCCESS_GROUP);
        studentForm.setSubDepartment (STUDENT_SUCCESS_SUB);
        studentForm.setIsHead (STUDENT_SUCCESS_ISHEAD);
        studentForm.setForceHead (STUDENT_SUCCESS_FORCEHEAD);
        // We need some implementation of BindingResult here
        BindingResult result = new BindException(studentForm, STUDENT_MODEL_NAME);
        studentDeleteValidator.validate (studentForm, result);
        assert (result.hasErrors());
    }

    /**
     * Test if StudentDeleteValidator fails on null id
     */
    @Test
    public void studentDeleteValidatorFailId() {
        StudentForm studentForm =  new StudentForm();
        studentForm.setId (STUDENT_FAIL_ID);
        studentForm.setFirstName (STUDENT_SUCCESS_NAME);
        studentForm.setLastName (STUDENT_SUCCESS_LASTNAME);
        studentForm.setGroup (STUDENT_SUCCESS_GROUP);
        studentForm.setSubDepartment (STUDENT_SUCCESS_SUB);
        studentForm.setIsHead (STUDENT_SUCCESS_ISHEAD);
        studentForm.setForceHead (STUDENT_SUCCESS_FORCEHEAD);
        // We need some implementation of BindingResult here
        BindingResult result = new BindException(studentForm, STUDENT_MODEL_NAME);
        studentDeleteValidator.validate (studentForm, result);
        assert (result.hasErrors());
    }

    /**
     * Test if StudentDeleteValidator not cares about all fields except id
     */
    @Test
    public void studentDeleteValidatorSuccessEmpty() {
        StudentForm studentForm =  new StudentForm();
        studentForm.setId (STUDENT_SUCCESS_ID);
        studentForm.setFirstName (STUDENT_SUCCESS_EMPTY_NAME);
        studentForm.setLastName (STUDENT_SUCCESS_EMPTY_LASTNAME);
        studentForm.setGroup (STUDENT_SUCCESS_EMPTY_GROUP);
        studentForm.setSubDepartment (STUDENT_SUCCESS_EMPTY_SUB);
        // We need some implementation of BindingResult here
        BindingResult result = new BindException(studentForm, STUDENT_MODEL_NAME);
        studentDeleteValidator.validate (studentForm, result);
        assert (!result.hasErrors());
    }
}
