package com.rpnkv.practice.lucene.search;

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
import java.util.List;

public class CustomizableSearcher {

    private Directory directory;
    private Analyzer analyzer;
    private List<String> fieldNames;

    public CustomizableSearcher(List<String> fieldNames, Analyzer analyzer) {
        this.fieldNames = fieldNames;
        this.analyzer = analyzer;
        directory = new RAMDirectory();
    }

    public void addToIndex(List<String> values) throws IOException {
        if (values.size() != fieldNames.size())
            throw new RuntimeException("Values list length does not match required!");

        Document document = new Document();
        for (int i = 0; i < values.size(); i++)
            document.add(new TextField(fieldNames.get(i), values.get(i), Field.Store.YES));

        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
        IndexWriter indexWriter = new IndexWriter(directory, iwc);
        indexWriter.addDocument(document);
        indexWriter.close();
    }

    public TopDocs searchForDocs(Query query) throws IOException {
        DirectoryReader directoryReader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(directoryReader);
        TopDocs searchResult = searcher.search(query, searcher.count(query));
        directoryReader.close();
        return searchResult;
    }

    public int searchForCount(Query query) throws IOException{
        DirectoryReader directoryReader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(directoryReader);
        int count = searcher.count(query);
        directoryReader.close();
        return count;
    }

    public List<String> getFieldNames() {
        return fieldNames;
    }

    public String getFieldName(int fieldIndex) {
        return fieldNames.get(fieldIndex);
    }
}
