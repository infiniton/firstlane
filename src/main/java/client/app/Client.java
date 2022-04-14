package client.app;

import java.net.*;
import java.io.*;

import org.json.JSONObject;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.*;


public class Client {
    private HttpURLConnection con;

    private String user;
    private String password;
    private String salt;

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

        // get response
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
        String loginUrl = "http://localhost:8080/api/user?user=" + user;

        JSONObject req = new JSONObject();
        req.put("user", user);
        req.put("password", password);

        String respBody = sendRequest(loginUrl, "GET", req.toString());
        JSONObject resp = new JSONObject(respBody);

        //compare password in db to password passed in
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
        URL registerUrl = new URL("http://localhost:8080/api/user");

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

    public int addPass(String name, String username, String pass, String url, String notes) throws IOException {
        System.out.println('\n' + "Adding password...");
        String addPassUrl = "http://localhost:8080/api/password" + user;

        JSONObject req = new JSONObject();
        req.put("user", user);
        req.put("name", AESUtils.encrypt(name, password, salt));
        req.put("username", AESUtils.encrypt(username, password, salt));
        req.put("password", AESUtils.encrypt(pass, password, salt));
        req.put("url", AESUtils.encrypt(url, password, salt));
        req.put("notes", AESUtils.encrypt(notes, password, salt));


        String respBody = sendRequest(addPassUrl, "POST", req.toString());
        JSONObject resp = new JSONObject(respBody);

        System.out.println("Password added");

        return 0;
    }

    public JSONObject getUserData() throws IOException {
        String userUrl = "http://localhost:8080/api/user?user=" + user;
        String respBody = sendRequest(userUrl, "GET", "");
        JSONObject resp = new JSONObject(respBody);
        return resp;
    }

}