package it._7bits.web.student.web.controller;

import it._7bits.web.student.domain.Department;
import it._7bits.web.student.domain.Group;
import it._7bits.web.student.domain.SubDepartment;
import it._7bits.web.student.service.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/ajax")
public class AjaxController {

    protected final Logger LOG = Logger.getLogger(getClass());
    @Autowired
    private IDepartmentService departmentService;
    @Autowired
    private ISubDepartmentService subDepartmentService;
    @Autowired
    private IGroupService groupService;
    @Autowired
    private MessageSource messageSource;

    /**
     * First Page with javascript ajax-menu to the left
     * and iframe with working content to the right
     * @return model for this page
     */
    @RequestMapping("/view")
    public ModelAndView defaultView() {
        ModelAndView model = new ModelAndView("ajax/view");
        return model;
    }

    /**
     * List of all Departments for Ajax/JS menu
     * @return JSON list of Departments or null if service request fails
     */
    @RequestMapping(value="/get/departments", method = RequestMethod.GET)
    public @ResponseBody List<Department> getDepartments (HttpServletResponse response) {
        try {
            return departmentService.findAllDepartments();
        } catch (ServiceGeneralException e) {
            response.setStatus (HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
    }

    /**
     * Group name for localisation purposes for Ajax/JS menu
     * @return group name (String)
     */
    @RequestMapping(value="/get/groupname", method = RequestMethod.GET)
    public @ResponseBody Object getGroupName () {
            return new Object[] {messageSource.getMessage ("ajax.view.groups", null, null)};
    }

    /**
     * SubDepartment name for localisation purposes for Ajax/JS menu
     * @return "subdepartment" name (String)
     */
    @RequestMapping(value="/get/subdepname", method = RequestMethod.GET)
    public @ResponseBody Object getSubdepName () {
        return new Object[] {messageSource.getMessage ("ajax.view.subdepartments", null, null)};
    }

    /**
     * List groups in exact department for Ajax/JS menu
     * @param departmentId    Department Id
     * @return list of groups in department
     */
    @RequestMapping(value="/get/groups/{departmentId}", method = RequestMethod.GET)
    public @ResponseBody List<Group> getGroups(@PathVariable Long departmentId,
                                               HttpServletResponse response) {
        try {
            return groupService.findGroupsInDep(departmentId);
        } catch (ServiceGeneralException e) {
            response.setStatus (HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
    }

    /**
     * List SubDepartments in exact department for Ajax/JS menu
     * @param departmentId    Department Id
     * @return list of subdepartments in department
     */
    @RequestMapping(value="/get/subdeps/{departmentId}", method = RequestMethod.GET)
    public @ResponseBody List<SubDepartment> getSubdeps(@PathVariable Long departmentId,
                                                        HttpServletResponse response) {
        try {
            return subDepartmentService
                    .findSubDepartmentsInDep(departmentId);
        } catch (ServiceGeneralException e) {
            response.setStatus (HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
    }

    public void setDepartmentService(IDepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    public void setSubDepartmentService(ISubDepartmentService subDepartmentService) {
        this.subDepartmentService = subDepartmentService;
    }

    public void setGroupService(IGroupService groupService) {
        this.groupService = groupService;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}