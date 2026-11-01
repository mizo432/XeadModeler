package xeadModeler.service;

import xeadModeler.model.AbstractTreeNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TreeNodeServiceImplのテスト
 */
@DisplayName("TreeNodeServiceImplテスト")
public class TreeNodeServiceImplTest {
    
    /**
     * テスト用の具象ノードクラス
     */
    private static class TestTreeNode extends AbstractTreeNode {
        public TestTreeNode(String id, String name) {
            super(id, name);
        }
        
        @Override
        public String getType() {
            return "test";
        }
    }
    
    private TreeNodeServiceImpl service;
    
    @BeforeEach
    public void setUp() {
        service = new TreeNodeServiceImpl();
    }
    
    @Test
    @DisplayName("ノードの作成と取得")
    public void testCreateAndFind() {
        // Given
        TestTreeNode node = new TestTreeNode("node-1", "Test Node");
        
        // When
        AbstractTreeNode created = service.create(node);
        AbstractTreeNode found = service.findById("node-1");
        
        // Then
        assertNotNull(created);
        assertNotNull(found);
        assertEquals("node-1", found.getId());
        assertEquals("Test Node", found.getName());
    }
    
    @Test
    @DisplayName("null IDでの作成は例外をスロー")
    public void testCreateWithNullId() {
        // Given
        AbstractTreeNode node = new TestTreeNode(null, "Test");
        
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            service.create(node);
        });
    }
    
    @Test
    @DisplayName("空文字IDでの作成は例外をスロー")
    public void testCreateWithEmptyId() {
        // Given
        AbstractTreeNode node = new TestTreeNode("", "Test");
        
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            service.create(node);
        });
    }
    
    @Test
    @DisplayName("存在しないIDの取得")
    public void testFindNonExistent() {
        // When
        AbstractTreeNode found = service.findById("non-existent");
        
        // Then
        assertNull(found);
    }
    
    @Test
    @DisplayName("すべてのノードの取得")
    public void testFindAll() {
        // Given
        service.create(new TestTreeNode("node-1", "Node 1"));
        service.create(new TestTreeNode("node-2", "Node 2"));
        service.create(new TestTreeNode("node-3", "Node 3"));
        
        // When
        List<AbstractTreeNode> all = service.findAll();
        
        // Then
        assertEquals(3, all.size());
    }
    
    @Test
    @DisplayName("ノードの更新")
    public void testUpdate() {
        // Given
        TestTreeNode node = new TestTreeNode("node-1", "Original");
        service.create(node);
        
        // When
        node.setName("Updated");
        AbstractTreeNode updated = service.update(node);
        
        // Then
        assertEquals("Updated", updated.getName());
        assertEquals("Updated", service.findById("node-1").getName());
    }
    
    @Test
    @DisplayName("存在しないノードの更新は例外をスロー")
    public void testUpdateNonExistent() {
        // Given
        TestTreeNode node = new TestTreeNode("non-existent", "Test");
        
        // When & Then
        assertThrows(IllegalStateException.class, () -> {
            service.update(node);
        });
    }
    
    @Test
    @DisplayName("ノードの削除")
    public void testDelete() {
        // Given
        service.create(new TestTreeNode("node-1", "Test Node"));
        
        // When
        service.delete("node-1");
        
        // Then
        assertNull(service.findById("node-1"));
        assertEquals(0, service.size());
    }
    
    @Test
    @DisplayName("子ノード付きノードの削除")
    public void testDeleteWithChildren() {
        // Given
        TestTreeNode parent = new TestTreeNode("parent", "Parent");
        TestTreeNode child = new TestTreeNode("child", "Child");
        parent.addChild(child);
        
        service.create(parent);
        service.create(child);
        
        // When
        service.delete("parent");
        
        // Then
        assertNull(service.findById("parent"));
        assertNull(service.findById("child"));
        assertEquals(0, service.size());
    }
    
    @Test
    @DisplayName("リポジトリのクリア")
    public void testClear() {
        // Given
        service.create(new TestTreeNode("node-1", "Node 1"));
        service.create(new TestTreeNode("node-2", "Node 2"));
        
        // When
        service.clear();
        
        // Then
        assertEquals(0, service.size());
        assertEquals(0, service.findAll().size());
    }
    
    @Test
    @DisplayName("リポジトリのサイズ確認")
    public void testSize() {
        // When & Then
        assertEquals(0, service.size());
        
        service.create(new TestTreeNode("node-1", "Node 1"));
        assertEquals(1, service.size());
        
        service.create(new TestTreeNode("node-2", "Node 2"));
        assertEquals(2, service.size());
    }
    
    @Test
    @DisplayName("重複IDでの作成は上書きされる")
    public void testCreateWithDuplicateId() {
        // Given
        service.create(new TestTreeNode("node-1", "Node 1"));
        
        // When
        service.create(new TestTreeNode("node-1", "Node 1 Updated"));
        
        // Then
        assertEquals(1, service.size());
        assertEquals("Node 1 Updated", service.findById("node-1").getName());
    }
    
    @Test
    @DisplayName("更新後に取得したノードは同じ参照")
    public void testUpdateReturnsSameReference() {
        // Given
        TestTreeNode node = new TestTreeNode("node-1", "Original");
        service.create(node);
        
        // When
        AbstractTreeNode updated = service.update(node);
        
        // Then
        assertSame(node, updated);
    }
    
    @Test
    @DisplayName("findAllはスナップショットを返す")
    public void testFindAllReturnsSnapshot() {
        // Given
        service.create(new TestTreeNode("node-1", "Node 1"));
        
        // When
        List<AbstractTreeNode> all = service.findAll();
        service.create(new TestTreeNode("node-2", "Node 2"));
        
        // Then - 元のリストは影響を受けない
        assertEquals(1, all.size());
        assertEquals(2, service.findAll().size());
    }
    
    @Test
    @DisplayName("クリア後に新規作成可能")
    public void testCreateAfterClear() {
        // Given
        service.create(new TestTreeNode("node-1", "Node 1"));
        service.clear();
        
        // When
        service.create(new TestTreeNode("node-2", "Node 2"));
        
        // Then
        assertEquals(1, service.size());
        assertNotNull(service.findById("node-2"));
        assertNull(service.findById("node-1"));
    }
    
    @Test
    @DisplayName("3段階層の子ノード削除")
    public void testDeleteThreeLevelHierarchy() {
        // Given
        TestTreeNode grandParent = new TestTreeNode("gp", "GrandParent");
        TestTreeNode parent = new TestTreeNode("p", "Parent");
        TestTreeNode child = new TestTreeNode("c", "Child");
        
        grandParent.addChild(parent);
        parent.addChild(child);
        
        service.create(grandParent);
        service.create(parent);
        service.create(child);
        
        // When
        service.delete("gp");
        
        // Then - deleteは再帰的に子ノードも削除するため、全て削除される
        assertNull(service.findById("gp"));
        assertNull(service.findById("p"));
        assertNull(service.findById("c"));
        assertEquals(0, service.size());
    }
}
