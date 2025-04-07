package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import shared.AuthService;
import dao.OnlineUserDAO;
import dao.UserInfoDAO;

public class AuthServiceImpl extends UnicastRemoteObject implements AuthService {

    public AuthServiceImpl() throws RemoteException {
        super();
        // Clear the OnlineUser table when server starts
        try {
            OnlineUserDAO.removeAllSession();
            System.out.println("OnlineUser.txt cleared at server start");
        } catch (SQLException e) {
            System.err.println("Error deleting all past sessions from database: " + e.getMessage());
        }
    }

    @Override
    public boolean login(String username, String password) throws RemoteException {
        // Validate user from UserInfo.txt
        if (!validateUser(username, password)) {
            System.out.println("Login failed: Invalid username or password for " + username);
            return false;
        }

        // Check if user is already logged in using OnlineUser.txt
        if (isUserOnline(username)) {
            System.out.println("Login failed: User " + username + " is already logged in");
            return false;
        }

        // Update OnlineUser.txt
        if (addUserToOnlineList(username)) {
            System.out.println("User " + username + " logged in successfully");
            return true;
        }

        return false;
    }

    @Override
    public boolean register(String username, String password) throws RemoteException {
        // Check if username already exists in UserInfo.txt
        if (isUserRegistered(username)) {
            System.out.println("Registration failed: Username " + username + " already exists");
            return false;
        }

        // Add user to UserInfo.txt
        if (addUserToUserInfo(username, password)) {
            System.out.println("User " + username + " registered successfully");
            return true;
        }

        return false;
    }

    @Override
    public boolean logout(String username) throws RemoteException {
        // Update OnlineUser.txt by removing the user

        if (removeUserFromOnlineList(username)) {
            System.out.println("User " + username + " logged out successfully");
            return true;
        }

        System.out.println("Logout failed: User " + username + " not found in online list");
        return false;
    }

    // Helper methods
    private boolean validateUser(String username, String password) {
        try {
            return UserInfoDAO.authorizeUser(username, password);
        } catch (SQLException e) {
            System.err.println("Error validating user: " + e.getMessage());
        }
        return false;
    }

    private boolean isUserRegistered(String username) {
        try {
            return UserInfoDAO.isUserRegistered(username);
        } catch (SQLException e) {
            System.err.println("Error checking whether user is registered: " + e.getMessage());
        }
        return false;
    }

    private boolean isUserOnline(String username) {
        try {
            return OnlineUserDAO.isUserOnline(username);
        } catch (SQLException e) {
            System.err.println("Error checking whether user is online: " + e.getMessage());
        }
        return false;
    }

    private boolean addUserToUserInfo(String username, String password) {
        
        try {
            UserInfoDAO.insertUser(username, password);
            return true;
        } catch (SQLException e) {
            System.err.println("Error adding user to UserInfo: " + e.getMessage());
            return false;
        }        
    }

    private boolean addUserToOnlineList(String username) {
        try  {
            OnlineUserDAO.addSession(username);
            return true;
        } catch (SQLException e) {
            System.err.println("Error adding user to OnlineUser " + e.getMessage());
            return false;
        }
    }

    private boolean removeUserFromOnlineList(String username) {
        try {
            OnlineUserDAO.removeSession(username);
            return true;
        } catch (SQLException e) {
            System.err.println("Error updating OnlineUser: " + e.getMessage());
            return false;
        }    
    }

}