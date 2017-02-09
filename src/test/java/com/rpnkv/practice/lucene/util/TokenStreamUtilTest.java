package com.rpnkv.practice.lucene.util;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.junit.Before;
import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class TokenStreamUtilTest {

    private final String testPhrase = "tempest keep was merely";
    private Tokenizer tokenizer = new WhitespaceTokenizer();

    @Before
    public void setUp() throws Exception {
        Reader testPhraseReader = new StringReader(testPhrase);
        tokenizer.setReader(testPhraseReader);
    }

    @Test
    public void getTokenStreamAsStream() throws Exception {
        Collection<String> expectedTokens = Arrays.asList(testPhrase.split(" "));
        Iterator<String> tokenIterator = TokenStreamUtil.getTokenStreamIterator(tokenizer);

        while (tokenIterator.hasNext()) {
            assertTrue(expectedTokens.contains(tokenIterator.next()));
        }
    }

    @Test
    public void getTokenStreamIterator() throws Exception {
        Stream<String> tokenStream = TokenStreamUtil.getTokenStreamAsStream(tokenizer);
        Collection<String> expectedTokens = Arrays.asList(testPhrase.split(" "));

        tokenStream.forEach(token -> assertTrue(expectedTokens.contains(token)));
    }

}