package com.izanaar.practice.lucene.analysis;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.junit.Test;

import java.util.Collections;

public class AnalyzerDemoTest {

    private static final String text = "Tempest keep was merely a setback!";

    @Test
    public void showTokens() throws Exception {
        AnalyzerDemo.analyze(text);
    }

    @Test
    public void showTokensInDetail() throws Exception {
        AnalyzerDemo.analyzeWithDetails(text);
    }

    @Test
    public void standardAnalyzerDetailView() throws Exception {
        AnalyzerDemo.analyzeWithDetails("I'll write my answer to abc@example.com.", new StandardAnalyzer());
    }

    @Test
    public void myStopAnalyzer() throws Exception {
        AnalyzerDemo.analyzeWithDetails(text, new MyStopAnalyzer(Collections.singleton("was")));
    }

    @Test
    public void analyze() throws Exception {
        AnalyzerDemo.analyze();
    }

    @Test
    public void synonymReplacement() throws Exception {
        AnalyzerDemo.analyzeWithDetails("tempest", new SimpleSynonymAnalyser());
    }

}