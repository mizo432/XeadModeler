package xeadModeler.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * XeadElementのテスト
 */
@DisplayName("XeadElementテスト")
public class XeadElementTest {
    
    private Document createDocument() throws Exception {
        return DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
    }
    
    private Element createElement(Document doc, String tagName, String sortKey) {
        Element element = doc.createElement(tagName);
        element.setAttribute("SortKey", sortKey);
        return element;
    }
    
    @Test
    @DisplayName("要素の作成と取得")
    public void testCreateAndGetElement() throws Exception {
        // Given
        Document doc = createDocument();
        Element element = createElement(doc, "Table", "001");
        
        // When
        XeadElement xeadElement = new XeadElement(element);
        
        // Then
        assertSame(element, xeadElement.getElement());
        assertEquals("Table", xeadElement.getElement().getTagName());
        assertEquals("001", xeadElement.getElement().getAttribute("SortKey"));
    }
    
    @Test
    @DisplayName("SortKeyによる比較 - 小さい場合")
    public void testCompareToLessThan() throws Exception {
        // Given
        Document doc = createDocument();
        Element element1 = createElement(doc, "Table", "001");
        Element element2 = createElement(doc, "Table", "002");
        
        XeadElement xead1 = new XeadElement(element1);
        XeadElement xead2 = new XeadElement(element2);
        
        // When & Then
        assertTrue(xead1.compareTo(xead2) < 0);
    }
    
    @Test
    @DisplayName("SortKeyによる比較 - 大きい場合")
    public void testCompareToGreaterThan() throws Exception {
        // Given
        Document doc = createDocument();
        Element element1 = createElement(doc, "Table", "002");
        Element element2 = createElement(doc, "Table", "001");
        
        XeadElement xead1 = new XeadElement(element1);
        XeadElement xead2 = new XeadElement(element2);
        
        // When & Then
        assertTrue(xead1.compareTo(xead2) > 0);
    }
    
    @Test
    @DisplayName("SortKeyによる比較 - 等しい場合")
    public void testCompareToEqual() throws Exception {
        // Given
        Document doc = createDocument();
        Element element1 = createElement(doc, "Table", "001");
        Element element2 = createElement(doc, "Table", "001");
        
        XeadElement xead1 = new XeadElement(element1);
        XeadElement xead2 = new XeadElement(element2);
        
        // When & Then
        assertEquals(0, xead1.compareTo(xead2));
    }
    
    @Test
    @DisplayName("SortKeyによるソート")
    public void testSorting() throws Exception {
        // Given
        Document doc = createDocument();
        XeadElement xead1 = new XeadElement(createElement(doc, "Table", "003"));
        XeadElement xead2 = new XeadElement(createElement(doc, "Table", "001"));
        XeadElement xead3 = new XeadElement(createElement(doc, "Table", "002"));
        
        List<XeadElement> list = Arrays.asList(xead1, xead2, xead3);
        
        // When
        Collections.sort(list);
        
        // Then
        assertEquals("001", list.get(0).getElement().getAttribute("SortKey"));
        assertEquals("002", list.get(1).getElement().getAttribute("SortKey"));
        assertEquals("003", list.get(2).getElement().getAttribute("SortKey"));
    }
    
    @Test
    @DisplayName("複数の属性を持つ要素")
    public void testElementWithMultipleAttributes() throws Exception {
        // Given
        Document doc = createDocument();
        Element element = createElement(doc, "Table", "001");
        element.setAttribute("ID", "table-1");
        element.setAttribute("Name", "TestTable");
        
        // When
        XeadElement xeadElement = new XeadElement(element);
        
        // Then
        assertEquals("001", xeadElement.getElement().getAttribute("SortKey"));
        assertEquals("table-1", xeadElement.getElement().getAttribute("ID"));
        assertEquals("TestTable", xeadElement.getElement().getAttribute("Name"));
    }
}
