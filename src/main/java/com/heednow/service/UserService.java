package com.heednow.service;

import com.heednow.bo.*;
import com.heednow.dao.ClientDAO;
import com.heednow.dao.user.UsersDAO;
import com.heednow.exceptions.RoleNotFoundException;
import com.heednow.exceptions.UserNotFoundException;
import com.heednow.request.user.*;
import com.heednow.requesthandler.UserRequestHandler;
import com.heednow.response.user.*;
import com.heednow.response.util.MessageResponse;
import com.heednow.response.util.ResponseGenerator;
import com.heednow.util.UserRequestValidation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by System-2 on 12/24/2016.
 */
@Path("/user")
public class UserService {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/login")
    public Response login(LoginRequest loginRequest) {
        LoginRequestBO loginRequestBO = new LoginRequestBO();
        loginRequestBO.setUserName(loginRequest.getUserName());
        loginRequestBO.setPassword(loginRequest.getPassword());
        loginRequestBO.setClientId(loginRequest.getClientId());
        UserRequestHandler userRequestHandler = new UserRequestHandler();
        LoginResponse loginResponse = new LoginResponse();
        MessageResponse messageResponse = new MessageResponse();
        try {
            int id = (ClientDAO.getClient(loginRequestBO.getClientId()).getId());
            LoginResponseBO loginResponseBO = userRequestHandler
                    .login(loginRequestBO,id);
            loginResponse.setRoleId(loginResponseBO.getRoleId());
            loginResponse.setUserName(loginResponseBO.getUserName());
            loginResponse.setName(loginResponseBO.getName());
            loginResponse.setEmail(loginResponseBO.getEmail());
            loginResponse.setStatus(loginResponseBO.getStatus());
            loginResponse.setSessionId(loginResponseBO.getSessionId());
            loginResponse.setId(loginResponseBO.getId());
            loginResponse.setMenuAccess(loginResponseBO.getMenuAccess());
            loginResponse.setOutletAccess(loginResponseBO.getOutletAccess());
            loginResponse.setClientId(loginResponseBO.getClientId());
            if (loginResponseBO.getSessionId() != null && loginResponseBO.getStatus().equals("A")) {
                return ResponseGenerator.generateSuccessResponse(loginResponse, String.valueOf(loginResponseBO.getSessionId()));
            } else {
                return ResponseGenerator.generateFailureResponse(messageResponse, "Invalid password or inactive user.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Invalid Log in");
        }

    }

    @POST
    @Path("/logout")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout(@HeaderParam("sessionId") String sessionId) {
        LoginResponse loginResponse = new LoginResponse();
        MessageResponse messageResponse = new MessageResponse();
        try {
            UserRequestHandler userRequestHandler = new UserRequestHandler();
            String[] sessionIdParts = sessionId.split("@");
            Boolean isLoggedOut = userRequestHandler.logout(Integer.parseInt(sessionIdParts[1]), sessionIdParts
                    [0]);
            if (isLoggedOut) {
                return ResponseGenerator.generateSuccessResponse(loginResponse, "Log out successfully.");
            } else {
                return ResponseGenerator.generateFailureResponse(messageResponse, "Unable to log out the current user.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Unable to log out the current user.");
        }
    }

    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(UpdateUserRequest updateUserRequest, @HeaderParam("Auth") String auth) {
        MessageResponse messageResponse = new MessageResponse();
        try {
            if (auth != null && UserRequestValidation.isRequestValid(auth)) {
                UserRequestHandler userRequestHandler = new UserRequestHandler();
                UpdateUserRequestBO updateUserRequestBO = new UpdateUserRequestBO();
                updateUserRequestBO.setEmail(updateUserRequest.getEmail());
                updateUserRequestBO.setName(updateUserRequest.getName());
                updateUserRequestBO.setRole(updateUserRequest.getRole());
                updateUserRequestBO.setStatus(updateUserRequest.getStatus());
                updateUserRequestBO.setId(updateUserRequest.getId());
                Boolean isLoggedOut = userRequestHandler.updateUser(updateUserRequestBO);

                if (isLoggedOut) {
                    return ResponseGenerator.generateSuccessResponse(messageResponse, "updated successfully.");

                } else {
                    return ResponseGenerator.generateFailureResponse(messageResponse, "Unable to update user.");
                }
            } else {
                return ResponseGenerator.generateResponse(UserRequestValidation.getUnautheticatedResponse());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Unable to update user.");
        }
    }

    @GET
    @Path("/userInfo/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    public Response getuserById(@PathParam("id") int id, @HeaderParam("Auth") String auth) {
        System.out.println(auth);
        UserRequestHandler userRequestHandler = new UserRequestHandler();
        MessageResponse messageResponse = new MessageResponse();
        try {
            if (auth != null && UserRequestValidation.isRequestValid(auth)) {
                System.out.println(auth);
                UserResponse userResponse = userRequestHandler.getUserById(id);
                return ResponseGenerator.generateSuccessResponse(userResponse, "UserInfo");
            } else {
                return ResponseGenerator.generateResponse(UserRequestValidation.getUnautheticatedResponse());
            }
        } catch (UserNotFoundException e) {
            return ResponseGenerator.generateFailureResponse(messageResponse, "INVALID UserId ");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "failed to retrieve. ");
        }
    }


    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/menuList")

    public Response getMenuList(@HeaderParam("Auth") String auth) {
        UserRequestHandler userRequestHandler = new UserRequestHandler();
        MenuResponseList menuResponseList = new MenuResponseList();
        MessageResponse messageResponse = new MessageResponse();
        try {
            if (auth != null && UserRequestValidation.isRequestValid(auth)) {
                menuResponseList.setMenus(userRequestHandler.getMenuList());
                return ResponseGenerator.generateSuccessResponse(menuResponseList, "List of menus.");
            } else {
                return ResponseGenerator.generateResponse(UserRequestValidation.getUnautheticatedResponse());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Failed to retrieve.");
        }
    }

    @GET
    @Path("/roleDetail/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    public Response getroleById(@PathParam("id") int id, @HeaderParam("Auth") String auth) {
        UserRequestHandler userRequestHandler = new UserRequestHandler();
        MessageResponse messageResponse = new MessageResponse();
        try {
            if (auth != null && UserRequestValidation.isRequestValid(auth)) {
                RoleByIdResponse roleByIdResponse = userRequestHandler.getroleById(id);
                return ResponseGenerator.generateSuccessResponse(roleByIdResponse, "SUCCESS");
            } else {
                return ResponseGenerator.generateResponse(UserRequestValidation.getUnautheticatedResponse());
            }
        } catch (RoleNotFoundException e) {
            return ResponseGenerator.generateFailureResponse(messageResponse, "INVALID RollId ");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Failed to retrieve.");
        }
    }


    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/roleList")
    public Response getRoleList(@HeaderParam("Auth") String auth) {
        UserRequestHandler userRequestHandler = new UserRequestHandler();
        RoleResponseList roleResponseList = new RoleResponseList();
        MessageResponse messageResponse = new MessageResponse();
        try {
            if (auth != null && UserRequestValidation.isRequestValid(auth)) {
                int clientId = UserRequestValidation.getClient(auth);
                roleResponseList.setRoles(userRequestHandler.getRoleList(clientId));
                return ResponseGenerator.generateSuccessResponse(roleResponseList, "List of roles.");
            } else {
                return ResponseGenerator.generateResponse(UserRequestValidation.getUnautheticatedResponse());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Failed to retrieve.");
        }
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/list")
    public Response getUserList(@HeaderParam("Auth") String auth) {
        UserRequestHandler userRequestHandler = new UserRequestHandler();
        UserResponseList userResponseList = new UserResponseList();
        try {
            if (auth != null && UserRequestValidation.isRequestValid(auth)) {
                int clientId = UserRequestValidation.getClient(auth);
                userResponseList.setUsers(userRequestHandler.getUserList(clientId));
                return ResponseGenerator.generateSuccessResponse(userResponseList, "List of users.");
            } else {
                return ResponseGenerator.generateResponse(UserRequestValidation.getUnautheticatedResponse());
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessageResponse messageResponse = new MessageResponse();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Failed to retrieve.");
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/create")
    public Response createUser(UserRequest userRequest, @HeaderParam("Auth") String auth) {
        try {
            if (auth != null && UserRequestValidation.isRequestValid(auth)) {
                UserRequestBO userRequestBO = new UserRequestBO();
                userRequestBO.setUserName(userRequest.getUserName());
                userRequestBO.setName(userRequest.getName());
                userRequestBO.setEmail(userRequest.getEmail());
                userRequestBO.setPassword(userRequest.getPassword());
                userRequestBO.setRoleId(userRequest.getRoleId());
                int clientId = UserRequestValidation.getClient(auth);
                MessageResponse createUserResponse = new MessageResponse();
                UserRequestHandler userRequestHandler = new UserRequestHandler();

                if (!UsersDAO.getuser(userRequest.getUserName(), userRequest.getEmail(), userRequest.getName())) {
                    int userId = userRequestHandler.createUser(userRequestBO,clientId);
                    return ResponseGenerator.generateSuccessResponse(createUserResponse, String.valueOf(userId));

                } else {
                    return ResponseGenerator.generateFailureResponse(createUserResponse, "Username or email address already exists.");
                }
            } else {
                return ResponseGenerator.generateResponse(UserRequestValidation.getUnautheticatedResponse());
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessageResponse createUserResponse = new MessageResponse();
            return ResponseGenerator.generateFailureResponse(createUserResponse, "User creation Failed");
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/createRole")
    public Response createRoll(RollRequest rollRequest, @HeaderParam("Auth") String auth) {
        try {
            if (auth != null && UserRequestValidation.isRequestValid(auth)) {
                RoleRequestBO roleRequestBO = new RoleRequestBO();
                roleRequestBO.setName(rollRequest.getName());
                roleRequestBO.setMenuAccess(rollRequest.getMenuAccess());
                roleRequestBO.setOutletAccess(rollRequest.getOutletAccess());
                roleRequestBO.setIsAll(rollRequest.getIsAll());
                int clientId = UserRequestValidation.getClient(auth);
                roleRequestBO.setClientId(clientId);
                MessageResponse createUserResponse = new MessageResponse();
                UserRequestHandler userRequestHandler = new UserRequestHandler();

                if (!UsersDAO.getRole(rollRequest.getName())) {
                    int userId = userRequestHandler.createRoll(roleRequestBO);
                    return ResponseGenerator.generateSuccessResponse(createUserResponse, String.valueOf(userId));
                } else {
                    return ResponseGenerator.generateFailureResponse(createUserResponse, "Role already exists.");
                }
            } else {
                return ResponseGenerator.generateResponse(UserRequestValidation.getUnautheticatedResponse());
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessageResponse createUserResponse = new MessageResponse();
            return ResponseGenerator.generateFailureResponse(createUserResponse, "Role creation failed");
        }
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/updateRole")
    public Response updateRoll(UpdateRollRequest updateRollRequest, @HeaderParam("Auth") String auth) {
        try {
            if (auth != null && UserRequestValidation.isRequestValid(auth)) {
                UpdateRollRequestBO updateFeedbackRequestBO = new UpdateRollRequestBO();
                updateFeedbackRequestBO.setRoleId(updateRollRequest.getRoleId());
                updateFeedbackRequestBO.setIsAll(updateRollRequest.getIsAll());
                updateFeedbackRequestBO.setName(updateRollRequest.getName());
                updateFeedbackRequestBO.setMenuAccess(updateRollRequest.getMenuAccess());
                updateFeedbackRequestBO.setOutletAccess(updateRollRequest.getOutletAccess());

                UserRequestHandler userRequestHandler = new UserRequestHandler();
                MessageResponse messageResponse = new MessageResponse();
                if (userRequestHandler.updateRoll(updateFeedbackRequestBO)) {
                    return ResponseGenerator.generateSuccessResponse(messageResponse, "Role updated successfully");
                } else {
                    return ResponseGenerator.generateFailureResponse(messageResponse, "Unable to update the role.");
                }
            } else {
                return ResponseGenerator.generateResponse(UserRequestValidation.getUnautheticatedResponse());
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessageResponse messageResponse = new MessageResponse();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Unable to update the role.");
        }
    }

    @POST
    @Path("/changePassword")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response changePassword(ChangePasswordRequest changePwdReq) {
        ChangePasswordBO changePwdBO = new ChangePasswordBO(
                changePwdReq.getUserId(), changePwdReq.getOldPassword(),
                changePwdReq.getNewPassword());

        UserRequestHandler appUserRequestHandler = new UserRequestHandler();
        MessageResponse loginResponse = new MessageResponse();
        try {
            if (appUserRequestHandler.changePassword(changePwdBO)) {
                return ResponseGenerator.generateSuccessResponse(loginResponse, "Password updated.");
            } else {
                return ResponseGenerator.generateFailureResponse(loginResponse, "Password update failed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(loginResponse, "Password update failed.");
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/resetPassword")
    public Response changePassword(ResetPasswordRequest resetPasswordRequest, @HeaderParam("Auth") String auth) {
        try {
            if (auth != null && UserRequestValidation.isRequestValid(auth)) {
                ResetPasswordRequestBO resetPasswordRequestBO = new ResetPasswordRequestBO(
                        resetPasswordRequest.getId(),
                        resetPasswordRequest.getNewPassword());
                UserRequestHandler userRequestHandler = new UserRequestHandler();
                MessageResponse messageResponse = new MessageResponse();
                if (userRequestHandler.resetPassword(resetPasswordRequestBO)) {
                    return ResponseGenerator.generateSuccessResponse(messageResponse, "Password has been reset successfully.");
                } else {
                    return ResponseGenerator.generateFailureResponse(messageResponse, "Reset password failed.");
                }
            } else {
                return ResponseGenerator.generateResponse(UserRequestValidation.getUnautheticatedResponse());
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessageResponse messageResponse = new MessageResponse();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Reset password failed.");
        }
    }
}
