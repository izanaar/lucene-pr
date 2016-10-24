package com.izanaar.practice.lucene.util;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.*;

import java.io.IOException;
import java.io.StringReader;

public class AnalyzerUtils {

    public static void displayTokens(Analyzer analyzer, String text) throws IOException {
        displayTokens(analyzer.tokenStream("contents", new StringReader(text)));
    }

    public static void displayTokens(TokenStream stream) throws IOException {
        CharTermAttribute term = stream.addAttribute(CharTermAttribute.class);

        stream.reset();
        while (stream.incrementToken()) {
            System.out.print("[" + term.toString() + "] ");
        }
        stream.close();
    }

    public static void displayTokensWithFullInfo(Analyzer analyzer, String text) throws IOException {
        displayTokensWithFullInfo(analyzer.tokenStream("content", new StringReader(text)));
    }

    public static void displayTokensWithFullInfo(TokenStream stream) throws IOException {
        CharTermAttribute charAttribute = stream.addAttribute(CharTermAttribute.class);
        OffsetAttribute offsetAttribute = stream.addAttribute(OffsetAttribute.class);
        TypeAttribute typeAttribute = stream.addAttribute(TypeAttribute.class);
        PositionIncrementAttribute positionIncrementAttribute = stream.addAttribute(PositionIncrementAttribute.class);

        int counter = 1;

        stream.reset();
        while (stream.incrementToken()) {
            System.out.print(counter++ + ": ");
            System.out.print(offsetAttribute.startOffset() + "->" + offsetAttribute.endOffset() + " \"");
            System.out.print(charAttribute.toString());

            System.out.print("\" (" + typeAttribute.type() + ")");
            System.out.println(" pos increment: " + positionIncrementAttribute.getPositionIncrement());

            System.out.println();
        }

        stream.close();
    }
}
