package com.rpnkv.practice.lucene.analysis;

import com.rpnkv.practice.lucene.util.AnalyzerUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;

import java.io.IOException;

/**
 * Outputs tokens, produced by analyzer provided.
 */
class AnalyzerDemo {

    private static final String[] examples = {
            "The quick brown fox jumped over the lazy dog",
            "XY&Z Corporation - xyz@example.com"
    };

    private static final Analyzer[] analyzers = new Analyzer[]{
            new WhitespaceAnalyzer(),
            new SimpleAnalyzer(),
            new StopAnalyzer(),
            new StandardAnalyzer()
    };

    static void analyzeWithDetails(String text, Analyzer ... analyzers) throws IOException{
        System.out.println("Analyzing \"" + text + "\"");

        for (Analyzer analyzer : analyzers) {
            String name = analyzer.getClass().getSimpleName();
            System.out.println(" " + name + ":");
            AnalyzerUtils.displayTokensWithFullInfo(analyzer, text);
            System.out.println();
        }
    }

    static void analyzeWithDetails(String text) throws IOException{
        analyzeWithDetails(text, analyzers);
    }

    static void analyze(String text) throws IOException {
        analyze(text, analyzers);
    }

    static void analyze(String text, Analyzer ... analyzers) throws IOException {
        System.out.println("Analyzing \"" + text + "\"");

        for (Analyzer analyzer : analyzers) {
            String name = analyzer.getClass().getSimpleName();
            System.out.println(" " + name + ":");
            System.out.print("  ");
            AnalyzerUtils.displayTokens(analyzer, text);
            System.out.println("\n");
        }
    }

    static void analyze() throws IOException {
        for(String example: examples)
            analyzeWithDetails(example);
    }

}
