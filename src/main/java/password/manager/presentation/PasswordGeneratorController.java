package password.manager.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import password.manager.business.PasswordService;
import password.manager.business.password.GeneratedPassword;
import password.manager.business.password.Password;
import password.manager.business.results.PasswordOperationResults;

@RestController
@RequestMapping("/api/passwords/generate")
public class PasswordGeneratorController {
    private final PasswordService passwordService;

    public PasswordGeneratorController(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    @PostMapping
    public ResponseEntity<GeneratedPassword> generatePassword(@RequestBody GeneratedPassword password){
        if(ObjectUtils.isEmpty(password.getPasswordId())) {
            GeneratedPassword generatedPass = passwordService.generatePassword(password);
            return new ResponseEntity<>(generatedPass, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @PatchMapping()
    public ResponseEntity<Password> generatePasswordById(@RequestBody GeneratedPassword generatedPassword){
        if(ObjectUtils.isEmpty(generatedPassword.getPasswordId())){
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        PasswordOperationResults result = passwordService.generatePassword(generatedPassword.getPasswordId(),generatedPassword);
        if(result == PasswordOperationResults.PASSWORD_NOT_EXISTS){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Password pass = new Password(passwordService.getPasswordById(generatedPassword.getPasswordId(),false));
        return new ResponseEntity<>(pass,HttpStatus.OK);

    }


}
