package com.heednow.requesthandler;

import com.heednow.bo.*;
import com.heednow.dao.outlet.OutletDAO;
import com.heednow.dto.request.UpdateSettingsDTO;
import com.heednow.dto.request.OutletDTO;
import com.heednow.dto.request.TempDTO;
import com.heednow.exceptions.OutletNotFoundException;
import com.heednow.exceptions.UserNotFoundException;
import com.heednow.response.outlet.OutletInfo;
import com.heednow.response.outlet.OutletResponseL;
import com.heednow.response.outlet.OutletResponseList;
import com.heednow.sync.Outlet;
import com.heednow.util.Base64Encoder;

import java.sql.SQLException;
import java.util.*;

/**
 * Created by System-2 on 12/20/2016.
 */
public class OutletRequestHandler {
    public Boolean assignTemplate(AssignTemplateRequestBO assignTemplateRequestBO, int outletId) throws SQLException {
        OutletDAO outletDAO = new OutletDAO();
        Boolean isCreated = outletDAO.assignTemplate(buildOutletDTOFromBO(assignTemplateRequestBO), outletId);
        return isCreated;
    }

    private TempDTO buildOutletDTOFromBO(AssignTemplateRequestBO assignTemplateRequestBO) {
        TempDTO tempDTO = new TempDTO();
        tempDTO.setTemplateId(assignTemplateRequestBO.getTemplateId());
        tempDTO.setFromDate(assignTemplateRequestBO.getFromDate());
        tempDTO.setToDate(assignTemplateRequestBO.getToDate());
        return tempDTO;
    }

    public List<OutletResponseL> getOutlate(OutletListRequestBO outletListRequestBO,int clientId) throws SQLException, UserNotFoundException {
        OutletDAO outletDAO = new OutletDAO();
        List<OutletResponseL> outletResponseLists = getOutletListDTOsFromBO(outletDAO.getOutlet(clientId,outletListRequestBO.getOutletId(), outletListRequestBO.getUserId()));
        return outletResponseLists;
    }

    public List<OutletResponseL> getOutletListDTOsFromBO(List<OutletDTO> outletDTOs) throws SQLException {
        List<OutletResponseL> outletResponseLists = new ArrayList<OutletResponseL>();
        Iterator<OutletDTO> outletListDTOIterator = outletDTOs.iterator();
        while (outletListDTOIterator.hasNext()) {
            OutletDTO outletDTO = outletListDTOIterator.next();
            OutletResponseL outletResponse = new OutletResponseL();
            outletResponse.setPosStoreId(outletDTO.getPosStoreId());
            outletResponse.setId(outletDTO.getId());
            outletResponse.setTemplateName(outletDTO.getTemplateName());
            outletResponse.setTemplateId(outletDTO.getTemplateId());
            outletResponse.setGroupId(outletDTO.getGroupId());
            outletResponse.setRegionId(outletDTO.getRegionId());
            outletResponse.setCompanyId(outletDTO.getCompanyId());
            outletResponse.setClusterId(outletDTO.getClusterId());
            outletResponse.setShortDesc(outletDTO.getShortDesc());
            outletResponse.setClusterName(outletDTO.getClusterName());
            outletResponse.setGroupName(outletDTO.getGroupName());
            outletResponse.setOutletDesc(outletDTO.getOutletDesc());
            outletResponse.setRegionName(outletDTO.getRegionName());
            outletResponse.setCompanyName(outletDTO.getCompanyName());
            outletResponseLists.add(outletResponse);
        }
        return outletResponseLists;
    }

    public OutletInfo getOutletById(int outletId) throws SQLException, OutletNotFoundException {
        OutletDAO outletDAO = new OutletDAO();
        OutletInfo outletResponse = buildResponseFromDTO1(outletDAO.getOutletById(outletId));
        return outletResponse;
    }

    private OutletInfo buildResponseFromDTO1(OutletDTO outletDTO) {
        OutletInfo outletResponse = new OutletInfo();
        outletResponse.setPosStoreId(outletDTO.getPosStoreId());
        outletResponse.setId(outletDTO.getId());
        outletResponse.setGroupId(outletDTO.getGroupId());
        outletResponse.setRegionId(outletDTO.getRegionId());
        outletResponse.setCompanyId(outletDTO.getCompanyId());
        outletResponse.setClusterId(outletDTO.getClusterId());
        outletResponse.setBannerUrl(outletDTO.getBannerUrl());
        outletResponse.setShortDesc(outletDTO.getShortDesc());
        outletResponse.setClusterName(outletDTO.getClusterName());
        outletResponse.setGroupName(outletDTO.getGroupName());
        outletResponse.setMobileNoLength(outletDTO.getMobileNoLength());
        outletResponse.setOutletDesc(outletDTO.getOutletDesc());
        outletResponse.setRegionName(outletDTO.getRegionName());
        outletResponse.setCompanyName(outletDTO.getCompanyName());
        outletResponse.setTemplateName(outletDTO.getTemplateName());
        outletResponse.setTemplateId(outletDTO.getTemplateId());
        outletResponse.setTableNoRange(outletDTO.getTableNoRange());
        outletResponse.setPocName(outletDTO.getPocName());
        outletResponse.setPocEmail(outletDTO.getPocEmail());
        outletResponse.setPocMobile(outletDTO.getPocMobile());
        outletResponse.setSmsGatewayId(outletDTO.getSmsGatewayId());
        outletResponse.setMgrName(outletDTO.getMgrName());
        outletResponse.setMgrMobile(outletDTO.getMgrMobile());
        outletResponse.setMgrEmail(outletDTO.getMgrEmail());

        return outletResponse;
    }

    private OutletResponseList buildResponseFromDTO(OutletDTO outletDTO) throws SQLException {
        OutletResponseList outletResponse = new OutletResponseList();
        TemplateRequestHandler templateRequestHandler = new TemplateRequestHandler();
        outletResponse.setPosStoreId(outletDTO.getPosStoreId());
        outletResponse.setId(outletDTO.getId());
        outletResponse.setGroupId(outletDTO.getGroupId());
        outletResponse.setRegionId(outletDTO.getRegionId());
        outletResponse.setCompanyId(outletDTO.getCompanyId());
        outletResponse.setClusterId(outletDTO.getClusterId());
        outletResponse.setShortDesc(outletDTO.getShortDesc());
        outletResponse.setClusterName(outletDTO.getClusterName());
        outletResponse.setGroupName(outletDTO.getGroupName());
        outletResponse.setOutletDesc(outletDTO.getOutletDesc());
        outletResponse.setRegionName(outletDTO.getRegionName());
        outletResponse.setCompanyName(outletDTO.getCompanyName());
        outletResponse.setTemplateName(outletDTO.getTemplateName());
        outletResponse.setTemplateId(outletDTO.getTemplateId());
        outletResponse.setTableNoRange(outletDTO.getTableNoRange());
        outletResponse.setBannerUrl(outletDTO.getBannerUrl());
        outletResponse.setMobileNoLength(outletDTO.getMobileNoLength());
        outletResponse.setQuestions(templateRequestHandler.getAssignedQuestions1(outletDTO.getTemplateId()));
        return outletResponse;
    }

    public OutletResponseList getOutletByStoreId(String token) throws SQLException, OutletNotFoundException {
        OutletDAO outletDAO = new OutletDAO();
        List<String> decode= Base64Encoder.decode(token);
        OutletResponseList outletResponse = buildResponseFromDTO(outletDAO.getOutletByStoreId(decode.get(1),Integer.parseInt(decode.get(2))));
        return outletResponse;
    }

    public Boolean updateSettings(UpdateSettingsRequestBO updateSettingsRequestBO, int outletId) throws SQLException {
        Boolean isProcessed;
        OutletDAO outletDAO = new OutletDAO();
        UpdateSettingsDTO updateSettingsDTO = OutletDAO.getSetting(outletId);
        if (updateSettingsDTO != null) {
            isProcessed = outletDAO.updateSettings(buildDTOFromBO(updateSettingsRequestBO), outletId);
        } else {
            isProcessed = outletDAO.createSettings(buildDTOFromBO(updateSettingsRequestBO), outletId);
        }
        return isProcessed;
    }

    private UpdateSettingsDTO buildDTOFromBO(UpdateSettingsRequestBO updateSettingsRequestBO) {
        UpdateSettingsDTO updateSettingsDTO = new UpdateSettingsDTO();
        updateSettingsDTO.setBannerUrl(updateSettingsRequestBO.getBannerUrl());
        updateSettingsDTO.setMobileNoLength(updateSettingsRequestBO.getMobileNoLength());
        updateSettingsDTO.setTableNoRange(updateSettingsRequestBO.getTableNoRange());
        updateSettingsDTO.setPocName(updateSettingsRequestBO.getPocName());
        updateSettingsDTO.setPocMobile(updateSettingsRequestBO.getPocMobile());
        updateSettingsDTO.setPocEmail(updateSettingsRequestBO.getPocEmail());
        updateSettingsDTO.setMgrEmail(updateSettingsRequestBO.getMgrEmail());
        updateSettingsDTO.setMgrMobile(updateSettingsRequestBO.getMgrMobile());
        updateSettingsDTO.setMgrName(updateSettingsRequestBO.getMgrName());
        updateSettingsDTO.setSmsGatewayId(updateSettingsRequestBO.getSmsGatewayId());
        return updateSettingsDTO;
    }

    public int createOutlet(OutletRequestBO outletRequestBO,int clientId) throws SQLException {
        int  id=0;
        OutletDAO outletDAO = new OutletDAO();
        id = outletDAO.createOutlet(buildOutletDTOFromBO(outletRequestBO),clientId);
        return id;
    }

    private OutletDTO buildOutletDTOFromBO(OutletRequestBO outletRequestBO) {
        OutletDTO outletRequestDTO = new OutletDTO();
        outletRequestDTO.setOutletDesc(outletRequestBO.getOutletDesc());
        outletRequestDTO.setShortDesc(outletRequestBO.getShortDesc());
        outletRequestDTO.setClusterId(outletRequestBO.getClusterId());
        outletRequestDTO.setRegionId(outletRequestBO.getRegionId());
        outletRequestDTO.setCompanyId(outletRequestBO.getCompanyId());
        outletRequestDTO.setGroupId(outletRequestBO.getGroupId());
        outletRequestDTO.setPosStoreId(outletRequestBO.getPosStoreId());
        return outletRequestDTO;
    }

    public boolean updateOutlet(UpdateOutletRequestBO outletRequestBO) throws SQLException {
        OutletDAO outletDAO = new OutletDAO();
        boolean isProcessed = outletDAO.updateOutlet(buildOutletDTOFromBO1(outletRequestBO));
        return isProcessed;
    }

    private Outlet buildOutletDTOFromBO1(UpdateOutletRequestBO outletRequestBO) {
        Outlet outletRequestDTO = new Outlet();
        outletRequestDTO.setId(outletRequestBO.getId());
        outletRequestDTO.setOutletDesc(outletRequestBO.getOutletDesc());
        outletRequestDTO.setShortDesc(outletRequestBO.getShortDesc());
        outletRequestDTO.setClusterId(outletRequestBO.getClusterId());
        outletRequestDTO.setRegionId(outletRequestBO.getRegionId());
        outletRequestDTO.setCompanyId(outletRequestBO.getCompanyId());
        outletRequestDTO.setGroupId(outletRequestBO.getGroupId());
        outletRequestDTO.setPosStoreId(outletRequestBO.getPosStoreId());
        return outletRequestDTO;
    }
}
