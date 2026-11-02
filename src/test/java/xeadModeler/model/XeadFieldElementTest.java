package xeadModeler.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import xeadModeler.model.json.TableField;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * XeadFieldElementのテスト
 * JSON POJOを使用してテストします。
 */
@DisplayName("XeadFieldElementテスト")
public class XeadFieldElementTest {
    
    private TableField createTableField(String alias) {
        TableField field = new TableField();
        field.setAlias(alias);
        return field;
    }
    
    @Test
    @DisplayName("要素の作成と取得")
    public void testCreateAndGetElement() throws Exception {
        // Given
        TableField field = createTableField("name");
        
        // When
        XeadFieldElement xeadFieldElement = new XeadFieldElement(field);
        
        // Then
        assertSame(field, xeadFieldElement.getPojo());
        assertEquals("name", ((TableField) xeadFieldElement.getPojo()).getAlias());
    }
    
    @Test
    @DisplayName("Aliasによる比較 - 小さい場合")
    public void testCompareToLessThan() throws Exception {
        // Given
        TableField field1 = createTableField("aaa");
        TableField field2 = createTableField("bbb");
        
        XeadFieldElement xead1 = new XeadFieldElement(field1);
        XeadFieldElement xead2 = new XeadFieldElement(field2);
        
        // When & Then
        assertTrue(xead1.compareTo(xead2) < 0);
    }
    
    @Test
    @DisplayName("Aliasによる比較 - 大きい場合")
    public void testCompareToGreaterThan() throws Exception {
        // Given
        TableField field1 = createTableField("zzz");
        TableField field2 = createTableField("aaa");
        
        XeadFieldElement xead1 = new XeadFieldElement(field1);
        XeadFieldElement xead2 = new XeadFieldElement(field2);
        
        // When & Then
        assertTrue(xead1.compareTo(xead2) > 0);
    }
    
    @Test
    @DisplayName("Aliasによる比較 - 等しい場合")
    public void testCompareToEqual() throws Exception {
        // Given
        TableField field1 = createTableField("name");
        TableField field2 = createTableField("name");
        
        XeadFieldElement xead1 = new XeadFieldElement(field1);
        XeadFieldElement xead2 = new XeadFieldElement(field2);
        
        // When & Then
        assertEquals(0, xead1.compareTo(xead2));
    }
    
    @Test
    @DisplayName("Aliasによるソート")
    public void testSorting() throws Exception {
        // Given
        XeadFieldElement xead1 = new XeadFieldElement(createTableField("zzz"));
        XeadFieldElement xead2 = new XeadFieldElement(createTableField("aaa"));
        XeadFieldElement xead3 = new XeadFieldElement(createTableField("mmm"));
        
        List<XeadFieldElement> list = Arrays.asList(xead1, xead2, xead3);
        
        // When
        Collections.sort(list);
        
        // Then
        TableField f1 = (TableField) list.get(0).getPojo();
        TableField f2 = (TableField) list.get(1).getPojo();
        TableField f3 = (TableField) list.get(2).getPojo();
        assertEquals("aaa", f1.getAlias());
        assertEquals("mmm", f2.getAlias());
        assertEquals("zzz", f3.getAlias());
    }
    
    @Test
    @DisplayName("複数の属性を持つ要素")
    public void testElementWithMultipleAttributes() throws Exception {
        // Given
        TableField field = createTableField("name");
        field.setId("field-1");
        field.setDataTypeId("VARCHAR");
        
        // When
        XeadFieldElement xeadFieldElement = new XeadFieldElement(field);
        
        // Then
        assertEquals("name", ((TableField) xeadFieldElement.getPojo()).getAlias());
        assertEquals("field-1", ((TableField) xeadFieldElement.getPojo()).getId());
        assertEquals("VARCHAR", ((TableField) xeadFieldElement.getPojo()).getDataTypeId());
    }
}
