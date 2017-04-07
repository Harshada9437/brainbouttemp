package com.heednow.requesthandler;

import com.heednow.dao.table.TableDAO;
import com.heednow.dto.request.TableDTO;
import com.heednow.response.table.TableResponse;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by System-2 on 12/26/2016.
 */
public class TableRequestHandler {
    public List<TableResponse> getTables() throws SQLException {
        TableDAO tableDAO=new TableDAO();
        List<TableResponse> statusList = new ArrayList<TableResponse>();
        statusList = getResponseListFromDTOs(tableDAO.getTables());
        return statusList;
    }
    private List<TableResponse> getResponseListFromDTOs(List<TableDTO> tableDTOs) throws SQLException {
        List<TableResponse> tableResponseListResponse = new ArrayList<TableResponse>();
        Iterator<TableDTO> iterator = tableDTOs.iterator();
        while (iterator.hasNext()) {
            TableDTO tableDTO = iterator.next();
            TableResponse tableResponse = new TableResponse(tableDTO.getId(), tableDTO.getStatus(),tableDTO.getTableName());
            tableResponseListResponse.add(tableResponse);
        }
        return tableResponseListResponse;
    }
}
