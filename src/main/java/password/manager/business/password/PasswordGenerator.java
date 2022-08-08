package password.manager.business.password;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class PasswordGenerator {
        private String CHARACTERS = "1234567890!@#$%&*()./AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz";

        public Integer length;


        public String generatePassword(){
            StringBuffer stringBuffer = new StringBuffer();
            SecureRandom rnd = new SecureRandom();
            for(int counter = 0; counter < length; counter++){
                int index = rnd.nextInt(CHARACTERS.length());
                stringBuffer.append(CHARACTERS.charAt(index));
            }

            return stringBuffer.toString();
        }
}




