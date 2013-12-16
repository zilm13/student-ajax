package it._7bits.web.student.web.validator;

import it._7bits.web.student.domain.SubDepartment;
import it._7bits.web.student.service.ISubDepartmentService;
import it._7bits.web.student.service.ServiceGeneralException;
import it._7bits.web.student.web.form.SubDepartmentForm;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * Validator for "SubDepartment Delete" Form
 */
@Component
public class SubDepartmentDeleteValidator implements Validator {

    @Autowired
    private ISubDepartmentService subDepartmentService;
    protected final Logger LOG = Logger.getLogger(getClass());

    public SubDepartmentDeleteValidator () {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    public boolean supports(Class clazz) {
        return SubDepartmentForm.class.equals(clazz);
    }

    public void validate (Object object, Errors errors) {

        ValidationUtils.rejectIfEmpty(errors, "id", "NotNull.subDepartmentDelete.id");
        SubDepartmentForm subDepartmentForm = (SubDepartmentForm) object;
        if (subDepartmentForm.getId() != null) {
            try {
                SubDepartment subDepartment = subDepartmentService.
                        findSubDepartmentById (subDepartmentForm.getId());
                if (subDepartment == null) {
                    errors.rejectValue ("id", "NotExist.subDepartmentDelete.id");
                }
            } catch (ServiceGeneralException e) {
                errors.rejectValue ("id", "ServerError.subDepartmentDelete.id");
                LOG.warn ("Validator couldn't validate subDepartment instance because of service error: ", e);
            }
        }
    }

    public void setSubDepartmentService(ISubDepartmentService subDepartmentService) {
        this.subDepartmentService = subDepartmentService;
    }
}
