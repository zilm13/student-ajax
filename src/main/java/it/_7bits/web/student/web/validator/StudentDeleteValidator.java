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


/**
 *  Validator for "Student Delete" Form
 */
@Service
public class StudentDeleteValidator implements Validator {
    @Autowired
    private IStudentService studentService;
    protected final Logger LOG = Logger.getLogger(getClass());

    public StudentDeleteValidator() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    public boolean supports(Class clazz) {
        return StudentForm.class.equals(clazz);
    }

    public void validate (Object object, Errors errors) {
        ValidationUtils.rejectIfEmpty (errors, "id", "Error.studentDelete.id");
        StudentForm studentForm = (StudentForm) object;
        if (studentForm.getId() != null) {
            try {
                Student student = studentService
                        .findStudentById (studentForm.getId());
                if (student ==  null) {
                    errors.rejectValue ("id", "NotExist.studentDelete.id");
                }
            } catch (ServiceGeneralException e)  {
                errors.rejectValue ("id", "ServerError.studentDelete.id");
                LOG.warn ("Validator couldn't validate student instance because of service error: ", e);
            }
        }
    }

    public void setStudentService(IStudentService studentService) {
        this.studentService = studentService;
    }
}
