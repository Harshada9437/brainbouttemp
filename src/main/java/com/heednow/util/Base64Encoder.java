package com.heednow.util;

import com.sun.jersey.core.util.Base64;

import javax.xml.bind.DatatypeConverter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by System-2 on 4/6/2017.
 */
public class Base64Encoder {

    public static List<String> decode(String key) {
        Base64 decoder = new Base64();
        byte[] decodedBytes = decoder.decode(key);
        String decodedString = new String(decodedBytes);
        String[] stringParts = decodedString.split(":");
        List<String> decodeString = new ArrayList<String>();
        for (int i=0;i<stringParts.length;i++){
            decodeString.add(stringParts[i]);
        }
        return decodeString;
    }

    public static String encode(String key){
        String encoded = DatatypeConverter.printBase64Binary(key.getBytes());
        return encoded;
    }

}
