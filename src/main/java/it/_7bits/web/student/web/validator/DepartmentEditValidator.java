package it._7bits.web.student.web.validator;

import it._7bits.web.student.domain.Department;
import it._7bits.web.student.service.IDepartmentService;
import it._7bits.web.student.service.ServiceGeneralException;
import it._7bits.web.student.web.form.DepartmentForm;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * Validator for "Edit Department" Form
 */
@Component
public class DepartmentEditValidator implements Validator {
    @Autowired
    private IDepartmentService departmentService;
    protected final Logger LOG = Logger.getLogger(getClass());

    public DepartmentEditValidator () {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    public boolean supports(Class clazz) {
        return DepartmentForm.class.equals(clazz);
    }

    public void validate (Object object, Errors errors) {

        ValidationUtils.rejectIfEmpty (errors, "id", "NotNull.departmentEdit.id");
        ValidationUtils.rejectIfEmpty (errors, "departmentName", "NotEmpty.departmentEdit.departmentName");
        ValidationUtils.rejectIfEmpty (errors, "deanFirstName", "NotEmpty.departmentEdit.deanFirstName");
        ValidationUtils.rejectIfEmpty (errors, "deanLastName", "NotEmpty.departmentEdit.deanLastName");
        DepartmentForm departmentForm = (DepartmentForm) object;
        if (departmentForm.getId() != null) {
            try {
                LOG.debug ("DepartmentId " + departmentForm.getId());
                Department department = departmentService.
                        findDepartmentById (departmentForm.getId());
                if (department == null) {
                    errors.rejectValue ("id", "NotExist.departmentEdit.id");
                }
            } catch (ServiceGeneralException e) {
                errors.rejectValue ("id", "ServerError.departmentEdit.id");
                LOG.warn ("Validator couldn't validate department instance because of service error: ", e);
            }
        }
    }

    public void setDepartmentService(IDepartmentService departmentService) {
        this.departmentService = departmentService;
    }
}
