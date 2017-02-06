package com.rpnkv.practice.lucene.analysis;

import com.rpnkv.practice.lucene.filter.ConcatPayloadFilter;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.PayloadAttribute;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PayloadAnalyzerTest {

    @Test
    public void payloadAttribute() throws Exception {
        Analyzer payloadAnalyzer = new PayloadAnalyzer(ConcatPayloadFilter::new);

        TokenStream tokenStream = payloadAnalyzer.tokenStream("field", "some test text");

        PayloadAttribute payloadAttribute = tokenStream.addAttribute(PayloadAttribute.class);
        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);

        try {
            tokenStream.reset();

            while (tokenStream.incrementToken()) {
                System.out.println(payloadAttribute.getPayload().utf8ToString());
                assertEquals(charTermAttribute.toString().concat("_pld_appended"), payloadAttribute.getPayload().utf8ToString());
            }
        } finally {
            tokenStream.close();
        }
    }
}