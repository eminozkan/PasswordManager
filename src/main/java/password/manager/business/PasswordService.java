package password.manager.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import password.manager.business.password.Generator;
import password.manager.business.password.Password;
import password.manager.business.results.PasswordOperationResults;
import password.manager.persistence.PasswordRepo;

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

    private Boolean isPasswordEmpty(Password pass){
        return ObjectUtils.isEmpty(pass);

    }

    private List<Password> hidePasswordInf(List<Password> passwords){
        for(Password pass : passwords){
            int length = pass.getPassword().length();
            for(int i = 0; i < length;i++){
                StringBuffer sb = new StringBuffer();
                sb.append("*");
                pass.setPassword(sb.toString());
            }
        }
        return passwords;
    }
    public PasswordOperationResults savePassword(Password pass){
        if(isPasswordTitleEmpty(pass)){
            return PasswordOperationResults.TITLE_IS_NULL;
        }else if(!isPasswordEmpty(pass)){
            return PasswordOperationResults.PASSWORD_EXISTS;
        }else if(repo.isPasswordTitleExist(pass.getTitle())){
            return PasswordOperationResults.TITLE_EXISTS;
        }
        pass.setId(UUID.randomUUID().toString());
        repo.save(pass);
        return PasswordOperationResults.SUCCESS;
    }



    public List<Password> listPassword(){
        List<Password> passwords =  hidePasswordInf(repo.list());
        return passwords;
    }

    public PasswordOperationResults updatePassword(Password pass){
        if(isPasswordEmpty(pass)){
            return PasswordOperationResults.PASSWORD_NOT_EXISTS;
        }
        repo.save(pass);
        return PasswordOperationResults.SUCCESS;
    }

    public PasswordOperationResults deletePassword(Password pass){
        if(isPasswordEmpty(pass)){
            return PasswordOperationResults.PASSWORD_NOT_EXISTS;
        }
        repo.deleteById(pass.getId());
        return PasswordOperationResults.SUCCESS;
    }

    public String getPasswordInfById(String id){
        Password pass = repo.findById(id);
        return pass.getPassword();
    }

    public String generatePassword(Integer length){
        return Generator.generatePassword(length);
    }





}
