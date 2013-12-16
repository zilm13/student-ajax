package it._7bits.web.student.web.validator;


import it._7bits.web.student.domain.Student;
import it._7bits.web.student.service.IStudentService;
import it._7bits.web.student.service.ServiceGeneralException;
import it._7bits.web.student.web.form.StudentForm;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.util.List;

/**
 *  Validator for "Student Edit" Form
 */
@Service
public class StudentAddValidator implements Validator {
    @Autowired
    private IStudentService studentService;
    protected final Logger LOG = Logger.getLogger(getClass());

    public StudentAddValidator() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    public boolean supports(Class clazz) {
        return StudentForm.class.equals(clazz);
    }

    public void validate (Object object, Errors errors) {
        ValidationUtils.rejectIfEmpty (errors, "firstName", "NotEmpty.studentAdd.firstName");
        ValidationUtils.rejectIfEmpty (errors, "lastName", "NotEmpty.studentAdd.lastName");
        ValidationUtils.rejectIfEmpty (errors, "group", "NotNull.studentAdd.group");
        StudentForm studentForm = (StudentForm) object;
        try {
            if (studentForm.getGroup() != null && studentForm.getIsHead()) {
                List<Student> heads = studentService
                        .findHeadStudentsInGroup (studentForm.getGroup().getId());
                if (!heads.isEmpty()) {
                    errors.rejectValue("isHead", "Error.studentAdd.isHead");
                }
            }
        } catch (ServiceGeneralException e)  {
            errors.rejectValue ("id", "ServerError.studentAdd.id");
            LOG.warn ("Validator couldn't validate student instance because of service error: ", e);
        }

    }

    public void setStudentService(IStudentService studentService) {
        this.studentService = studentService;
    }
}
