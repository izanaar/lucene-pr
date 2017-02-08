package com.rpnkv.practice.lucene.util;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.junit.Test;

import java.io.StringReader;
import java.util.Iterator;
import java.util.stream.StreamSupport;

import static org.junit.Assert.*;

public class AnalyzerUtilsTest {

    @Test
    public void getTokenList() throws Exception {
        Tokenizer tokenizer = new WhitespaceTokenizer();
        tokenizer.setReader(new StringReader("tempest keep was merely a"));

        Iterator<String> tokenIterator = AnalyzerUtils.getTokenIterator(tokenizer);

        while (tokenIterator.hasNext())
            System.out.println(tokenIterator.next());
    }

}