package com.izanaar.practice.lucene.tokenize;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;
import java.io.StringReader;

public class TokenizerOperator {

    private Tokenizer tokenizer;

    public TokenizerOperator(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public void showTokens(String text) throws IOException {
        tokenizer.setReader(new StringReader(text));
        try {
            tokenizer.reset();
            CharTermAttribute charTermAttribute = tokenizer.addAttribute(CharTermAttribute.class);

            while (tokenizer.incrementToken()){
                System.out.println(charTermAttribute);
            }
        }finally {
            tokenizer.close();
        }
    }

    public void setTokenizer(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }
}
