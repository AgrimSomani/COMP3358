package client;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RMIClientFactory {
    private static final Map<Class<?>, Object> services = new ConcurrentHashMap<>();
    private static final Map<Class<?>, Long> servicesLastAccessTime = new ConcurrentHashMap<>();
    private static Registry registry;

    @SuppressWarnings("unchecked")
    public synchronized static <T> T getService(Class<T> serviceClass) throws RemoteException {
        if (!services.containsKey(serviceClass)) {
            addService(serviceClass);
        }

        // Check the last accessed time of the service and remove if older than 5
        // minutes with a new instance
        long lastAccessTime = servicesLastAccessTime.get(serviceClass);
        if (System.currentTimeMillis() - lastAccessTime > 5 * 60 * 1000) {
            addService(serviceClass);
        }

        return (T) services.get(serviceClass);
    }

    private static <T> void addService(Class<T> serviceClass) throws RemoteException {
        try {
            String serviceName = serviceClass.getSimpleName().replace("Service", "");
            T service = serviceClass.cast(getRegistry().lookup(serviceName));
            services.put(serviceClass, service);
            servicesLastAccessTime.put(serviceClass, System.currentTimeMillis());
        } catch (Exception e) {
            throw new RemoteException("Service not found: " + serviceClass, e);
        }
    }

    private static Registry getRegistry() throws RemoteException {
        if (registry == null) {
            registry = LocateRegistry.getRegistry("localhost", 1099);
        }
        return registry;
    }
}