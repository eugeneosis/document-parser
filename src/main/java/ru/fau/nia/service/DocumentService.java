package ru.fau.nia.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class DocumentService {

    public static final String FILE_NAME = "Список акредитованных лиц.txt";

    @Value("${inputFolder.path}")
    private String folderPath;

    @Value("${outputFile.path}")
    private String outputFilePath;

    @PostConstruct
    public void parseFiles() {
        parseFiles(new File(folderPath));
    }

    @SneakyThrows
    public void parseFiles(File dir) {
        File[] files = dir.listFiles();

        for (File file : Objects.requireNonNull(files)) {
            if (file.isDirectory()) {

                parseFiles(file);
            } else {
                if (file.getName().endsWith((".pdf"))) {
                    System.out.println(file.getCanonicalPath());
                    extractText(file);
                }
            }
        }
    }

    @SneakyThrows
    private void extractText(File file) {
        createDir();
        PDDocument document = PDDocument.load(file);
        PDFTextStripper pdfStripper = new PDFTextStripper();
        String textFromDocument = pdfStripper.getText(document);
        System.out.println(textFromDocument);
        printToFile(textFromDocument);
        document.close();
    }

    @Value("${outputFile.path}")
    @SneakyThrows
    private void printToFile(String textFromDocument) {
        File file = new File(FILE_NAME);
        FileWriter writer = new FileWriter(outputFilePath + file);
        Optional<String> first = textFromDocument.lines()
                .filter(f -> f.contains("RA.RU.21ЛИ01")) //write regex
                .filter(f -> f.contains("ГОСТ"))
                .findFirst();
        if (first.isPresent()) {
            writer.write(String.valueOf(first));
            writer.close();
        }
    }

    @Value("${outputFile.path}")
    @SneakyThrows
    private void createDir() {
        Files.createDirectories(Path.of(outputFilePath));
    }
}