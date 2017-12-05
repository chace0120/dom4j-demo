import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.util.Iterator;
import java.util.List;

/**
 * 测试类: 测试dom4j相关api功能
 *
 * @author chenx
 * @date 2017/12/5
 */
@RunWith(JUnit4.class)
public class Dom4jTest {
    private Document document;

    @Before
    public void init() throws DocumentException {
        SAXReader saxReader = new SAXReader();
        document = saxReader.read(getClass().getResourceAsStream("demo.xml"));
    }

    @Test
    public void testIterator() {
        Element root = document.getRootElement();
        for (Iterator<Element> iterator = root.elementIterator(); iterator.hasNext();) {
            Element element = iterator.next();
            if (element.isTextOnly()) {
                System.out.println(element.getQualifiedName() + ":" + element.getTextTrim());
            }
        }
    }

    @Test
    public void testXpath() {
        List<Node> nodes = document.selectNodes("//G_CONSIDERED_DATE/TOTAL_GUEST");
        for (Node node : nodes) {
            System.out.println(node.getName() + ":" + node.getStringValue());
        }

        List<Node> dates = document.selectNodes("//G_CONSIDERED_DATE");
        for (Node date : dates) {
            Element element = (Element) date;
            Element totalGuest = element.element("TOTAL_GUEST");
            System.out.println(totalGuest.getQualifiedName() + ":" + totalGuest.getTextTrim());
            Element morningRooms = element.element("ROOMS_MORNING");
            System.out.println(morningRooms.getQualifiedName() + ":" + morningRooms.getTextTrim());
        }
    }
}
