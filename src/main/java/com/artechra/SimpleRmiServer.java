package com.artechra;

import java.math.BigDecimal;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SimpleRmiServer extends UnicastRemoteObject implements SimpleService {

    public static final int REGISTRY_PORT = 1099 ;


    private final static Logger LOG = Logger.getLogger(SimpleRmiServer.class.getName());

    public SimpleRmiServer() throws RemoteException {
    }

    SimpleRmiServer(int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException {
        super(port, csf, ssf) ;
    }

    public static void main(String[] args) {
        try {
            SimpleRmiServer server = new SimpleRmiServer() ;
            server.createCalculatorServer("SimpleService", REGISTRY_PORT);
            LOG.info("SimpleService bound");
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Unexpected exception:", e);
        }
    }

    void createCalculatorServer(String name, int port) throws RemoteException, AlreadyBoundException {
        Registry registry = createRegistry(port) ;
        registry.bind(name, this);
    }

    Registry createRegistry(int port) throws RemoteException {
        LOG.info("Creating local registry on port " + port + " (no SSL)") ;
        return LocateRegistry.createRegistry(port) ;
    }

    public long add(int x, int y) throws RemoteException {
        return x+y ;
    }

    public BigDecimal add(BigDecimal x, BigDecimal y) throws RemoteException {
        return x.add(y) ;
    }

    public String concat(String s1, String s2) throws RemoteException {
        return s1 + s2 ;
    }
}
