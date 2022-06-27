package ru.fau.nia.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDDocumentNameDictionary;
import org.apache.pdfbox.pdmodel.PDEmbeddedFilesNameTreeNode;
import org.apache.pdfbox.pdmodel.common.filespecification.PDComplexFileSpecification;
import org.apache.pdfbox.pdmodel.common.filespecification.PDEmbeddedFile;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DocumentService {

    Pattern alPattern = Pattern.compile("RA\\.RU\\.", Pattern.CASE_INSENSITIVE);
    Pattern gostPattern = Pattern.compile("^.*\\sГОСТ\\s.*0.*$", Pattern.CASE_INSENSITIVE);
    //Pattern gostPattern = Pattern.compile(", ", Pattern.CASE_INSENSITIVE);

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
        File fileText = new File(FILE_NAME);
        Set<String> alList = new HashSet<>();
        for (File file : Objects.requireNonNull(files)) {
            if (file.isDirectory()) {
                parseFiles(file);
            } else {
                if (file.getName().endsWith((".pdf"))) {
                    System.out.println(file.getCanonicalPath());
                    alList.add(extractText(file));
                    FileWriter writer = new FileWriter(outputFilePath + fileText);
                    if (!alList.isEmpty()) {
                        writer.write(alList.toString());
                        writer.close();
                    }
                }
            }
        }
    }

    @SneakyThrows
    private String extractText(File file) {
        createDir();
        PDDocument document = PDDocument.load(file);
        PDFTextStripper pdfStripper = new PDFTextStripper();
        String textFromDocument = pdfStripper.getText(document);

        try{
            PDDocumentCatalog catalog = document.getDocumentCatalog();
            PDDocumentNameDictionary names = catalog.getNames();
            PDEmbeddedFilesNameTreeNode embeddedFiles = names.getEmbeddedFiles();
            Map<String, PDComplexFileSpecification> embeddedFileNames = embeddedFiles.getNames();

            for (Map.Entry<String, PDComplexFileSpecification> entry : embeddedFileNames.entrySet()) {
                PDEmbeddedFile pdEmbeddedFile = getEmbeddedFile(entry.getValue());
                String s = new String(pdEmbeddedFile.toByteArray(), Charset.defaultCharset());
                String first = Arrays.stream(s.split("<pickedMethodologies>"))
                        .findFirst().orElse("");
                System.out.println(first);

                //You might need to configure the logger first
                log.info("Inputfile: " + file +"Found embedded File: " + entry.getKey() + ":");
            }
        }
        catch (Exception e){
            System.out.println("Document has no attachments. ");
        }


        //System.out.println(textFromDocument);
        /*List<String> stringStream = textFromDocument.lines()
                .filter(gostPattern.asPredicate())
                .collect(Collectors.toList());*/
        List<String> collect = textFromDocument.lines()
                .filter(gostPattern.asPredicate())
                .collect(Collectors.toList());

        List<String> results = collect.stream()
                .flatMap(input -> Arrays.stream(input.split("^.*\\sГОСТ\\s.*0.*$")))
                //.filter(pairing -> pairing.startsWith(" "))
                //.map(pairing -> pairing.replace(",", ""))
                .collect(Collectors.toList());
        System.out.println("-------------------------------------");
        System.out.println(collect);
        System.out.println("-------------------------------------");
        //System.out.println(results);
        document.close();
        return printToFile(textFromDocument);
    }

    private static PDEmbeddedFile getEmbeddedFile(PDComplexFileSpecification fileSpec) {
        PDEmbeddedFile embeddedFile = null;
        if (fileSpec != null) {
            embeddedFile = fileSpec.getEmbeddedFileUnicode();
            if (embeddedFile == null) {
                embeddedFile = fileSpec.getEmbeddedFileDos();
            }
            if (embeddedFile == null) {
                embeddedFile = fileSpec.getEmbeddedFileMac();
            }
            if (embeddedFile == null) {
                embeddedFile = fileSpec.getEmbeddedFileUnix();
            }
            if (embeddedFile == null) {
                embeddedFile = fileSpec.getEmbeddedFile();
            }
        }
        return embeddedFile;
    }

    @SneakyThrows
    private String printToFile(String textFromDocument) {
        return textFromDocument.lines()
                .filter(alPattern.asPredicate())
                .findFirst()
                .orElse("");
    }

    @SneakyThrows
    private void createDir() {
        Files.createDirectories(Path.of(outputFilePath));
    }
}
