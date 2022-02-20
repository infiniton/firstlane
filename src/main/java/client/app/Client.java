package client.app;

import java.net.*;
import java.io.*;

import org.json.JSONObject;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class Client {
    private HttpURLConnection con;

    private String user;

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
        String loginUrl = "http://localhost:8080/api/user?username=" + user;

        JSONObject req = new JSONObject();
        req.put("username", user);
        req.put("password", password);

        String respBody = sendRequest(loginUrl, "GET", req.toString());
        JSONObject resp = new JSONObject(respBody);

        //compare password in db to password passed in
        if (passwordEncoder.matches(password, resp.getString("password"))) {
            System.out.println("Login successful");
            return 0;
        } else {
            System.out.println("Login failed");
            return 1;
        }

    }

    public void register(String user, String password) throws IOException {
        URL registerUrl = new URL("http://localhost:8080/api/user");

        // configure connection
        con = (HttpURLConnection) registerUrl.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setUseCaches(false);
        con.setAllowUserInteraction(false);
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);

        // make jsonobject with user ans password
        JSONObject json = new JSONObject();
        json.put("username", user);
        json.put("password", passwordEncoder.encode(password));

        // send user and password to server
        DataOutputStream out = new DataOutputStream(con.getOutputStream());
        out.writeBytes(json.toString());
        out.flush();
        out.close();


        // get response
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        
    }

}