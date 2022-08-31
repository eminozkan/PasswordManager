package password.manager.persistence;

import org.springframework.stereotype.Repository;
import password.manager.business.password.Password;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryPasswordRepository implements PasswordRepository{
    private final Map<String, Password> passwords = new ConcurrentHashMap<>();

    @Override
    public void save(Password password){
        passwords.put(password.getId(),password);
    }

    public void deleteById(String id){
        passwords.remove(id);
    }

    @Override
    public void update(Password password){
        passwords.put(password.getId(),password);
    }

    @Override
    public List<Password> list(){
        List<Password> passwordList = new ArrayList<>();
        for (Password pass:passwords.values().stream().toList()) {
            passwordList.add(new Password(pass));
        }
        return passwordList;
    }

    @Override
    public Password findById(String id){
        return passwords.get(id);
    }

    @Override
    public Boolean isPasswordTitleExist(String title){
        List<Password> passwordList = list();
        for (Password pass : passwordList) {
            if(pass.getTitle().equals(title)){
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean isPasswordExists(String id){
        return passwords.containsKey(id);
    }

}
