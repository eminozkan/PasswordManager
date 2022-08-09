package password.manager.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import password.manager.business.PasswordService;
import password.manager.business.password.Password;
import password.manager.business.password.PasswordGenerator;
import password.manager.business.results.PasswordOperationResults;
import password.manager.persistence.PasswordRepo;

import java.util.List;

@RestController
@RequestMapping("/api/passwords")
@EnableAutoConfiguration
public class PasswordController {
    @Autowired
    private PasswordService passwordService;

    @GetMapping()
    public List<Password> listPassword(@RequestParam(required = false) String directoryName){
        if(ObjectUtils.isEmpty(directoryName)){
            return passwordService.listPassword();
        }
        return passwordService.listPasswordByDirectory(directoryName);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<String> getPasswordInf(@PathVariable("id") String id){
        Password pass = passwordService.getPasswordInfById(id);
        if(ObjectUtils.isEmpty(pass)){
            return new ResponseEntity("Password not exists",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(pass.getPassword(),HttpStatus.OK);
    }

    @PostMapping("/generate")
    public ResponseEntity<String> generatePassword(@RequestBody PasswordGenerator generator){
        String password = passwordService.generatePassword(generator);
        return new ResponseEntity<String>(password, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> addPassword(@RequestBody Password pass){
        PasswordOperationResults result = passwordService.savePassword(pass);
        if(result == PasswordOperationResults.TITLE_IS_NULL) {
            return new ResponseEntity<>("Title can't be null",HttpStatus.UNPROCESSABLE_ENTITY);
        }else if(result == PasswordOperationResults.TITLE_EXISTS) {
            return new ResponseEntity<>("Title is exists", HttpStatus.CONFLICT);
        }else{
            String responseBody ="{ \"id\" : \"" + pass.getId() + "\"," +
                    "\"title\" : \"" + pass.getTitle() + "\"}";
            return new ResponseEntity<>(responseBody,HttpStatus.CREATED);
        }
    }



    @PatchMapping("/{id}")
    public ResponseEntity<String> updatePassword(@PathVariable("id") String id,@RequestBody Password pass){
        PasswordOperationResults result = passwordService.updatePassword(id,pass);
        if(result == PasswordOperationResults.PASSWORD_NOT_EXISTS){
            return new ResponseEntity("Password not exists",HttpStatus.NOT_FOUND);
        }else if(result == PasswordOperationResults.TITLE_EXISTS){
            return new ResponseEntity<>("Title is exists", HttpStatus.CONFLICT);
        }else{
            return new ResponseEntity<>("Password updated",HttpStatus.OK);
        }
    }

    @PatchMapping(value = "/{id}/generate")
    public ResponseEntity<String> generatePassword(@PathVariable("id") String id,@RequestBody PasswordGenerator generator){
        PasswordOperationResults result = passwordService.generatePassword(id,generator);
        if(result == PasswordOperationResults.PASSWORD_NOT_EXISTS){
            return new ResponseEntity<String>("Password not exists",HttpStatus.NOT_FOUND);
        }
        Password pass = passwordService.getPasswordInfById(id);
        String responseBody = "{ \"id\" : \"" + id + "\"," +
                "\"password\" : \"" + pass.getPassword() + "\"}";
        return new ResponseEntity<String>(responseBody,HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePassword(@PathVariable("id") String id){
        PasswordOperationResults result = passwordService.deletePassword(id);
        return new ResponseEntity<>("Successfully deleted",HttpStatus.NO_CONTENT);
    }

}
