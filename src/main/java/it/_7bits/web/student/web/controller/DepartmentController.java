package it._7bits.web.student.web.controller;

import it._7bits.web.student.domain.Cookah;
import it._7bits.web.student.domain.Department;
import it._7bits.web.student.service.IDefaultValuesService;
import it._7bits.web.student.service.IDepartmentService;
import it._7bits.web.student.service.ServiceGeneralException;
import it._7bits.web.student.web.form.DepartmentForm;
import it._7bits.web.student.web.validator.DepartmentDeleteValidator;
import it._7bits.web.student.web.validator.DepartmentEditValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Department section controller
 */
@Controller
@RequestMapping("/department")
public class DepartmentController {

    protected final Logger LOG = Logger.getLogger(getClass());
    @Autowired
    private IDepartmentService departmentService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private Cookah cookah;
    @Autowired
    private IDefaultValuesService defaultValuesService;
    @Autowired
    private DepartmentEditValidator departmentEditValidator;
    @Autowired
    private DepartmentDeleteValidator departmentDeleteValidator;

    /**
     * Default page with listing of all departments
     * @param request    HttpServletRequest
     * @return model with all departments
     */
    @RequestMapping("/view")
    public ModelAndView departmentView (HttpServletRequest request) {

        ModelAndView model = new ModelAndView ("department/view");
        try {
            model.addObject ("departments", departmentService.findAllDepartments());
        } catch (ServiceGeneralException e) {
            model.addObject ("departmentsError",
                    messageSource.getMessage ("ServiceGeneralException.Error", null, null));
        }
        cookah.setReturnPath (request.getRequestURL().toString());
        cookah.setReturnUri (request.getServletPath());
        return model;
    }

    /**
     * Department Add controller requested with GET method
     * @return view for department add
     */
    @RequestMapping(value="/add", method = RequestMethod.GET)
    public String departmentAdd (DepartmentForm departmentForm){
        return "department/add";
    }

    /**
     * Department Add controller requested with POST method
     * @param departmentForm        Department Form model attribute
     * @param bindingResult         BindingResult
     * @param redirectAttributes    RedirectAttributes
     * @return if there are no errors, redirects to "Department view" view
     * otherwise returns to "Department Add" view with errors binded
     */
    @RequestMapping(value="/add", method = RequestMethod.POST)
    public String departmentAddValidation (@Valid @ModelAttribute("departmentForm")
                                               DepartmentForm departmentForm,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            LOG.info("Adding department have errors");
            redirectAttributes.addFlashAttribute("errors",
                    bindingResult.getFieldError().getDefaultMessage());
            return "department/add";
        }

        LOG.info ("Adding department does not have errors");
        Department department = new Department ();
        department.setDepartmentName (departmentForm.getDepartmentName());
        department.setDeanFirstName (departmentForm.getDeanFirstName());
        department.setDeanLastName (departmentForm.getDeanLastName());

        try {
            departmentService.addDepartment (department);
            String greenMessage = messageSource.getMessage ("department.add.greenmessage",
                    new Object[] {department.getDepartmentName(),
                            department.getDeanFirstName(),
                            department.getDeanLastName()}, null);
            cookah.setGreenMessage (greenMessage);
        } catch (ServiceGeneralException e) {
            String redMessage = messageSource.getMessage ("department.add.failmessage", null, null);
            cookah.setRedMessage (redMessage);
        }
        return "redirect:" + cookah.getReturnPath();
    }

    /**
     * Department Edit controller requested with GET method
     * If department not exist or service failure,
     * adds departmentError to model
     * @param departmentId id of Department for edit
     * @return view for department edit
     */
    @RequestMapping(value="/edit/{departmentId}", method = RequestMethod.GET)
    public ModelAndView departmentEdit (@PathVariable Long departmentId) {

        ModelAndView model = new ModelAndView("department/edit");
        try {
            Department department = departmentService.findDepartmentById (departmentId);
            if (department == null) {
                model.addObject ("departmentError",
                        messageSource.getMessage ("NotExist.departmentEdit.id", null, null));

            } else {
                DepartmentForm departmentForm = new DepartmentForm();
                departmentForm.setId (department.getId());
                departmentForm.setDepartmentName(department.getDepartmentName());
                departmentForm.setDeanFirstName(department.getDeanFirstName());
                departmentForm.setDeanLastName (department.getDeanLastName());
                model.addObject("departmentForm", departmentForm);
            }
        } catch (ServiceGeneralException e) {
            model.addObject("departmentError",
                    messageSource.getMessage("ServiceGeneralException.Error", null, null));
        }
        return model;
    }

    /**
     * Department Edit controller requested with POST method
     * If errors found, returns to edit model, with error messages,
     * otherwise returns to "Department view" page with success message
     * (or if service update fails with fail  message)
     * @param departmentId          Id of department to edit
     * @param departmentForm        Department Form model attribute
     * @param bindingResult         BindingResult
     * @param redirectAttributes    RedirectAttributes
     * @return "department edit" view if errors found otherwise
     * redirects to "department view"
     */
    @RequestMapping(value="/edit/{departmentId}", method = RequestMethod.POST)
    public String departmentEditValidation (@PathVariable Long departmentId,
                                            @ModelAttribute("departmentForm")
                                            DepartmentForm departmentForm,
                                           BindingResult bindingResult,
                                           RedirectAttributes redirectAttributes) {

        //DepartmentEditValidator departmentEditValidator = new DepartmentEditValidator();
        departmentEditValidator.validate (departmentForm, bindingResult);
        if (bindingResult.hasErrors()) {
            LOG.info("Editing department has errors");
            redirectAttributes.addFlashAttribute("errors",
                    bindingResult.getFieldError().getDefaultMessage());
            return "department/edit";
        }

        try {
            Department oldDepartment = departmentService.findDepartmentById(departmentId);
            LOG.info("Editing department does not have errors");
            Department department = new Department();
            department.setId (departmentForm.getId());
            department.setDepartmentName(departmentForm.getDepartmentName());
            department.setDeanFirstName(departmentForm.getDeanFirstName());
            department.setDeanLastName (departmentForm.getDeanLastName());
            departmentService.updateDepartment(department);

            String greenMessage = messageSource.getMessage ("department.edit.greenmessage",
                    new Object[] {oldDepartment.getDepartmentName(),
                            oldDepartment.getDeanFirstName(),
                            oldDepartment.getDeanLastName(),
                            department.getDepartmentName(),
                            department.getDeanFirstName(),
                            department.getDeanLastName()}, null);
            cookah.setGreenMessage(greenMessage);
        } catch (ServiceGeneralException e) {
            String redMessage = messageSource.getMessage ("department.edit.failmessage",
                    new Object[] {departmentForm.getDepartmentName(),
                    departmentForm.getDeanFirstName(),
                    departmentForm.getDeanLastName()}, null);
            cookah.setRedMessage (redMessage);
        }
        return "redirect:" + cookah.getReturnPath();
    }

    /**
     * Department delete controller requested with GET method
     * @param departmentId    Id of department to delete
     * @return model and view with delete question
     */
    @RequestMapping(value="/delete/{departmentId}", method = RequestMethod.GET)
    public ModelAndView departmentDelete (@PathVariable Long departmentId) {

        ModelAndView model = new ModelAndView("department/delete");
        try {
            Department department = departmentService.findDepartmentById (departmentId);
            if (department == null) {
                model.addObject ("departmentError",
                        messageSource.getMessage ("NotExist.departmentDelete.id", null, null));
            } else {
                DepartmentForm departmentForm = new DepartmentForm();
                departmentForm.setId (department.getId());
                departmentForm.setDepartmentName (department.getDepartmentName());
                departmentForm.setDeanFirstName (department.getDeanFirstName());
                departmentForm.setDeanLastName (department.getDeanLastName());
                model.addObject("departmentForm", departmentForm);
            }
        } catch (ServiceGeneralException e) {
            model.addObject("departmentError",
                    messageSource.getMessage("ServiceGeneralException.Error", null, null));
        }
        return model;
    }

    /**
     * Department Delete  controller requested with POST method
     * @param departmentId          Id of department to delete
     * @param departmentForm        Department Form model attribute
     * @param bindingResult         BindingResult
     * @param redirectAttributes    RedirectAttributes
     * @return delete view if form fails with error message,
     * otherwise redirects to "view department" view
     */
    @RequestMapping(value="/delete/{departmentId}", method = RequestMethod.POST)
    public String departmentDeleteValidation (@PathVariable Long departmentId,
                                            @ModelAttribute("departmentForm")
                                            DepartmentForm departmentForm,
                                            BindingResult bindingResult,
                                            RedirectAttributes redirectAttributes) {

        //DepartmentDeleteValidator departmentDeleteValidator = new DepartmentDeleteValidator();
        departmentDeleteValidator.validate (departmentForm, bindingResult);

        if (bindingResult.hasErrors()) {
            LOG.info ("Deleting department has errors");
            redirectAttributes.addFlashAttribute("errors",
                    bindingResult.getFieldError().getDefaultMessage());
            return "department/delete";
        }

        try {
            Department department = departmentService
                    .findDepartmentById (departmentForm.getId());
            departmentService.deleteDepartment (department);
            LOG.info ("Deleting department does not have errors");
            String redMessage = messageSource.getMessage ("department.delete.redmessage",
                    new Object[] {departmentForm.getDepartmentName(),
                            departmentForm.getDeanFirstName(),
                            departmentForm.getDeanLastName()}, null);
            cookah.setRedMessage (redMessage);
        } catch (ServiceGeneralException e) {
            LOG.warn ("Deleting department fails because of service exceptiun");
            String redMessage = messageSource.getMessage ("department.delete.failmessage",
                    new Object[] {departmentForm.getDepartmentName(),
                            departmentForm.getDeanFirstName(),
                            departmentForm.getDeanLastName()}, null);
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
                    defaultValuesService.getDepartmentReturnUri());
        }
        if (cookah.getReturnUri() == null || cookah.getReturnUri().isEmpty()) {
            cookah.setReturnUri (defaultValuesService.getDepartmentReturnUri());
        }
        return cookah;
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
