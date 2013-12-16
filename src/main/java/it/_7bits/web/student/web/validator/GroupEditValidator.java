package it._7bits.web.student.web.validator;

import it._7bits.web.student.domain.Group;
import it._7bits.web.student.service.IGroupService;
import it._7bits.web.student.service.ServiceGeneralException;
import it._7bits.web.student.web.form.GroupForm;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * Validator for "Group Edit" Form
 */
@Component
public class GroupEditValidator implements Validator{

    @Autowired
    private IGroupService groupService;
    protected final Logger LOG = Logger.getLogger(getClass());

    public GroupEditValidator () {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    public boolean supports(Class clazz) {
        return GroupForm.class.equals(clazz);
    }

    public void validate (Object object, Errors errors) {

        ValidationUtils.rejectIfEmpty(errors, "id", "NotNull.groupEdit.id");
        ValidationUtils.rejectIfEmpty (errors, "groupName", "NotEmpty.groupEdit.groupName");
        ValidationUtils.rejectIfEmpty (errors, "department", "NotNull.groupEdit.department");
        GroupForm groupForm = (GroupForm) object;
        if (groupForm.getId() != null) {
            try {
                Group group = groupService.
                        findGroupById(groupForm.getId());
                if (group == null) {
                    errors.rejectValue ("id", "NotExist.groupEdit.id");
                }
            } catch (ServiceGeneralException e) {
                errors.rejectValue ("id", "ServerError.groupEdit.id");
                LOG.warn ("Validator couldn't validate group instance because of service error: ", e);
            }
        }
    }

    public void setGroupService(IGroupService groupService) {
        this.groupService = groupService;
    }
}
