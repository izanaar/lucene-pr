package com.izanaar.practice.lucene.terms;

import com.izanaar.practice.lucene.analysis.PayloadAnalyzer;
import com.izanaar.practice.lucene.util.PhrasesUtil;
import com.izanaar.practice.lucene.util.Util;
import org.apache.lucene.analysis.tokenattributes.PayloadAttribute;
import org.apache.lucene.index.*;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.AttributeSource;
import org.apache.lucene.util.BytesRef;

import java.io.IOException;

class TermReader {

    private Directory directory;

    TermReader() throws Exception {
        directory = new RAMDirectory();
        Util.indexIntoDirectory(PhrasesUtil.loadPhrasesAsDocuments(), directory, new PayloadAnalyzer());
    }

    void showTerms() throws IOException {
        IndexReader reader = new IndexSearcher(DirectoryReader.open(directory)).getIndexReader();
        Terms terms = MultiFields.getTerms(reader, PhrasesUtil.getFieldNames()[2]);

        TermsEnum termsEnum = terms.iterator();
        BytesRef  bytesRef = termsEnum.next();
        while(bytesRef  != null){
            System.out.print(" token: '" + bytesRef.utf8ToString());
            System.out.print("' docFreq: '" + termsEnum.docFreq());
            System.out.println("' totalTermFreq: '" + termsEnum.totalTermFreq() + "'");

            AttributeSource attributeSource = termsEnum.attributes();
            PayloadAttribute payloadAttribute = attributeSource.addAttribute(PayloadAttribute.class);

            bytesRef = termsEnum.next();
        }
    }
}
