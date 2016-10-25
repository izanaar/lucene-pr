package com.izanaar.practice.lucene.analysis;

import com.izanaar.practice.lucene.util.AnalyzerUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.charfilter.MappingCharFilter;
import org.apache.lucene.analysis.charfilter.NormalizeCharMap;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;

public class MappingCharFilterTest {

    private TokenStream stream;
    private final static String SOURCE_TEXT = "Tempest Keep was merely a setback";

    @Before
    public void setUp() throws Exception {
        NormalizeCharMap.Builder charMapBuilder = new NormalizeCharMap.Builder();
        charMapBuilder.add("merely a setback", "epic fail");

        Analyzer analyzer = new SimpleAnalyzer();
        stream = analyzer.tokenStream("a", new MappingCharFilter(charMapBuilder.build(), new StringReader(SOURCE_TEXT)));
    }

    @Test
    public void show() throws Exception {
        AnalyzerUtils.showTokenWords(stream);
    }
}
