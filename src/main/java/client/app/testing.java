package client.app;

import java.util.Scanner;
import java.util.Random;
import java.nio.charset.Charset;

public class testing {

    Client client;

    public static void main(String[] args) throws Exception {
        
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 32;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int) 
              (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();
    
        System.out.println(generatedString);
    }


	}


