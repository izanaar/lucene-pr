package com.izanaar.practice.lia;

import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class IndexingTest {

    private String[] ids = {"1", "2", "3"};

    private String[] unindexed =
            {"Netherlands",
                    "Italy",
                    "Germany"};

    private String[] unstored =
            {"Amsterdam has lots of bridges",
                    "Venice has lots of canals",
                    "Berlin is a city of contrast."};

    private String[] text =
            {"Amsterdam",
                    "Venice",
                    "Berlin"};

    private Directory directory;

    @Before
    public void setUp() throws Exception {
        directory = new RAMDirectory();
        buildIndex();
    }

    private void buildIndex() throws IOException {
        IndexWriter writer = getWriter();

        for (int i = 0; i < ids.length; i++) {
            Document document = new Document();
            document.add(new IntPoint("index", i));
            document.add(new StringField("country", unindexed[i], Field.Store.YES));
            document.add(new TextField("contents", unstored[i], Field.Store.NO));
            document.add(new TextField("city", text[i], Field.Store.NO));
            writer.addDocument(document);
        }
        writer.close();
    }

    private IndexWriter getWriter() throws IOException {
        IndexWriterConfig iwc = new IndexWriterConfig(new WhitespaceAnalyzer());
        return new IndexWriter(directory, iwc);
    }

    @Test
    public void indexWriter() throws Exception {
        IndexWriter writer = getWriter();
        assertEquals(ids.length, writer.numDocs());
    }

    @Test
    public void indexReader() throws Exception {
        IndexReader reader = DirectoryReader.open(directory);
        assertEquals(ids.length, reader.numDocs());
        assertEquals(ids.length, reader.maxDoc());
        reader.close();
    }

    @Test
    public void delete() throws Exception {
        IndexWriter writer = getWriter();
        Term term = new Term("city", "Venice");
        assertEquals(ids.length, writer.numDocs());
        writer.deleteDocuments(term);
        assertTrue(writer.hasDeletions());
        writer.commit();
        assertEquals(ids.length - 1, writer.numDocs());
        writer.close();
    }
}
