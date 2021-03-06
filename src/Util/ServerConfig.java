package Util;

import Crypto.Cryptor;
import com.google.gson.JsonObject;

public class ServerConfig {
    /*
    * Singleton Pattern
    * */

    private static ServerConfig instance = null;

    private String password;
    private int port;
    private String method;
    private Cryptor cryptor;

    public static ServerConfig loadConfigFromFile(String filepath) {
        if(instance !=null) return instance;

        JsonObject jsonObject = Util.getJsonObjectFromFile(filepath);
        if(jsonObject == null || !checkJson(jsonObject)) {
            return null;
        } else instance = new ServerConfig(jsonObject);
        return instance;
    }

    private static boolean checkJson(JsonObject obj) {
        return obj.has("port")
                && obj.has("password")
                && obj.has("method");
    }


    private ServerConfig(JsonObject obj) {
        this.password = obj.get("password").getAsString();
        this.port = obj.get("port").getAsInt();
        this.method = obj.get("method").getAsString();
        this.cryptor = Cryptor.createNewCryptor(method,password);
    }

    public String toString() {
        if(instance == null) return "ServerConfig Not Initilized.";
        else return "Port: "+port+"\n"
                +"Method: "+method+"\n"
                +"Password: "+password+"\n";
    }

    public String getMethod() {
        return method;
    }

    public String getPassword() {
        return password;
    }

    public int getPort() {
        return port;
    }

    public Cryptor getCryptor() {
        return cryptor;
    }
}
