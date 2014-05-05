package com.artechra.test;

import com.artechra.SimpleRmiServer;
import com.artechra.SimpleService;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class TestSimpleService {

    @Test
    public void serviceMustAddLongs() throws Exception {
        SimpleService s = new SimpleRmiServer() ;
        assertEquals(10, s.add(9,1)) ;
    }
    @Test
    public void serviceMustAddDecimals() throws Exception {
        SimpleService s = new SimpleRmiServer() ;
        assertEquals(new BigDecimal(10), s.add(new BigDecimal(9.0), new BigDecimal(1))) ;
    }
    @Test
    public void serviceMustConcatenateStrings() throws Exception {
        SimpleService s = new SimpleRmiServer() ;
        assertEquals("123456", s.concat("123", "456")) ;
    }
}
