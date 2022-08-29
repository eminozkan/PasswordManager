package password.manager.business;




import org.junit.jupiter.api.*;

import password.manager.business.password.Password;
import password.manager.business.results.PasswordOperationResults;
import password.manager.persistence.PasswordRepo;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;

@TestMethodOrder(OrderAnnotation.class)
class SavePasswordTest {

    private static PasswordService passwordService;

    private static Password password;

    @BeforeAll
    static void setUp(){
        PasswordRepo passwordRepository = new PasswordRepo();
        passwordService = new PasswordService(passwordRepository);
    }

    @BeforeEach
    void createPassword(){
        password = new Password();
    }

    @Test
    @Order(2)
    void savePasswordWithTitle(){

        password.setTitle("test");

        PasswordOperationResults result = passwordService.savePassword(password);

        assertEquals(PasswordOperationResults.SUCCESS,result);
        assertNotNull(passwordService.getPasswordById(password.getId()));
    }

    @Test
    @Order(3)
    void savePasswordWithoutTitle(){
        PasswordOperationResults result = passwordService.savePassword(password);

        assertEquals(PasswordOperationResults.TITLE_IS_NULL,result);
        assertNull(passwordService.getPasswordById(password.getId()));
    }

    @Test
    @Order(4)
    void savePasswordWithSameTitle(){
        password.setTitle("test");

        PasswordOperationResults result = passwordService.savePassword(password);

        assertEquals(PasswordOperationResults.TITLE_EXISTS,result);

    }






}
