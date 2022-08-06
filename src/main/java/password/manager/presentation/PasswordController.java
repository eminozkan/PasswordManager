package password.manager.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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



    @PostMapping
    public ResponseEntity<String> addPasword(@RequestBody Password pass){
        PasswordOperationResults result = passwordService.savePassword(pass);
        if(result == PasswordOperationResults.TITLE_IS_NULL) {
            return new ResponseEntity<>("Title can't be null",HttpStatus.BAD_REQUEST);
        }else if(result == PasswordOperationResults.TITLE_EXISTS) {
            return new ResponseEntity<>("Title is exists", HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<>("Saved Successfully",HttpStatus.CREATED);
        }
    }

}
