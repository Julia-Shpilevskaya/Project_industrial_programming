package file_types;

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
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(file_name);
            Element root = document.getDocumentElement();

            NodeList expressionList = root.getElementsByTagName("expression");
            if (expressionList.getLength() > 0) {
                Element expressionElement = (Element) expressionList.item(0);
                String expression = expressionElement.getTextContent();
                System.out.println("mathExpression: " + expression);

                return expression;
            } else {
                System.out.println("The expression element was not found in the XML file.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void Write(String file_name, String expressionString)
    {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.newDocument();
            Element root = document.createElement("mathExpression");
            document.appendChild(root);

            Element expressionElement = document.createElement("expression");
            expressionElement.setTextContent(expressionString);
            root.appendChild(expressionElement);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(file_name);
            transformer.transform(source, result);

            System.out.println("The XML file has been successfully created.");
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

