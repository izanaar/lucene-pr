package com.rpnkv.practice.lucene.util;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class TokenStreamUtil {

    public static Iterator<String> getTokenStreamIterator(TokenStream tokenStream) throws IOException {
        CharTermAttribute termAttribute = tokenStream.addAttribute(CharTermAttribute.class);
        tokenStream.reset();
        return new Iterator<String>() {
            @Override
            public boolean hasNext() {
                try {
                    return tokenStream.incrementToken();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public String next() {
                return termAttribute.toString();
            }
        };
    }

    public static Stream<String> getTokenStreamAsStream(TokenStream tokenStream) throws IOException {
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(
                        getTokenStreamIterator(tokenStream), Spliterator.ORDERED
                ), false
        );
    }

}
