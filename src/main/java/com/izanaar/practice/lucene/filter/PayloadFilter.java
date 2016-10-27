package com.izanaar.practice.lucene.filter;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.PayloadAttribute;
import org.apache.lucene.util.BytesRef;

import java.io.IOException;

public class PayloadFilter extends TokenFilter {
    /**
     * Construct a token stream filtering the given input.
     *
     * @param input
     */

    private PayloadAttribute payloadAttribute;
    private int counter = 2;

    public PayloadFilter(TokenStream input) {
        super(input);
        payloadAttribute = input.addAttribute(PayloadAttribute.class);
    }

    @Override
    public final boolean incrementToken() throws IOException {
        if (input.incrementToken()) {
            payloadAttribute.setPayload(new BytesRef("F117"));
            return true;
        } else {
            return false;
        }
    }
}
