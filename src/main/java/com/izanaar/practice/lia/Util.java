package com.izanaar.practice.lia;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Util {

    private Directory directory;
    private IndexWriter writer;

    private Path phrasesPath;

    public Util() throws URISyntaxException {
        phrasesPath = Paths.get(ClassLoader.getSystemResource("phrases.txt").toURI());
    }

    public Directory getPhrasesDirectory(Supplier<Analyzer> analyzerSupplier) {
        directory = new RAMDirectory();

        IndexWriterConfig iwc = new IndexWriterConfig(analyzerSupplier.get());

        try {
            writer = new IndexWriter(directory, iwc);
            Collection<Document> documents = getLines(phrasesPath)
                    .map(this::parseDocument)
                    .collect(Collectors.toList());

            writer.addDocuments(documents);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return directory;
    }

    public IndexWriter getWriter() {
        return writer;
    }

    private Document parseDocument(String line) {
        Document document = new Document();

        String[] fieldsString = line.split("/");
        document.add(new IntPoint("id", Integer.parseInt(fieldsString[0])));
        document.add(new StringField("name", fieldsString[1], Field.Store.NO));
        document.add(new TextField("phrase", fieldsString[2], Field.Store.NO));

        return document;
    }

    private Stream<String> getLines(Path path) throws IOException {
        return Files.lines(path);
    }

    private Stream<Document> getFullDocument(Integer ... id) throws IOException {
        Collection<Integer> ids = Arrays.asList(id);

        return getLines(phrasesPath)
                .map(this::parseDocument)
                .filter(doc -> ids.contains(doc.getField("id").numericValue().intValue()));
    }

    @Override
    protected void finalize() throws Throwable {
        writer.close();
    }
}
