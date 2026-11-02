package xeadModeler.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import xeadModeler.model.json.Table;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * XeadElementのテスト
 * JSON POJOを使用してテストします。
 */
@DisplayName("XeadElementテスト")
public class XeadElementTest {
    
    private Table createTable(String sortKey) {
        Table table = new Table();
        table.setSortKey(sortKey);
        return table;
    }
    
    @Test
    @DisplayName("要素の作成と取得")
    public void testCreateAndGetElement() throws Exception {
        // Given
        Table table = createTable("001");
        table.setName("TestTable");
        
        // When
        XeadElement xeadElement = new XeadElement(table);
        
        // Then
        assertSame(table, xeadElement.getPojo());
        assertEquals("001", xeadElement.getPojo().getClass().getMethod("getSortKey").invoke(xeadElement.getPojo()));
    }
    
    @Test
    @DisplayName("SortKeyによる比較 - 小さい場合")
    public void testCompareToLessThan() throws Exception {
        // Given
        Table table1 = createTable("001");
        Table table2 = createTable("002");
        
        XeadElement xead1 = new XeadElement(table1);
        XeadElement xead2 = new XeadElement(table2);
        
        // When & Then
        assertTrue(xead1.compareTo(xead2) < 0);
    }
    
    @Test
    @DisplayName("SortKeyによる比較 - 大きい場合")
    public void testCompareToGreaterThan() throws Exception {
        // Given
        Table table1 = createTable("002");
        Table table2 = createTable("001");
        
        XeadElement xead1 = new XeadElement(table1);
        XeadElement xead2 = new XeadElement(table2);
        
        // When & Then
        assertTrue(xead1.compareTo(xead2) > 0);
    }
    
    @Test
    @DisplayName("SortKeyによる比較 - 等しい場合")
    public void testCompareToEqual() throws Exception {
        // Given
        Table table1 = createTable("001");
        Table table2 = createTable("001");
        
        XeadElement xead1 = new XeadElement(table1);
        XeadElement xead2 = new XeadElement(table2);
        
        // When & Then
        assertEquals(0, xead1.compareTo(xead2));
    }
    
    @Test
    @DisplayName("SortKeyによるソート")
    public void testSorting() throws Exception {
        // Given
        XeadElement xead1 = new XeadElement(createTable("003"));
        XeadElement xead2 = new XeadElement(createTable("001"));
        XeadElement xead3 = new XeadElement(createTable("002"));
        
        List<XeadElement> list = Arrays.asList(xead1, xead2, xead3);
        
        // When
        Collections.sort(list);
        
        // Then
        Table t1 = (Table) list.get(0).getPojo();
        Table t2 = (Table) list.get(1).getPojo();
        Table t3 = (Table) list.get(2).getPojo();
        assertEquals("001", t1.getSortKey());
        assertEquals("002", t2.getSortKey());
        assertEquals("003", t3.getSortKey());
    }
    
    @Test
    @DisplayName("複数の属性を持つ要素")
    public void testElementWithMultipleAttributes() throws Exception {
        // Given
        Table table = createTable("001");
        table.setId("table-1");
        table.setName("TestTable");
        
        // When
        XeadElement xeadElement = new XeadElement(table);
        
        // Then
        assertEquals("001", ((Table) xeadElement.getPojo()).getSortKey());
        assertEquals("table-1", ((Table) xeadElement.getPojo()).getId());
        assertEquals("TestTable", ((Table) xeadElement.getPojo()).getName());
    }
}
