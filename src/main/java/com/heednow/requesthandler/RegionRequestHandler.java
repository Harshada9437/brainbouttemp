package com.heednow.requesthandler;


import com.heednow.bo.RegionRequestBO;
import com.heednow.dao.Sync.RegionDAO;
import com.heednow.dto.request.RegionRequestDTO;
import com.heednow.response.region.RegionResponse;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by System-3 on 4/3/2017.
 */
public class RegionRequestHandler {
    public Boolean createRegions(RegionRequestBO regionRequestBO) throws SQLException {
        Boolean  iscreated=true;
        RegionDAO regionDAO = new RegionDAO();
        iscreated = regionDAO.createRegions(buildRegionDTOFromBO(regionRequestBO));
        return iscreated;
    }

    private RegionRequestDTO buildRegionDTOFromBO(RegionRequestBO regionRequestBO) {
        RegionRequestDTO regionRequestDTO = new RegionRequestDTO();
        regionRequestDTO.setId(regionRequestBO.getId());
        regionRequestDTO.setRegionDesc(regionRequestBO.getRegionDesc());
        regionRequestDTO.setRegionsDesc(regionRequestBO.getRegionsDesc());
        regionRequestDTO.setCompanyId(regionRequestBO.getCompanyId());
        regionRequestDTO.setClientId(regionRequestBO.getClientId());

        return regionRequestDTO;
    }


    public List<RegionResponse> getRegionsList(int companyId,int clientId) throws SQLException {
        RegionDAO regionDAO = new RegionDAO();
        List<RegionResponse> regionList = new ArrayList<RegionResponse>();

        List<RegionRequestDTO> regionRequestDTOList = regionDAO.getRegionsDetail(companyId,clientId);
        for (RegionRequestDTO regionRequestDTO : regionRequestDTOList) {
            RegionResponse regionResponse = new RegionResponse();
            regionResponse.setId(regionRequestDTO.getId());
            regionResponse.setRegionDesc(regionRequestDTO.getRegionDesc());
            regionResponse.setRegionsDesc(regionRequestDTO.getRegionsDesc());
            regionResponse.setCompanyId(regionRequestDTO.getCompanyId());
            regionResponse.setClientId(regionRequestDTO.getClientId());
            regionList.add(regionResponse);
        }

        return regionList;
    }


    public boolean updateRegions(RegionRequestBO regionRequestBO) throws SQLException {

        RegionDAO regionDAO = new RegionDAO();
        Boolean isProcessed = regionDAO.updateRegions(buildDTOFromBO(regionRequestBO));
        return isProcessed;
    }

    private RegionRequestDTO buildDTOFromBO(RegionRequestBO regionRequestBO) {
        RegionRequestDTO regionRequestDTO = new RegionRequestDTO();
        regionRequestDTO.setId(regionRequestBO.getId());
        regionRequestDTO.setRegionDesc(regionRequestBO.getRegionDesc());
        regionRequestDTO.setRegionsDesc(regionRequestBO.getRegionsDesc());
        regionRequestDTO.setCompanyId(regionRequestBO.getCompanyId());

        return regionRequestDTO;
    }

}


