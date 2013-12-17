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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doAnswer;

/**
 * StudentEditValidator Tests
 */
public class StudentEditValidatorTests {

    @InjectMocks
    StudentEditValidator studentEditValidator;
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
    final static boolean STUDENT1_ISHEAD = true;
    final static Long STUDENT2_ID = 2L;
    final static String STUDENT2_NAME = "Артём";
    final static String STUDENT2_LASTNAME = "Полуванов";
    final static boolean STUDENT2_ISHEAD = false;
    final static Long STUDENT_SUCCESS_ID = 1L;
    final static Long STUDENT_FAIL_NULL_ID = null;
    final static Long STUDENT_FAIL_ID = 3L;
    final static String STUDENT_SUCCESS_NAME = "Иван";
    final static String STUDENT_SUCCESS_LASTNAME = "Иванов";
    final static boolean STUDENT_SUCCESS_ISHEAD = false;
    final static boolean STUDENT_SUCCESS_FORCEHEAD = false;
    SubDepartment STUDENT_SUCCESS_SUB;
    Group STUDENT_SUCCESS_GROUP;
    final static String STUDENT_FAIL_NAME = "";
    final static String STUDENT_FAIL_LASTNAME = "";
    final static SubDepartment STUDENT_SUCCESS_EMPTY_SUB = null;
    final static Group STUDENT_FAIL_GROUP = null;
    final static boolean STUDENT1_NOT_FAIL_TRUE_HEAD = true;
    final static boolean STUDENT2_FAIL_TRUE_HEAD = true;
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
        studentEditValidator.setStudentService (studentService);
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
        final Student student2 = new Student();
        student2.setId (STUDENT2_ID);
        student2.setFirstName(STUDENT2_NAME);
        student2.setLastName (STUDENT2_LASTNAME);
        student2.setGroup (group1);
        student2.setSubDepartment (subDepartment1);
        student2.setIsHead (STUDENT2_ISHEAD);

        // Mock setup for Student Service (find students by id)
        doAnswer (new Answer<Student>() {
            public Student answer (InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                LOG.debug ("Student id is " + args[0]);
                if (args[0] == null) {
                    return null;
                }
                if (args[0].equals (STUDENT1_ID)) {
                    LOG.debug ("Student 1 returned");
                    return student1;
                } else if (args[0].equals (STUDENT2_ID)) {
                    LOG.debug ("Student 2 returned");
                    return student2;
                } else {
                    LOG.debug ("Student not returned");
                    return null;
                }
            }
        }).when (studentService).
                findStudentById(anyLong());


        // Mock setup for Student Service (find heads)
        doAnswer (new Answer<List<Student>>() {
            public List<Student> answer (InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                LOG.debug ("Group id is " + args[0]);
                if (args[0].equals (GROUP1_ID)) {
                    LOG.debug ("Student head returned");
                    List<Student> studentList = new ArrayList<>();
                    studentList.add (student1);
                    return studentList;
                } else {
                    LOG.debug ("Student not returned");
                    return null;
                }
            }
        }).when (studentService).
                findHeadStudentsInGroup(anyLong());
    }

    /**
     * Test if StudentEditValidator not fails on correct input
     */
    @Test
    public void studentEditValidatorSuccess() {
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
        studentEditValidator.validate (studentForm, result);
        assert (!result.hasErrors());
    }

    /**
     * Test if StudentEditValidator fails on null id
     */
    @Test
    public void studentEditValidatorFailNullId() {
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
        studentEditValidator.validate (studentForm, result);
        assert (result.hasErrors());
    }

    /**
     * Test if StudentEditValidator fails on wrong id
     */
    @Test
    public void studentEditValidatorFailId() {
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
        studentEditValidator.validate (studentForm, result);
        assert (result.hasErrors());
    }

    /**
     * Test if StudentEditValidator fails on wrong name
     */
    @Test
    public void studentEditValidatorFailName() {
        StudentForm studentForm =  new StudentForm();
        studentForm.setId (STUDENT_SUCCESS_ID);
        studentForm.setFirstName (STUDENT_FAIL_NAME);
        studentForm.setLastName (STUDENT_SUCCESS_LASTNAME);
        studentForm.setGroup (STUDENT_SUCCESS_GROUP);
        studentForm.setSubDepartment (STUDENT_SUCCESS_SUB);
        studentForm.setIsHead (STUDENT_SUCCESS_ISHEAD);
        studentForm.setForceHead (STUDENT_SUCCESS_FORCEHEAD);
        // We need some implementation of BindingResult here
        BindingResult result = new BindException(studentForm, STUDENT_MODEL_NAME);
        studentEditValidator.validate (studentForm, result);
        assert (result.hasErrors());
    }

    /**
     * Test if StudentEditValidator fails on wrong last name
     */
    @Test
    public void studentEditValidatorFailLastName() {
        StudentForm studentForm =  new StudentForm();
        studentForm.setId (STUDENT_SUCCESS_ID);
        studentForm.setFirstName (STUDENT_SUCCESS_NAME);
        studentForm.setLastName (STUDENT_FAIL_LASTNAME);
        studentForm.setGroup (STUDENT_SUCCESS_GROUP);
        studentForm.setSubDepartment (STUDENT_SUCCESS_SUB);
        studentForm.setIsHead (STUDENT_SUCCESS_ISHEAD);
        studentForm.setForceHead (STUDENT_SUCCESS_FORCEHEAD);
        // We need some implementation of BindingResult here
        BindingResult result = new BindException(studentForm, STUDENT_MODEL_NAME);
        studentEditValidator.validate (studentForm, result);
        assert (result.hasErrors());
    }

    /**
     * Test if StudentEditValidator fails on wrong group
     */
    @Test
    public void studentEditValidatorFailGroup() {
        StudentForm studentForm =  new StudentForm();
        studentForm.setId (STUDENT_SUCCESS_ID);
        studentForm.setFirstName (STUDENT_SUCCESS_NAME);
        studentForm.setLastName (STUDENT_SUCCESS_LASTNAME);
        studentForm.setGroup (STUDENT_FAIL_GROUP);
        studentForm.setSubDepartment (STUDENT_SUCCESS_SUB);
        studentForm.setIsHead (STUDENT_SUCCESS_ISHEAD);
        studentForm.setForceHead (STUDENT_SUCCESS_FORCEHEAD);
        // We need some implementation of BindingResult here
        BindingResult result = new BindException(studentForm, STUDENT_MODEL_NAME);
        studentEditValidator.validate (studentForm, result);
        assert (result.hasErrors());
    }

    /**
     * Test if StudentEditValidator not fails on wrong sub
     */
    @Test
    public void studentEditValidatorNotFailSub() {
        StudentForm studentForm =  new StudentForm();
        studentForm.setId (STUDENT_SUCCESS_ID);
        studentForm.setFirstName (STUDENT_SUCCESS_NAME);
        studentForm.setLastName (STUDENT_SUCCESS_LASTNAME);
        studentForm.setGroup (STUDENT_SUCCESS_GROUP);
        studentForm.setSubDepartment (STUDENT_SUCCESS_EMPTY_SUB);
        studentForm.setIsHead (STUDENT_SUCCESS_ISHEAD);
        studentForm.setForceHead (STUDENT_SUCCESS_FORCEHEAD);
        // We need some implementation of BindingResult here
        BindingResult result = new BindException(studentForm, STUDENT_MODEL_NAME);
        studentEditValidator.validate (studentForm, result);
        assert (!result.hasErrors());
    }


    /**
     * Test if StudentEditValidator not fails on updating head
     */
    @Test
    public void studentEditValidatorNotFailUpdateHead() {
        StudentForm studentForm =  new StudentForm();
        studentForm.setId (STUDENT_SUCCESS_ID);
        studentForm.setFirstName (STUDENT_SUCCESS_NAME);
        studentForm.setLastName (STUDENT_SUCCESS_LASTNAME);
        studentForm.setGroup (STUDENT_SUCCESS_GROUP);
        studentForm.setSubDepartment (STUDENT_SUCCESS_SUB);
        studentForm.setIsHead (STUDENT_SUCCESS_ISHEAD);
        studentForm.setForceHead (STUDENT1_NOT_FAIL_TRUE_HEAD);
        // We need some implementation of BindingResult here
        BindingResult result = new BindException(studentForm, STUDENT_MODEL_NAME);
        studentEditValidator.validate (studentForm, result);
        assert (!result.hasErrors());
    }


    /**
     * Test if StudentEditValidator fails on editing student to head if head already exists
     */
    @Test
    public void studentEditValidatorFailEditHeadIfExists() {
        StudentForm studentForm =  new StudentForm();
        studentForm.setId (STUDENT2_ID);
        studentForm.setFirstName (STUDENT_SUCCESS_NAME);
        studentForm.setLastName (STUDENT_SUCCESS_LASTNAME);
        studentForm.setGroup (STUDENT_SUCCESS_GROUP);
        studentForm.setSubDepartment (STUDENT_SUCCESS_SUB);
        studentForm.setIsHead (STUDENT2_FAIL_TRUE_HEAD);
        studentForm.setForceHead (STUDENT_SUCCESS_FORCEHEAD);
        // We need some implementation of BindingResult here
        BindingResult result = new BindException(studentForm, STUDENT_MODEL_NAME);
        studentEditValidator.validate (studentForm, result);
        assert (result.hasErrors());
    }


    /**
     * Test if StudentEditValidator not cares forcehead
     */
    @Test
    public void studentEditValidatorIgnoreForceHead() {
        StudentForm studentForm =  new StudentForm();
        studentForm.setId (STUDENT_SUCCESS_ID);
        studentForm.setFirstName (STUDENT_SUCCESS_NAME);
        studentForm.setLastName (STUDENT_SUCCESS_LASTNAME);
        studentForm.setGroup (STUDENT_SUCCESS_GROUP);
        studentForm.setSubDepartment (STUDENT_SUCCESS_SUB);
        studentForm.setIsHead (STUDENT_SUCCESS_ISHEAD);
        // We need some implementation of BindingResult here
        BindingResult result = new BindException(studentForm, STUDENT_MODEL_NAME);
        studentEditValidator.validate (studentForm, result);
        assert (!result.hasErrors());
    }
}
