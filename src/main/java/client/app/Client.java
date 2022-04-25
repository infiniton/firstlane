package client.app;

import java.net.*;
import java.util.UUID;
import java.io.*;

import org.json.JSONObject;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Client {
    private HttpURLConnection con;

    private String user;
    private String password;
    private String salt;
    private final String REQUEST_URL = "fl.infiniton.xyz";

    // instantiate password encoder
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Client() throws IOException {

    }

    private String sendRequest(String url, String method, String body) throws IOException {
        URL loginUrl = new URL(url);

        // configure connection
        con = (HttpURLConnection) loginUrl.openConnection();
        con.setRequestMethod(method);
        if ("POST".equals(method)) {
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);
        }

        if ("POST".equals(method)) {
            // send request body
            DataOutputStream out = new DataOutputStream(con.getOutputStream());
            out.writeBytes(body);
            out.flush();
            out.close();
        }

        // get response from server
        System.out.println("Response: ");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        return response.toString();
    }

    public int login(String user, String password) throws IOException {
        System.out.println('\n' + "Logging in...");
        this.user = user;
        this.password = password;
        String loginUrl = "http://" + REQUEST_URL + "/api/user?user=" + user;

        JSONObject req = new JSONObject();
        req.put("user", user);
        req.put("password", password);

        String respBody = sendRequest(loginUrl, "GET", req.toString());
        JSONObject resp = new JSONObject(respBody);
        this.salt = resp.getString("salt");

        // compare password in db to password passed in
        if (passwordEncoder.matches(password, resp.getString("password"))) {
            System.out.println("Login successful");
            this.user = user;
            return 0;
        } else {
            System.out.println("Login failed");
            return 1;
        }
    }

    public int register(String user, String password) throws IOException {
        System.out.println('\n' + "Logging in...");
        this.user = user;
        URL registerUrl = new URL("http://" + REQUEST_URL + "/api/user");

        JSONObject req = new JSONObject();
        req.put("user", user);
        req.put("password", passwordEncoder.encode(password));
        req.put("data", " ");

        // generate random 32 character salt
        String salt = AESUtils.saltGen();
        req.put("salt", salt);

        String respBody = sendRequest(registerUrl.toString(), "POST", req.toString());
        JSONObject resp = new JSONObject(respBody);

        System.out.println("Registration successful");

        return 0;
    }

    public String decrypt(String strToDecrypt, String salt) {
        this.salt = salt;

        return AESUtils.decrypt(strToDecrypt, password, salt);
    }

    public JSONObject addPass(String name, String username, String pass, String url, String notes) throws IOException {
        System.out.println('\n' + "Adding password...");
        String addPassUrl = "http://" + REQUEST_URL + "/api/password";
        String data;

        // get current data from users db
        try {
            data = getUserData().getString("data");
        } catch (Exception e) {
            data = null;
        }
        System.out.println("encrypted data: " + data);
        // decrypt data
        // read jsonobject from data and add new nested password
        // if data is has no content, create a jsonobject
        JSONObject json;

        if (data != null) {
            String decryptedData = decrypt(data, salt);
            System.out.println("decryptedData: " + decryptedData);
            json = new JSONObject(decryptedData);
        } else {
            json = new JSONObject();
        }

        // json = new JSONObject();
        JSONObject newpass = new JSONObject();
        String uuid = UUID.randomUUID().toString();
        newpass.put("name", name);
        newpass.put("username", username);
        newpass.put("password", pass);
        newpass.put("url", url);
        newpass.put("notes", notes);
        System.out.println("newpass: " + newpass.toString());
        json.put(uuid, newpass);
        System.out.println("json: " + json.toString());
        JSONObject toSend = new JSONObject();
        toSend.put("data", AESUtils.encrypt(json.toString(), password, salt));
        toSend.put("user", user);
        String toSendBody = toSend.toString();
        System.out.println("Calling server with body: " + toSend);
        String respBody = sendRequest(addPassUrl, "POST", toSendBody);
        System.out.println(respBody);
        JSONObject resp = new JSONObject(respBody);

        System.out.println("Password added");

        return json;
    }

    public JSONObject deletePass(int index) throws IOException {
        System.out.println('\n' + "Deleting password...");
        String deletePassUrl = "http://" + REQUEST_URL + "/api/password";
        String data;

        // get current data from users db
        try {
            data = AESUtils.decrypt(getUserData().getString("data"), password, salt);
        } catch (Exception e) {
            data = null;
        }
        // remove password from jsonobject
        // encrypt jsonobject and send to server
        JSONObject json = new JSONObject(data);
        //remove password at index
        json.remove(json.names().getString(index));
        System.out.println("json: " + json.toString());

        
        JSONObject toSend = new JSONObject();
        toSend.put("data", AESUtils.encrypt(json.toString(), password, salt));
        toSend.put("user", user);
        String toSendBody = toSend.toString();
        System.out.println("Calling server with body: " + toSend);
        String respBody = sendRequest(deletePassUrl, "POST", toSendBody);
        System.out.println(respBody);

        return json;
    }

    public JSONObject getUserData() throws IOException {
        String userUrl = "http://" + REQUEST_URL + "/api/user?user=" + user;
        String respBody = sendRequest(userUrl, "GET", "");
        JSONObject resp = new JSONObject(respBody);
        return resp;
    }

    public JSONObject getPass(String uuid) throws IOException {
        System.out.println("Calling getPass with uuid: " + uuid);
        String passUrl = "http://" + REQUEST_URL + "/api/password?uuid=" + uuid;
        String respBody = sendRequest(passUrl, "GET", "");
        JSONObject resp = new JSONObject(respBody.trim());
        System.out.println("resp: " + resp.toString());

        JSONObject json = new JSONObject();

        // decrypt data
        json.put("name", AESUtils.decrypt(resp.getString("name"), password, salt));
        json.put("username", AESUtils.decrypt(resp.getString("username"), password, salt));
        json.put("password", AESUtils.decrypt(resp.getString("password"), password, salt));
        json.put("url", AESUtils.decrypt(resp.getString("url"), password, salt));
        json.put("notes", AESUtils.decrypt(resp.getString("notes"), password, salt));

        return json;
    }

}