package it._7bits.web.student.web.converter;

import it._7bits.web.student.domain.Group;
import it._7bits.web.student.service.IGroupService;
import it._7bits.web.student.service.ServiceGeneralException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Converts Java String with id to Group entity
 */
@Service
public class GroupConverter extends AutoRegisteredConverter<String,Group> {

    @Autowired
    private IGroupService groupService;
    protected final Logger LOG = Logger.getLogger(getClass());

    public Group convert (String groupId) {
        if (groupId.isEmpty()) {
            return null;
        }
        try {
            return groupService.
                    findGroupById(Long.parseLong(groupId));
        } catch (ServiceGeneralException e) {
            LOG.info ("Converter cannot convert group id to group instance: " + e);
            return null;
        }
    }

    public void setGroupService(IGroupService groupService) {
        this.groupService = groupService;
    }
}
