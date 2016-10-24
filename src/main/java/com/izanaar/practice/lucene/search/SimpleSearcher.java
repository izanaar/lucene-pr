package com.izanaar.practice.lucene.search;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

import java.io.IOException;

public class SimpleSearcher {

    private Directory directory;
    private static final String fieldName = "content";
    private Analyzer analyzer;

    public SimpleSearcher(Analyzer analyzer) {
        this.analyzer = analyzer;
        directory = new RAMDirectory();
    }

    public void addToIndex(String text) throws IOException {
        Document document = new Document();
        document.add(new TextField(fieldName, text, Field.Store.YES));
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
        IndexWriter indexWriter = new IndexWriter(directory, iwc);
        indexWriter.addDocument(document);
        indexWriter.close();
    }

    public TopDocs search(Query query) throws IOException {
        DirectoryReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);
        TopDocs result = searcher.search(query, 10);
        reader.close();
        return result;
    }

    public String getFieldName() {
        return fieldName;
    }
}
