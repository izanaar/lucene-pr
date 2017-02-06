package com.rpnkv.practice.lucene.search;

import com.rpnkv.practice.lucene.util.AssertUtil;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class PhraseSlopQueryTest {

    private SimpleSearcher searcher;
    private final String field = "alpha bravo charlie delta echo foxtrot golf hotel india juliet kilo lima mike november";

    @Before
    public void setUp() throws Exception {
        searcher = new SimpleSearcher(new SimpleAnalyzer());
        searcher.addToIndex(field);
    }

    @Test
    public void phraseSlopQuery() throws Exception {
        String[] terms = new String[]{"alpha", "delta"};
        AssertUtil.assertCount(searcher.search(buildQuery(Arrays.asList(terms), 1)), 0);
        AssertUtil.assertCount(searcher.search(buildQuery(Arrays.asList(terms), 2)), 1);
    }

    @Test
    public void threeWordsSlopQuery() throws Exception {
        List<String> terms1 = Arrays.asList("alpha", "charlie", "echo"),
                terms2 = Arrays.asList("alpha", "charlie", "foxtrot"),
                terms3 = Arrays.asList("", "", "");

        Query query = buildQuery(terms1, 2);
        System.out.println(searcher.search(query).totalHits);
    }

    @Test
    public void singleBoolArg() throws Exception {
        Query query = new BooleanQuery.Builder()
                .add(new MatchAllDocsQuery(), BooleanClause.Occur.MUST)
                .add(new TermQuery(new Term(searcher.getFieldName(), "charlie")), BooleanClause.Occur.MUST_NOT)
                .build();

        AssertUtil.assertCount(searcher.search(query), 0);

        searcher.addToIndex("alpla bravo delta echo");

        AssertUtil.assertCount(searcher.search(query), 1);
    }

    private Query buildQuery(Iterable<String> terms, int slop) {
        PhraseQuery.Builder builder = new PhraseQuery.Builder();
        final String field = searcher.getFieldName();

        terms.forEach(term -> builder.add(new Term(field, term)));
        builder.setSlop(slop);

        return builder.build();
    }
}
