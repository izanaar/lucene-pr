package com.izanaar.practice.lucene.util;

import org.apache.lucene.document.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class PhrasesUtil {

    private static final String SPLITTER = "/", //file line value's splitter //TODO ask linguist about grammar
            FILE_NAME = "phrases.txt";          //file name

    private static final Field[] DEFAULT_FIELD_TYPES = new Field[]{
            // TODO: 26.10.16 provide custom field type functionality
    };

    private static final String[] FIELD_NAMES = new String[]{"id", "character", "phrase"};

    /**
     * Loads epic WoW character phrases from this.FILE_NAME, located within project resources.
     *
     * @return List of documents, parsed from text file.
     * @throws IOException in case of file absence.
     * @throws URISyntaxException in case of URL parse failure
     */
    public static List<Document> loadPhrasesAsDocuments() throws URISyntaxException, IOException {
        Path phrasesPath = Paths.get(ClassLoader.getSystemResource("phrases.txt").toURI());

        return Files.lines(phrasesPath)
                .map(PhrasesUtil::parsePhraseLine)
                .collect(Collectors.toList());
    }

    /**
     * Parses phrase's string representation into Lucene's Document.
     *
     * @param phraseLine string to parse.
     * @return parsed document.
     */
    private static Document parsePhraseLine(String phraseLine) {
        String[] parts = phraseLine.split(SPLITTER);

        Document document = new Document();
        document.add(new IntPoint(FIELD_NAMES[0], Integer.parseInt(parts[0])));
        document.add(new StringField(FIELD_NAMES[1], parts[1], Field.Store.YES));
        document.add(new TextField(FIELD_NAMES[2], parts[2], Field.Store.YES));

        return document;
    }

    public static String[] getFieldNames() {
        return FIELD_NAMES;
    }
}
