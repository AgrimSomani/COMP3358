package server;

import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import shared.AuthService;

public class AuthServiceImpl extends UnicastRemoteObject implements AuthService {
    private static final String USER_INFO_FILE = "UserInfo.txt";
    private final Object user_info_lock = new Object();

    private static final String ONLINE_USER_FILE = "OnlineUser.txt";
    private final Object online_user_lock = new Object();

    public AuthServiceImpl() throws RemoteException {
        super();
        // Clear the OnlineUser.txt file when server starts
        try {
            new FileWriter(ONLINE_USER_FILE, false).close();
            System.out.println("OnlineUser.txt cleared at server start");
        } catch (IOException e) {
            System.err.println("Error clearing OnlineUser.txt: " + e.getMessage());
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
        synchronized (user_info_lock) {
            try (BufferedReader reader = new BufferedReader(new FileReader(USER_INFO_FILE))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(":");
                    if (parts.length == 2 && parts[0].equals(username) && parts[1].equals(password)) {
                        return true;
                    }
                }
            } catch (IOException e) {
                System.err.println("Error validating user: " + e.getMessage());
            }
            return false;
        }
    }

    private boolean isUserRegistered(String username) {
        synchronized (user_info_lock) {
            try (BufferedReader reader = new BufferedReader(new FileReader(USER_INFO_FILE))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(":");
                    if (parts.length >= 1 && parts[0].equals(username)) {
                        return true;
                    }
                }
            } catch (IOException e) {
                System.err.println("Error checking if user is registered: " + e.getMessage());
                // If the file doesn't exist yet, it means no users are registered
                if (e instanceof FileNotFoundException) {
                    return false;
                }
            }
            return false;
        }
    }

    private boolean isUserOnline(String username) {
        synchronized (online_user_lock) {
            try (BufferedReader reader = new BufferedReader(new FileReader(ONLINE_USER_FILE))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.equals(username)) {
                        return true;
                    }
                }
            } catch (IOException e) {
                System.err.println("Error checking if user is online: " + e.getMessage());
                // If the file doesn't exist yet, it means no users are online
                if (e instanceof FileNotFoundException) {
                    return false;
                }
            }
            return false;
        }
    }

    private boolean addUserToUserInfo(String username, String password) {
        synchronized (user_info_lock) {
            try (FileWriter writer = new FileWriter(USER_INFO_FILE, true)) {
                writer.write(username + ":" + password + "\n");
                return true;
            } catch (IOException e) {
                System.err.println("Error adding user to UserInfo.txt: " + e.getMessage());
                return false;
            }
        }
    }

    private boolean addUserToOnlineList(String username) {
        synchronized (online_user_lock) {
            try (FileWriter writer = new FileWriter(ONLINE_USER_FILE, true)) {
                writer.write(username + "\n");
                return true;
            } catch (IOException e) {
                System.err.println("Error adding user to OnlineUser.txt: " + e.getMessage());
                return false;
            }
        }

    }

    private boolean removeUserFromOnlineList(String username) {
        List<String> onlineUsers = new ArrayList<>();
        boolean userFound = false;

        synchronized (online_user_lock) {
            // Read all online users
            try (BufferedReader reader = new BufferedReader(new FileReader(ONLINE_USER_FILE))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.equals(username)) {
                        userFound = true;
                    } else {
                        onlineUsers.add(line);
                    }
                }
            } catch (IOException e) {
                System.err.println("Error reading OnlineUser.txt: " + e.getMessage());
                return false;
            }

            if (!userFound) {
                return false;
            }

            // Write back all users except the one to remove
            try (FileWriter writer = new FileWriter(ONLINE_USER_FILE, false)) {
                for (String user : onlineUsers) {
                    writer.write(user + "\n");
                }
                return true;
            } catch (IOException e) {
                System.err.println("Error updating OnlineUser.txt: " + e.getMessage());
                return false;
            }
        }
    }

}