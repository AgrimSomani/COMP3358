package server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import shared.AuthService;

public class Server {
    public static void main(String[] args) {
        try {
            // Create and export the registry on port 1099
            Registry registry = LocateRegistry.createRegistry(1099);

            // Create the service implementation
            AuthService authService = new AuthServiceImpl();

            // Bind the service to the registry
            registry.rebind("Auth", authService);

            System.out.println("User Service is running...");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
