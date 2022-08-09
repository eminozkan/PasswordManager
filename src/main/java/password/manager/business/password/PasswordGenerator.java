package password.manager.business.password;


import java.security.SecureRandom;

public class PasswordGenerator {
        private static final String CHARACTERS = "1234567890!@#$%&*()./AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz";

        public String generatePassword(Integer length){
            StringBuilder stringBuilder = new StringBuilder();
            SecureRandom rnd = new SecureRandom();
            for(int counter = 0; counter < length; counter++){
                int index = rnd.nextInt(CHARACTERS.length());
                stringBuilder.append(CHARACTERS.charAt(index));
            }

            return stringBuilder.toString();
        }
}




