package password.manager.business;

import org.junit.jupiter.api.*;
import password.manager.business.password.Password;
import password.manager.business.results.PasswordOperationResults;
import password.manager.persistence.PasswordRepo;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UpdatePasswordTest {

    private static PasswordService passwordService;

    private Password password;

    @BeforeAll
    static void setUp(){
        PasswordRepo passwordRepo = new PasswordRepo();
        passwordService = new PasswordService(passwordRepo);
    }

    @Order(1)
    @Test
    void updateUnsavedPassword(){
        Password password = new Password();
        PasswordOperationResults results = passwordService.updatePassword("non-saved",password);

        assertEquals(PasswordOperationResults.PASSWORD_NOT_EXISTS,results);
    }

    @BeforeEach
    void createPassword(){
        password = new Password();
        password.setTitle("test-pass");
        password.setUsername("user");

    }

    @Order(2)
    @Test
    void updatePasswordWithSameTitle(){
        passwordService.savePassword(password);
        password.setTitle("test-pass");
        PasswordOperationResults result = passwordService.updatePassword(password.getId(),password);

        String changedTitleFromService = passwordService.getPasswordById(password.getId()).getTitle();

        assertEquals("test-pass",changedTitleFromService);
        assertEquals(PasswordOperationResults.SUCCESS,result);
    }

    @Order(3)
    @Test
    void updatePasswordWithoutUniqueTitle(){

        passwordService.savePassword(password);

        Password newPass = new Password();
        newPass.setTitle("unique-title");
        passwordService.savePassword(newPass);


        Password updatedPassword = new Password();
        updatedPassword.setTitle("test-pass");

        PasswordOperationResults result = passwordService.updatePassword(newPass.getId(),updatedPassword);
        assertEquals(PasswordOperationResults.TITLE_EXISTS,result);

    }

}
