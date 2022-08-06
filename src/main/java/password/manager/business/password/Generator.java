package password.manager.business.password;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;

    @Service
public class Generator {
        private String CHARACTERS = "1234567890!@#$%&*()./AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz";

        public String generatePassword(Integer Length){
            StringBuffer stringBuffer = new StringBuffer();
            SecureRandom rnd = new SecureRandom();
            for(int counter = 0; counter < Length; counter++){
                int index = rnd.nextInt(CHARACTERS.length());
                stringBuffer.append(CHARACTERS.charAt(index));
            }

            return stringBuffer.toString();
        }
}




