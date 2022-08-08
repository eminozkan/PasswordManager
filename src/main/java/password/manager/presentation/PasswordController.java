package password.manager.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import password.manager.business.PasswordService;
import password.manager.business.password.Password;
import password.manager.business.results.PasswordOperationResults;
import password.manager.persistence.PasswordRepo;

import java.util.List;

@RestController
@RequestMapping("/api/passwords")
public class PasswordController {
    PasswordRepo repo = new PasswordRepo();

    PasswordService passwordService = new PasswordService(repo);

    @GetMapping
    public List<Password> listPassword(){
        List<Password> passwords = passwordService.listPassword();
        return passwords;
    }

    @GetMapping(value = "/{length}")
    public String generatePassword(@PathVariable("length") Integer length){
        return passwordService.generatePassword(length);
    }

    @GetMapping(value = "/{id}/")
    public ResponseEntity<String> getPasswordInf(@PathVariable("id") String id){
        Password pass = passwordService.getPasswordInfById(id);
        if(ObjectUtils.isEmpty(pass)){
            return new ResponseEntity("Password not exists",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(pass.getPassword(),HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}/{directoryName}/")
    public ResponseEntity<String> changeDirectory(@PathVariable("id") String id, @PathVariable("directoryName") String directoryName){
        PasswordOperationResults result;
        if(directoryName.equals("-")){
            result = passwordService.updateDirectoryInf(id,"no-folder");
        }else {
            result = passwordService.updateDirectoryInf(id, directoryName);
        }
        if(result == PasswordOperationResults.PASSWORD_NOT_EXISTS){
            return new ResponseEntity<String>("Password not exists",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Directory info changed successfully",HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}/{length}")
    public ResponseEntity<String> generatePassword(@PathVariable("id") String id,@PathVariable("length") Integer length){
        PasswordOperationResults result = passwordService.generatePassword(id,length);
        if(result == PasswordOperationResults.PASSWORD_NOT_EXISTS){
            return new ResponseEntity<String>("Password not exists",HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>("Success",HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<String> addPassword(@RequestBody Password pass){
        PasswordOperationResults result = passwordService.savePassword(pass);
        if(result == PasswordOperationResults.TITLE_IS_NULL) {
            return new ResponseEntity<>("Title can't be null",HttpStatus.BAD_REQUEST);
        }else if(result == PasswordOperationResults.TITLE_EXISTS) {
            return new ResponseEntity<>("Title is exists", HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<>("Saved Successfully",HttpStatus.CREATED);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> updatePassword(@RequestBody Password pass,@PathVariable("id") String id){
        PasswordOperationResults result = passwordService.updatePassword(pass,id);
        if(result == PasswordOperationResults.TITLE_EXISTS){
            return new ResponseEntity<>("Title is used",HttpStatus.BAD_REQUEST);
        }else if(result == PasswordOperationResults.PASSWORD_NOT_EXISTS){
            return new ResponseEntity<>("Password not exist",HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<>("Password information succesfully changed",HttpStatus.OK);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePassword(@PathVariable("id") String id){
        PasswordOperationResults result = passwordService.deletePassword(id);
        return new ResponseEntity<>("Successfully deleted",HttpStatus.NO_CONTENT);
    }

}
