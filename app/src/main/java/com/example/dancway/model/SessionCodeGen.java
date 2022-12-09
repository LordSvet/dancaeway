package com.example.dancway.model;

import java.util.Random;

/**
 * Generates a 5-character String with 3 numbers and 2 letters
 */
public class SessionCodeGen {

    /**
     * @return the result of codeGenerator function
     */
    public String getCode(){
        return codeGenerator();
    }

    /**
     * @return a randomly generated 5-character String with 3 numbers and 2 letters
     */
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