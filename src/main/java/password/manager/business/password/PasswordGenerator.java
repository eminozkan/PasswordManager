package password.manager.business.password;


import org.springframework.util.ObjectUtils;

import java.security.SecureRandom;

public class PasswordGenerator {
    private static final String NUMERIC_CHARACTERS = "1234567890";
    private static final String LOWERCASE_CHARACTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String SPECIAL_CHARACTERS = "!@#$%&*()./\\<>?_+{}";




    public String generatePassword(GeneratedPassword password){
        String passwordChars = "";
        if(password.getHasNumericCharacters()){
            passwordChars+=NUMERIC_CHARACTERS;
        }
        if(password.getHasSpecialCharacters()){
            passwordChars+=SPECIAL_CHARACTERS;
        }

        if(password.getHasUpperCaseCharacters()){
            passwordChars+=UPPERCASE_CHARACTERS;
        }

        if(password.getHasLowerCaseCharacters()){
            passwordChars+=LOWERCASE_CHARACTERS;
        }

        if(ObjectUtils.isEmpty(passwordChars)){
            passwordChars+=LOWERCASE_CHARACTERS;
        }

        StringBuilder stringBuilder = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for(int i = 0; i < password.getLength();i++){
            stringBuilder.append(passwordChars.charAt(random.nextInt(passwordChars.length())));
        }

        return stringBuilder.toString();

    }
}




