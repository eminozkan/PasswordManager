package password.manager.business;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import password.manager.business.password.Password;
import password.manager.persistence.PasswordRepository;

import static org.assertj.core.api.Assertions.*;
import static password.manager.business.results.PasswordOperationResults.*;


@ExtendWith(MockitoExtension.class)
public class DefaultPasswordServiceTest {
    @Mock
    PasswordRepository repository;
    DefaultPasswordService passwordService;

    @Captor
    private ArgumentCaptor<Password> captor;

    @BeforeEach
    void setUp() {
        passwordService = new DefaultPasswordService(repository);
    }

    @Nested
    class SavePassword {
        Password password;

        @BeforeEach
        void setUp() {
            password = new Password()
                    .setTitle("title")
                    .setDirectoryName("directory")
                    .setUsername("username")
                    .setPassword("password")
                    .setNotes("notes")
                    .setUrl("url");
        }

        @DisplayName("Success")
        @Test
        void success() {
            assertThat(SUCCESS).isEqualTo(passwordService.savePassword(password));

            assertThat(password.getId()).isNotNull().isNotBlank();

            Mockito.verify(repository).save(captor.capture());

            final Password capturedPassword = captor.getValue();

            assertThat(capturedPassword.getId()).isEqualTo(password.getId());
            assertThat(capturedPassword.getTitle()).isEqualTo(password.getTitle());
            assertThat(capturedPassword.getUsername()).isEqualTo(password.getUsername());
            assertThat(capturedPassword.getPassword()).isEqualTo(password.getPassword());
            assertThat(capturedPassword.getUrl()).isEqualTo(password.getUrl());
            assertThat(capturedPassword.getNotes()).isEqualTo(password.getNotes());

            System.out.println("Save password test finished successfully");
        }

        @DisplayName("No title")
        @Test
        void noTitle() {
            password.setTitle("");
            assertThat(passwordService.savePassword(password)).isEqualTo(TITLE_IS_NULL);
            Mockito.verifyNoInteractions(repository);
        }

        @DisplayName("Not Unique Title")
        @Test
        void notUniqueTitle() {
            password.setTitle("not-unique-title");

            Mockito.doReturn(true)
                    .when(repository)
                    .isPasswordTitleExist("not-unique-title");

            Mockito.verifyNoMoreInteractions(repository);

            assertThat(passwordService.savePassword(password)).isEqualTo(TITLE_EXISTS);
        }
    }

    @Nested
    class UpdatePassword {
        private Password password;
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

        @DisplayName("Doesnt Exist Password")
        @Test
        void doesntExist() {
            Mockito.doReturn(null)
                    .when(repository)
                    .findById("id");

            assertThat(passwordService.updatePassword("id", password)).isEqualTo(PASSWORD_NOT_EXISTS);
            Mockito.verifyNoMoreInteractions(repository);
        }

        @DisplayName("Not Unique Title")
        @Test
        void notUniqueTitle() {

            Password oldPass = new Password(password);

            password.setTitle("not-unique-title");

            Mockito.doReturn(oldPass)
                    .when(repository)
                    .findById("id");

            Mockito.doReturn(true)
                    .when(repository)
                    .isPasswordTitleExist("not-unique-title");

            assertThat(passwordService.updatePassword("id", password)).isEqualTo(TITLE_EXISTS);
            Mockito.verify(repository).isPasswordTitleExist("not-unique-title");
        }

        @DisplayName("Success")
        @Test
        void success() {
            Password newPassword = new Password()
                    .setTitle("new-title")
                    .setUsername("new-username")
                    .setPassword("new-password")
                    .setUrl("new-url");

            Mockito.doReturn(password)
                    .when(repository)
                    .findById("id");

            assertThat(passwordService.updatePassword("id", newPassword)).isEqualTo(SUCCESS);

            Mockito.verify(repository).update(captor.capture());

            Password capturedPassword = captor.getValue();

            assertThat(capturedPassword.getId()).isEqualTo("id");
            assertThat(capturedPassword.getTitle()).isEqualTo(newPassword.getTitle());
            assertThat(capturedPassword.getDirectoryName()).isEqualTo(password.getDirectoryName());
            assertThat(capturedPassword.getUsername()).isEqualTo(newPassword.getUsername());
            assertThat(capturedPassword.getPassword()).isEqualTo(newPassword.getPassword());
            assertThat(capturedPassword.getNotes()).isEqualTo(password.getNotes());
            assertThat(capturedPassword.getUrl()).isEqualTo(newPassword.getUrl());
        }

        @DisplayName("Same Title")
        @Test
        void sameTitle() {
            Password newPassword = new Password()
                    .setTitle("title")
                    .setUsername("new-username")
                    .setPassword("new-password")
                    .setUrl("new-url");

            Mockito.doReturn(password)
                    .when(repository)
                    .findById("id");

            assertThat(passwordService.updatePassword("id", newPassword)).isEqualTo(SUCCESS);

            Mockito.verify(repository).findById("id");
            Mockito.verify(repository).update(Mockito.any());

            Mockito.verifyNoMoreInteractions(repository);
        }
    }

    @DisplayName("Delete password by id")
    @Test
    void deletePasswordById(){
        passwordService.deletePassword("id");
        Mockito.verify(repository).deleteById("id");
        Mockito.verifyNoMoreInteractions(repository);
    }

}
