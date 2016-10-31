package com.izanaar.practice.lucene.tokenize;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TokenizerOperatorTest {

    private TokenizerOperator tokenizerOperator;

    @Before
    public void setUp() throws Exception {
        tokenizerOperator = new TokenizerOperator(new WhitespaceTokenizer());
    }

    @Test
    public void showTokens() throws Exception {
        tokenizerOperator.showTokens("Tempest Keep was merely a setback.");
    }

}