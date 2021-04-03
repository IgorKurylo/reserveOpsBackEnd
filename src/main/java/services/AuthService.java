package services;

import application.ApplicationConfig;
import models.user.AccessToken;
import models.user.AuthCredentials;
import models.user.User;
import repository.contracts.IAuthRepository;
import security.AccessTokenGenerator;
import models.*;
import security.AuthenticationTokenDetails;
import interfaces.IConverter;
import utils.RestResponseBuilder;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;


@Path("auth")
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
        User user = repository.authentication(authCredentials);
        if (user.getId() != 0) {
            AuthenticationTokenDetails details = new AuthenticationTokenDetails(user);
            String token = this.accessTokenGenerator.generate(details);
            baseResponse = new BaseResponse<>(new AccessToken(token, user.getRole().name(), user), "", true);
            response = new RestResponseBuilder(200)
                    .withEntity(converter.Serializable(baseResponse))
                    .create();
        } else {
            baseResponse = new BaseResponse<>(null, "User not exists", false);
            response = new RestResponseBuilder(404)
                    .withEntity(converter.Serializable(baseResponse))
                    .create();
        }
        return response;
    }

    @POST
    @Path("register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registration(User user) {
        int userId = repository.registration(user);
        Response response = null;
        BaseResponse<User> baseResponse;
        if (userId != -1) {
            baseResponse = new BaseResponse<>(new User(userId), "user created", true);
            response = new RestResponseBuilder(201).withEntity(baseResponse).create();
        } else {
            baseResponse = new BaseResponse<>(null, "fail on user creation", false);
            response = new RestResponseBuilder(400).withEntity(baseResponse).create();
        }
        return response;
    }
}
