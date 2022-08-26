package password.manager;




import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import password.manager.business.PasswordService;
import password.manager.business.password.Password;
import password.manager.persistence.PasswordRepo;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class PasswordServiceTest {

    private static PasswordService passwordService;

    private static Password password;

    @BeforeAll
    static void setUp(){
        PasswordRepo passwordRepository = new PasswordRepo();
        passwordService = new PasswordService(passwordRepository);
    }

    @BeforeAll
    static void createPassword(){
        password = new Password();
        password.setTitle("test");
        password.setUsername("test-user");
        password.setPassword("password");
        password.setDirectoryName("test-directory");
        password.setNotes("no-note");
        password.setUrl("no-url");
        passwordService.savePassword(password);
    }

    @Test
    void checksIsPasswordSaved(){
        assertNotNull(passwordService.getPasswordById(password.getId()));
        System.out.println("Save password test is success");
    }

    @Test
    void checksIsPasswordInfoHidden(){
        Password testPass = passwordService.getPasswordById(password.getId(),false);
        assertNotEquals(password.getPassword(),testPass.getPassword());
        System.out.println("Password informations are hided.");
    }

    @Test
    void checksListPasswords(){
        List<Password> passwordList = passwordService.listPassword();

        assertNotNull(passwordList);
        System.out.println("List password test passed.");

    }




}
