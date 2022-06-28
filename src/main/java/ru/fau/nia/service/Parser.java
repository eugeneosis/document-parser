package ru.fau.nia.service;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class Parser {
    private static final String FILENAME = "C:\\Users\\rak_e.d.osis\\AppData\\Roaming\\JetBrains\\IntelliJIdea2021.1\\scratches\\test.xml";

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(FILENAME));
        document.getDocumentElement().normalize();

        NodeList list = document.getElementsByTagName("declaration");

        printElements(list);
    }

    private static void printElements(NodeList list) {
        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                String certificateNumber = element.getElementsByTagName("name").item(0).getTextContent();
                String pickedMethodologies = element.getElementsByTagName("pickedMethodologies").item(0).getTextContent();
                List<String> gosts = pickedMethodologies.lines()
                        .filter(x -> x.contains("ГОСТ "))
                        .map(String::trim)
                        .map(x -> x.replaceAll("^ГОСТ\\s\\d\\d\\d\\d\\d-\\d\\d\\s.*$",""))
                        .map(x -> x.replaceAll("^ГОСТ\\sР$",""))
                        .map(x -> x.replaceAll(", $", ""))
                        .collect(Collectors.toList());

                System.out.println("Акредитованное лицо: " + certificateNumber);
                System.out.println("Список ГОСТ: " + gosts);
                System.out.println("---------------------------------------");
            }
        }
    }
}
