package br.com.leandro.resource;

import br.com.leandro.entity.Message;
import br.com.leandro.service.MessageJms;
import br.com.leandro.service.MessageDB;
import br.com.leandro.service.MessageService;
import jakarta.inject.Inject;
import jakarta.jms.JMSException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;

@Path("/messages")
public class MessageResource {

    @Inject
    private MessageDB messageDB;

    @Inject
    private MessageService service;

    @Inject
    private MessageJms jms;

    @GET
    public List<Message> all() {
        return messageDB.findAll();
    }
    @GET
    @Path("{id}")
    public Message byId(String id) {
        return messageDB.findById(id);
    }

    @GET
    @Path("consume")
    public List<Message> consume() throws JMSException {
        return jms.consume();
    }

    @PUT
    @Path("produce")
    public RestResponse<String> produce() throws JMSException {
        return RestResponse.ok(jms.createAndSend());
    }

    @POST
    public RestResponse<Message> save(@Context UriInfo uriInfo) {
        Message saved = messageDB.save();
        return RestResponse.seeOther(uriInfo.getAbsolutePathBuilder().path(saved.getId()).build());
    }

    @POST
    @Path("findAndSend")
    public RestResponse<String> findByIdAndSend(String id) throws JMSException {
        return RestResponse.ok(service.findByIdAndSend(id));
    }

    @POST
    @Path("consumeAndSave")
    public RestResponse<List<Message>> consumeAndSave() throws JMSException {
        return RestResponse.ok(service.consumeAndSave());
    }

}
