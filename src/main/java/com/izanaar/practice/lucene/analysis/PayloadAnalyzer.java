package com.izanaar.practice.lucene.analysis;

import com.izanaar.practice.lucene.filter.PayloadFilter;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseTokenizer;

public class PayloadAnalyzer extends Analyzer {


    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        Tokenizer source = new LowerCaseTokenizer();
        return new TokenStreamComponents(source, new PayloadFilter(source));
    }
}
