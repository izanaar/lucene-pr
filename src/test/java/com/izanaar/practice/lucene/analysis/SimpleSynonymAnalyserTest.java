package com.izanaar.practice.lucene.analysis;

import com.izanaar.practice.lucene.search.SimpleSearcher;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class SimpleSynonymAnalyserTest {

    private SimpleSearcher searcher;
    private Analyzer analyzer;

    @Before
    public void setUp() throws Exception {
        analyzer = new SimpleSynonymAnalyser(Collections.singletonMap("merely", Arrays.asList("just", "simply")));
        searcher = new SimpleSearcher(analyzer);
    }

    @Test
    public void synonymSearch() throws Exception {
        searcher.addToIndex("Tempest keep was merely a setback.");

        assertCount(1, "merely");
        assertCount(1, "just");
        assertCount(1, "simply");
        assertCount(0, "naxxadar");
    }

    private void assertCount(int expectedCount, String word) throws IOException {
        Query query = new TermQuery(new Term(searcher.getFieldName(), word));
        assertEquals(expectedCount, searcher.search(query).totalHits);
    }
}