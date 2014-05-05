package com.artechra;

import com.artechra.timing.TimerGroup;

import java.math.BigDecimal;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;
import java.util.logging.Logger;

public class RmiTestClient {

    private final static Logger LOG = Logger.getLogger(RmiTestClient.class.getName()) ;

    private SimpleService calc;
    private final Random randomSource = new Random(System.currentTimeMillis() % 4);


    void init(String hostname, int port) throws RemoteException, NotBoundException {
        Registry registry = getRegistry(hostname, port) ;
        Object stub = registry.lookup("SimpleService");
        this.calc = (SimpleService)stub ;
    }

    Registry getRegistry(String host, int port) throws RemoteException {
        return LocateRegistry.getRegistry(host, port) ;
    }

    long add(int x, int y) {
        long ret ;
        try {
            ret = this.calc.add(x,y) ;
        } catch(RemoteException re) {
            throw new RuntimeException("Remote exception calling add()", re) ;
        }
        return ret ;
    }

    BigDecimal add(BigDecimal x, BigDecimal y) {
        BigDecimal ret ;
        try {
            ret = this.calc.add(x,y) ;
        } catch(RemoteException re) {
            throw new RuntimeException("Remote exception calling add()", re) ;
        }
        return ret ;
    }

    String concat(String s1, String s2) {
        String ret ;
        try {
            ret = this.calc.concat(s1, s2) ;
        } catch(RemoteException re) {
            throw new RuntimeException("Remote exception calling concat()", re) ;
        }
        return ret ;
    }

    TimerGroup.Summary testAdditionPerformance(int number) {
        int[] xvalues = generateIntegerValues(number) ;
        int[] yvalues = generateIntegerValues(number) ;
        TimerGroup tg = new TimerGroup() ;
        for (int i=0; i < number; i++) {
            tg.startTimer(i + "");
            long v = add(xvalues[i], yvalues[i]) ;
            assert v == xvalues[i] + yvalues[i] ;
            tg.stopTimer(i + "") ;
        }
        return tg.getSummaryStats() ;
    }

    TimerGroup.Summary testConcatenationPerformance(int number, int len) {
        String[] s1Values = generateStringValues(number, len) ;
        String[] s2Values = generateStringValues(number, len) ;
        TimerGroup tg = new TimerGroup() ;
        for (int i=0; i < number; i++) {
            tg.startTimer(i + "");
            String result = concat(s1Values[i], s2Values[i]) ;
            assert result != null ;
            tg.stopTimer(i + "") ;
        }
        return tg.getSummaryStats() ;
    }

    private int[] generateIntegerValues(int number) {
        int[] ret = new int[number] ;
        for (int i=0; i < number; i++) {
            ret[i] = randomSource.nextInt(1000) ;
        }
        return ret ;
    }

    private String[] generateStringValues(int number, int len) {
        String[] ret = new String[number] ;
        for (int i=0; i < number; i++) {
            ret[i] = generateRandomString(len) ;
        }
        return ret ;
    }

    private String generateRandomString(int len) {
        Random rand = new Random() ;
        StringBuilder string = new StringBuilder(len) ;
        for (int i=1; i <= len; i++) {
            string.append((char)(rand.nextInt(25)+97))  ;
        }
        return string.toString() ;
    }

    static void runTests(RmiTestClient client) {
        BigDecimal result = client.add(new BigDecimal(10.0), new BigDecimal(12.0)) ;
        assert result.equals(new BigDecimal(22.0)) ;
        TimerGroup.Summary additionSummary = client.testAdditionPerformance(10000) ;
        LOG.info("Result of 1000 addition calls: " + additionSummary) ;
        TimerGroup.Summary concatenationSummary = client.testConcatenationPerformance(10000, 500) ;
        LOG.info("Result of 1000 concatenation calls with 500 character strings: " + concatenationSummary) ;
    }

    public static void main(String[] args) {

        try {
            RmiTestClient c = new RmiTestClient();
            LOG.info(String.format("Binding to %s:%d using plain JRMP", args[0], SimpleRmiServer.REGISTRY_PORT));
            c.init(args[0], SimpleRmiServer.REGISTRY_PORT);
            runTests(c) ;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
