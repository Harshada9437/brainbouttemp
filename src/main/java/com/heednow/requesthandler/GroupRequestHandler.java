package com.heednow.requesthandler;

import com.heednow.bo.GroupRequestBO;
import com.heednow.bo.UpdateGroupBO;
import com.heednow.dao.Sync.GroupDAO;
import com.heednow.response.group.GetGroupResponse;
import com.heednow.response.group.GroupDetail;
import com.heednow.sync.Group;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by System-2 on 4/4/2017.
 */
public class GroupRequestHandler {
    public List<GroupDetail> getGroups(int clientId) throws SQLException {
        GroupDAO groupDAO = new GroupDAO();
        List<GroupDetail> groups = getResponseFromDto(groupDAO.getGroups(clientId));
        return groups;
    }

    public List<GroupDetail> getResponseFromDto(List<Group> groupDTOS) throws SQLException {
        List<GroupDetail> groupDetails = new ArrayList<GroupDetail>();
        Iterator<Group> iterator = groupDTOS.iterator();
        while (iterator.hasNext()) {
            Group groupDTO = iterator.next();
            GroupDetail answerResponseList = new GroupDetail(groupDTO.getId(),
                    groupDTO.getDesc(),
                    groupDTO.getShortDesc());
            groupDetails.add(answerResponseList);
        }
        return groupDetails;
    }

    public GetGroupResponse getGroup(int id) throws SQLException {
        GroupDAO groupDAO = new GroupDAO();
        Group groupDTO = groupDAO.getGroupById(id);
        GetGroupResponse groupResponse = new GetGroupResponse(groupDTO.getId(),
                groupDTO.getDesc(),
                groupDTO.getShortDesc());
        return groupResponse;
    }

    public int addGroup(GroupRequestBO groupRequesttBO,int clientId) throws SQLException{
        Group groupDTO = new Group();
        GroupDAO groupDAO = new GroupDAO();

        groupDTO.setDesc(groupRequesttBO.getDesc());
        groupDTO.setShortDesc(groupRequesttBO.getShortDesc());

        int id = groupDAO.createGroup(groupDTO,clientId);
        return id;
    }

    public Boolean updateGroup(UpdateGroupBO groupRequesttBO) throws SQLException{
        Group groupDTO = new Group();
        GroupDAO groupDAO = new GroupDAO();

        groupDTO.setShortDesc(groupRequesttBO.getShortDesc());
        groupDTO.setDesc(groupRequesttBO.getDesc());
        groupDTO.setId(groupRequesttBO.getId());

        Boolean isProcessed = groupDAO.updateGroup(groupDTO);
        return isProcessed;
    }
}
