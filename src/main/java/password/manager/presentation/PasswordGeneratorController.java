package password.manager.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import password.manager.business.DefaultPasswordService;
import password.manager.business.password.PasswordGenerationOptions;
import password.manager.business.password.Password;
import password.manager.business.results.PasswordOperationResults;

@RestController
@RequestMapping("/api/passwords/generate")
public class PasswordGeneratorController {
    private final DefaultPasswordService passwordService;

    public PasswordGeneratorController(DefaultPasswordService passwordService) {
        this.passwordService = passwordService;
    }

    @PostMapping
    public ResponseEntity<PasswordGenerationOptions> generatePassword(@RequestBody PasswordGenerationOptions password){
        if(ObjectUtils.isEmpty(password.getPasswordId())) {
            PasswordGenerationOptions generatedPass = passwordService.generatePassword(password);
            return new ResponseEntity<>(generatedPass, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @PatchMapping()
    public ResponseEntity<Password> generatePasswordById(@RequestBody PasswordGenerationOptions passwordGenerationOptions){
        if(ObjectUtils.isEmpty(passwordGenerationOptions.getPasswordId())){
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        PasswordOperationResults result = passwordService.generatePassword(passwordGenerationOptions.getPasswordId(), passwordGenerationOptions);
        if(result == PasswordOperationResults.PASSWORD_NOT_EXISTS){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Password pass = new Password(passwordService.getPasswordById(passwordGenerationOptions.getPasswordId(),false));
        return new ResponseEntity<>(pass,HttpStatus.OK);

    }


}
