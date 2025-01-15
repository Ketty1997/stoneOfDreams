package com.example.demo.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncoder {
    
    public String encode(String password){
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        return hashedPassword;
    }

    public boolean pwMaches(String password, String hashedPassword){
        if (hashedPassword == null || password == null) {
            return false; // Prevenzione di errori con valori null
        }
        boolean mached = BCrypt.checkpw(password, hashedPassword);
        return mached;
    }
}
