package xeadModeler.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;

import static org.junit.jupiter.api.Assertions.*;

/**
 * DatamodelRelationshipのテスト
 */
@DisplayName("DatamodelRelationshipテスト")
public class DatamodelRelationshipTest {
    
    private Document createDocument() throws Exception {
        return DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
    }
    
    private Element createSubsystemRelationshipElement(Document doc) {
        return doc.createElement("SubsystemRelationship");
    }
    
    private Element createRelationshipElement(Document doc, String table1Id, String table2Id, 
                                                String key1Id, String key2Id,
                                                String existWhen1, String existWhen2) {
        Element element = doc.createElement("Relationship");
        element.setAttribute("Table1ID", table1Id);
        element.setAttribute("Table2ID", table2Id);
        element.setAttribute("TableKey1ID", key1Id);
        element.setAttribute("TableKey2ID", key2Id);
        element.setAttribute("ExistWhen1", existWhen1);
        element.setAttribute("ExistWhen2", existWhen2);
        return element;
    }
    
    @Test
    @DisplayName("関係の作成とプロパティ取得")
    public void testCreateAndGetProperties() throws Exception {
        // Given
        Document doc = createDocument();
        Element subsystemRel = createSubsystemRelationshipElement(doc);
        Element relationship = createRelationshipElement(doc, "table1", "table2", "key1", "key2", "", "");
        
        // When
        DatamodelRelationship rel = new DatamodelRelationship(subsystemRel, relationship);
        
        // Then
        assertSame(subsystemRel, rel.getSubsystemRelationshipElement());
        assertSame(relationship, rel.getRelationshipElement());
        assertEquals("table1", rel.getTable1Id());
        assertEquals("table2", rel.getTable2Id());
        assertEquals("key1", rel.getTableKey1Id());
        assertEquals("key2", rel.getTableKey2Id());
    }
    
    @Test
    @DisplayName("Terminal1がオプションの場合")
    public void testTerminal1Optional() throws Exception {
        // Given
        Document doc = createDocument();
        Element subsystemRel = createSubsystemRelationshipElement(doc);
        Element relationship = createRelationshipElement(doc, "table1", "table2", "key1", "key2", "1", "");
        
        // When
        DatamodelRelationship rel = new DatamodelRelationship(subsystemRel, relationship);
        
        // Then
        assertTrue(rel.isTerminal1Optional());
        assertFalse(rel.isTerminal2Optional());
    }
    
    @Test
    @DisplayName("Terminal2がオプションの場合")
    public void testTerminal2Optional() throws Exception {
        // Given
        Document doc = createDocument();
        Element subsystemRel = createSubsystemRelationshipElement(doc);
        Element relationship = createRelationshipElement(doc, "table1", "table2", "key1", "key2", "", "1");
        
        // When
        DatamodelRelationship rel = new DatamodelRelationship(subsystemRel, relationship);
        
        // Then
        assertFalse(rel.isTerminal1Optional());
        assertTrue(rel.isTerminal2Optional());
    }
    
    @Test
    @DisplayName("両方のTerminalがオプションの場合")
    public void testBothTerminalsOptional() throws Exception {
        // Given
        Document doc = createDocument();
        Element subsystemRel = createSubsystemRelationshipElement(doc);
        Element relationship = createRelationshipElement(doc, "table1", "table2", "key1", "key2", "1", "1");
        
        // When
        DatamodelRelationship rel = new DatamodelRelationship(subsystemRel, relationship);
        
        // Then
        assertTrue(rel.isTerminal1Optional());
        assertTrue(rel.isTerminal2Optional());
    }
    
    @Test
    @DisplayName("両方のTerminalが必須の場合")
    public void testBothTerminalsRequired() throws Exception {
        // Given
        Document doc = createDocument();
        Element subsystemRel = createSubsystemRelationshipElement(doc);
        Element relationship = createRelationshipElement(doc, "table1", "table2", "key1", "key2", "", "");
        
        // When
        DatamodelRelationship rel = new DatamodelRelationship(subsystemRel, relationship);
        
        // Then
        assertFalse(rel.isTerminal1Optional());
        assertFalse(rel.isTerminal2Optional());
    }
    
    @Test
    @DisplayName("初期状態はshowOnModel=true")
    public void testDefaultShowOnModel() throws Exception {
        // Given
        Document doc = createDocument();
        Element subsystemRel = createSubsystemRelationshipElement(doc);
        Element relationship = createRelationshipElement(doc, "table1", "table2", "key1", "key2", "", "");
        
        // When
        DatamodelRelationship rel = new DatamodelRelationship(subsystemRel, relationship);
        
        // Then
        assertTrue(rel.isShowOnModel());
    }
    
    @Test
    @DisplayName("showOnModelの設定と取得")
    public void testSetShowOnModel() throws Exception {
        // Given
        Document doc = createDocument();
        Element subsystemRel = createSubsystemRelationshipElement(doc);
        Element relationship = createRelationshipElement(doc, "table1", "table2", "key1", "key2", "", "");
        DatamodelRelationship rel = new DatamodelRelationship(subsystemRel, relationship);
        
        // When
        rel.setShowOnModel(false);
        
        // Then
        assertFalse(rel.isShowOnModel());
        
        // When
        rel.setShowOnModel(true);
        
        // Then
        assertTrue(rel.isShowOnModel());
    }
    
    @Test
    @DisplayName("relationshipElementがnullの場合")
    public void testNullRelationshipElement() throws Exception {
        // Given
        Document doc = createDocument();
        Element subsystemRel = createSubsystemRelationshipElement(doc);
        
        // When
        DatamodelRelationship rel = new DatamodelRelationship(subsystemRel, null);
        
        // Then
        assertSame(subsystemRel, rel.getSubsystemRelationshipElement());
        assertNull(rel.getRelationshipElement());
        assertNull(rel.getTable1Id());
        assertNull(rel.getTable2Id());
        assertNull(rel.getTableKey1Id());
        assertNull(rel.getTableKey2Id());
        assertFalse(rel.isTerminal1Optional());
        assertFalse(rel.isTerminal2Optional());
        assertTrue(rel.isShowOnModel());
    }
}
