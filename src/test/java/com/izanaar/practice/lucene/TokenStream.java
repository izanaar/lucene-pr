package com.izanaar.practice.lucene;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.junit.Test;

import java.io.StringReader;

public class TokenStream {

    @Test
    public void ts() throws Exception {
        Tokenizer tokenizer = new StandardTokenizer();
        tokenizer.setReader(new StringReader("The dog sleeps on a bed."));



    }
}
