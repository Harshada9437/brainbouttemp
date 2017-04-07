package com.heednow.requesthandler;

import com.heednow.bo.CompanyRequestBO;
import com.heednow.dao.Sync.CompanyDAO;
import com.heednow.dto.request.CompanyDTO;
import com.heednow.response.company.CompanyResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by System-3 on 4/5/2017.
 */
public class CompanyRequestHandler {
    public Boolean createCompanies(CompanyRequestBO companyRequestBO) throws SQLException {
        Boolean  iscreated=true;
        CompanyDAO companyDAO = new CompanyDAO();
        iscreated = companyDAO.createCompanies(buildCompanyDTOFromBO(companyRequestBO));
        return iscreated;
    }

    private CompanyDTO buildCompanyDTOFromBO(CompanyRequestBO companyRequestBO) {
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setCompanyDesc(companyRequestBO.getCompanyDesc());
        companyDTO.setCompanyShortDesc(companyRequestBO.getCompanyShortDesc());
        companyDTO.setGroupId(companyRequestBO.getGroupId());
        companyDTO.setClientId(companyRequestBO.getClientId());

        return companyDTO;
    }


    public List<CompanyResponse> getCompaniesList(int groupId,int clientId) throws SQLException {
        CompanyDAO companyDAO = new CompanyDAO();
        List<CompanyResponse> companyList = new ArrayList<CompanyResponse>();

        List<CompanyDTO> companyRequestDTOList = companyDAO.getCompaniesDetail(groupId,clientId);

        for (CompanyDTO companyDTO : companyRequestDTOList) {
            CompanyResponse companyResponse = new CompanyResponse();
            companyResponse.setId(companyDTO.getId());
            companyResponse.setCompanyDesc(companyDTO.getCompanyDesc());
            companyResponse.setCompanyShortDesc(companyDTO.getCompanyShortDesc());
            companyResponse.setGroupId(companyDTO.getGroupId());
            companyResponse.setClientId(companyDTO.getClientId());
            companyList.add(companyResponse);
        }

        return companyList;
    }


    public boolean updateCompanies(CompanyRequestBO companyRequestBO) throws SQLException {

        CompanyDAO companyDAO = new CompanyDAO();
        Boolean isProcessed = companyDAO.updateCompanies(buildDTOFromBO(companyRequestBO));
        return isProcessed;
    }

    private CompanyDTO buildDTOFromBO(CompanyRequestBO companyRequestBO) {
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setId(companyRequestBO.getId());
        companyDTO.setCompanyDesc(companyRequestBO.getCompanyDesc());
        companyDTO.setCompanyShortDesc(companyRequestBO.getCompanyShortDesc());
        companyDTO.setGroupId(companyRequestBO.getGroupId());
        companyDTO.setClientId(companyRequestBO.getClientId());

        return companyDTO;
    }
}
