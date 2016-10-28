package com.izanaar.practice.lucene.terms;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.TextField;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TermReaderTest{

    private int id;

    @Test
    public void showTermsFromPhrases() throws Exception {
        new TermReader().showTerms();
    }

    @Test
    public void showTermsFromCustomDocuments() throws Exception {
        List<Document> documents = new ArrayList<>();
        id = 0;

        documents.add(createTestCustomDocument("fairfax cat dog whisper"));
        documents.add(createTestCustomDocument("Nunc condimentum lorem libero,"));
        documents.add(createTestCustomDocument("Sed metus fairfax lectus, fairfax fringilla sit"));

        TermReader termReader = new TermReader(documents, new StandardAnalyzer());
        termReader.showTerms("content");

    }

    private Document createTestCustomDocument(String content){
        Document document = new Document();
        document.add(new IntPoint("id", id++));
        document.add(new TextField("content", content, Field.Store.YES));
        return document;
    }


}