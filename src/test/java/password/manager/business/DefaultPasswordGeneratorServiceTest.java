package password.manager.business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import password.manager.business.password.Password;
import password.manager.business.password.PasswordGenerateOptions;
import password.manager.business.results.PasswordOperationResults;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class DefaultPasswordGeneratorServiceTest {

    @Mock
    PasswordService passwordService;

    @Captor
    private ArgumentCaptor<Password> captor;

    PasswordGenerateOptions options;

    DefaultPasswordGeneratorService generatorService;

    @BeforeEach
    void setUp(){
        generatorService = new DefaultPasswordGeneratorService(passwordService);

        options = new PasswordGenerateOptions()
                .setLength(10)
                .setHasLowerCaseCharacters(false)
                .setHasUpperCaseCharacters(false)
                .setHasSpecialCharacters(false)
                .setHasNumericCharacters(false);
    }

    @Nested
    class GenerateWithNoId {

        private static boolean hasPasswordAnyCharInString(String password,String str) {
            boolean hasPasswordAnyChar = false;
            for (int i = 0; i < str.length(); i++) {
                if (password.contains(str.substring(i, i + 1))) {
                    hasPasswordAnyChar = true;
                    break;
                }
            }
            return hasPasswordAnyChar;
        }

        @Test
        @DisplayName("lower case")
        void lowerCasePassword() {
            options.setHasLowerCaseCharacters(true);

            Password result = generatorService.generatePasswordWithNoId(options);
            assertThat(result).isNotNull();
            final String LOWERCASE_CHARS = generatorService.getLOWERCASE_CHARACTERS();

            assertThat(hasPasswordAnyCharInString(result.getPassword(),LOWERCASE_CHARS)).isTrue();
            assertThat(result.getPassword().length()).isEqualTo(10);
        }

        @Test
        @DisplayName("upper case")
        void upperCasePassword() {
            options.setHasUpperCaseCharacters(true);

            Password result = generatorService.generatePasswordWithNoId(options);
            assertThat(result).isNotNull();
            final String UPPERCASE_CHARS = generatorService.getUPPERCASE_CHARACTERS();

            assertThat(hasPasswordAnyCharInString(result.getPassword(),UPPERCASE_CHARS)).isTrue();
            assertThat(result.getPassword().length()).isEqualTo(10);
        }

        @Test
        @DisplayName("special characters")
        void specialCasePasswords() {
            options.setHasSpecialCharacters(true);

            Password result = generatorService.generatePasswordWithNoId(options);
            assertThat(result).isNotNull();
            final String SPECIAL_CHARS = generatorService.getSPECIAL_CHARACTERS();

            assertThat(hasPasswordAnyCharInString(result.getPassword(),SPECIAL_CHARS)).isTrue();
            assertThat(result.getPassword().length()).isEqualTo(10);
        }

        @Test
        @DisplayName("numeric characters")
        void numericChars() {
            options.setHasNumericCharacters(true);

            Password result = generatorService.generatePasswordWithNoId(options);
            assertThat(result).isNotNull();
            final String NUMERIC_CHARS = generatorService.getNUMERIC_CHARS();

            assertThat(hasPasswordAnyCharInString(result.getPassword(), NUMERIC_CHARS)).isTrue();
            assertThat(result.getPassword().length()).isEqualTo(10);
        }

        @Test
        @DisplayName("no character")
        void noChars() {
            Password result = generatorService.generatePasswordWithNoId(options);
            assertThat(result).isNotNull();
            final String LOWERCASE_CHARS = generatorService.getLOWERCASE_CHARACTERS();

            assertThat(hasPasswordAnyCharInString(result.getPassword(), LOWERCASE_CHARS)).isTrue();
            assertThat(result.getPassword().length()).isEqualTo(10);
        }

        @Test
        @DisplayName("all characters")
        void allChars() {
            options
                    .setHasLowerCaseCharacters(true)
                    .setHasUpperCaseCharacters(true)
                    .setHasNumericCharacters(true)
                    .setHasSpecialCharacters(true);

            Password result = generatorService.generatePasswordWithNoId(options);
            assertThat(result).isNotNull();
            final String LOWERCASE_CHARS = generatorService.getLOWERCASE_CHARACTERS();
            final String UPPERCASE_CHARS = generatorService.getUPPERCASE_CHARACTERS();
            final String SPECIAL_CHARS = generatorService.getSPECIAL_CHARACTERS();
            final String NUMERIC_CHARS = generatorService.getNUMERIC_CHARS();


            assertThat(hasPasswordAnyCharInString(result.getPassword(), LOWERCASE_CHARS)).isTrue();
            assertThat(hasPasswordAnyCharInString(result.getPassword(),UPPERCASE_CHARS)).isTrue();
            assertThat(hasPasswordAnyCharInString(result.getPassword(), SPECIAL_CHARS)).isTrue();
            assertThat(hasPasswordAnyCharInString(result.getPassword(), NUMERIC_CHARS)).isTrue();
            assertThat(result.getPassword().length()).isEqualTo(10);
        }

        @Test
        @DisplayName("different Length")
        void differentLength() {
            options
                    .setLength(15)
                    .setHasLowerCaseCharacters(true)
                    .setHasUpperCaseCharacters(true)
                    .setHasNumericCharacters(true)
                    .setHasSpecialCharacters(true);

            Password result = generatorService.generatePasswordWithNoId(options);
            assertThat(result).isNotNull();

            assertThat(result.getPassword().length()).isEqualTo(15);
        }
    }

    @Nested
    class GenerateWithId {

        @Test
        @DisplayName("not exists")
        void createAndSave(){
            options.setPasswordId("not-exist-id");
            Mockito.doReturn(PasswordOperationResults.PASSWORD_NOT_EXISTS)
                    .when(passwordService)
                    .updatePassword(Mockito.anyString(),Mockito.any());

            assertThat(generatorService.generatePasswordWithId(options)).isEqualTo(PasswordOperationResults.PASSWORD_NOT_EXISTS);
        }

        @Test
        @DisplayName("success")
        void success(){
            options.setPasswordId("id");
            generatorService.generatePasswordWithId(options);
            Mockito.verify(passwordService).updatePassword(Mockito.anyString(),captor.capture());
            Password password = captor.getValue();
            assertThat(password.getPassword()).isNotNull().isNotEmpty();
        }
    }

}