package com.rpnkv.practice.lucene.analysis;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseTokenizer;

import java.util.function.Function;

public class PayloadAnalyzer extends Analyzer {

    private Function<TokenStream, TokenFilter> filterConstructor;

    public PayloadAnalyzer(Function<TokenStream, TokenFilter> filterConstructor) {
        this.filterConstructor = filterConstructor;
    }

    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        Tokenizer source = new LowerCaseTokenizer();
        return new TokenStreamComponents(source, filterConstructor.apply(source));
    }
}
