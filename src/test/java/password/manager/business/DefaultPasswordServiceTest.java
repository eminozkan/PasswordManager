package password.manager.business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import password.manager.business.password.Password;
import password.manager.business.results.PasswordOperationResults;
import password.manager.persistence.PasswordRepository;

import static org.assertj.core.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class DefaultPasswordServiceTest {
    @Mock
    PasswordRepository repository;
    DefaultPasswordService passwordService;

    @Captor
    private ArgumentCaptor<Password> captor;

    @BeforeEach
    void setUp(){
        passwordService = new DefaultPasswordService(repository);
    }

    @Nested
    class SavePassword {
        Password password;
        @BeforeEach
        void setUp(){
            password = new Password()
                    .setTitle("title")
                    .setDirectoryName("directory")
                    .setUsername("username")
                    .setPassword("password")
                    .setNotes("notes")
                    .setUrl("url");
        }

        @Test
        void savePasswordSuccess() {
            assertThat(PasswordOperationResults.SUCCESS).isEqualTo(passwordService.savePassword(password));

            assertThat(password.getId()).isNotNull().isNotBlank();

            Mockito.verify(repository).save(captor.capture());

            final Password capturedPassword = captor.getValue();

            assertThat(capturedPassword.getId()).isEqualTo(password.getId());
            assertThat(capturedPassword.getTitle()).isEqualTo(password.getTitle());
            assertThat(capturedPassword.getUsername()).isEqualTo(password.getUsername());
            assertThat(capturedPassword.getPassword()).isEqualTo(password.getPassword());
            assertThat(capturedPassword.getUrl()).isEqualTo(password.getUrl());
            assertThat(capturedPassword.getNotes()).isEqualTo(password.getNotes());
        }

        @Test
        void savePasswordNoTitle() {
            password.setTitle("");
            assertThat(passwordService.savePassword(password)).isEqualTo(PasswordOperationResults.TITLE_IS_NULL);
            Mockito.verifyNoInteractions(repository);
        }

        @Test
        void savePasswordNotUniqueTitle() {
            password.setTitle("not-unique-title");

            Mockito.doReturn(true)
                    .when(repository)
                    .isPasswordTitleExist("not-unique-title");

            Mockito.verifyNoMoreInteractions(repository);

            assertThat(passwordService.savePassword(password)).isEqualTo(PasswordOperationResults.TITLE_EXISTS);
        }
    }
}
