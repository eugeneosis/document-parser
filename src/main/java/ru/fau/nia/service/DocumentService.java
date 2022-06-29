package ru.fau.nia.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
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
import ru.fau.nia.dto.AccreditationLine;
import ru.fau.nia.dto.Declaration;
import ru.fau.nia.dto.item.DictionaryDto;
import ru.fau.nia.dto.item.NormativeDocumentItem;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class DocumentService {

    public static final String FILE_NAME = "Список акредитованных лиц.txt";

    File file = new File(FILE_NAME);

    @Value("${inputFolder.path}")
    private String folderPath;

    @Value("${outputFile.path}")
    private String outputFilePath;

    @PostConstruct
    @SneakyThrows
    public void parseFiles() {
        boolean exists = new File(outputFilePath + file).exists();
        if (exists) {
            new File(outputFilePath + file).delete();
        }
        FileWriter writer = new FileWriter(outputFilePath + file, true);

        parseFiles(new File(folderPath), writer);

        writer.flush();
        writer.close();
    }

    @SneakyThrows
    public void parseFiles(File dir, FileWriter writer) {
        File[] files = dir.listFiles();

        for (File file : Objects.requireNonNull(files)) {
            if (file.isDirectory()) {
                parseFiles(file, writer);
            } else {
                if (file.getName().endsWith((".pdf"))) {
                    System.out.println(file.getCanonicalPath());
                    extractText(file, writer);
                    System.out.println("===========================================================");
                }
            }
        }
    }

    @SneakyThrows
    private void extractText(File file, FileWriter writer) {
        createDir();
        PDDocument document = PDDocument.load(file);
        PDFTextStripper pdfStripper = new PDFTextStripper();
        pdfStripper.getText(document);

        Map<String, PDComplexFileSpecification> embeddedFileNames = convertDocument(document);

        iterateDocument(embeddedFileNames, writer);

        document.close();
    }

    @SneakyThrows
    private void iterateDocument(Map<String, PDComplexFileSpecification> embeddedFileNames, FileWriter writer) {
        for (Map.Entry<String, PDComplexFileSpecification> entry : embeddedFileNames.entrySet()) {
            PDEmbeddedFile pdEmbeddedFile = getEmbeddedFile(entry.getValue());
            String pdfToXml = new String(pdEmbeddedFile.toByteArray(), Charset.defaultCharset());
            printValuesToFile(pdfToXml, writer);
        }
    }

    @SneakyThrows
    private Map<String, PDComplexFileSpecification> convertDocument(PDDocument document) {
        PDDocumentCatalog catalog = document.getDocumentCatalog();
        PDDocumentNameDictionary names = catalog.getNames();
        PDEmbeddedFilesNameTreeNode embeddedFiles = names.getEmbeddedFiles();
        Map<String, PDComplexFileSpecification> embeddedFileNames = embeddedFiles.getNames();
        return embeddedFileNames;
    }

    @SneakyThrows
    private void printValuesToFile(String pdfToXml, FileWriter writer) {
        ObjectMapper objectMapper = new XmlMapper();
        Declaration pojo = objectMapper.readValue(pdfToXml, Declaration.class);
        String certificateNumberName = pojo.getCertificateNumber().getValue();
        System.out.println(certificateNumberName);
        writer.write("====================================================" + System.lineSeparator());
        writer.write(certificateNumberName + System.lineSeparator());

        Collection<AccreditationLine> accreditationLines = pojo.getAccreditationLines();
        accreditationLines.forEach((accreditationLine -> accreditationLine.getAccreditationAreas()
                .forEach(accreditationAreas -> accreditationAreas.getAccreditationItems()
                        .forEach(accreditationItem -> accreditationItem.getItems()
                                .forEach(item -> {
                                    if (item instanceof NormativeDocumentItem) {
                                        Object value = item.getValue().getValue();
                                        if (value instanceof DictionaryDto) {
                                            String code = ((DictionaryDto) value).getCode();
                                            System.out.println(code);
                                            try {
                                                writer.write(code + System.lineSeparator());
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                })))));
    }

    @SneakyThrows
    private void createDir() {
        Files.createDirectories(Path.of(outputFilePath));
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
}