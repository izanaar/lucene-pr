package com.rpnkv.practice.lucene.cache;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RandomDoubleDocTest {

    private RandomDoubleDoc rdd;

    @Before
    public void setUp() throws Exception {
        rdd = new RandomDoubleDoc(10);
    }

    @Test
    public void asseq() throws Exception {
        assertTrue(true);
    }

    @After
    public void tearDown() throws Exception {
        rdd.eraseDirectory();
    }
}