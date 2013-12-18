package it._7bits.web.student.web.controller;

import it._7bits.web.student.domain.Cookah;
import it._7bits.web.student.domain.Group;
import it._7bits.web.student.service.*;
import it._7bits.web.student.web.form.GroupForm;
import it._7bits.web.student.web.validator.GroupDeleteValidator;
import it._7bits.web.student.web.validator.GroupEditValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Group section controller
 */
@Controller
@RequestMapping("/group")
public class GroupController {

    protected final Logger LOG = Logger.getLogger(getClass());
    @Autowired
    private IGroupService groupService;
    @Autowired
    private IDepartmentService departmentService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private Cookah cookah;
    @Autowired
    private IDefaultValuesService defaultValuesService;

    /**
     * First page with listing of all groups
     * @param request    HttpServletRequest
     * @return model with all groups
     */
    @RequestMapping("/view")
    public ModelAndView groupView(HttpServletRequest request) {

        ModelAndView model = new ModelAndView("group/view");
        try {
            model.addObject("groups", groupService.findAllGroups());
        } catch (ServiceGeneralException e) {
            model.addObject ("groupsError",
                    messageSource.getMessage ("ServiceGeneralException.Error", null, null));
        }
        cookah.setReturnPath(request.getRequestURL().toString());
        cookah.setReturnUri(request.getServletPath());
        return model;
    }

    /**
     * First page with listing of groups in department
     * @param departmentId    Id of department
     * @param request         HttpServletRequest
     * @return model with groups in exact department
     */
    @RequestMapping("/view/{departmentId}")
    public ModelAndView groupViewInDep(@PathVariable Long departmentId,
                                  HttpServletRequest request) {

        ModelAndView model = new ModelAndView ("group/view");
        cookah.setReturnPath (request.getRequestURL().toString());
        cookah.setReturnUri (request.getServletPath());
        try {
            model.addObject ("titleplus", departmentService
                    .findDepartmentById(departmentId).getDepartmentName());
            model.addObject ("departmentId",departmentId);
            model.addObject ("groups", groupService.findGroupsInDep (departmentId));
        } catch (ServiceGeneralException e) {
            model.addObject ("groupsError",
                    messageSource.getMessage ("ServiceGeneralException.Error", null, null));
        }
        return model;
    }

    /**
     * Group Add controller requested with GET method
     * @param groupForm Group Form model attribute
     * @return model for "group add"
     */
    @RequestMapping(value={"/add","/add/"}, method = RequestMethod.GET)
    public ModelAndView groupAdd (GroupForm groupForm) {

        ModelAndView model = new ModelAndView("group/add");
        try {
            model.addObject("departments", departmentService.findAllDepartments());
        } catch (ServiceGeneralException e) {
            model.addObject("groupError",
                    messageSource.getMessage("ServiceGeneralException.Error", null, null));
        }
        return model;
    }

    /**
     * Group Add controller with department param requested with GET method
     * @param departmentId    id of department to auto-select
     * @param groupForm        Group Form model attribute
     * @return model for "group add"
     */
    @RequestMapping(value="/add/{departmentId}", method = RequestMethod.GET)
    public ModelAndView groupAddinDep (@PathVariable Long departmentId, GroupForm groupForm) {

        ModelAndView model = new ModelAndView("group/add");
        try {
            groupForm.setDepartment (departmentService
                    .findDepartmentById(departmentId));
            model.addObject("groupForm", groupForm);
            model.addObject("departments", departmentService.findAllDepartments());
        } catch (ServiceGeneralException e) {
            model.addObject("groupError",
                    messageSource.getMessage("ServiceGeneralException.Error", null, null));
        }
        return model;
    }

    /**
     * Group Add controller requested with POST method
     * @param groupForm             Group Form  model attribute
     * @param bindingResult         BindingResult
     * @param redirectAttributes    RedirectAttributes
     * @return "Add group" model if errors found
     * otherwise redirects to "group view" with successful message
     */
    @RequestMapping(value={"/add","/add/*"}, method = RequestMethod.POST)
    public ModelAndView groupAddValidation (@Valid @ModelAttribute("groupForm")
                                                GroupForm groupForm,
                                           BindingResult bindingResult,
                                           RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            LOG.info("Adding group have errors");
            ModelAndView model = new ModelAndView("group/add");
            model.addObject("errors", bindingResult.getFieldError().getDefaultMessage());
            try {
                model.addObject("departments", departmentService.findAllDepartments());
            } catch (ServiceGeneralException e) {
                model.addObject("groupError",
                        messageSource.getMessage("ServiceGeneralException.Error", null, null));
            }
            return model;
        }

        Group group = new Group();
        group.setGroupName (groupForm.getGroupName());
        group.setDepartment (groupForm.getDepartment());

        try {
            LOG.debug("Group department name is: " + group.getDepartment().getDepartmentName());
            groupService.addGroup(group);
            LOG.info ("Adding group does not have errors");
            String greenMessage = messageSource.getMessage ("group.add.greenmessage",
                    new Object[] {group.getGroupName(),
                            group.getDepartment().getDepartmentName()}, null);
            cookah.setGreenMessage(greenMessage);
        } catch (ServiceGeneralException e) {
            String redMessage = messageSource.getMessage ("group.add.failmessage",
                    new Object[] {group.getGroupName(),
                            group.getDepartment().getDepartmentName()}, null);
            cookah.setRedMessage (redMessage);
        }
        return new ModelAndView("redirect:" + cookah.getReturnUri());
    }

    /**
     * Group Edit controller requested with GET method
     * If group not exist or service failure,
     * adds groupError to model
     * @param groupId id of Group for edit
     * @return model and view for group edit
     */
    @RequestMapping(value="/edit/{groupId}", method = RequestMethod.GET)
    public ModelAndView groupEdit (@PathVariable Long groupId) {

         ModelAndView model = new ModelAndView("group/edit");
        try {
            Group group = groupService.findGroupById(groupId);
            if (group == null) {
                model.addObject("groupError",
                        messageSource.getMessage("NotExist.groupEdit.id", null, null));
            } else {
                GroupForm groupForm = new GroupForm();
                groupForm.setId (group.getId());
                groupForm.setGroupName(group.getGroupName());
                groupForm.setDepartment(group.getDepartment());
                model.addObject("groupForm", groupForm);
                model.addObject("departments", departmentService.findAllDepartments());
            }
        } catch (ServiceGeneralException e) {
            model.addObject("groupError",
                    messageSource.getMessage("ServiceGeneralException.Error", null, null));
        }
        return model;
    }

    /**
     * Group Edit controller requested with POST method
     * If errors found, returns to edit model, with error messages,
     * otherwise returns to "Group view" page with success message
     * (or if service update fails with fail  message)
     * @param groupId          Id of group to edit
     * @param groupForm        Group Form  model attribute
     * @param bindingResult         BindingResult
     * @param redirectAttributes    RedirectAttributes
     * @return "group edit" view if errors found otherwise
     * redirects to "group view"
     */
    @RequestMapping(value="/edit/{groupId}", method = RequestMethod.POST)
    public ModelAndView groupEditValidation (@PathVariable Long groupId,
                                             @ModelAttribute("groupForm")
                                             GroupForm groupForm,
                                             BindingResult bindingResult,
                                             RedirectAttributes redirectAttributes) {

        GroupEditValidator groupEditValidator = new GroupEditValidator();
        groupEditValidator.validate (groupForm, bindingResult);
        if (bindingResult.hasErrors()) {
            ModelAndView model = new ModelAndView("group/edit");
            LOG.info("Editing group has errors");
            model.addObject("errors",
                    bindingResult.getFieldError().getDefaultMessage());
            try {
                model.addObject("departments", departmentService.findAllDepartments());
            } catch (ServiceGeneralException e) {
                model.addObject("groupError",
                        messageSource.getMessage("ServiceGeneralException.Error", null, null));
            }
            return model;
        }

        try {
            Group oldGroup = groupService.findGroupById (groupId);
            Group group = new Group();
            group.setId (groupForm.getId());
            group.setGroupName (groupForm.getGroupName());
            group.setDepartment (groupForm.getDepartment());
            groupService.updateGroup (group);
            LOG.info("Editing group does not have errors");
            String greenMessage = messageSource.getMessage ("group.edit.greenmessage",
                    new Object[] {oldGroup.getGroupName(),
                            oldGroup.getDepartment().getDepartmentName(),
                            group.getGroupName(),
                            group.getDepartment().getDepartmentName()}, null);
            cookah.setGreenMessage (greenMessage);
        } catch (ServiceGeneralException e) {
            LOG.warn ("Editing group fails because of service exceptiun");
            String redMessage = messageSource.getMessage ("group.edit.failmessage",
                    new Object[] {groupForm.getGroupName(),
                            groupForm.getDepartment().getDepartmentName()}, null);
            cookah.setRedMessage (redMessage);
        }
        return new ModelAndView("redirect:" + cookah.getReturnUri());
    }

    /**
     * Group delete controller requested with GET method
     * @param groupId    Id of group to delete
     * @return model and view with delete question
     */
    @RequestMapping(value="/delete/{groupId}", method = RequestMethod.GET)
    public ModelAndView groupDelete (@PathVariable Long groupId) {

        ModelAndView model = new ModelAndView("group/delete");
        try {
            Group group = groupService.findGroupById(groupId);
            if (group == null) {
                model.addObject ("groupError",
                        messageSource.getMessage ("NotExist.groupDelete.id", null, null));
            } else {
                GroupForm groupForm = new GroupForm();
                groupForm.setId (group.getId());
                groupForm.setGroupName(group.getGroupName());
                groupForm.setDepartment (group.getDepartment());
                model.addObject("groupForm", groupForm);
            }
        } catch (ServiceGeneralException e) {
            model.addObject("groupError",
                    messageSource.getMessage("ServiceGeneralException.Error", null, null));
        }
        return model;
    }

    /**
     * Group Delete  controller requested with POST method
     * @param groupId               Id of group to delete
     * @param groupForm             Group Form model attribute
     * @param bindingResult         BindingResult
     * @param redirectAttributes    RedirectAttributes
     * @return delete view if form fails with error message,
     * otherwise redirects to "view group" view
     */
    @RequestMapping(value="/delete/{groupId}", method = RequestMethod.POST)
    public String groupDeleteValidation (@PathVariable Long groupId,
                                         @ModelAttribute("groupForm")
                                         GroupForm groupForm,
                                         BindingResult bindingResult,
                                         RedirectAttributes redirectAttributes) {

        GroupDeleteValidator groupDeleteValidator = new GroupDeleteValidator();
        groupDeleteValidator.validate (groupForm, bindingResult);
        if (bindingResult.hasErrors()) {
            LOG.info("Deleting group has errors");
            redirectAttributes.addFlashAttribute("errors",
                    bindingResult.getFieldError().getDefaultMessage());
            return "group/delete";
        }

        try {
            Group groupDelete = groupService.findGroupById (groupId);
            groupService.deleteGroup (groupDelete);
            LOG.info("Deleting group does not have errors");
            String redMessage = messageSource.getMessage ("group.delete.redmessage",
                    new Object[] {groupDelete.getGroupName(),
                            groupDelete.getDepartment().getDepartmentName()}, null);
            cookah.setRedMessage (redMessage);
        } catch (ServiceGeneralException e) {
            LOG.warn ("Deleting group fails because of service exceptiun");
            String redMessage = messageSource.getMessage ("group.delete.failmessage",
                    new Object[] {groupForm.getGroupName(),
                            groupForm.getDepartment().getDepartmentName()}, null);
            cookah.setRedMessage (redMessage);
        } catch (ServiceConstraintViolationException e) {
            String redMessage = messageSource.getMessage ("group.delete.constraintfailmessage",
                    new Object[] {groupForm.getGroupName(),
                            groupForm.getDepartment().getDepartmentName()}, null);
            cookah.setRedMessage (redMessage);
        }
        return "redirect:" + cookah.getReturnPath();
    }

    /**
     * Cookie handler. If cookie data is lost, default values are used
     * @return cookie object
     */
    @ModelAttribute("cookah")
    private Cookah getCookah() {
        if (cookah.getReturnPath() == null || cookah.getReturnPath().isEmpty()) {
            cookah.setReturnPath (defaultValuesService.getBaseUrl() +
                    defaultValuesService.getGroupReturnUri());
        }
        if (cookah.getReturnUri() == null || cookah.getReturnUri().isEmpty()) {
            cookah.setReturnUri (defaultValuesService.getGroupReturnUri());
        }
        return cookah;
    }

    public void setGroupService(IGroupService groupService) {
        this.groupService = groupService;
    }

    public void setDepartmentService(IDepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public void setCookah (Cookah cookah) {
        this.cookah = cookah;
    }
}
