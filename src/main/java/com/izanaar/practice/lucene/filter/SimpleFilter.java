package com.izanaar.practice.lucene.filter;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;

import java.io.IOException;

public class SimpleFilter extends TokenFilter {
    /**
     * Construct a token stream filtering the given input.
     *
     * @param input
     */
    protected SimpleFilter(TokenStream input) {
        super(input);
    }

    @Override
    public boolean incrementToken() throws IOException {
        return false;
    }
}
