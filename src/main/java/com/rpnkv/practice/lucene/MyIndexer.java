package com.rpnkv.practice.lucene;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;

public class MyIndexer {

    private Directory directory;
    private IndexWriter indexWriter;
    private IndexWriterConfig indexWriterConfig;

    public MyIndexer() throws IOException {
        directory = FSDirectory.open(Constants.INDEX_PATH);
        indexWriterConfig = new IndexWriterConfig(new StandardAnalyzer());
        indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
    }

    public void indexDocument(Document document){
        try {
            indexWriter = new IndexWriter(directory, indexWriterConfig);
            indexWriter.addDocument(document);
            indexWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Indexing has failed.");
        }
    }

}
