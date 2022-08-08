package password.manager.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import password.manager.business.password.PasswordGenerator;
import password.manager.business.password.Password;
import password.manager.business.results.PasswordOperationResults;
import password.manager.persistence.PasswordRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class PasswordService {
    private PasswordRepo repo;

    @Autowired
    public PasswordService(PasswordRepo repo){
        this.repo = repo;
    }
    private Boolean isPasswordTitleEmpty(Password pass){
        return ObjectUtils.isEmpty(pass.getTitle());
    }

    public PasswordOperationResults savePassword(Password pass){
        if(isPasswordTitleEmpty(pass)){
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
            StringBuffer stringBuffer = new StringBuffer();
            for(int i = 0; i < p.getPassword().length(); i++){
                stringBuffer.append("*");
            }
            Password pass = new Password(p);
            passwords.add(pass);
            passwords.get(index).setPassword(stringBuffer.toString());
            index++;
        }

        return passwords;
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
        oldPass.setTitle(newPass.getTitle());
        oldPass.setDirectoryName(newPass.getDirectoryName());
        oldPass.setPassword(newPass.getPassword());
        oldPass.setUrl(newPass.getUrl());
        oldPass.setNotes(newPass.getNotes());
        oldPass.setUsername(newPass.getUsername());
    }

    public PasswordOperationResults updatePassword(Password newPass,String id){
        if(!repo.isPasswordExists(id)){
            return PasswordOperationResults.PASSWORD_NOT_EXISTS;
        }
        Password oldPass = repo.findById(id);
        Boolean arePasswordsTitleSame =  oldPass.getTitle().equals(newPass.getTitle());
        if(!arePasswordsTitleSame && repo.isPasswordTitleExist(newPass.getTitle())){
            return PasswordOperationResults.TITLE_EXISTS;
        }
        replacePasswordFields(oldPass,newPass);
        return PasswordOperationResults.SUCCESS;
    }

    public PasswordOperationResults deletePassword(String id){
        repo.deleteById(id);
        return PasswordOperationResults.SUCCESS;
    }

    public Password getPasswordInfById(String id){
        if(!repo.isPasswordExists(id)){
            return null;
        }
        Password pass = repo.findById(id);
        return pass;
    }

    public PasswordOperationResults updateDirectoryInf(String id,String directoryName){
        if(!repo.isPasswordExists(id)){
            return PasswordOperationResults.PASSWORD_NOT_EXISTS;
        }

        Password pass = repo.findById(id);
        pass.setDirectoryName(directoryName);
        return PasswordOperationResults.SUCCESS;
    }

    public String generatePassword(Integer length){
        PasswordGenerator generator = new PasswordGenerator();
        return generator.generatePassword(length);
    }


    public PasswordOperationResults generatePassword(String id,Integer length){
        if(!repo.isPasswordExists(id)){
            return PasswordOperationResults.PASSWORD_NOT_EXISTS;
        }
        Password pass = repo.findById(id);
        PasswordGenerator generator = new PasswordGenerator();
        pass.setPassword(generator.generatePassword(length));
        return PasswordOperationResults.SUCCESS;

    }





}
