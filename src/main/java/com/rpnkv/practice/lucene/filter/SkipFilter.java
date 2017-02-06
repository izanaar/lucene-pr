package com.rpnkv.practice.lucene.filter;

import org.apache.lucene.analysis.FilteringTokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;
import java.util.Collection;

public class SkipFilter extends FilteringTokenFilter{

    private final Collection<String> wordsToSkip;
    private final CharTermAttribute charTermAttribute;

    /**
     * Create a new {@link FilteringTokenFilter}.
     *
     * @param in the {@link TokenStream} to consume
     */
    public SkipFilter(TokenStream in, Collection<String> wordsToSkip) {
        super(in);
        this.wordsToSkip = wordsToSkip;
        charTermAttribute = in.addAttribute(CharTermAttribute.class);
    }

    @Override
    protected boolean accept() throws IOException {
        return !wordsToSkip.contains(charTermAttribute.toString());
    }
}
