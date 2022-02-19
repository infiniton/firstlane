import java.net.*;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.io.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.security.*;

import org.json.JSONObject;

public class Client {
    private Socket client;
    private BufferedReader in;
    private OutputStreamWriter out;
    private LinkedBlockingQueue<JSONObject> queue;

    private String user;

    public Client() throws IOException {
        this.client = new Socket("localhost", 5050);
        this.out = new OutputStreamWriter(client.getOutputStream());

    }

    public void login(String user, String password) throws IOException {
        this.user = user;
        JSONObject json = new JSONObject();
        json.put("type", "login");
        json.put("user", user);
        this.out.write(json.toJSONString() + "\n");
        this.out.flush();

        JSONObject jsonIn = null;
        
        // get user from server
        String serverUser = (String)jsonIn.get("user");

        //make jsonobject with salt and hash
        JSONObject jsonPWdata = new JSONObject();
        jsonPWdata.put("type", "login");
        jsonPWdata.put("user", user);
        jsonPWdata.put("salt", (String)jsonIn.get("salt"));
        jsonPWdata.put("hash", (String)jsonIn.get("hash"));


        // passwordutils check password
        PasswordUtils.check(user, password, jsonPWdata);

        // send passwordutils output to server
        JSONObject jsonOut = new JSONObject();
        jsonOut.put("type", "res");
        jsonOut.put("check", PasswordUtils.check(user, password, jsonPWdata));


        this.out.write(jsonOut.toJSONString() + "\n");
        this.out.flush();


    }    

    public JSONObject getResponse() throws IOException {
        String line = this.in.readLine();
        JSONParser parser = new JSONParser();
        JSONObject json = null;
        try {
            json = (JSONObject) parser.parse(line);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return json;
    }
}