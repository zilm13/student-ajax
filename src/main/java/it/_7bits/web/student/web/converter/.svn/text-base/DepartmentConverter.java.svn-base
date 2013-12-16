package it._7bits.web.student.web.converter;

import it._7bits.web.student.domain.Department;
import it._7bits.web.student.service.IDepartmentService;
import it._7bits.web.student.service.ServiceGeneralException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Converts Java String with id to Department entity
 */
@Service
public class DepartmentConverter extends AutoRegisteredConverter<String,Department> {

    @Autowired
    private IDepartmentService departmentService;
    protected final Logger LOG = Logger.getLogger(getClass());

    public Department convert (String departmentId) {
        if (departmentId.isEmpty()) {
            return null;
        }
        try {
            return departmentService.
                    findDepartmentById ( Long.parseLong (departmentId));
        } catch (ServiceGeneralException e) {
            LOG.info ("Converter cannot convert department id to department instance: " + e);
            return null;
        }
    }

    public void setDepartmentService(IDepartmentService departmentService) {
        this.departmentService = departmentService;
    }
}

