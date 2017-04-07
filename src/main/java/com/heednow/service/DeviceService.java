package com.heednow.service;

import com.heednow.bo.DeviceStatusRequestBO;
import com.heednow.bo.RegisterRequestBO;
import com.heednow.bo.UpdateDeviceRequestBO;
import com.heednow.dao.ClientDAO;
import com.heednow.dao.device.DeviceDAO;
import com.heednow.dao.outlet.OutletDAO;
import com.heednow.dto.request.UpdateSettingsDTO;
import com.heednow.dto.request.OutletDTO;
import com.heednow.exceptions.DeviceNotFoundException;
import com.heednow.exceptions.OutletNotFoundException;
import com.heednow.request.device.DeviceStatusRequest;
import com.heednow.request.device.RegisterRequest;
import com.heednow.request.device.UpdateDeviceRequest;
import com.heednow.requesthandler.DeviceRequestHandler;
import com.heednow.response.device.DeviceResponseList;
import com.heednow.response.util.MessageResponse;
import com.heednow.response.util.ResponseGenerator;
import com.heednow.util.EmailService;
import com.heednow.util.UserRequestValidation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

/**
 * Created by System-2 on 1/17/2017.
 */
@Path("/device")
public class DeviceService {
    @POST
    @Path("/verify")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response verifyDevice(UpdateDeviceRequest deviceRequest) {
        UpdateDeviceRequestBO deviceRequestBO = new UpdateDeviceRequestBO();
        MessageResponse messageResponse = new MessageResponse();

        deviceRequestBO.setAndroidDeviceId(deviceRequest.getAndroidDeviceId());
        deviceRequestBO.setInstallationId(deviceRequest.getInstallationId());
        deviceRequestBO.setOtp(deviceRequest.getOtp());
        deviceRequestBO.setStoreId(deviceRequest.getStoreId());
        deviceRequestBO.setFingerprint(deviceRequest.getFingerprint());
        deviceRequestBO.setClientId(deviceRequest.getClientId());

        try {
            DeviceRequestHandler deviceRequestHandler = new DeviceRequestHandler();
            int clientId = ClientDAO.getClient(deviceRequestBO.getClientId()).getId();
            if (DeviceDAO.getValidOtp(deviceRequestBO.getInstallationId(), deviceRequestBO.getOtp()) > 0) {
                String id = deviceRequestHandler.verifyDevice(deviceRequestBO, clientId);
                return ResponseGenerator.generateSuccessResponse(messageResponse, id);
            } else {
                return ResponseGenerator.generateFailureResponse(messageResponse, "Invalid otp.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Unable to verify the device.");
        } catch (DeviceNotFoundException e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Invalid device id");
        }
    }

    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response installDevice(DeviceStatusRequest deviceRequest, @HeaderParam("Auth") String auth) {
        try {
            if (auth != null && UserRequestValidation.isRequestValid(auth)) {
                DeviceStatusRequestBO deviceRequestBO = new DeviceStatusRequestBO();
                MessageResponse messageResponse = new MessageResponse();

                deviceRequestBO.setAndroidDeviceId(deviceRequest.getAndroidDeviceId());
                deviceRequestBO.setStatus(deviceRequest.getStatus());

                DeviceRequestHandler deviceRequestHandler = new DeviceRequestHandler();
                Boolean isCreate = deviceRequestHandler.updateDevice(deviceRequestBO);
                if (isCreate) {
                    return ResponseGenerator.generateSuccessResponse(messageResponse, "Device updated successfully.");
                } else {
                    return ResponseGenerator.generateFailureResponse(messageResponse, "Unable to add the device.");
                }
            } else {
                return ResponseGenerator.generateResponse(UserRequestValidation.getUnautheticatedResponse());
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessageResponse messageResponse = new MessageResponse();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Unable to add the device.");
        }
    }

    @GET
    @Path("/list")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDeviceList(@HeaderParam("Auth") String auth) {
        try {
            if (auth != null && UserRequestValidation.isRequestValid(auth)) {
                DeviceRequestHandler deviceRequestHandler = new DeviceRequestHandler();
                int clientId = UserRequestValidation.getClient(auth);
                DeviceResponseList deviceResponseList = new DeviceResponseList();
                deviceResponseList.setDevices(deviceRequestHandler.getDeviceList(clientId));
                return ResponseGenerator.generateSuccessResponse(deviceResponseList, "device list.");
            } else {
                return ResponseGenerator.generateResponse(UserRequestValidation.getUnautheticatedResponse());
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessageResponse messageResponse = new MessageResponse();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Unable to retrieve the device list.");
        }
    }


    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerDevice(RegisterRequest registerRequest) {
        DeviceRequestHandler deviceRequestHandler = new DeviceRequestHandler();
        MessageResponse messageResponse = new MessageResponse();

        RegisterRequestBO registerRequestBO = new RegisterRequestBO();
        registerRequestBO.setAndroidDeviceId(registerRequest.getAndroidDeviceId());
        registerRequestBO.setInstallationId(registerRequest.getInstallationId());
        registerRequestBO.setStoreId(registerRequest.getStoreId());
        registerRequestBO.setClientId(registerRequest.getClientId());
        OutletDTO outletDTO = null;
        try {
            int clientId = ClientDAO.getClient(registerRequestBO.getClientId()).getId();
            int otp = DeviceDAO.getValidOtp(registerRequestBO.getInstallationId(), 0);
            outletDTO = OutletDAO.getOutletByStoreId(registerRequestBO.getStoreId(), clientId);
            if (otp == 0) {
                if (outletDTO != null) {
                    deviceRequestHandler.getValidDevice(registerRequestBO, outletDTO.getId());
                    return ResponseGenerator.generateSuccessResponse(messageResponse, "Otp is send to registered email id");
                } else {
                    return ResponseGenerator.generateFailureResponse(messageResponse, "Invalid store id.");
                }
            } else {
                UpdateSettingsDTO updateSettingsDTO = OutletDAO.getSetting(outletDTO.getId());
                EmailService.sendOtp(updateSettingsDTO.getPocEmail(), updateSettingsDTO.getPocName(), otp);
                return ResponseGenerator.generateSuccessResponse(messageResponse, "Otp is send to registered email id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Unable to validate the device.");
        } catch (OutletNotFoundException e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Invalid store id.");
        }
    }

}
