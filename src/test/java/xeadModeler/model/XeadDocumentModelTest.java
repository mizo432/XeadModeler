package xeadModeler.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;

import static org.junit.jupiter.api.Assertions.*;

/**
 * XeadDocumentModelのテスト
 */
@DisplayName("XeadDocumentModelテスト")
public class XeadDocumentModelTest {
    
    private XeadDocumentModel model;
    
    @BeforeEach
    public void setUp() {
        model = new XeadDocumentModel();
    }
    
    @Test
    @DisplayName("初期状態の確認")
    public void testInitialState() {
        assertNull(model.getDomDocument());
        assertNull(model.getDomDocumentSaved());
        assertNull(model.getCurrentFileName());
        assertNull(model.getCurrentFileFolder());
        assertEquals(0, model.getLastModifyTime());
        assertNull(model.getSystemName());
    }
    
    @Test
    @DisplayName("DOMドキュメントの設定と取得")
    public void testSetAndGetDomDocument() throws Exception {
        // Given
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        
        // When
        model.setDomDocument(doc);
        
        // Then
        assertSame(doc, model.getDomDocument());
    }
    
    @Test
    @DisplayName("保存済みDOMドキュメントの設定と取得")
    public void testSetAndGetDomDocumentSaved() throws Exception {
        // Given
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        
        // When
        model.setDomDocumentSaved(doc);
        
        // Then
        assertSame(doc, model.getDomDocumentSaved());
    }
    
    @Test
    @DisplayName("ファイル名の設定と取得")
    public void testSetAndGetFileName() {
        // When
        model.setCurrentFileName("/path/to/file.xead");
        
        // Then
        assertEquals("/path/to/file.xead", model.getCurrentFileName());
    }
    
    @Test
    @DisplayName("ファイル名設定時にフォルダが自動設定される")
    public void testFileNameSetsFolder() {
        // When
        model.setCurrentFileName("/path/to/file.xead");
        
        // Then
        assertEquals("/path/to", model.getCurrentFileFolder());
    }
    
    @Test
    @DisplayName("ファイル名がnullの場合フォルダもnull")
    public void testNullFileName() {
        // When
        model.setCurrentFileName(null);
        
        // Then
        assertNull(model.getCurrentFileName());
        assertNull(model.getCurrentFileFolder());
    }
    
    @Test
    @DisplayName("最終更新時刻の設定と取得")
    public void testSetAndGetLastModifyTime() {
        // When
        model.setLastModifyTime(1234567890L);
        
        // Then
        assertEquals(1234567890L, model.getLastModifyTime());
    }
    
    @Test
    @DisplayName("システム名の設定と取得")
    public void testSetAndGetSystemName() {
        // When
        model.setSystemName("TestSystem");
        
        // Then
        assertEquals("TestSystem", model.getSystemName());
    }
    
    @Test
    @DisplayName("DDLコメントマークのデフォルト値")
    public void testDefaultDdlCommentMark() {
        assertEquals("--", model.getDdlCommentMark());
    }
    
    @Test
    @DisplayName("DDLセクションマークのデフォルト値")
    public void testDefaultDdlSectionMark() {
        assertEquals(";", model.getDdlSectionMark());
    }
    
    @Test
    @DisplayName("DDLフィールドコメントタイプのデフォルト値")
    public void testDefaultDdlFieldCommentType() {
        assertEquals("1", model.getDdlFieldCommentType());
    }
    
    @Test
    @DisplayName("CamelCase変換のデフォルト値")
    public void testDefaultConvertCamel() {
        assertTrue(model.isIsToConvertCamel());
    }
    
    @Test
    @DisplayName("外部キー制約無視のデフォルト値")
    public void testDefaultIgnoreForeignKeyConstraints() {
        assertFalse(model.isIgnoreForeignKeyConstraints());
    }
    
    @Test
    @DisplayName("DDL設定の変更")
    public void testSetDdlSettings() {
        // When
        model.setDdlCommentMark("#");
        model.setDdlSectionMark(":");
        model.setDdlFieldCommentType("2");
        model.setIsToConvertCamel(false);
        model.setIgnoreForeignKeyConstraints(true);
        
        // Then
        assertEquals("#", model.getDdlCommentMark());
        assertEquals(":", model.getDdlSectionMark());
        assertEquals("2", model.getDdlFieldCommentType());
        assertFalse(model.isIsToConvertCamel());
        assertTrue(model.isIgnoreForeignKeyConstraints());
    }
    
    @Test
    @DisplayName("リファレンスファイルリストの初期状態")
    public void testReferringFileListsInitialState() {
        assertTrue(model.getReferringFileNameList().isEmpty());
        assertTrue(model.getReferringFileDocList().isEmpty());
    }
}
