package com.rpnkv.practice.lucene.search;

import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class PerFieldAnalyzerTest {

    private CustomizableSearcher searcher;

    private static final String SIMPLE_FIELD_NAME = "simple", STANDARD_FIELD_NAME = "standard";

    @Before
    public void setUp() throws Exception {
        PerFieldAnalyzerWrapper analyzerWrapper = new PerFieldAnalyzerWrapper(
                new SimpleAnalyzer(),
                Collections.singletonMap("standard", new StandardAnalyzer())
        );

        searcher = new CustomizableSearcher(Arrays.asList(SIMPLE_FIELD_NAME, STANDARD_FIELD_NAME), analyzerWrapper);
        String sourceText = "Tempest Keep was merely";
        searcher.addToIndex(Arrays.asList(sourceText, sourceText));
    }

    @Test
    public void testWrapper() throws Exception {
        String queryPhrase = "was";
        Query simpleAnalyzerQuery = new TermQuery(new Term(SIMPLE_FIELD_NAME, queryPhrase)),
                standardAnalyzerQuery = new TermQuery(new Term(STANDARD_FIELD_NAME, queryPhrase));

        assertEquals(1, searcher.searchForCount(simpleAnalyzerQuery));
        assertEquals(0, searcher.searchForCount(standardAnalyzerQuery));
    }
}
