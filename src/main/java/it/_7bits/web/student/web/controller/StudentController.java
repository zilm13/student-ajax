package it._7bits.web.student.web.controller;

import it._7bits.web.student.domain.Cookah;
import it._7bits.web.student.domain.Student;
import it._7bits.web.student.service.*;
import it._7bits.web.student.web.form.StudentForm;
import it._7bits.web.student.web.validator.StudentAddValidator;
import it._7bits.web.student.web.validator.StudentDeleteValidator;
import it._7bits.web.student.web.validator.StudentEditValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Student section controller
 */
@Controller
@RequestMapping("/student")
public class StudentController {
    protected final Logger LOG = Logger.getLogger(getClass());
    @Autowired
    private ISubDepartmentService subDepartmentService;
    @Autowired
    private IGroupService groupService;
    @Autowired
    private IStudentService studentService;
    @Autowired
    private Cookah cookah;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private IDefaultValuesService defaultValuesService;


    /**
     * First page with listing of all students
     * @param request    HttpServletRequest
     * @return model with all students
     */
    @RequestMapping("/view")
    public ModelAndView studentView(HttpServletRequest request) {

        ModelAndView model = new ModelAndView ("student/view");
        try {
            model.addObject ("students", studentService.findAllStudents());
        } catch (ServiceGeneralException e) {
            model.addObject ("studentsError",
                    messageSource.getMessage ("ServiceGeneralException.Error", null, null));
        }
        cookah.setReturnPath (request.getRequestURL().toString());
        cookah.setReturnUri (request.getServletPath());
        return model;
    }

    /**
     * First page with listing of students in subDepartment
     * @param subDepartmentId    Id of subDepartment
     * @param request            HttpServletRequest
     * @return model with students in exact subDepartment
     */
    @RequestMapping("/view/subdep/{subDepartmentId}")
    public ModelAndView studentViewInSub(@PathVariable Long subDepartmentId,
                                    HttpServletRequest request) {

        ModelAndView model = new ModelAndView ("student/view");
        model.addObject ("subDepartmentId",subDepartmentId);
        try {
        model.addObject ("titleplussub",
                subDepartmentService
                        .findSubDepartmentById (subDepartmentId)
                        .getSubDepartmentName());
        model.addObject ("students",
                studentService.findStudentsInSub(subDepartmentId));
        } catch (ServiceGeneralException e) {
            model.addObject ("studentsError",
                    messageSource.getMessage ("ServiceGeneralException.Error", null, null));
        }
        cookah.setReturnPath (request.getRequestURL().toString());
        cookah.setReturnUri (request.getServletPath());
        return model;
    }

    /**
     * First page with listing of students in group
     * @param groupId            Id of group
     * @param request            HttpServletRequest
     * @return model with students in exact group
     */
    @RequestMapping("/view/group/{groupId}")
    public ModelAndView studentViewInGroup(@PathVariable Long groupId,
                                    HttpServletRequest request) {
        ModelAndView model = new ModelAndView ("student/view");
        model.addObject ("groupId",groupId);
        try {
            model.addObject ("titleplusgroup",
                    groupService
                            .findGroupById(groupId)
                            .getGroupName());
            model.addObject ("students",
                    studentService.findStudentsInGroup (groupId));
        } catch (ServiceGeneralException e) {
            model.addObject ("studentsError",
                    messageSource.getMessage ("ServiceGeneralException.Error", null, null));
        }
        cookah.setReturnPath (request.getRequestURL().toString());
        cookah.setReturnUri (request.getServletPath());
        return model;
    }

    /**
     * Student Add controller requested with GET method
     * @param studentForm   Student Form model attribute
     * @return model for "student add"
     */
    @RequestMapping(value={"/add/","/add"}, method = RequestMethod.GET)
    public ModelAndView studentAdd (StudentForm studentForm) {

        ModelAndView model = new ModelAndView ("student/add");
        try {
            model.addObject ("groups", groupService.findAllGroups());
            model.addObject( "subdepartments", subDepartmentService.findAllSubDepartments());
        } catch (ServiceGeneralException e) {
            model.addObject ("studentError",
                    messageSource.getMessage ("ServiceGeneralException.Error", null, null));
        }
        return model;
    }

    /**
     * Student Add controller with group param requested with GET method
     * @param groupId          id of group to auto-select
     * @param studentForm      Student Form model attribute
     * @return model for "student add"
     */
    @RequestMapping(value="/add/group/{groupId}", method = RequestMethod.GET)
    public ModelAndView studentAddInGroup (@PathVariable Long groupId,
                                           StudentForm studentForm) {

        ModelAndView model = new ModelAndView ("student/add");
        try {
            studentForm.setGroup (groupService.findGroupById (groupId));
            model.addObject ("studentForm", studentForm);
            model.addObject ("groups", groupService.findAllGroups());
            model.addObject ("subdepartments", subDepartmentService.findAllSubDepartments());
        } catch (ServiceGeneralException e) {
            model.addObject ("studentError",
                    messageSource.getMessage ("ServiceGeneralException.Error", null, null));
        }
        return model;
    }

    /**
     * Student Add controller with subDepartment param requested with GET method
     * @param subDepartmentId          id of group to auto-select
     * @param studentForm              Student Form model attribute
     * @return model for "student add"
     */
    @RequestMapping(value="/add/subdep/{subDepartmentId}", method = RequestMethod.GET)
    public ModelAndView studentAddInSubDep (@PathVariable Long subDepartmentId,
                                           StudentForm studentForm) {

        ModelAndView model = new ModelAndView ("student/add");
        try {
            studentForm.setSubDepartment (subDepartmentService
                    .findSubDepartmentById(subDepartmentId));
            model.addObject ("studentForm", studentForm);
            model.addObject ("groups", groupService.findAllGroups());
            model.addObject( "subdepartments", subDepartmentService.findAllSubDepartments());
        } catch (ServiceGeneralException e) {
            model.addObject ("studentError",
                    messageSource.getMessage ("ServiceGeneralException.Error", null, null));
        }
        return model;
    }

    @RequestMapping(value={"/add/*","/add", "/add/group/*", "/add/subdep/*"},
            method = RequestMethod.POST)
    public ModelAndView studentAddValidation (@ModelAttribute("studentForm")
                                                  StudentForm studentForm,
                                              BindingResult bindingResult,
                                              RedirectAttributes redirectAttributes,
                                              HttpServletRequest request) {

        StudentAddValidator studentAddValidator = new StudentAddValidator();
        studentAddValidator.validate (studentForm, bindingResult);

        Student headFound = null;
        if (bindingResult.hasErrors()) {
            LOG.info ("Adding student have errors");
            ModelAndView model = new ModelAndView("student/add");
            model.addObject ("errors", bindingResult.getFieldError().getDefaultMessage());
            try {
                model.addObject ("groups", groupService.findAllGroups());
                model.addObject ("subdepartments", subDepartmentService.findAllSubDepartments());
                if (studentForm.getGroup() != null &&
                        studentForm.getIsHead()) {
                    List<Student> heads = studentService
                            .findHeadStudentsInGroup (studentForm.getGroup().getId());
                    if (!heads.isEmpty()) {
                        headFound = heads.get(0);
                    }
                }
                if (headFound != null) model.addObject ("headFound", headFound);
            } catch (ServiceGeneralException e) {
                model.addObject ("studentError",
                        messageSource.getMessage ("ServiceGeneralException.Error", null, null));
            }
            return model;
        }

        try {//removing head status from old head
            if (studentForm.getForceHead()) {
                List<Student> heads = studentService
                        .findHeadStudentsInGroup(studentForm.getGroup().getId());
                if (!heads.isEmpty()) {
                    headFound = heads.get (0);
                    headFound.setIsHead (false);
                    studentService.updateStudent (headFound);
                }
            }
            Student student = new Student();
            student.setFirstName (studentForm.getFirstName());
            student.setLastName(studentForm.getLastName());
            student.setGroup(studentForm.getGroup());
            student.setSubDepartment(studentForm.getSubDepartment());
            student.setIsHead (studentForm.getIsHead() || studentForm.getForceHead());
            studentService.addStudent (student);
             LOG.info ("Adding group does not have errors");
            String greenMessage = messageSource.getMessage ("student.add.greenmessage",
                    new Object[] {studentForm.getFirstName(),
                            studentForm.getLastName(),
                            studentForm.getGroup().getGroupName()}, null);
            cookah.setGreenMessage(greenMessage);
        } catch (ServiceGeneralException e) {
            String redMessage = messageSource.getMessage ("student.add.failmessage",
                    new Object[] {studentForm.getFirstName(),
                    studentForm.getLastName(),
                    studentForm.getGroup().getGroupName()}, null);
            cookah.setRedMessage (redMessage);
        }
        return new ModelAndView("redirect:" + cookah.getReturnUri());
    }

    /**
     * Student Edit controller requested with GET method
     * If student not exist or service failure,
     * adds studentError to model
     * @param studentId id of Student for edit
     * @return model and view for student edit
     */
    @RequestMapping(value="/edit/{studentId}", method = RequestMethod.GET)
    public ModelAndView studentEdit (@PathVariable Long studentId) {

        ModelAndView model = new ModelAndView ("student/edit");
        try {
            Student student = studentService.findStudentById (studentId);
            if (student == null) {
                model.addObject ("studentError",
                        messageSource.getMessage ("NotExist.studentEdit.id", null, null));
            }

            StudentForm studentForm = new StudentForm();
            studentForm.setId (student.getId());
            studentForm.setFirstName (student.getFirstName());
            studentForm.setLastName (student.getLastName());
            studentForm.setGroup (student.getGroup());
            studentForm.setSubDepartment (student.getSubDepartment());
            studentForm.setIsHead (student.getIsHead());

            model.addObject ("studentForm", studentForm);
            model.addObject ("groups", groupService.findAllGroups());
            model.addObject ("subdepartments", subDepartmentService.findAllSubDepartments());
        } catch (ServiceGeneralException e) {
            model.addObject ("studentError",
                    messageSource.getMessage ("ServiceGeneralException.Error", null, null));
        }
        return model;
    }

    /**
     * Student Edit controller requested with POST method
     * If errors found, returns to edit model, with error messages,
     * otherwise returns to "Student view" page with success message
     * (or if service update fails with fail  message)
     * @param studentId             Id of student to edit
     * @param studentForm           Student Form  model attribute
     * @param bindingResult         BindingResult
     * @param redirectAttributes    RedirectAttributes
     * @return "student edit" view if errors found otherwise
     * redirects to "student view"
     */
    @RequestMapping(value="/edit/{studentId}", method = RequestMethod.POST)
    public ModelAndView studentEditValidation (@PathVariable Long studentId,
                                               @ModelAttribute("studentForm")
                                               StudentForm studentForm,
                                               BindingResult bindingResult,
                                               RedirectAttributes redirectAttributes,
                                               HttpServletRequest request) {

        StudentEditValidator studentEditValidator = new StudentEditValidator();
        studentEditValidator.validate (studentForm, bindingResult);
        Student headFound = null;

        if (bindingResult.hasErrors()) {
            ModelAndView model = new ModelAndView ("student/edit");
            LOG.info ("Editing student has errors");
            model.addObject("errors",
                    bindingResult.getFieldError().getDefaultMessage());
            try {
                model.addObject ("groups", groupService.findAllGroups());
                model.addObject ("subdepartments", subDepartmentService.findAllSubDepartments());
                if (studentForm.getGroup() != null && studentForm.getIsHead()) {
                    List<Student> heads = studentService
                            .findHeadStudentsInGroup (studentForm.getGroup().getId());
                    if (!heads.isEmpty() &&
                            heads.get(0).getId() != studentForm.getId()) {
                        headFound = heads.get(0);
                    }
                }
                if (headFound != null) model.addObject ("headFound", headFound);
            } catch (ServiceGeneralException e) {
                model.addObject ("studentError",
                        messageSource.getMessage ("ServiceGeneralException.Error", null, null));
            }
            return model;
        }

        try {
            Student oldStudent = studentService
                    .findStudentById (studentForm.getId());
            //removing head status from old head
            if (studentForm.getForceHead()) {
                List<Student> heads = studentService
                        .findHeadStudentsInGroup(studentForm.getGroup().getId());
                if (!heads.isEmpty()) {
                    headFound = heads.get(0);
                    headFound.setIsHead (false);
                    studentService.updateStudent (headFound);
                }
            }

            Student student = new Student();
            student.setId (studentForm.getId());
            student.setFirstName(studentForm.getFirstName());
            student.setLastName(studentForm.getLastName());
            student.setGroup(studentForm.getGroup());
            student.setSubDepartment(studentForm.getSubDepartment());
            student.setIsHead (studentForm.getIsHead() || studentForm.getForceHead());

            studentService.updateStudent (student);
            LOG.info ("Editing student does not have errors");
            String greenMessage = messageSource.getMessage ("student.edit.greenmessage",
                    new Object[] {oldStudent.getFirstName(),
                            oldStudent.getLastName(),
                            oldStudent.getGroup().getGroupName(),
                            studentForm.getFirstName(),
                            studentForm.getLastName(),
                            studentForm.getGroup().getGroupName()}, null);
            cookah.setGreenMessage(greenMessage);
        } catch (ServiceGeneralException e) {
            String redMessage = messageSource.getMessage ("student.edit.failmessage",
                    new Object[] {studentForm.getFirstName(),
            studentForm.getLastName(),
            studentForm.getGroup().getGroupName()}, null);
            cookah.setRedMessage (redMessage);
        }
        return new ModelAndView ("redirect:" + cookah.getReturnUri());
    }

    /**
     * Student delete controller requested with GET method
     * @param studentId    Id of student to delete
     * @return model and view with delete question
     */
    @RequestMapping(value="/delete/{studentId}", method = RequestMethod.GET)
    public ModelAndView studentDelete (@PathVariable Long studentId) {

        ModelAndView model = new ModelAndView ("student/delete");
        try {
            Student student = studentService.findStudentById (studentId);
            if (student == null) {
                model.addObject ("studentError",
                        messageSource.getMessage ("NotExist.studentDelete.id", null, null));
            }

            StudentForm studentForm = new StudentForm();
            studentForm.setId (student.getId());
            studentForm.setFirstName (student.getFirstName());
            studentForm.setLastName (student.getLastName());
            studentForm.setGroup (student.getGroup());
            studentForm.setSubDepartment (student.getSubDepartment());
            studentForm.setIsHead (student.getIsHead());

            model.addObject("studentForm", studentForm);
        } catch (ServiceGeneralException e) {
            model.addObject ("studentError",
                    messageSource.getMessage ("ServiceGeneralException.Error", null, null));
        }
        return model;
    }


    /**
     * Student Delete  controller requested with POST method
     * @param studentId             Id of student to delete
     * @param studentForm           Student Form model attribute
     * @param bindingResult         BindingResult
     * @param redirectAttributes    RedirectAttributes
     * @return delete view if form fails with error message,
     * otherwise redirects to "view student" view
     */
    @RequestMapping(value="/delete/{studentId}", method = RequestMethod.POST)
    public String studentDeleteValidation (@PathVariable Long studentId,
                                           @ModelAttribute("studentForm")
                                           StudentForm studentForm,
                                           BindingResult bindingResult,
                                           RedirectAttributes redirectAttributes) {

        StudentDeleteValidator studentDeleteValidator = new StudentDeleteValidator();
        studentDeleteValidator.validate (studentForm, bindingResult);
        if (bindingResult.hasErrors()) {
            LOG.info ("Deleting student has errors");
            redirectAttributes.addFlashAttribute ("errors",
                    bindingResult.getFieldError().getDefaultMessage());
            return "student/delete";
        }
        try {
            LOG.info ("Deleting student does not have errors");
            Student student = studentService.findStudentById (studentForm.getId());
            studentService.deleteStudent (student);
            String redMessage = messageSource.getMessage ("student.delete.redmessage",
                    new Object[] {student.getFirstName(),
                            student.getLastName(),
                            student.getGroup().getGroupName()}, null);
            cookah.setRedMessage (redMessage);
        } catch (ServiceGeneralException e) {
            String redMessage = messageSource.getMessage ("student.delete.failmessage",
                    new Object[] {studentForm.getFirstName(),
                            studentForm.getLastName(),
                            studentForm.getGroup().getGroupName()}, null);
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
                    defaultValuesService.getStudentReturnUri());
        }
        if (cookah.getReturnUri() == null || cookah.getReturnUri().isEmpty()) {
            cookah.setReturnUri (defaultValuesService.getStudentReturnUri());
        }
        return cookah;
    }

    public void setSubDepartmentService(ISubDepartmentService subDepartmentService) {
        this.subDepartmentService = subDepartmentService;
    }

    public void setGroupService(IGroupService groupService) {
        this.groupService = groupService;
    }

    public void setStudentService(IStudentService studentService) {
        this.studentService = studentService;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public void setCookah (Cookah cookah) {
        this.cookah = cookah;
    }
}