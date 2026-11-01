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
 * XeadFieldElementのテスト
 */
@DisplayName("XeadFieldElementテスト")
public class XeadFieldElementTest {
    
    private Document createDocument() throws Exception {
        return DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
    }
    
    private Element createElement(Document doc, String tagName, String alias) {
        Element element = doc.createElement(tagName);
        element.setAttribute("Alias", alias);
        return element;
    }
    
    @Test
    @DisplayName("要素の作成と取得")
    public void testCreateAndGetElement() throws Exception {
        // Given
        Document doc = createDocument();
        Element element = createElement(doc, "Field", "name");
        
        // When
        XeadFieldElement xeadFieldElement = new XeadFieldElement(element);
        
        // Then
        assertSame(element, xeadFieldElement.getElement());
        assertEquals("Field", xeadFieldElement.getElement().getTagName());
        assertEquals("name", xeadFieldElement.getElement().getAttribute("Alias"));
    }
    
    @Test
    @DisplayName("Aliasによる比較 - 小さい場合")
    public void testCompareToLessThan() throws Exception {
        // Given
        Document doc = createDocument();
        Element element1 = createElement(doc, "Field", "aaa");
        Element element2 = createElement(doc, "Field", "bbb");
        
        XeadFieldElement xead1 = new XeadFieldElement(element1);
        XeadFieldElement xead2 = new XeadFieldElement(element2);
        
        // When & Then
        assertTrue(xead1.compareTo(xead2) < 0);
    }
    
    @Test
    @DisplayName("Aliasによる比較 - 大きい場合")
    public void testCompareToGreaterThan() throws Exception {
        // Given
        Document doc = createDocument();
        Element element1 = createElement(doc, "Field", "zzz");
        Element element2 = createElement(doc, "Field", "aaa");
        
        XeadFieldElement xead1 = new XeadFieldElement(element1);
        XeadFieldElement xead2 = new XeadFieldElement(element2);
        
        // When & Then
        assertTrue(xead1.compareTo(xead2) > 0);
    }
    
    @Test
    @DisplayName("Aliasによる比較 - 等しい場合")
    public void testCompareToEqual() throws Exception {
        // Given
        Document doc = createDocument();
        Element element1 = createElement(doc, "Field", "name");
        Element element2 = createElement(doc, "Field", "name");
        
        XeadFieldElement xead1 = new XeadFieldElement(element1);
        XeadFieldElement xead2 = new XeadFieldElement(element2);
        
        // When & Then
        assertEquals(0, xead1.compareTo(xead2));
    }
    
    @Test
    @DisplayName("Aliasによるソート")
    public void testSorting() throws Exception {
        // Given
        Document doc = createDocument();
        XeadFieldElement xead1 = new XeadFieldElement(createElement(doc, "Field", "zzz"));
        XeadFieldElement xead2 = new XeadFieldElement(createElement(doc, "Field", "aaa"));
        XeadFieldElement xead3 = new XeadFieldElement(createElement(doc, "Field", "mmm"));
        
        List<XeadFieldElement> list = Arrays.asList(xead1, xead2, xead3);
        
        // When
        Collections.sort(list);
        
        // Then
        assertEquals("aaa", list.get(0).getElement().getAttribute("Alias"));
        assertEquals("mmm", list.get(1).getElement().getAttribute("Alias"));
        assertEquals("zzz", list.get(2).getElement().getAttribute("Alias"));
    }
    
    @Test
    @DisplayName("複数の属性を持つ要素")
    public void testElementWithMultipleAttributes() throws Exception {
        // Given
        Document doc = createDocument();
        Element element = createElement(doc, "Field", "name");
        element.setAttribute("ID", "field-1");
        element.setAttribute("DataType", "VARCHAR");
        
        // When
        XeadFieldElement xeadFieldElement = new XeadFieldElement(element);
        
        // Then
        assertEquals("name", xeadFieldElement.getElement().getAttribute("Alias"));
        assertEquals("field-1", xeadFieldElement.getElement().getAttribute("ID"));
        assertEquals("VARCHAR", xeadFieldElement.getElement().getAttribute("DataType"));
    }
}
