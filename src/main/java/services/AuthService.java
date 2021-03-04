package services;

import application.ApplicationConfig;
import repository.contracts.IAuthRepository;
import security.AccessTokenGenerator;
import models.*;
import security.AuthenticationTokenDetails;
import utils.IConverter;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;

@Path("/auth")
public class AuthService {
    @Inject
    IAuthRepository repository;
    @Inject
    IConverter converter;
    AccessTokenGenerator accessTokenGenerator;

    public AuthService() {
        try {
            accessTokenGenerator = new AccessTokenGenerator(ApplicationConfig.getInstance().getValue("token_secret"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response authentication(AuthCredentials authCredentials) {
        Response response = null;
        BaseResponse<AccessToken> baseResponse;
        User user = repository.authentication(authCredentials.getUserName(), authCredentials.getPassword());
        if (user != null) {
            AuthenticationTokenDetails details = new AuthenticationTokenDetails(user);
            String token = this.accessTokenGenerator.generate(details);
            baseResponse = new BaseResponse<>(new AccessToken(token), "", true);
            response = Response.status(200).entity(converter.Serializable(baseResponse)).build();
        } else {
            baseResponse = new BaseResponse<>(null, "User not exists", false);
            response = Response.status(400).entity(converter.Serializable(baseResponse)).build();
        }
        return response;
    }
}
