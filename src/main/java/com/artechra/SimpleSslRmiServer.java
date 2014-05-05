package com.artechra;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.rmi.ssl.SslRMIClientSocketFactory;
import javax.rmi.ssl.SslRMIServerSocketFactory;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class SimpleSslRmiServer extends SimpleRmiServer {

    public static final int REGISTRY_PORT = 2099 ;
    private static final String CLIENT_AUTH_PROPERTY = "requireClientAuth" ;

    private final static Logger LOG = Logger.getLogger(SimpleSslRmiServer.class.getName());

    private boolean requireClientAuth ;

    private SimpleSslRmiServer(boolean requireClientAuth) throws RemoteException {
        super(0,
                new SslRMIClientSocketFactory(),
                new SslRMIServerSocketFactory(null, null, requireClientAuth));
        this.requireClientAuth = requireClientAuth ;
    }

    @Override
    protected Registry createRegistry(int port) throws RemoteException {
        LOG.info("Creating local registry on port " + port + " using SSL socket factories (with" +
                (this.requireClientAuth ? "" : "out") + " client authentication)") ;
        return LocateRegistry.createRegistry(port, new SslRMIClientSocketFactory(),
                new SslRMIServerSocketFactory(null, null, this.requireClientAuth)) ;
    }

    public static void main(String[] args) {
        try {
            SimpleSslRmiServer server = new SimpleSslRmiServer(getRequireClientAuthFlag()) ;
            server.createCalculatorServer("SimpleService", REGISTRY_PORT);
            LOG.info("SSL SimpleService Server ready");
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Unexpected exception:", e);
        }
    }

    private static boolean getRequireClientAuthFlag() {
        return Boolean.parseBoolean(System.getProperty(CLIENT_AUTH_PROPERTY)) ;
    }

}
