package com.izanaar.practice.lucene;

import org.apache.lucene.analysis.util.CharFilterFactory;
import org.junit.Test;

public class TokenStream {

    @Test
    public void ts() throws Exception {
        CharFilterFactory.availableCharFilters().forEach(System.out::println);
    }
}
