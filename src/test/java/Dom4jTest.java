import org.apache.commons.lang3.StringUtils;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
        document = saxReader.read(getClass().getResourceAsStream("Gps_Stops.xml"));
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
        List<Node> nodes = document.selectNodes("//G_CONSIDERED_DATE");
        for (Node node : nodes) {
            Element element = (Element) node;
            Element curDate = element.element("CONSIDERED_DATE");
            System.out.println(curDate.getQualifiedName() + ":" + curDate.getTextTrim());
        }
    }

    @Test
    public void testIteratorAttr() {
        Map<String, String> namespaceMap = new HashMap<>();
        namespaceMap.put("ns", "GpsStopsByGroupReport");
        XPath xPath = document.createXPath("//ns:table1_GroupName/ns:table1_UserNum_Collection/ns:table1_UserNum");
        xPath.setNamespaceURIs(namespaceMap);
        List<Node> nodes = xPath.selectNodes(document);
        nodes.forEach(node -> {
            System.out.println(node.valueOf("@UserName"));

//            XPath otherXPath = DocumentHelper.createXPath("//ns:table1_GroupName/ns:table1_UserNum_Collection/ns:table1_UserNum");
//            otherXPath.setNamespaceURIs(namespaceMap);
//            List<Node> carsInfos = otherXPath.selectNodes(node);
//            carsInfos.forEach(carsInfo -> {
//                String interval = StringUtils.normalizeSpace(carsInfo.valueOf("@UserName"));
//                System.out.println(interval);
//            });
        });
    }

    @Test
    public void testIteratorByElement() {
        Element root = document.getRootElement();

        Iterator<Element> hotelInfos = root.elements().get(1).elements().get(0).elements().get(0).elements().get(0).elements().iterator();
        while (hotelInfos.hasNext()) {
            Element hotelInfo = hotelInfos.next();
            System.out.println(hotelInfo.attributeValue("Group"));

            Iterator<Element> carInfos = hotelInfo.elements().get(0).elements().iterator();
            while (carInfos.hasNext()) {
                Element carInfo = carInfos.next();
                System.out.println(carInfo.attributeValue("UserName"));
            }
        }
    }
}
