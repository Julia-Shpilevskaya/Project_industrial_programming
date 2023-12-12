package file_types;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

class XMLTest {

    @Test
    void Read() {
        String text = "10+44*(5+6)*9";

        try {
            // Создание фабрики для создания парсера DOM
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Создание нового документа XML
            Document document = builder.newDocument();

            // Создание корневого элемента
            Element root = document.createElement("mathExpression");
            document.appendChild(root);

            // Создание и добавление элемента выражения
            Element expressionElement = document.createElement("expression");
            expressionElement.setTextContent(text);
            root.appendChild(expressionElement);

            // Создание объекта Transformer для записи в файл
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            // Запись в файл
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult("file12.xml");
            transformer.transform(source, result);
        }
        catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        XML xml = new XML();

        String text_in_file = xml.Read("file12.xml");

        Assert.assertEquals(text,text_in_file);


    }

    @Test
    void Write() {
        String text = "10+44*(5+6)";

        XML xml = new XML();

        xml.Write("file11.xml", text);

        try {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        // Чтение XML-файла
        Document document = builder.parse("file11.xml");

        // Получение корневого элемента
        Element root = document.getDocumentElement();

        // Получение элемента выражения
        NodeList expressionList = root.getElementsByTagName("expression");

           if (expressionList.getLength() > 0) {
            Element expressionElement = (Element) expressionList.item(0);
            String expression = expressionElement.getTextContent();

            Assert.assertEquals(text,expression);
            }
            else {
            System.out.println("The expression element was not found in the XML file.");
            }
        }
        catch (Exception e) {
        e.printStackTrace();
        }
    }
}