package password.manager.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import password.manager.business.PasswordGeneratorService;
import password.manager.business.password.Password;
import password.manager.business.password.PasswordGenerateOptions;
import password.manager.business.results.PasswordOperationResults;

@RestController
@RequestMapping("/api/passwords/generate")
public class PasswordGeneratorController {

    private final PasswordGeneratorService generatorService;

    public PasswordGeneratorController(PasswordGeneratorService service) {
        this.generatorService = service;
    }

    @PostMapping
    public ResponseEntity<Password> generatePassword(@RequestBody PasswordGenerateOptions options){
        if(ObjectUtils.isEmpty(options.getPasswordId())) {
            Password generatedPass = generatorService.generatePasswordWithNoId(options);
            return new ResponseEntity<>(generatedPass, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @PatchMapping()
    public ResponseEntity<String> generatePasswordById(@RequestBody PasswordGenerateOptions options){
        if(ObjectUtils.isEmpty(options.getPasswordId())){
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        PasswordOperationResults result = generatorService.generatePasswordWithId(options);
        if(result == PasswordOperationResults.PASSWORD_NOT_EXISTS){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Success",HttpStatus.OK);
    }
}
