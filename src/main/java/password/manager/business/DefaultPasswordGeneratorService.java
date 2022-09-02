package password.manager.business;

import org.springframework.stereotype.Service;
import password.manager.business.password.Password;
import password.manager.business.password.PasswordGenerateOptions;
import password.manager.business.results.PasswordOperationResults;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@Service
public class DefaultPasswordGeneratorService implements PasswordGeneratorService {

    private static final String LOWERCASE_CHARACTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String SPECIAL_CHARACTERS = "!@#$%&*()./\\<>?_+{}";
    private static final String NUMERIC_CHARACTERS = "1234567890";

    PasswordService passwordService;

    public DefaultPasswordGeneratorService(PasswordService service) {
        this.passwordService = service;
    }

    public Password generatePasswordWithNoId(PasswordGenerateOptions options) {
        Password createdPassword = new Password();
        List<String> passwordChars = new ArrayList<>();

        if (options.getHasLowerCaseCharacters()) {
            passwordChars.add(LOWERCASE_CHARACTERS);
        }
        if (options.getHasUpperCaseCharacters()) {
            passwordChars.add(UPPERCASE_CHARACTERS);
        }
        if (options.getHasSpecialCharacters()) {
            passwordChars.add(SPECIAL_CHARACTERS);
        }
        if (options.getHasNumericCharacters()) {
            passwordChars.add(NUMERIC_CHARACTERS);
        }
        if (passwordChars.isEmpty()) {
            passwordChars.add(LOWERCASE_CHARACTERS);
        }
        StringBuilder stringBuilder = new StringBuilder();
        SecureRandom random = new SecureRandom();

        for (int i = 0; i < options.getLength(); i++) {
            String chars = passwordChars.get(i % passwordChars.size());
            stringBuilder.append(chars.charAt(random.nextInt(chars.length())));
        }
        createdPassword.setPassword(stringBuilder.toString());

        return createdPassword;
    }

    public PasswordOperationResults generatePasswordWithId(PasswordGenerateOptions options) {
        PasswordOperationResults result = passwordService.updatePassword(options.getPasswordId(), generatePasswordWithNoId(options));
        if (result == PasswordOperationResults.PASSWORD_NOT_EXISTS) {
            return PasswordOperationResults.PASSWORD_NOT_EXISTS;
        }
        return PasswordOperationResults.SUCCESS;
    }

    String getLOWERCASE_CHARACTERS() {
        return LOWERCASE_CHARACTERS;
    }

    String getUPPERCASE_CHARACTERS() {
        return UPPERCASE_CHARACTERS;
    }

    String getSPECIAL_CHARACTERS() {
        return SPECIAL_CHARACTERS;
    }

    String getNUMERIC_CHARS() {
        return NUMERIC_CHARACTERS;
    }
}