package com.rpnkv.practice.lucene.filter;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.PayloadAttribute;
import org.apache.lucene.util.BytesRef;

import java.io.IOException;

public class ConcatPayloadFilter extends TokenFilter {
    /**
     * Construct a token stream filtering the given input.
     *
     * @param input
     */

    private PayloadAttribute payloadAttribute;
    private CharTermAttribute charTermAttribute;

    private String appendableString = "_pld_appended";

    public ConcatPayloadFilter(TokenStream input) {
        super(input);
        payloadAttribute = input.addAttribute(PayloadAttribute.class);
        charTermAttribute = input.addAttribute(CharTermAttribute.class);
    }

    public ConcatPayloadFilter(TokenStream input, String appendableString) {
        super(input);
        payloadAttribute = input.addAttribute(PayloadAttribute.class);
        charTermAttribute = input.addAttribute(CharTermAttribute.class);
        this.appendableString = appendableString;
    }

    @Override
    public final boolean incrementToken() throws IOException {
        if (input.incrementToken()) {
            BytesRef payloadBytes = new BytesRef(charTermAttribute.toString().concat(appendableString));
            payloadAttribute.setPayload(payloadBytes);
            return true;
        } else {
            return false;
        }
    }
}
