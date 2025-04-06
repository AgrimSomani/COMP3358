package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AuthService extends Remote {
    // Login a user with username and password
    boolean login(String username, String password) throws RemoteException;

    // Register a new user
    boolean register(String username, String password) throws RemoteException;

    // Logout a user
    boolean logout(String username) throws RemoteException;
}