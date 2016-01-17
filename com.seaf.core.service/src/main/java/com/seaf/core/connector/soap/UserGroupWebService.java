package com.seaf.core.connector.soap;

import com.seaf.core.service.model.utils.EnvelopeList;
import org.apache.cxf.jaxrs.model.wadl.*;
import javax.validation.constraints.*;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


/**
 * Created by Grégoire JEANMART on 2016-01-15.
 */
@Path("/usergroup/")
@WebService
public interface UserGroupWebService {

    @WebMethod
    @GET
    @Produces( {MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON} )
    @Path("{user}")
    @Descriptions({
            @Description(value = "returns the users list", target = DocTarget.METHOD),
            @Description(value = "the users list", target = DocTarget.RETURN)
    })
    EnvelopeList getUser(
            @Description(value = "Query String") @PathParam("searchQuery") String searchQuery,
            @Description(value = "Pagination : n° of page") @PathParam("pageNumber") @NotNull int pageNumber,
            @Description(value = "Pagination : size of page") @PathParam("pageSize") @NotNull int pageSize,
            @Description(value = "Sort : Attribute for sorting result") @PathParam("sortAttribute") @NotNull String sortAttribute,
            @Description(value = "Direction [asc/desc] for sorting result") @PathParam("sortDirection") @NotNull String sortDirection) throws Exception;

}
