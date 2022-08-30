package password.manager.business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import password.manager.business.password.GeneratedPassword;
import password.manager.business.password.Password;
import password.manager.business.results.PasswordOperationResults;
import password.manager.persistence.PasswordRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

@ExtendWith(MockitoExtension.class)
class PasswordServiceTest {

    @Mock
    private PasswordRepository repository;
    private PasswordService passwordService;
    private Password password;


    @BeforeEach
    void setUp() {
        passwordService = new DefaultPasswordService(repository);
        password = new Password()
                .setTitle("title")
                .setDirectoryName("directory")
                .setUsername("username")
                .setPassword("password")
                .setNotes("notes")
                .setUrl("url");
    }

    @Nested
    class SavePassword {

        @DisplayName("No title")
        @Test
        void noTitle() {
            password.setTitle("");
            passwordService.savePassword(password);
            assertEquals(PasswordOperationResults.TITLE_IS_NULL, passwordService.savePassword(password));
        }

        @DisplayName("Non-unique Title")
        @Test
        void nonUniqueTitle() {
            Mockito.doReturn(true)
                    .when(repository)
                    .isPasswordTitleExist(password.getTitle());

            assertEquals(PasswordOperationResults.TITLE_EXISTS, passwordService.savePassword(password));
        }

        @DisplayName("Success")
        @Test
        void success() {
            Mockito.doNothing()
                    .when(repository)
                    .save(password);
            assertEquals(PasswordOperationResults.SUCCESS, passwordService.savePassword(password));
            assertNotNull(password.getId());
        }
    }


    @Nested
    class UpdatePassword {

        @DisplayName("Non-saved password")
        @Test
        void nonSavedPassword() {
            Mockito.doReturn(false)
                    .when(repository)
                    .isPasswordExists("id");

            assertEquals(PasswordOperationResults.PASSWORD_NOT_EXISTS, passwordService.updatePassword("id", password));
        }

        @DisplayName("Non-unique title")
        @Test
        void nonUnique() {

            Mockito.doReturn(true)
                    .when(repository)
                    .isPasswordExists("id");

            Mockito.doReturn(password)
                    .when(repository)
                    .findById("id");

            Mockito.doReturn(true)
                    .when(repository)
                    .isPasswordTitleExist("non-unique-title");


            Password newPass = new Password(password);
            newPass.setTitle("non-unique-title");

            assertEquals(PasswordOperationResults.TITLE_EXISTS, passwordService.updatePassword("id", newPass));
        }

        @DisplayName("Success")
        @Test
        void success() {
            Mockito.doReturn(true)
                    .when(repository)
                    .isPasswordExists("id");


            Mockito.doReturn(password)
                    .when(repository)
                    .findById("id");

            assertEquals(PasswordOperationResults.SUCCESS, passwordService.updatePassword("id", password));

            Password fromDb = repository.findById("id");
            assumeTrue(password.equals(fromDb));
        }

    }


    @DisplayName("Delete Password")
    @Test
    void deletePassword() {
        passwordService.deletePassword(password.getId());
        assertNull(repository.findById(password.getId()));
    }

    @Nested
    class GetPassword {

        @DisplayName("Non-saved Password")
        @Test
        void nonSavedPassword() {
            Mockito.doReturn(false)
                    .when(repository)
                    .isPasswordExists("id");

            assertNull(passwordService.getPasswordById("id", true));
        }

        @DisplayName("Reveal true")
        @Test
        void revealTrue() {
            Mockito.doReturn(true)
                    .when(repository)
                    .isPasswordExists("id");

            Mockito.doReturn(password)
                    .when(repository)
                    .findById("id");

            Password fromService = passwordService.getPasswordById("id", true);
            assertEquals(password.getPassword(), fromService.getPassword());
        }

        @DisplayName("Reveal false")
        @Test
        void revealFalse() {
            Mockito.doReturn(true)
                    .when(repository)
                    .isPasswordExists("id");

            Mockito.doReturn(password)
                    .when(repository)
                    .findById("id");

            Password fromService = passwordService.getPasswordById("id", false);
            assertNotEquals(password.getPassword(), fromService.getPassword());
            assertEquals(password.getPassword().length(), fromService.getPassword().length());
        }
    }


    @Nested
    class GeneratePassword {
        private GeneratedPassword generatedPassword;

        @BeforeEach
        void setUp() {
            generatedPassword = new GeneratedPassword()
                    .setHasLowerCaseCharacters(true)
                    .setHasNumericCharacters(true)
                    .setHasSpecialCharacters(true)
                    .setHasUpperCaseCharacters(true);
        }

        @Test
        @DisplayName("No id")
        void noId() {
            passwordService.generatePassword(generatedPassword);
            assertNotNull(generatedPassword.getPassword());
        }


        @Test
        @DisplayName("With id")
        void withId() {
            Mockito.doReturn(true)
                    .when(repository)
                    .isPasswordExists("id");

            Mockito.doReturn(password)
                    .when(repository)
                    .findById("id");

            assertEquals(PasswordOperationResults.SUCCESS, passwordService.generatePassword("id", generatedPassword));
            assertNotNull(password.getPassword());
            assertEquals(password.getPassword(), generatedPassword.getPassword());
        }

    }

    @Nested
    class ListPasswords{

        List<Password> passwordList;
        List<Password> pList;
        @BeforeEach
        void setUp(){
            passwordList = new ArrayList<>();
            passwordList.add(password);

            Mockito.doReturn(passwordList)
                    .when(repository)
                    .list();

            pList = passwordService.listPassword();
        }

        @DisplayName("Is password field hided")
        @Test
        void isPasswordFieldHided(){
            for (Password p: pList) {
                assertTrue(p.getPassword().contains("*"));
            }
        }
        @DisplayName("List Password")
        @Test
        void list(){
            assertNotNull(pList);
        }

        @DisplayName("Filter by directory")
        @Test
        void listByDirectory(){
            pList = passwordService.listPasswordByDirectory("directory");
            assertNotNull(pList);
            pList = passwordService.listPasswordByDirectory("nullDirectory");
            assertTrue(pList.isEmpty());
        }


    }

}