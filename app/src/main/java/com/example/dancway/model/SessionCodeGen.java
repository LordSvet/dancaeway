package com.example.dancway.model;

import java.util.Random;

public class SessionCodeGen {   //Generates a simple 5-character String with 3 numbers and 2 letters

    public String getCode(){
        return codeGenerator();
    }

    private String codeGenerator(){     //TODO: When sessions are implemented check if code already exists in RT DB
        String code = "";
        Random r = new Random();
        code += String.valueOf(r.nextInt(10));
        code += (char) (r.nextInt(26) + 'a');
        code += String.valueOf(r.nextInt(10));
        code += (char) (r.nextInt(26) + 'a');
        code += String.valueOf(r.nextInt(10));
        return code.toUpperCase();
    }
}
