package com.rpnkv.practice.lucene.util;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Util {

    public static void indexIntoDirectory(Collection<Document> documents, Directory directory, Analyzer analyzer) throws IOException {
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
        IndexWriter writer = new IndexWriter(directory, iwc);
        writer.addDocuments(documents);
        writer.close();
    }


}
