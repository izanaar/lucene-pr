package com.rpnkv.practice.lucene.util;

import org.apache.lucene.search.TopDocs;

import static org.junit.Assert.assertEquals;

public class AssertUtil {

    public static void assertCount(TopDocs result, int expectedCount) {
        assertEquals("Actual count (" + result.scoreDocs.length + ") does not match expected (" + expectedCount + ").",
                result.scoreDocs.length, expectedCount);
    }

}
