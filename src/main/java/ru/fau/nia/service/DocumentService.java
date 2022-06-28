package ru.fau.nia.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDDocumentNameDictionary;
import org.apache.pdfbox.pdmodel.PDEmbeddedFilesNameTreeNode;
import org.apache.pdfbox.pdmodel.common.filespecification.PDComplexFileSpecification;
import org.apache.pdfbox.pdmodel.common.filespecification.PDEmbeddedFile;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.fau.nia.dto.AccreditationItem;
import ru.fau.nia.dto.Declaration;
import ru.fau.nia.dto.item.DictionaryDto;
import ru.fau.nia.dto.item.Item;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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

        PDDocumentCatalog catalog = document.getDocumentCatalog();
        PDDocumentNameDictionary names = catalog.getNames();
        PDEmbeddedFilesNameTreeNode embeddedFiles = names.getEmbeddedFiles();
        Map<String, PDComplexFileSpecification> embeddedFileNames = embeddedFiles.getNames();

        for (Map.Entry<String, PDComplexFileSpecification> entry : embeddedFileNames.entrySet()) {
            PDEmbeddedFile pdEmbeddedFile = getEmbeddedFile(entry.getValue());
            String pdfToXml = new String(pdEmbeddedFile.toByteArray(), Charset.defaultCharset());

            ObjectMapper objectMapper = new XmlMapper();
            Declaration pojo = objectMapper.readValue(pdfToXml, Declaration.class);
            //System.out.println(pojo);
            //System.out.println(pdfToXml);
            String value = pojo.getCertificateNumber().getValue();
            System.out.println(value);
            //Item item = objectMapper.readValue(pdfToXml, Item.class);
            //List<String> collect = item.getValues().stream().map(ru.fau.nia.dto.item.Value::getCode).collect(Collectors.toList());
            //System.out.println(collect);
            //printToFile(textFromDocument);
        }
        document.close();
    }

    @SneakyThrows
    private void printToFile(String textFromDocument) {
        File file = new File(FILE_NAME);
        FileWriter writer = new FileWriter(outputFilePath + file);

        ObjectMapper objectMapper = new XmlMapper();
        Declaration pojo = objectMapper.readValue(textFromDocument, Declaration.class);
        System.out.println(pojo);

        writer.write(String.valueOf(pojo));
        writer.close();
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
