package com.rpnkv.practice.lucene.index;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.SimpleFSDirectory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class IndexReplication {

    private static final String I1_DIR = "ind orig",
            I2_DIR = "ind merged";

    public static void main(String[] args) throws IOException {
        String[] doc1 = "Tempest Keep was merely a setback".split(" "),
                doc2 = "merely back grow shrink".split(" ");

        List<Document> doc1List = arrayToDocuments(doc1),
                doc2List = arrayToDocuments(doc2);

        indexDocuments(doc1List, Paths.get(I1_DIR));
        indexDocuments(doc2List, Paths.get(I2_DIR));

        IndexWriter writer = new IndexWriter(new SimpleFSDirectory(Paths.get(I2_DIR)),
                new IndexWriterConfig(new StandardAnalyzer()));

        writer.addIndexes(new SimpleFSDirectory(Paths.get(I1_DIR)));
        writer.commit();
        writer.close();
        IndexSearcher searcher = new IndexSearcher(DirectoryReader.open(new SimpleFSDirectory(Paths.get(I2_DIR))));
        long count = searcher.count(new TermQuery(new Term("s", "shrink")));
        System.out.println(count);
    }

    private static List<Document> arrayToDocuments(String[] documentsArray) {
        List<Document> documentsList = new ArrayList<>(documentsArray.length);
        for (int i = 0; i < documentsArray.length; i++) {
            Document document = new Document();
            document.add(new IntPoint("i", i));
            document.add(new StringField("s", documentsArray[i], Field.Store.NO));
            documentsList.add(document);
        }
        return documentsList;
    }

    private static void indexDocuments(List<Document> documents, Path indexPath) throws IOException {
        IndexWriterConfig iwc = new IndexWriterConfig(new StandardAnalyzer());
        IndexWriter writer = new IndexWriter(new SimpleFSDirectory(indexPath), iwc);
        writer.addDocuments(documents);
        writer.commit();
        writer.close();
    }

}
