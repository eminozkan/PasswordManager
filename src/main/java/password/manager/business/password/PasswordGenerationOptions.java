package password.manager.business.password;

public class PasswordGenerationOptions {

    private int length;

    private String password;

    private String passwordId;

    private boolean hasSpecialCharacters;

    private boolean hasNumericCharacters;

    private boolean hasUpperCaseCharacters;

    private boolean hasLowerCaseCharacters;

    public int getLength() {
        return length;
    }

    public PasswordGenerationOptions setLength(int length) {
        this.length = length;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public PasswordGenerationOptions setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPasswordId() {
        return passwordId;
    }

    public PasswordGenerationOptions setPasswordId(String passwordId) {
        this.passwordId = passwordId;
        return this;
    }

    public boolean getHasSpecialCharacters() {
        return hasSpecialCharacters;
    }

    public PasswordGenerationOptions setHasSpecialCharacters(boolean hasSpecialCharacters) {
        this.hasSpecialCharacters = hasSpecialCharacters;
        return this;
    }

    public boolean getHasNumericCharacters() {
        return hasNumericCharacters;
    }

    public PasswordGenerationOptions setHasNumericCharacters(boolean hasNumericCharacters) {
        this.hasNumericCharacters = hasNumericCharacters;
        return this;
    }

    public boolean getHasUpperCaseCharacters() {
        return hasUpperCaseCharacters;
    }

    public PasswordGenerationOptions setHasUpperCaseCharacters(boolean hasUpperCaseCharacters) {
        this.hasUpperCaseCharacters = hasUpperCaseCharacters;
        return this;
    }

    public boolean getHasLowerCaseCharacters() {
        return hasLowerCaseCharacters;
    }

    public PasswordGenerationOptions setHasLowerCaseCharacters(boolean hasLowerCaseCharacters) {
        this.hasLowerCaseCharacters = hasLowerCaseCharacters;
        return this;
    }
}