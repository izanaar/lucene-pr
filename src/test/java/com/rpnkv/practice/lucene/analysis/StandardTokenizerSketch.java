package com.rpnkv.practice.lucene.analysis;

import com.rpnkv.practice.lucene.util.AnalyzerUtils;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;

public class StandardTokenizerSketch {

    private Tokenizer standatdTokenizer;


    @Before
    public void setUp() throws Exception {
        standatdTokenizer = new StandardTokenizer();
    }

    @Test
    public void tokenize() throws Exception {
        standatdTokenizer.setReader(new StringReader("I was using mailbox: izanaar@gmail.com"));
        AnalyzerUtils.displayTokens(standatdTokenizer);
    }
}
