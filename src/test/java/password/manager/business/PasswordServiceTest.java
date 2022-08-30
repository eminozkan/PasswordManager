package password.manager.business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import password.manager.business.password.GeneratedPassword;
import password.manager.business.password.Password;
import password.manager.business.results.PasswordOperationResults;
import password.manager.persistence.PasswordRepository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

class PasswordServiceTest {

    private PasswordRepository repository;
    private PasswordService passwordService;
    private Password password;


    @BeforeEach
    void setUp() {
        password = new Password()
                .setTitle("title")
                .setDirectoryName("directory")
                .setUsername("username")
                .setPassword("password")
                .setNotes("notes")
                .setUrl("url");
        repository = new Repository();
        passwordService = new DefaultPasswordService(repository);
    }

    @Nested
    class SavePassword {

        @DisplayName("No title")
        @Test
        void noTitle() {
            password.setTitle("");
            assertEquals(PasswordOperationResults.TITLE_IS_NULL, passwordService.savePassword(password));
        }

        @DisplayName("Non-unique Title")
        @Test
        void nonUniqueTitle() {
            passwordService.savePassword(password);
            Password nonUniquePassword = new Password(password);
            assertEquals(PasswordOperationResults.TITLE_EXISTS, passwordService.savePassword(nonUniquePassword));
        }

        @DisplayName("Success")
        @Test
        void success() {
            assertEquals(PasswordOperationResults.SUCCESS, passwordService.savePassword(password));
            assertNotNull(password.getId());
        }
    }


    @Nested
    class UpdatePassword {

        @DisplayName("Non-saved password")
        @Test
        void nonSavedPassword() {
            assertEquals(PasswordOperationResults.PASSWORD_NOT_EXISTS, passwordService.updatePassword("id", password));
        }

        @DisplayName("Non-unique title")
        @Test
        void nonUnique() {
            passwordService.savePassword(password);

            Password newPass = new Password(password);
            newPass.setTitle("unique-title");
            passwordService.savePassword(newPass);

            Password nonUniquePassword = new Password(password);
            assertEquals(PasswordOperationResults.TITLE_EXISTS, passwordService.updatePassword(newPass.getId(), nonUniquePassword));
        }

        @DisplayName("Success")
        @Test
        void success() {
            passwordService.savePassword(password);
            Password newPass = new Password()
                    .setTitle("new-title")
                    .setDirectoryName("new-directory")
                    .setUsername("new-username")
                    .setPassword("new-password")
                    .setNotes("new-note")
                    .setUrl("new-url");
            assertEquals(PasswordOperationResults.SUCCESS, passwordService.updatePassword(password.getId(),newPass));

            Password fromDb = repository.findById(password.getId());
            assumeTrue(password.equals(fromDb));


        }

    }


    @DisplayName("Delete Password")
    @Test
    void deletePassword(){
        passwordService.savePassword(password);
        passwordService.deletePassword(password.getId());

        assertNull(repository.findById(password.getId()));
    }

    @Nested
    class GetPassword{

        @BeforeEach
        void savePassword(){
            passwordService.savePassword(password);
        }
        @DisplayName("Reveal true")
        @Test
        void revealTrue(){
            Password fromService = passwordService.getPasswordById(password.getId(),true);
            assertEquals(password.getPassword(),fromService.getPassword());
        }

        @DisplayName("Reveal false")
        @Test
        void revealFalse(){
            Password fromService = passwordService.getPasswordById(password.getId(),false);
            assertNotEquals(password.getPassword(),fromService.getPassword());
            assertEquals(password.getPassword().length(),fromService.getPassword().length());
        }
    }


    @Nested
    class GeneratePassword{
        private GeneratedPassword generatedPassword;
        @BeforeEach
        void setUp(){
            generatedPassword = new GeneratedPassword()
                    .setHasLowerCaseCharacters(true)
                    .setHasNumericCharacters(true)
                    .setHasSpecialCharacters(true)
                    .setHasUpperCaseCharacters(true);
        }

        @Test
        @DisplayName("No id")
        void noId(){
            passwordService.generatePassword(generatedPassword);
            assertNotNull(generatedPassword.getPassword());
        }


        @Test
        @DisplayName("With id")
        void withId(){
            passwordService.savePassword(password);
            passwordService.generatePassword(password.getId(),generatedPassword);
            assertNotNull(password.getPassword());
            assertEquals(password.getPassword(),generatedPassword.getPassword());
        }

    }


    static class Repository implements PasswordRepository {
        Map<String, Password> passwordList = new ConcurrentHashMap<>();

        @Override
        public void save(Password password) {
            passwordList.put(password.getId(), password);
        }

        @Override
        public void deleteById(String id) {
            passwordList.remove(id);
        }

        @Override
        public List<Password> list() {
            return passwordList.values().stream().toList();
        }

        @Override
        public Password findById(String id) {
            return passwordList.get(id);
        }

        @Override
        public Boolean isPasswordTitleExist(String title) {
            List<Password> passwords = passwordList.values().stream().toList();
            for (Password pass : passwords) {
                if (pass.getTitle().equals(title)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public Boolean isPasswordExists(String id) {
            return passwordList.containsKey(id);
        }
    }
}