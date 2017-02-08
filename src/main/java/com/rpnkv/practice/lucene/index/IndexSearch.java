package com.rpnkv.practice.lucene.index;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.SimpleFSDirectory;

import java.io.IOException;
import java.nio.file.Paths;

public class IndexSearch {

    public static void main(String[] args) throws IOException {
        IndexSearcher searcher = new IndexSearcher(DirectoryReader.open(new SimpleFSDirectory(Paths.get("0"))));
        //System.out.println(searcher.count(new TermQuery(new Term("alpha","affa"))));
        System.out.println(searcher.count(new TermQuery(new Term("alpha", "affa"))));

    }


}
