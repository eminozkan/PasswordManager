package password.manager.persistence;

import password.manager.business.password.Password;

import java.util.*;

public class PasswordRepo implements PasswordRepository{
    private Map<String, Password> passwords = new HashMap<>();

    @Override
    public void save(Password password){
        passwords.put(password.getId(),password);
    }

    @Override
    public void changeDirectory(String id,String directoryName){
        passwords.get(id).setDirectoryName(directoryName);
    }
    @Override
    public void update(Password password){
        passwords.put(password.getId(),password);
    }

    @Override
    public void deleteById(String id){
        passwords.remove(id);
    }

    @Override
    public List<Password> list(){
        return passwords.values().stream().toList();
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
