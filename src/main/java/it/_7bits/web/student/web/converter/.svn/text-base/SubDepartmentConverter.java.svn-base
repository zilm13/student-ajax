package it._7bits.web.student.web.converter;

import it._7bits.web.student.domain.SubDepartment;
import it._7bits.web.student.service.ISubDepartmentService;
import it._7bits.web.student.service.ServiceGeneralException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Converts Java String with id to SubDepartment entity
 */
@Service
public class SubDepartmentConverter extends AutoRegisteredConverter<String,SubDepartment> {

    @Autowired
    private ISubDepartmentService subDepartmentService;
    protected final Logger LOG = Logger.getLogger(getClass());

    public SubDepartment convert (String subDepartmentId) {
        if (subDepartmentId.isEmpty()) {
            return null;
        }
        try {
            return subDepartmentService.
                    findSubDepartmentById(Long.parseLong(subDepartmentId));
        } catch (ServiceGeneralException e) {
            LOG.info ("Converter cannot convert subdepartment id to subdepartment instance: " + e);
            return null;
        }
    }

    public void setSubDepartmentService(ISubDepartmentService subDepartmentService) {
        this.subDepartmentService = subDepartmentService;
    }
}
