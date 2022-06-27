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

@Service
public class Parser {
    private static final String FILENAME = "/Users/evgeny/Library/Application Support/JetBrains/IntelliJIdea2021.3/scratches/test.xml";

    public static void main(String[] args) {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(new File(FILENAME));

            doc.getDocumentElement().normalize();

            NodeList list = doc.getElementsByTagName("certificateNumber");
            NodeList list2 = doc.getElementsByTagName("selected");

            for (int i = 0; i < list.getLength(); i++) {
                for (int j = 0; j < list.getLength(); j++) {

                    Node node = list.item(i);
                    Node node2 = list2.item(j);

                    if (node.getNodeType() == Node.ELEMENT_NODE || node2.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;
                        Element element2 = (Element) node2;

                        String name = element.getElementsByTagName("name").item(0).getTextContent();
                        String code = element2.getElementsByTagName("code").item(0).getTextContent();

                        System.out.println("Current Element: " + node.getNodeName());
                        System.out.println("certificateNumber: " + name);
                        System.out.println("---------------------------------------");
                        System.out.println("Current Element: " + node2.getNodeName());
                        System.out.println("code: " + code);
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}