import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.json.simple.JSONObject;

public class PasswordUtils {

    public PasswordUtils() {
        // default constructor

    }

    public static JSONObject secure(String user, String password) {
        JSONObject obj = new JSONObject();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);

            md.update(salt);
            byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b & 0xff));
            }

            obj.put("user", user);
            obj.put("hash", sb.toString());
            obj.put("salt", salt);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static int check(String user, String password, JSONObject obj) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            byte[] salt = (byte[]) obj.get("salt");
            md.update(salt);
            byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b & 0xff));
            }

            if (sb.toString().equals((String) obj.get("hash"))) {
                return 1;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return 0;
    }

}