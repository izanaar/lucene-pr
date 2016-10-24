package com.izanaar.practice.lucene.analysis;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseTokenizer;
import org.apache.lucene.analysis.core.StopFilter;

import java.util.Collection;
import java.util.Collections;

public class MyStopAnalyzer extends Analyzer {

    private CharArraySet stopWords;

    public MyStopAnalyzer() {
        stopWords = new CharArraySet(Collections.singletonList("was"), false);
    }

    public MyStopAnalyzer(Collection<String> stopWords) {
        this.stopWords = new CharArraySet(stopWords, true);
    }

    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        Tokenizer source = new LowerCaseTokenizer();
        return new TokenStreamComponents(source, new StopFilter(source, stopWords));
    }


}
