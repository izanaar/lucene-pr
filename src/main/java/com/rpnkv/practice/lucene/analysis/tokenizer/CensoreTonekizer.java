package com.rpnkv.practice.lucene.analysis.tokenizer;

import org.apache.lucene.analysis.Tokenizer;

import java.io.IOException;

public class CensoreTonekizer extends Tokenizer{


    @Override
    public boolean incrementToken() throws IOException {

        return false;
    }
}
