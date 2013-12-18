package it._7bits.web.student.web.controller;

import it._7bits.web.student.domain.Cookah;
import it._7bits.web.student.domain.SubDepartment;
import it._7bits.web.student.service.*;
import it._7bits.web.student.web.form.GroupForm;
import it._7bits.web.student.web.form.SubDepartmentForm;
import it._7bits.web.student.web.validator.SubDepartmentDeleteValidator;
import it._7bits.web.student.web.validator.SubDepartmentEditValidator;
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
 * SubDepartment section controller
 */
@Controller
@RequestMapping("/subdepartment")
public class SubDepartmentController {
    protected final Logger LOG = Logger.getLogger(getClass());
    @Autowired
    private ISubDepartmentService subDepartmentService;
    @Autowired
    private IDepartmentService departmentService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private Cookah cookah;
    @Autowired
    private IDefaultValuesService defaultValuesService;

    /**
     * First page with listing of all subDepartments
     * @param request    HttpServletRequest
     * @return model with all subDepartments
     */
    @RequestMapping("/view")
    public ModelAndView subDepartmentView(HttpServletRequest request) {

        ModelAndView model = new ModelAndView ("subdepartment/view");
        try {
            model.addObject ("subdepartments", subDepartmentService.findAllSubDepartments());
        } catch (ServiceGeneralException e) {
            model.addObject ("subDepartmentsError",
                    messageSource.getMessage ("ServiceGeneralException.Error", null, null));
        }
        cookah.setReturnPath (request.getRequestURL().toString());
        cookah.setReturnUri (request.getServletPath());
        return model;
    }

    /**
     * First page with listing of subDepartments in department
     * @param departmentId    Id of department
     * @param request         HttpServletRequest
     * @return model with subDepartments in exact department
     */
    @RequestMapping("/view/{departmentId}")
    public ModelAndView subDepViewInDep(@PathVariable Long departmentId,
                                       HttpServletRequest request) {

        ModelAndView model = new ModelAndView ("subdepartment/view");
        model.addObject("departmentId", departmentId);
        try {
            model.addObject ("titleplus",
                    departmentService.findDepartmentById(departmentId).getDepartmentName());
            model.addObject ("departmentId",departmentId);
            model.addObject ("subdepartments",
                    subDepartmentService.findSubDepartmentsInDep(departmentId));
        } catch (ServiceGeneralException e) {
            model.addObject ("subDepartmentsError",
                    messageSource.getMessage ("ServiceGeneralException.Error", null, null));
        }
        cookah.setReturnPath (request.getRequestURL().toString());
        cookah.setReturnUri (request.getServletPath());
        return model;
    }

    /**
     * SubDepartment Add controller requested with GET method
     * @param subDepartmentForm SubDepartment Form model attribute
     * @return model for "sub department add"
     */
    @RequestMapping(value={"/add/","/add"}, method = RequestMethod.GET)
    public ModelAndView subDepartmentAdd (SubDepartmentForm subDepartmentForm) {

        ModelAndView model = new ModelAndView ("subdepartment/add");
        try {
            model.addObject ("departments", departmentService.findAllDepartments());
        } catch (ServiceGeneralException e) {
            model.addObject ("subDepartmentError",
                    messageSource.getMessage("ServiceGeneralException.Error", null, null));
        }
        return model;
    }

    /**
     * SubDepartment Add controller with department param requested with GET method
     * @param departmentId    id of department to auto-select
     * @param subDepartmentForm        SubDepartment Form model attribute
     * @return model for "sub department add"
     */
    @RequestMapping(value="/add/{departmentId}", method = RequestMethod.GET)
    public ModelAndView subDepartmentAddInDep (@PathVariable Long departmentId,
                                               SubDepartmentForm subDepartmentForm) {

        ModelAndView model = new ModelAndView ("subdepartment/add");
        try {
            subDepartmentForm.setDepartment (departmentService
                    .findDepartmentById(departmentId));
            model.addObject ("subDepartmentForm", subDepartmentForm);
            model.addObject ("departments", departmentService.findAllDepartments());
        } catch (ServiceGeneralException e) {
            model.addObject ("subDepartmentError",
                    messageSource.getMessage("ServiceGeneralException.Error", null, null));
        }
        return model;
    }

    /**
     * SubDepartment Add controller requested with POST method
     * @param subDepartmentForm     SubDepartment Form  model attribute
     * @param bindingResult         BindingResult
     * @param redirectAttributes    RedirectAttributes
     * @return "Add sub department" model if errors found
     * otherwise redirects to "sub department view" with successful message
     */
    @RequestMapping(value={"/add/*","/add"}, method = RequestMethod.POST)
    public ModelAndView subDepartmentAddValidation (@Valid @ModelAttribute("subDepartmentForm")
                                                        SubDepartmentForm subDepartmentForm,
                                                    BindingResult bindingResult,
                                                    RedirectAttributes redirectAttributes,
                                                    HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            LOG.info ("Adding subdepartment have errors");
            ModelAndView model = new ModelAndView ("subdepartment/add");
            model.addObject ("errors", bindingResult.getFieldError().getDefaultMessage());
            try {
                model.addObject ("departments", departmentService.findAllDepartments());
            } catch (ServiceGeneralException e) {
                model.addObject ("subDepartmentError",
                        messageSource.getMessage ("ServiceGeneralException.Error", null, null));
            }
            return model;
        }

        try {
            SubDepartment subDepartment = new SubDepartment();
            subDepartment.setSubDepartmentName (subDepartmentForm.getSubDepartmentName());
            subDepartment.setDepartment (subDepartmentForm.getDepartment());
            subDepartmentService.addSubDepartment(subDepartment);
            LOG.info("Adding subdepartment does not have errors");
            String greenMessage = messageSource.getMessage ("subdepartment.add.greenmessage",
                    new Object[] {subDepartment.getSubDepartmentName(),
                            subDepartment.getDepartment().getDepartmentName()}, null);
            cookah.setGreenMessage (greenMessage);
        } catch (ServiceGeneralException e) {
            String redMessage = messageSource.getMessage ("subdepartment.add.failmessage",
                    new Object[] {subDepartmentForm.getSubDepartmentName(),
                            subDepartmentForm.getDepartment().getDepartmentName()}, null);
            cookah.setRedMessage (redMessage);
        }
        return new ModelAndView("redirect:" + cookah.getReturnUri());
    }


    /**
     * SubDepartment Edit controller requested with GET method
     * If subDepartment not exist or service failure,
     * adds subDepartmentError to model
     * @param subDepartmentId id of subDepartment for edit
     * @return model and view for subDepartment edit
     */
    @RequestMapping(value="/edit/{subDepartmentId}", method = RequestMethod.GET)
    public ModelAndView subDepartmentEdit (@PathVariable Long subDepartmentId) {

        ModelAndView model = new ModelAndView("subdepartment/edit");
        try {
            SubDepartment subDepartment = subDepartmentService.findSubDepartmentById(subDepartmentId);
            if (subDepartment == null) {
                model.addObject("subDepartmentError",
                        messageSource.getMessage("NotExist.subDepartmentEdit.id", null, null));
            } else {
                SubDepartmentForm subDepartmentForm = new SubDepartmentForm();
                subDepartmentForm.setId (subDepartment.getId());
                subDepartmentForm.setSubDepartmentName(subDepartment.getSubDepartmentName());
                subDepartmentForm.setDepartment (subDepartment.getDepartment());
                model.addObject("subDepartmentForm", subDepartmentForm);
                model.addObject("departments", departmentService.findAllDepartments());
            }
        } catch (ServiceGeneralException e) {
            model.addObject ("subDepartmentError",
                    messageSource.getMessage ("ServiceGeneralException.Error", null, null));
        }
        return model;
    }

    /**
     * SubDepartment Edit controller requested with POST method
     * If errors found, returns to edit model, with error messages,
     * otherwise returns to "SubDepartment view" page with success message
     * (or if service update fails with fail  message)
     * @param subDepartmentId          Id of SubDepartment to edit
     * @param subDepartmentForm        SubDepartment Form  model attribute
     * @param bindingResult            BindingResult
     * @param redirectAttributes       RedirectAttributes
     * @return "SubDepartment edit" view if errors found otherwise
     * redirects to "SubDepartment view"
     */
    @RequestMapping(value="/edit/{subDepartmentId}", method = RequestMethod.POST)
    public ModelAndView groupEditValidation (@PathVariable Long subDepartmentId,
                                             @ModelAttribute("subDepartmentForm")
                                             SubDepartmentForm subDepartmentForm,
                                             BindingResult bindingResult,
                                             RedirectAttributes redirectAttributes,
                                             HttpServletRequest request) {

        SubDepartmentEditValidator subDepartmentEditValidator = new SubDepartmentEditValidator();
        subDepartmentEditValidator.validate (subDepartmentForm, bindingResult);
        if (bindingResult.hasErrors()) {
            ModelAndView model = new ModelAndView("subdepartment/edit");
            LOG.info("Editing subdepartment has errors");
            model.addObject("errors",
                    bindingResult.getFieldError().getDefaultMessage());
            try {
                model.addObject("departments", departmentService.findAllDepartments());
            } catch (ServiceGeneralException e) {
                model.addObject ("subDepartmentError",
                        messageSource.getMessage ("ServiceGeneralException.Error", null, null));
            }
            return model;
        }

        try {
            SubDepartment oldSubDepartment = subDepartmentService
                    .findSubDepartmentById(subDepartmentId);
            SubDepartment subDepartment = new SubDepartment ();
            subDepartment.setId (subDepartmentForm.getId());
            subDepartment.setSubDepartmentName(subDepartmentForm.getSubDepartmentName());
            subDepartment.setDepartment (subDepartmentForm.getDepartment());
            subDepartmentService.updateSubDepartment (subDepartment);
            LOG.info("Editing subdepartment does not have errors");
            String greenMessage = messageSource.getMessage ("subdepartment.edit.greenmessage",
                    new Object[] {oldSubDepartment.getSubDepartmentName(),
                            oldSubDepartment.getDepartment().getDepartmentName(),
                            subDepartment.getSubDepartmentName(),
                            subDepartment.getDepartment().getDepartmentName()}, null);
            cookah.setGreenMessage(greenMessage);
        } catch (ServiceGeneralException e) {
            String redMessage = messageSource.getMessage ("subdepartment.edit.failmessage",
                    new Object[] {subDepartmentForm.getSubDepartmentName(),
            subDepartmentForm.getDepartment().getDepartmentName()}, null);
            cookah.setRedMessage (redMessage);
        }
        return new ModelAndView("redirect:" + cookah.getReturnUri());
    }

    /**
     * SubDepartment delete controller requested with GET method
     * @param subDepartmentId    Id of subDepartment to delete
     * @return model and view with delete question
     */
    @RequestMapping(value="/delete/{subDepartmentId}", method = RequestMethod.GET)
    public ModelAndView subDepartmentDelete (@PathVariable Long subDepartmentId) {
        ModelAndView model = new ModelAndView("subdepartment/delete");
        try {
            SubDepartment subDepartment = subDepartmentService
                    .findSubDepartmentById (subDepartmentId);
            if (subDepartment== null) {
                model.addObject ("subDepartmentError",
                        messageSource.getMessage ("NotExist.subDepartmentDelete.id", null, null));
            } else {
                SubDepartmentForm subDepartmentForm = new SubDepartmentForm ();
                subDepartmentForm.setId (subDepartment.getId());
                subDepartmentForm.setSubDepartmentName (subDepartment.getSubDepartmentName());
                subDepartmentForm.setDepartment (subDepartment.getDepartment());
                model.addObject("subDepartmentForm", subDepartmentForm);
            }
        } catch (ServiceGeneralException e) {
            model.addObject ("subDepartmentError",
                    messageSource.getMessage ("ServiceGeneralException.Error", null, null));
        }
        return model;
    }

    /**
     * SubDepartment Delete  controller requested with POST method
     * @param subDepartmentId       Id of SubDepartment to delete
     * @param subDepartmentForm     SubDepartment Form model attribute
     * @param bindingResult         BindingResult
     * @param redirectAttributes    RedirectAttributes
     * @return delete view if form fails with error message,
     * otherwise redirects to "view SubDepartment" view
     */
    @RequestMapping(value="/delete/{subDepartmentId}", method = RequestMethod.POST)
    public String subDepartmentDeleteValidation (@PathVariable Long subDepartmentId,
                                                 @ModelAttribute("subDepartmentForm")
                                                 SubDepartmentForm subDepartmentForm,
                                                 BindingResult bindingResult,
                                                 RedirectAttributes redirectAttributes) {

        SubDepartmentDeleteValidator subDepartmentDeleteValidator = new SubDepartmentDeleteValidator();
        subDepartmentDeleteValidator.validate (subDepartmentForm, bindingResult);
        if (bindingResult.hasErrors()) {
            LOG.info("Deleting subdepartment has errors");
            redirectAttributes.addFlashAttribute("errors",
                    bindingResult.getFieldError().getDefaultMessage());
            return "subdepartment/delete";
        }

        try {
            SubDepartment subDepartmentDelete = subDepartmentService
                    .findSubDepartmentById(subDepartmentId);
            subDepartmentService.deleteSubDepartment (subDepartmentDelete);
            LOG.info("Deleting subdepartment does not have errors");
            String redMessage = messageSource.getMessage ("subdepartment.delete.redmessage",
                    new Object[] {subDepartmentDelete.getSubDepartmentName(),
                            subDepartmentDelete.getDepartment().getDepartmentName()}, null);
            cookah.setRedMessage(redMessage);
        } catch (ServiceGeneralException e) {
            String redMessage = messageSource.getMessage ("subdepartment.delete.failmessage",
                    new Object[] {subDepartmentForm.getSubDepartmentName(),
                            subDepartmentForm.getDepartment().getDepartmentName()}, null);
            cookah.setRedMessage(redMessage);
        } catch (ServiceConstraintViolationException e) {
            String redMessage = messageSource.getMessage ("subdepartment.delete.constraintfailmessage",
                    new Object[] {subDepartmentForm.getSubDepartmentName(),
                            subDepartmentForm.getDepartment().getDepartmentName()}, null);
            cookah.setRedMessage(redMessage);
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
                    defaultValuesService.getSubDepartmentReturnUri());
        }
        if (cookah.getReturnUri() == null || cookah.getReturnUri().isEmpty()) {
            cookah.setReturnUri (defaultValuesService.getSubDepartmentReturnUri());
        }
        return cookah;
    }

    public void setSubDepartmentService(ISubDepartmentService subDepartmentService) {
        this.subDepartmentService = subDepartmentService;
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

