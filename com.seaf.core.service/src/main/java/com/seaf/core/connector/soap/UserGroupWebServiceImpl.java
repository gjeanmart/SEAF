package com.seaf.core.connector.soap;

import com.seaf.core.service.business.UserGroupService;
import com.seaf.core.service.model.UserDto;
import com.seaf.core.service.model.utils.EnvelopeList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import java.util.List;

/**
 * Created by Grégoire JEANMART on 2016-01-15.
 */
@Component
@Service("userGroupWebServiceEndpoint")
public class UserGroupWebServiceImpl implements UserGroupWebService {
    private static final Logger LOGGER  						= LoggerFactory.getLogger(UserGroupWebServiceImpl.class);

    @Autowired
    private UserGroupService userGroupService;

    @Override
    public EnvelopeList getUser(String searchQuery, int pageNumber, int pageSize, String sortAttribute, String sortDirection) throws Exception {
        LOGGER.debug("[START] Get users with search query ''");

        if(searchQuery != null && searchQuery.equals(""))
            searchQuery = null;

        EnvelopeList envelope = userGroupService.getUsers(searchQuery, pageNumber, pageSize, sortAttribute, sortDirection);

        LOGGER.debug("[END] Get users with search query '' : {} result(s)", envelope.getTotal());

        return envelope;
    }
}
