package pfr.evgen.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

public class XMLHelper {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder;

    public XMLHelper() {
        try {
            this.builder = this.factory.newDocumentBuilder();
        } catch (ParserConfigurationException var2) {
            var2.printStackTrace();
        }

    }

    public String getTextsWithImagePlaceholder(InputStream is) {
        String text = "";

        try {
            Document doc = this.builder.parse(is);
            Element root = doc.getDocumentElement();
            NodeList root_nodes = root.getElementsByTagName("text:p");

            for(int i = 0; i < root_nodes.getLength(); ++i) {
                text = text + root_nodes.item(i).getTextContent() + "\n";
                NodeList childs = root_nodes.item(i).getChildNodes();

                for(int j = 0; j < childs.getLength(); ++j) {
                    NodeList images = childs.item(j).getChildNodes();

                    for(int k = 0; k < images.getLength(); ++k) {
                        Node image = images.item(k);
                        if (image.getNodeName().equals("draw:image")) {
                            text = text + String.valueOf(image.getAttributes().getNamedItem("xlink:href")).replace("xlink:href", "").replace("=", "").replace("\"", "") + "\n";
                        }
                    }
                }
            }
        } catch (SAXException var12) {
            var12.printStackTrace();
        } catch (IOException var13) {
            var13.printStackTrace();
        }

        return text;
    }
}