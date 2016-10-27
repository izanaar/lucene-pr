package com.izanaar.practice.lucene.cache;

import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.DoubleDocValuesField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.LeafReader;
import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.queries.function.ValueSource;
import org.apache.lucene.queries.function.valuesource.DoubleFieldSource;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.stream.Stream;

class RandomDoubleDoc {

    private Directory directory;
    private final static String DEFAULT_DIR_NAME = "rand_double_doc_index";

    private Path dirPath;

    RandomDoubleDoc(int mRecords) throws IOException {
        IndexWriter writer = openDirectory();
        fillDirectory(mRecords, writer);
        writer.close();
    }

    private IndexWriter openDirectory() throws IOException {
        dirPath = Files.createTempDirectory(DEFAULT_DIR_NAME);
        directory = FSDirectory.open(dirPath);

        IndexWriterConfig iwc = new IndexWriterConfig(new SimpleAnalyzer());
        return new IndexWriter(directory, iwc);
    }

    private void fillDirectory(int mRecords, IndexWriter writer) {
        Random rnd = new Random();
        Stream.generate(rnd::nextDouble).limit(mRecords * 1_000_000)
                .map(this::wrapToDocument)
                .forEach(doc ->{
                    try {
                        writer.addDocument(doc);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    private Document wrapToDocument(Double aDouble) {
        Document document = new Document();
        document.add(new DoubleDocValuesField("content", aDouble));
        return document;
    }

    void eraseDirectory() throws IOException {
        Files.newDirectoryStream(dirPath)
                .forEach(path -> {
                    if(path.toFile().isFile()){
                        deleteFile(path);
                    }
                });
        deleteFile(dirPath);
    }

    private double[] getValues(){
        ValueSource valueSource = new DoubleFieldSource("content");
        //LeafReaderContext leafReaderContext = ;
        //valueSource.getValues(,leafReaderContext);
        return null;
    }

    private void deleteFile(Path file) {
        try {
            Files.delete(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


