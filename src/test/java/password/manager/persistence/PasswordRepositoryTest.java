package password.manager.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import password.manager.business.password.Password;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

class PasswordRepositoryTest {

    Password password = new Password();

    InMemoryDb repository = new InMemoryDb();

    @BeforeEach
    void setUp(){
        password = new Password()
                .setId("id")
                .setTitle("title")
                .setDirectoryName("directory")
                .setUsername("username")
                .setPassword("password")
                .setNotes("notes")
                .setUrl("url");

        repository.save(password);

    }

    @DisplayName("Save Password")
    @Test
    void savePassword(){
        Password fromDb = repository.findById(password.getId());

        assertNotNull(fromDb);
        assertNotNull(password.getId());
        assertNotNull(password.getTitle());
        assertNotNull(password.getDirectoryName());
        assertNotNull(password.getUsername());
        assertNotNull(password.getPassword());
        assertNotNull(password.getNotes());
        assertNotNull(password.getUrl());
    }

    @DisplayName("Delete Password")
    @Test
    void deletePassword(){

        repository.deleteById(password.getId());
        assertNull(repository.findById(password.getId()));

    }

    @DisplayName("List Password")
    @Test
    void listPasswords(){
        assertNotNull(repository.list());
    }

    @DisplayName("Password Exist")
    @Test
    void isPasswordExist(){
        assumeTrue(repository.isPasswordExists(password.getId()));
    }

    @DisplayName("Password Title Exist")
    @Test
    void isTitleExist(){
        assumeTrue(repository.isPasswordTitleExist(password.getTitle()));
    }





    static class InMemoryDb implements PasswordRepository{
        Map<String,Password> passwordList = new ConcurrentHashMap<>();

        @Override
        public void save(Password password) {
            passwordList.put(password.getId(),password);
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
            for (Password pass:passwords) {
                if(pass.getTitle().equals(title)){
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