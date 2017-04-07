package com.heednow.util;

import com.heednow.dao.user.UsersDAO;
import com.sun.jersey.core.util.Base64;
import com.heednow.response.util.RequestAuthenticationResponse;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by System-3 on 12/7/2016.
 */
public class UserRequestValidation
{
    public static Boolean isRequestValid(String autherization) throws Exception {
    List<String> stringParts = Base64Encoder.decode(autherization);
    Boolean isValidRequest = Boolean.FALSE;
    try {
        String password = MD5Encode.Encode(stringParts.get(1));
        Boolean verify = new UsersDAO().getValidUserBySessionIdPasswordUsername(stringParts.get(0),password,stringParts.get(2),Integer.parseInt(stringParts.get(3)));
        if(verify){
            isValidRequest = Boolean.TRUE;
        }
    }catch (SQLException e){
        e.printStackTrace();
        return Boolean.FALSE;
    }

    return isValidRequest;
}

    public static int getClient(String autherization) throws Exception {
        Base64 decoder = new Base64();
        byte[] decodedBytes = decoder.decode(autherization);
        String decodedString = new String(decodedBytes);
        String[] stringParts = decodedString.split(":");
        String password = MD5Encode.Encode(stringParts[1]);
        int id = new UsersDAO().getValidClient(stringParts[0],password,stringParts[2]);
        return id;
    }

    public static RequestAuthenticationResponse getUnautheticatedResponse(){
        RequestAuthenticationResponse requestAuthenticationResponse = new RequestAuthenticationResponse();
        requestAuthenticationResponse.setMessageType("UNAUTHORIZED");
        requestAuthenticationResponse.setMessage("UNAUTHORIZED ACCESS");
        return requestAuthenticationResponse;
    }
}
