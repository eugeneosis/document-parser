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
import java.util.concurrent.atomic.AtomicReference;

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
                    System.out.println("===========================================================");
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

            printToFile(pdfToXml);
        }
        document.close();
    }

    @SneakyThrows
    private void printToFile(String pdfToXml) {
        File file = new File(FILE_NAME);
        FileWriter writer = new FileWriter(outputFilePath + file, true);

        ObjectMapper objectMapper = new XmlMapper();
        Declaration pojo = objectMapper.readValue(pdfToXml, Declaration.class);
        String certificateNumberName = pojo.getCertificateNumber().getValue();
        AtomicReference<String> certificateNumberNameResult = new AtomicReference<>();
        certificateNumberNameResult.set(certificateNumberName);
        System.out.println(certificateNumberName);
        writer.write("====================================================" + System.lineSeparator());
        writer.write(certificateNumberName + System.lineSeparator());
        //writer.write("====================================================" + System.lineSeparator());

        AtomicReference<String> codeResult = new AtomicReference<>();

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
                                            codeResult.set(code);
                                            try {
                                                writer.write(codeResult + System.lineSeparator());
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                })))));
        writer.flush();
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
