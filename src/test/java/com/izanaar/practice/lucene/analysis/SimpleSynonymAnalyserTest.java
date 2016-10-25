package com.izanaar.practice.lucene.analysis;

import com.izanaar.practice.lucene.search.SimpleSearcher;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class SimpleSynonymAnalyserTest {

    private SimpleSearcher synSearcher,
            smplSearcher;

    private Analyzer analyzer;

    private String fieldName,
            searchText = "Tempest keep was merely a setback.";


    @Before
    public void setUp() throws Exception {
        analyzer = new SimpleSynonymAnalyser(Collections.singletonMap("merely", Arrays.asList("just", "simply")));

        synSearcher = new SimpleSearcher(analyzer);
        smplSearcher = new SimpleSearcher(new SimpleAnalyzer());

        fieldName = synSearcher.getFieldName();
        AnalyzerDemo.analyze(searchText, analyzer);

        synSearcher.addToIndex(searchText);
        smplSearcher.addToIndex(searchText);
    }

    @Test
    public void synonymSearch() throws Exception {
        assertHitCount(1, "merely");
        assertHitCount(1, "just");
        assertHitCount(1, "simply");
        assertHitCount(0, "naxxadar");

        Query phraseQuery = new PhraseQuery.Builder()
                .add(constructTerm("was"))
                .add(constructTerm("just"))
                .build();

        assertEquals(1, synSearcher.search(phraseQuery).totalHits);
    }

    @Test
    public void synonymQuerySearch() throws Exception {
        QueryParser stdParser = new QueryParser(fieldName, new SimpleAnalyzer()),
                synParser = new QueryParser(fieldName, analyzer);

        String queryString = "\"was merely\"";

        Query stdQuery = stdParser.parse(queryString),
                synQuery = synParser.parse(queryString);

        assertEquals(1, smplSearcher.search(synQuery).totalHits);
        assertEquals(1, synSearcher.search(stdQuery).totalHits);

        System.out.println("std query: " + stdQuery.toString());
        System.out.println("sym query: " + synQuery.toString());
    }

    private void assertHitCount(int expectedCount, String word) throws IOException {
        Query query = new TermQuery(new Term(synSearcher.getFieldName(), word));
        assertEquals(expectedCount, synSearcher.search(query).totalHits);
    }

    private Term constructTerm(String text) {
        return new Term(fieldName, text);
    }
}