package fudi.freddy.circlecisample.math;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @version 1.0
 * @autor Romell Domínguez
 * @date 2/10/18
 */
public class SumTest {
    @Test
    public void method() throws Exception {
        assertEquals(Sum.method(2,3),5);
    }


    @Test
    public void methodError() throws Exception {
        assertEquals(Sum.method(999,999),999+999+1);
    }

}