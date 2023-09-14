package br.com.leandro.resource;

import br.com.leandro.entity.Message;
import br.com.leandro.service.MessageJms;
import br.com.leandro.service.MessageService;
import jakarta.inject.Inject;
import jakarta.jms.JMSException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;

@Path("/messages")
public class MessageResource {

    @Inject
    private MessageService service;

    @Inject
    private MessageJms jms;

    @GET
    public List<Message> all() {
        return service.findAll();
    }
    @GET
    @Path("{id}")
    public Message byId(String id) {
        return service.findById(id);
    }

    @GET
    @Path("consume")
    public List<Message> consume() throws JMSException {
        return jms.consume();
    }

    @POST
    public RestResponse<Message> save(Message message, @Context UriInfo uriInfo) {
        Message saved = service.save(message);
        return RestResponse.seeOther(uriInfo.getAbsolutePathBuilder().path(saved.getId()).build());
    }

}
