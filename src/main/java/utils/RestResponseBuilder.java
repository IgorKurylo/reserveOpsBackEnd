package utils;

import javax.ws.rs.core.Response;

public class RestResponseBuilder {

    private int statusCode;
    private Object entity;

    public RestResponseBuilder(int statusCode) {
        this.statusCode = statusCode;
    }

    public RestResponseBuilder withEntity(Object o) {
        this.entity = o;
        return this;
    }

    public Response create() {
        return Response.status(this.statusCode).entity(this.entity).build();
    }
}
