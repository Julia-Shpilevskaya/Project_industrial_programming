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

public class XML {
    public String Read(String file_name) {
        try {
            // Создание фабрики для создания парсера DOM
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Чтение XML-файла
            Document document = builder.parse(file_name);

            // Получение корневого элемента
            Element root = document.getDocumentElement();

            // Получение элемента выражения
            NodeList expressionList = root.getElementsByTagName("expression");
            if (expressionList.getLength() > 0) {
                Element expressionElement = (Element) expressionList.item(0);
                String expression = expressionElement.getTextContent();
                System.out.println("Математическое выражение: " + expression);

                return expression;
            } else {
                System.out.println("Элемент выражения не найден в XML-файле.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void Write(String expressionString)
    {
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
            expressionElement.setTextContent(expressionString);
            root.appendChild(expressionElement);

            // Создание объекта Transformer для записи в файл
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            // Запись в файл
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult("output.xml");
            transformer.transform(source, result);

            System.out.println("XML файл успешно создан.");
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
