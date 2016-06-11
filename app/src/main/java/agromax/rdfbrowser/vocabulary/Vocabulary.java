package agromax.rdfbrowser.vocabulary;

import android.content.Context;
import android.support.annotation.NonNull;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

/**
 * Created by Dell on 11-06-2016.
 */
public class Vocabulary {
    private final Context context;
    private final Document document;
    private final XPath xPath;

    public Vocabulary(@NonNull Context context, @NonNull FileInputStream vocabStream) {
        this.context = context;

        // Lets load the vocabulary
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;
        Document doc = null;
        try {
            documentBuilder = builderFactory.newDocumentBuilder();
            doc = documentBuilder.parse(vocabStream);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        document = doc;
        if (document != null) {
            xPath = XPathFactory.newInstance().newXPath();
        } else {
            xPath = null;
        }
    }


    public ArrayList<String> query(CharSequence path, CharSequence constraint) {
        ArrayList<String> hints = new ArrayList<>();
        if (xPath != null) {
            try {
                XPathExpression compile = xPath.compile("vocabulary/" + path + "*[contains(@value, '" + constraint + "')]");
                if (compile != null) {
                    Object result = compile.evaluate(document, XPathConstants.NODESET);
                    NodeList resultantNodes = (NodeList) result;
                    for (int i = 0; i < resultantNodes.getLength(); i++) {
                        Node node = resultantNodes.item(i);
                        if (node != null) {
                            NamedNodeMap attrs = node.getAttributes();
                            if (attrs != null) {
                                Node value = attrs.getNamedItem("value");
                                if (value != null) {
                                    hints.add(value.getNodeValue());
                                }
                            }
                        }
                    }
                }
            } catch (XPathExpressionException e) {
                e.printStackTrace();
            }
        }
        return hints;
    }
}
