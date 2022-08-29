package password.manager.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import password.manager.business.password.GeneratedPassword;
import password.manager.business.password.Password;
import password.manager.business.password.PasswordGenerator;
import password.manager.business.results.PasswordOperationResults;
import password.manager.persistence.PasswordRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class PasswordService {
    private final PasswordRepo repo;

    @Autowired
    public PasswordService(PasswordRepo repo){
        this.repo = repo;
    }

    public PasswordOperationResults savePassword(Password pass){
        if(ObjectUtils.isEmpty(pass.getTitle())){
            return PasswordOperationResults.TITLE_IS_NULL;
        }else if(repo.isPasswordTitleExist(pass.getTitle())){
            return PasswordOperationResults.TITLE_EXISTS;
        }

        pass.setId(UUID.randomUUID().toString());
        repo.save(pass);
        return PasswordOperationResults.SUCCESS;
    }

    private List<Password> hidePasswordInfo(List<Password> passwordList){
        List<Password> passwords = new ArrayList<>();
        int index = 0;
        for(Password p : passwordList){
            String hiddenPassword = "*".repeat(p.getPassword().length());
            Password pass = new Password(p);
            passwords.add(pass);
            passwords.get(index).setPassword(hiddenPassword);
            index++;
        }

        return passwords;
    }

    private Password hidePasswordInfo(Password pass){
        Password password = new Password(pass);
        password.setPassword("*".repeat(pass.getPassword().length()));
        return password;
    }

    public List<Password> listPassword(){
        List<Password> passwords = repo.list();
        return hidePasswordInfo(passwords);
    }

    public List<Password> listPasswordByDirectory(String directoryName){
        List<Password> passwords = repo.list();
        List<Password> filteredPasswords = new ArrayList<>();
        for (Password pass : passwords){
            if(pass.getDirectoryName().equals(directoryName)){
                filteredPasswords.add(pass);
            }
        }
        return hidePasswordInfo(filteredPasswords);
    }

    private void replacePasswordFields(Password oldPass, Password newPass){

        if(!ObjectUtils.isEmpty(newPass.getTitle())){
            oldPass.setTitle(newPass.getTitle());
        }

        if(!ObjectUtils.isEmpty(newPass.getDirectoryName())){
            oldPass.setDirectoryName(newPass.getDirectoryName());
        }

        if(!ObjectUtils.isEmpty(newPass.getUsername())){
            oldPass.setUsername(newPass.getUsername());
        }

        if(!ObjectUtils.isEmpty(newPass.getPassword())){
            oldPass.setPassword(newPass.getPassword());
        }

        if(!ObjectUtils.isEmpty(newPass.getNotes())){
            oldPass.setNotes(newPass.getNotes());
        }

        if(!ObjectUtils.isEmpty(newPass.getUrl())){
            oldPass.setUrl(newPass.getUrl());
        }
    }

    public PasswordOperationResults updatePassword(String id,Password newPass){
        if(!repo.isPasswordExists(id)){
            return PasswordOperationResults.PASSWORD_NOT_EXISTS;
        }
        Password oldPass = repo.findById(id);
        boolean arePasswordsTitleSame =  oldPass.getTitle().equals(newPass.getTitle());
        if(!arePasswordsTitleSame && repo.isPasswordTitleExist(newPass.getTitle())){
            return PasswordOperationResults.TITLE_EXISTS;
        }
        replacePasswordFields(oldPass,newPass);
        return PasswordOperationResults.SUCCESS;
    }

    public void deletePassword(String id){
        repo.deleteById(id);
    }

    public Password getPasswordById(String id, Boolean reveal){
        if(!repo.isPasswordExists(id)){
            return null;
        }

        if(reveal){
            return new Password(repo.findById(id));
        }else{
            return new Password(hidePasswordInfo(repo.findById(id)));
        }
    }

    public Password getPasswordById(String id){
        if(!repo.isPasswordExists(id)){
            return null;
        }
            return new Password(repo.findById(id));
    }

    public PasswordOperationResults generatePassword(String id, GeneratedPassword password){
        if(!repo.isPasswordExists(id)){
            return PasswordOperationResults.PASSWORD_NOT_EXISTS;
        }
        Password pass = repo.findById(id);
        PasswordGenerator generator = new PasswordGenerator();
        password = generator.generatePassword(password);
        pass.setPassword(password.getPassword());
        return PasswordOperationResults.SUCCESS;

    }

    public GeneratedPassword generatePassword(GeneratedPassword password){
        PasswordGenerator generator = new PasswordGenerator();
        return generator.generatePassword(password);
    }

}
