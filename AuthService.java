// Created by Puteri Nur Liyana Imanina Abdullah (100804) - Draft 1 (2025-06-03)
// Tested by Melisa Lai Wan Jiun (99663), 2025-05-11

/* This interface defines the contract for authentication service in the application.
 * It provides the essential method required for user authentication operations including
 * user registration and user login functionality.
 */

 /* This interface provides a contract that must be implemented by any authentication service class,
  * ensuring consistent behavior across different authentication implementations.
  */

public interface AuthService {
    boolean signUp(String userName, String email, String password);
    boolean logIn(String userName, String password);
}