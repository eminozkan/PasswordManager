package password.manager.business;

import password.manager.business.password.Password;
import password.manager.business.password.PasswordGenerateOptions;
import password.manager.business.results.PasswordOperationResults;


public interface PasswordGeneratorService {

    Password generatePasswordWithNoId(PasswordGenerateOptions options);

    PasswordOperationResults generatePasswordWithId(PasswordGenerateOptions options);

}
