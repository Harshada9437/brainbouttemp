package com.heednow.requesthandler;

import com.heednow.bo.DeviceStatusRequestBO;
import com.heednow.bo.RegisterRequestBO;
import com.heednow.bo.UpdateDeviceRequestBO;
import com.heednow.dao.device.DeviceDAO;
import com.heednow.dao.outlet.OutletDAO;
import com.heednow.dto.request.UpdateSettingsDTO;
import com.heednow.dto.request.DeviceDTO;
import com.heednow.dto.request.RegisterDTO;
import com.heednow.exceptions.DeviceNotFoundException;
import com.heednow.response.device.DeviceResponse;
import com.heednow.util.DateUtil;
import com.heednow.util.EmailService;

import javax.xml.bind.DatatypeConverter;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by System-2 on 1/17/2017.
 */
public class DeviceRequestHandler {

    public List<DeviceResponse> getDeviceList(int clientId) throws SQLException {
        DeviceDAO deviceDAO = new DeviceDAO();
        List<DeviceDTO> deviceDTOs = deviceDAO.getDeviceList(clientId);
        List<DeviceResponse> deviceList = new ArrayList<DeviceResponse>();
        for (DeviceDTO deviceDTO : deviceDTOs) {
            DeviceResponse deviceResponse = new DeviceResponse(deviceDTO.getStoreId(),
                    deviceDTO.getId(),
                    deviceDTO.getFeedbackId(),
                    deviceDTO.getInstallationId(),
                    deviceDTO.getFingerprint(),
                    deviceDTO.getAndroidDeviceId(),
                    deviceDTO.getInstallationDate(),
                    deviceDTO.getStatus(),
                    deviceDTO.getFeedbackDate());
            deviceList.add(deviceResponse);
        }
        return deviceList;
    }

    public Boolean getValidDevice(RegisterRequestBO registerRequestBO, int outletId) throws SQLException {
        DeviceDAO deviceDAO = new DeviceDAO();
        int otp = Integer.parseInt(random(6));
        Boolean isCreate = deviceDAO.getValidDevice(biuildDTOFromBO(registerRequestBO), otp);
        if (isCreate) {
            UpdateSettingsDTO updateSettingsDTO = OutletDAO.getSetting(outletId);
            EmailService.sendOtp(updateSettingsDTO.getPocEmail(), updateSettingsDTO.getPocName(), otp);
        }
        return isCreate;
    }

    private RegisterDTO biuildDTOFromBO(RegisterRequestBO registerRequestBO) {
        RegisterDTO registerDTO = new RegisterDTO();

        registerDTO.setAndroidDeviceId(registerRequestBO.getAndroidDeviceId());
        registerDTO.setStoreId(registerRequestBO.getStoreId());
        registerDTO.setInstallationId(registerRequestBO.getInstallationId());

        return registerDTO;
    }

    public static String random(int size) {

        StringBuilder generatedToken = new StringBuilder();
        try {
            SecureRandom number = SecureRandom.getInstance("SHA1PRNG");
            // Generate 20 integers 0..20
            for (int i = 0; i < size; i++) {
                generatedToken.append(number.nextInt(9));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return generatedToken.toString();
    }

    public String verifyDevice(UpdateDeviceRequestBO deviceRequestBO,int clientId) throws SQLException, DeviceNotFoundException {
        DeviceDAO deviceDAO = new DeviceDAO();
        String token = getToken(deviceRequestBO,clientId);
        DeviceDTO deviceDTO = deviceDAO.getDeviceByInstallationId(deviceRequestBO.getInstallationId());
        String id=token;
        if (deviceDTO.getId() > 0) {
            deviceDTO.setFingerprint(deviceRequestBO.getFingerprint());
            deviceDTO.setAndroidDeviceId(deviceRequestBO.getAndroidDeviceId());
            deviceDTO.setStoreId(deviceRequestBO.getStoreId());
            deviceDTO.setInstallationDate(DateUtil.getCurrentServerTime());
            deviceDTO.setToken(token);
            deviceDAO.updateDevice(deviceDTO);
        } else {
            id = deviceDAO.verifyDevice(buildUpdateDtofromBo(deviceRequestBO, token,clientId));
        }
        return id;
    }

    private String getToken(UpdateDeviceRequestBO deviceRequestBO,int clientId) {
        String device = deviceRequestBO.getInstallationId() +":"+ deviceRequestBO.getStoreId() +":"+ clientId;
        String token = DatatypeConverter.printBase64Binary(device.getBytes());
        return token;
    }

    private DeviceDTO buildUpdateDtofromBo(UpdateDeviceRequestBO deviceRequestBO, String token,int clientId) {
        DeviceDTO deviceDTO = new DeviceDTO();

        deviceDTO.setOtp(deviceRequestBO.getOtp());
        deviceDTO.setFingerprint(deviceRequestBO.getFingerprint());
        deviceDTO.setInstallationId(deviceRequestBO.getInstallationId());
        deviceDTO.setAndroidDeviceId(deviceRequestBO.getAndroidDeviceId());
        deviceDTO.setStoreId(deviceRequestBO.getStoreId());
        deviceDTO.setClientId(clientId);
        deviceDTO.setToken(token);
        return deviceDTO;
    }

    public Boolean updateDevice(DeviceStatusRequestBO deviceRequestBO) throws SQLException, DeviceNotFoundException {
        DeviceDAO deviceDAO = new DeviceDAO();
        Boolean isCreate = deviceDAO.updateDevice(buildStatusDtofromBo(deviceRequestBO));
        return isCreate;
    }

    private DeviceDTO buildStatusDtofromBo(DeviceStatusRequestBO deviceRequestBO) {
        DeviceDTO deviceDTO = new DeviceDTO();

        deviceDTO.setAndroidDeviceId(deviceRequestBO.getAndroidDeviceId());
        deviceDTO.setStatus(deviceRequestBO.getStatus());
        return deviceDTO;
    }
}
