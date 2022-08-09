package password.manager.presentation;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import password.manager.business.PasswordService;
import password.manager.business.password.GeneratedPassword;
import password.manager.business.password.Password;
import password.manager.business.results.PasswordOperationResults;

@RestController
@EnableAutoConfiguration
@RequestMapping("/api/passwords/generate")
public class PasswordGeneratorController {
    private final PasswordService passwordService;

    public PasswordGeneratorController(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    @PostMapping
    public ResponseEntity<GeneratedPassword> generatePassword(@RequestBody GeneratedPassword password){
        GeneratedPassword generatedPass = passwordService.generatePassword(password);
        return new ResponseEntity<>(generatedPass, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Password> generatePassword(@PathVariable("id") String id, @RequestBody GeneratedPassword generatedPassword){
        PasswordOperationResults result = passwordService.generatePassword(id,generatedPassword);
        if(result == PasswordOperationResults.PASSWORD_NOT_EXISTS){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Password pass = new Password(passwordService.getPasswordById(id,false));
        return new ResponseEntity<>(pass,HttpStatus.OK);

    }


}
