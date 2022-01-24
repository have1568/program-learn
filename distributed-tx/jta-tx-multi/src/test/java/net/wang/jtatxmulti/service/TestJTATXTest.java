package net.wang.jtatxmulti.service;

import net.wang.jtatxmulti.JtaTxMultiApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class TestJTATXTest extends JtaTxMultiApplicationTests {

    @Autowired
    private TestJTATX testJTATX;
    @Test
    public void test1() {
        testJTATX.test();
    }
}