package com.izanaar.practice.lia;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.function.Supplier;

import static org.junit.Assert.*;

public class SearchTest {

    private Directory directory;
    private IndexSearcher searcher;

    private Util util;

    @Before
    public void setUp() throws Exception {
        util = new Util();
        initWithNewAnalyser(StandardAnalyzer::new);
    }

    private void initWithNewAnalyser(Supplier<Analyzer> analyzerSupplier) throws IOException {
        directory = util.getPhrasesDirectory(analyzerSupplier);
        searcher = new IndexSearcher(DirectoryReader.open(util.getWriter()));
    }

    @Test
    public void whiteSpaceAnalyzerStopWord() throws Exception {
        initWithNewAnalyser(WhitespaceAnalyzer::new);

        Term term = new Term("phrase", "a");
        Query query = new TermQuery(term);

        assertTrue(searcher.count(query) > 0);
    }

    @Test
    public void whiteSpaceUpperCase() throws Exception {
        initWithNewAnalyser(WhitespaceAnalyzer::new);

        Term term = new Term("phrase", "Mightiest");
        Query query = new TermQuery(term);

        assertFalse(searcher.count(query) > 0);
    }

    @Test
    public void whiteSpaceAnalyzerStandard() throws Exception {
        initWithNewAnalyser(StandardAnalyzer::new);

        Term term = new Term("phrase", "a");
        Query query = new TermQuery(term);

        assertTrue(searcher.count(query) == 0);
    }

    @Test
    public void queryParser() throws Exception {
        QueryParser whiteSpaceParser = new QueryParser("phrase", new WhitespaceAnalyzer()),
                standardParser = new QueryParser("phrase", new StandardAnalyzer());

        String termString = "mightest";

        Query whiteSpaceQuery = whiteSpaceParser.parse(termString),
                standardQuery = standardParser.parse(termString);

        initWithNewAnalyser(StandardAnalyzer::new);

        assertFalse(searcher.count(whiteSpaceQuery) > 0);
        assertTrue(searcher.count(standardQuery) > 0);
    }

    @Test
    public void score() throws Exception {
        initWithNewAnalyser(StandardAnalyzer::new);

        QueryParser parser = new QueryParser("phrase", new StandardAnalyzer());

        Query query = parser.parse("keep OR setback");

        TopDocs docs = searcher.search(query, 10);
        ScoreDoc[] scoreDocs = docs.scoreDocs;

        for (ScoreDoc scoreDoc : scoreDocs) {
            Document document = searcher.doc(scoreDoc.doc);
            System.out.println("ID: " + document.getField("id").stringValue() + " score: " + scoreDoc.score + ".");
            System.out.println(searcher.explain(query, scoreDoc.doc));
            System.out.println();
        }
    }

    @Test
    public void termRangeQuery() throws Exception {
        initWithNewAnalyser(StandardAnalyzer::new);

        Query query = TermRangeQuery.newStringRange("name", "A", "M", true, true);

        TopDocs docs = searcher.search(query, 10);
        System.out.println(docs.totalHits);
    }


    @Test
    public void booleanQuery() throws Exception {
        initWithNewAnalyser(StandardAnalyzer::new);

        Term term1 = new Term("phrase", "merely"),
                term2 = new Term("phrase", "setback"),
                term3 = new Term("phrase", "naxxanar");


        Query query = new BooleanQuery.Builder()
                .add(new BooleanClause(new TermQuery(term1), BooleanClause.Occur.SHOULD))
                .add(new BooleanClause(new TermQuery(term2), BooleanClause.Occur.SHOULD))
                .add(new BooleanClause(new TermQuery(term3), BooleanClause.Occur.MUST_NOT))
                .build();

        TopDocs topDocs = searcher.search(query, 10);
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;

        System.out.println(query.toString("id"));

        assertEquals(1, scoreDocs.length);

        assertEquals(searcher.search(IntPoint.newExactQuery("id", 1), 1).scoreDocs[0].doc, scoreDocs[0].doc);
    }

    @Test
    public void checkTermExistence() throws Exception {
        assertTrue(searcher.count(new TermQuery(new Term("phrase", "was"))) > 0);
    }

    @Test
    public void rangeQuery() throws Exception {
        initWithNewAnalyser(WhitespaceAnalyzer::new);
        assertTrue(matched("Tempest Keep was merely a setback.".split(" "), 1));
        assertFalse(matched("Tempest setback.".split(" "), 1));
        assertTrue(matched("Tempest setback.".split(" "), 4));
        assertTrue(matched("Tempest Keep", 0));

        assertTrue(matched("Keep Tempest", 2));
    }

    @Test
    public void wildCardQuery() throws Exception {
        Query query = new WildcardQuery(new Term("phrase","m?rely"));

        int hits = searcher.search(query, 10).totalHits;
        System.out.println(hits);

        assertTrue(hits > 0);
    }

    private boolean matched(String phrase, int slop) throws IOException {
        return matched(phrase.split(" "), slop);
    }

    private boolean matched(String[] phrase, int slop) throws IOException {
        PhraseQuery query = new PhraseQuery(slop, "phrase", phrase);

        TopDocs matches = searcher.search(query, 10);
        return matches.totalHits > 0;
    }

    @After
    public void tearDown() throws Exception {
        directory.close();
        util = null;
    }
}
