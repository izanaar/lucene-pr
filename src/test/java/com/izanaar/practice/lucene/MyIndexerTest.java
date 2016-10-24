package com.izanaar.practice.lucene;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MyIndexerTest {

    private MyIndexer indexer;

    @Before
    public void setUp() throws Exception {
        indexer = new MyIndexer();
    }

    @Test
    public void indexDocument() throws Exception {

        Document document = new Document();
        document.add(new StringField("title", "Quel'thas's pathetic speech.", Field.Store.YES));
        document.add(new TextField("text", "Don’t look so smug! I know what you’re thinking, " +
                "but Tempest Keep was merely a set back. Did you honestly believe I would trust " +
                "the future to some blind, half-night elf mongrel? Hahahaha… Oh no, no, no, he " +
                "was merely an instrument, a stepping stone to a much larger plan! It has all " +
                "led to this…and this time, you will not interfere!", Field.Store.YES));

        indexer.indexDocument(document);
    }

}