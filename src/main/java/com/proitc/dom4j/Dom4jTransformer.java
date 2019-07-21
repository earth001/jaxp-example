package com.proitc.dom4j;

import org.dom4j.*;
import org.dom4j.io.DocumentSource;
import org.dom4j.io.SAXReader;

import javax.xml.XMLConstants;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

class Dom4jTransformer {
    private final Document input;

    Dom4jTransformer(String resourcePath) throws DocumentException {
        // 1- Build the doc from the XML file
        SAXReader xmlReader = new SAXReader();
        this.input = xmlReader.read(resourcePath);
    }

    String modifyAttribute(String attribute, String oldValue, String newValue) throws TransformerException {
        // 2- Locate the node(s) with xpath, we can use index and iterator too.
        String expr = String.format("//*[contains(@%s, '%s')]", attribute, oldValue);
        XPath xpath = DocumentHelper.createXPath(expr);
        List<Node> nodes = xpath.selectNodes(input);
        // 3- Make the change on the selected nodes
        for (int i = 0; i < nodes.size(); i++) {
            Element element = (Element) nodes.get(i);
            element.addAttribute(attribute, newValue);
        }
        // 4- Save the result to a new XML doc
        TransformerFactory factory = TransformerFactory.newInstance();
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        Transformer xformer = factory.newTransformer();
        xformer.setOutputProperty(OutputKeys.INDENT, "yes");
        Writer output = new StringWriter();
        xformer.transform(new DocumentSource(input), new StreamResult(output));
        return output.toString();
    }
}
