package com.artechra;

import java.math.BigDecimal;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SimpleService extends Remote {
    public long add(int x, int y) throws RemoteException ;
    public BigDecimal add(BigDecimal x, BigDecimal y) throws RemoteException ;
    public String concat(String s1, String s2) throws RemoteException ;
}
