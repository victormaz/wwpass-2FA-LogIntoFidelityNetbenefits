package com.wwpass.fidelity.demo.web.authentication;

import com.google.gson.Gson;
import com.wwpass.connection.WWPassConnection;
import org.apache.commons.codec.binary.Base64;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * WwpassContainerStorage.java
 *
 */
public class WwpassContainerStorage {

    private WWPassConnection wwpassConnection;

    public void setWwpassConnection(WWPassConnection conn) {
        this.wwpassConnection = conn;
    }


    public void storeUsernamePasswordInWwpass(String username, String password, String ticket) throws IOException {
        UsernamePasswordPair pair = new UsernamePasswordPair();
        pair.username = username;
        pair.password = password;

        Gson gson = new Gson();
        String json = gson.toJson(pair);
        Base64 base64 = new Base64();
        String pack = null;
        try {
            pack = base64.encodeAsString(json.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        ticket = wwpassConnection.putTicket(ticket);
        wwpassConnection.writeData(ticket, pack, "wwc");

    }

    public UsernamePasswordPair readUsernamePasswordFromWwpass(String ticket) throws IOException {
        ticket = wwpassConnection.putTicket(ticket);
        String pack = wwpassConnection.readDataAsString(ticket, "wwc");

        Base64 base64 = new Base64();
        byte[] json = base64.decode(pack);


        Gson gson = new Gson();
        UsernamePasswordPair pair = gson.fromJson(new String(json), UsernamePasswordPair.class);

        return pair;
    }

    public static class UsernamePasswordPair {
        public String username;
        public String password;
    }
}
