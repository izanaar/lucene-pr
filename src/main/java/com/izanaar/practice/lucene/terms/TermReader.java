package com.izanaar.practice.lucene.terms;

import com.izanaar.practice.lucene.analysis.PayloadAnalyzer;
import com.izanaar.practice.lucene.util.PhrasesUtil;
import com.izanaar.practice.lucene.util.Util;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.tokenattributes.PayloadAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.*;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.AttributeSource;
import org.apache.lucene.util.BytesRef;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;

/**
 * Reads fills directory with index, produced by default or provided analyzer by documents, taken from "phrases.txt"
 * or provided and outputs default index meta information to System.out.
 */
public class TermReader {

    private Directory directory;

    /**
     * Fills directory with index, calculated by {@link PayloadAnalyzer} from "phrases.txt" resource's content.
     *
     * @throws IOException        thrown by {@link PhrasesUtil#loadPhrasesAsDocuments()} while performing I/O operations by
     *                            {@link Directory}
     * @throws URISyntaxException thrown by Path.toURI() while trying to get {@link java.nio.file.Path} to "phrases.txt"
     */
    TermReader() throws IOException, URISyntaxException {
        directory = new RAMDirectory();
        Util.indexIntoDirectory(PhrasesUtil.loadPhrasesAsDocuments(), directory, new PayloadAnalyzer());
    }

    /**
     * Fills {@link Directory} with index, provided by collection of documents & analyzer.
     *
     * @param documents documents, to create index from
     * @param analyzer  analyzer, to produce index
     * @throws IOException thrown while performing I/O operations to directory
     */
    TermReader(Collection<Document> documents, Analyzer analyzer) throws IOException {
        directory = new RAMDirectory();
        Util.indexIntoDirectory(documents, directory, analyzer);
    }

    void showTerms() throws IOException {
        showTerms(PhrasesUtil.getFieldNames()[2]);
    }

    void showTerms(String fieldName) throws IOException {
        IndexReader reader = new IndexSearcher(DirectoryReader.open(directory)).getIndexReader();
        Terms terms = MultiFields.getTerms(reader, fieldName);

        TermsEnum termsEnum = terms.iterator();
        BytesRef bytesRef = termsEnum.next();
        while (bytesRef != null) {
            System.out.print(" token: '" + bytesRef.utf8ToString());
            System.out.print("' docFreq: '" + termsEnum.docFreq());
            System.out.println("' totalTermFreq: '" + termsEnum.totalTermFreq() + "'");

            AttributeSource attributeSource = termsEnum.attributes();
            PayloadAttribute payloadAttribute = attributeSource.addAttribute(PayloadAttribute.class);

            bytesRef = termsEnum.next();
        }
    }
}
