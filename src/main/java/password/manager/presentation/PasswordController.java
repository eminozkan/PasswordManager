//package password.manager.presentation;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.util.ObjectUtils;
//import org.springframework.web.bind.annotation.*;
//import password.manager.business.password.Password;
//import password.manager.business.results.PasswordOperationResults;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/passwords")
//public class PasswordController {
//    private final DefaultPasswordService passwordService;
//
//    public PasswordController(DefaultPasswordService passwordService) {
//        this.passwordService = passwordService;
//    }
//
//    @GetMapping()
//    public List<Password> listPassword(@RequestParam(required = false) String directoryName){
//        if(ObjectUtils.isEmpty(directoryName)){
//            return passwordService.listPassword();
//        }
//        return passwordService.listPasswordByDirectory(directoryName);
//    }
//
//    @GetMapping(value = "/{id}")
//
//    public ResponseEntity<Password> getPasswordById(@PathVariable("id") String id,@RequestParam(required = false) Boolean reveal){
//        if(reveal == null){
//            reveal =false;
//        }
//        Password pass = passwordService.getPasswordById(id,reveal);
//        if(pass == null){
//
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>(pass,HttpStatus.OK);
//    }
//
//
//    @PostMapping
//    public ResponseEntity<Password> addPassword(@RequestBody Password pass){
//        PasswordOperationResults result = passwordService.savePassword(pass);
//        if(result == PasswordOperationResults.TITLE_IS_NULL) {
//            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
//        }else if(result == PasswordOperationResults.TITLE_EXISTS) {
//            return new ResponseEntity<>(HttpStatus.CONFLICT);
//        }else{
//            return new ResponseEntity<>(passwordService.getPasswordById(pass.getId(),false),HttpStatus.CREATED);
//        }
//    }
//
//
//
//    @PatchMapping("/{id}")
//    public ResponseEntity<String> updatePassword(@PathVariable("id") String id,@RequestBody Password pass){
//        PasswordOperationResults result = passwordService.updatePassword(id,pass);
//        if(result == PasswordOperationResults.PASSWORD_NOT_EXISTS){
//            return new ResponseEntity<>("Password not exists",HttpStatus.NOT_FOUND);
//        }else if(result == PasswordOperationResults.TITLE_EXISTS){
//            return new ResponseEntity<>("Title is exists", HttpStatus.CONFLICT);
//        }else{
//            return new ResponseEntity<>("Password updated",HttpStatus.OK);
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deletePassword(@PathVariable("id") String id){
//        passwordService.deletePassword(id);
//        return new ResponseEntity<>("Successfully deleted",HttpStatus.NO_CONTENT);
//    }
//
//}
