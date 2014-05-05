package com.artechra;

import javax.rmi.ssl.SslRMIClientSocketFactory;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Logger;

public class RmiSslTestClient extends RmiTestClient {

    private final static Logger LOG = Logger.getLogger(RmiSslTestClient.class.getName());

    @Override
    public Registry getRegistry(String host, int port) throws RemoteException {
        return LocateRegistry.getRegistry(host,  port, new SslRMIClientSocketFactory()) ;
    }

    public static void main(String[] args) {

        try {
            RmiSslTestClient c = new RmiSslTestClient();
            LOG.info(String.format("Binding to service on %s:%d via SSL", args[0], SimpleSslRmiServer.REGISTRY_PORT));
            c.init(args[0], SimpleSslRmiServer.REGISTRY_PORT);
            runTests(c);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
