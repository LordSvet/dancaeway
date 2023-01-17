package com.example.dancway.model;

import java.util.Random;

/**
 * This class generates a simple 5-character String with 3 numbers and 2 letters
 * Code is used to create/join party sessions
 */
public class SessionCodeGen {

    /**
     * Calls {@link #codeGenerator() codeGenerator()}
     * @return returns the code from codeGenerator()
     */
    public static String getCode(){
        return codeGenerator();
    }

    /**
     * Generates a random 5-character code with 3 numbers and 2 letters
     * @return  returns the generated code
     */
    private static String codeGenerator(){     // TODO: When sessions are implemented check if code already exists in RT DB
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
