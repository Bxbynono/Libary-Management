package cor;

import javax.swing.JOptionPane;

import LibraryFramework.LoginHandler;
import LibraryFramework.SignupHandler;

// Concrete Handlers
public class InputValidationHandler implements SignupHandler, LoginHandler {
    private SignupHandler nextSignupHandler;
    private LoginHandler nextLoginHandler;

    public void setNextHandler(SignupHandler handler) {
        this.nextSignupHandler = handler;
    }

    public void setNextHandler(LoginHandler handler) {
        this.nextLoginHandler = handler;
    }

    public void handleRequest(String username, String email, String password, String confirmPassword) {
        // Validate input for signup process
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(null, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            if (nextSignupHandler != null) {
                nextSignupHandler.handleRequest(username, email, password, confirmPassword);
            }
        }
    }

    public void handleRequest(String email, String password) {
        // Validate input for login process
        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            if (nextLoginHandler != null) {
                nextLoginHandler.handleRequest(email, password);
            }
        }
    }
}
