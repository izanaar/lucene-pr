package com.izanaar.practice.lucene.analysis;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseTokenizer;
import org.apache.lucene.analysis.synonym.SynonymFilter;
import org.apache.lucene.analysis.synonym.SynonymMap;
import org.apache.lucene.util.CharsRef;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class SimpleSynonymAnalyser extends Analyzer {

    private SynonymMap synonymMap;

    public SimpleSynonymAnalyser() throws IOException {
        SynonymMap.Builder builder = new SynonymMap.Builder(true);
        addTo(builder, "tempest", Collections.singletonList("keep"));
        synonymMap = builder.build();
    }

    public SimpleSynonymAnalyser(Map<String, Collection<String>> synonyms) throws IOException {
        SynonymMap.Builder builder = new SynonymMap.Builder(true);
        synonyms.keySet().forEach(origin -> addTo(builder, origin, synonyms.get(origin)));
        synonymMap = builder.build();
    }

    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        Tokenizer source = new LowerCaseTokenizer();
        return new TokenStreamComponents(source, new SynonymFilter(source, synonymMap, false));
    }

    private void addTo(SynonymMap.Builder builder, String origin, Collection<String> synonyms) {
        synonyms.forEach(synonym -> builder.add(new CharsRef(origin), new CharsRef(synonym), true));
    }
}
