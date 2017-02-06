package com.rpnkv.practice.lucene.util;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.TextField;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class DocumentGenUtilTest {

    @Test
    public void createDocument() throws Exception {
        List values = Arrays.asList(2, "test");
        List<Class> fieldTypes = Arrays.asList(IntPoint.class, TextField.class);
        List<String> fieldNames = Arrays.asList("id", "content");

        Document document = DocumentGenUtil.createDocument(values, fieldTypes, fieldNames);
    }

}