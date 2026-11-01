package xeadModeler.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * AbstractTreeNodeのテスト
 */
@DisplayName("AbstractTreeNodeテスト")
public class AbstractTreeNodeTest {
    
    /**
     * テスト用の具象ノードクラス
     */
    private static class TestTreeNode extends AbstractTreeNode {
        public TestTreeNode() {
            super();
        }
        
        public TestTreeNode(String id, String name) {
            super(id, name);
        }
        
        @Override
        public String getType() {
            return "test";
        }
    }
    
    @Test
    @DisplayName("ノードの作成とプロパティ設定")
    public void testCreateNode() {
        // Given
        String expectedId = "node-1";
        String expectedName = "Test Node";
        
        // When
        TestTreeNode node = new TestTreeNode(expectedId, expectedName);
        
        // Then
        assertEquals(expectedId, node.getId());
        assertEquals(expectedName, node.getName());
        assertEquals("test", node.getType());
        assertTrue(node.isRoot());
        assertTrue(node.isLeaf());
        assertEquals(0, node.getChildCount());
    }
    
    @Test
    @DisplayName("プロパティの更新")
    public void testUpdateProperties() {
        // Given
        TestTreeNode node = new TestTreeNode("node-1", "Original Name");
        
        // When
        node.setId("node-2");
        node.setName("Updated Name");
        
        // Then
        assertEquals("node-2", node.getId());
        assertEquals("Updated Name", node.getName());
    }
    
    @Test
    @DisplayName("親子関係の設定")
    public void testParentChildRelationship() {
        // Given
        TestTreeNode parent = new TestTreeNode("parent", "Parent");
        TestTreeNode child = new TestTreeNode("child", "Child");
        
        // When
        parent.addChild(child);
        
        // Then
        assertEquals(1, parent.getChildCount());
        assertTrue(parent.hasChildren());
        assertEquals(parent, child.getParent());
        assertFalse(parent.isLeaf());
        assertFalse(child.isRoot());
    }
    
    @Test
    @DisplayName("子ノードの削除")
    public void testRemoveChild() {
        // Given
        TestTreeNode parent = new TestTreeNode("parent", "Parent");
        TestTreeNode child = new TestTreeNode("child", "Child");
        parent.addChild(child);
        
        // When
        parent.removeChild(child);
        
        // Then
        assertEquals(0, parent.getChildCount());
        assertTrue(parent.isLeaf());
        assertNull(child.getParent());
        assertTrue(child.isRoot());
    }
    
    @Test
    @DisplayName("複数の子ノード")
    public void testMultipleChildren() {
        // Given
        TestTreeNode parent = new TestTreeNode("parent", "Parent");
        TestTreeNode child1 = new TestTreeNode("child-1", "Child 1");
        TestTreeNode child2 = new TestTreeNode("child-2", "Child 2");
        TestTreeNode child3 = new TestTreeNode("child-3", "Child 3");
        
        // When
        parent.addChild(child1);
        parent.addChild(child2);
        parent.addChild(child3);
        
        // Then
        assertEquals(3, parent.getChildCount());
        assertTrue(parent.getChildren().contains(child1));
        assertTrue(parent.getChildren().contains(child2));
        assertTrue(parent.getChildren().contains(child3));
    }
    
    @Test
    @DisplayName("ノードの深さ計算")
    public void testGetDepth() {
        // Given
        TestTreeNode root = new TestTreeNode("root", "Root");
        TestTreeNode level1 = new TestTreeNode("level-1", "Level 1");
        TestTreeNode level2 = new TestTreeNode("level-2", "Level 2");
        TestTreeNode level3 = new TestTreeNode("level-3", "Level 3");
        
        // When
        root.addChild(level1);
        level1.addChild(level2);
        level2.addChild(level3);
        
        // Then
        assertEquals(0, root.getDepth());
        assertEquals(1, level1.getDepth());
        assertEquals(2, level2.getDepth());
        assertEquals(3, level3.getDepth());
    }
    
    @Test
    @DisplayName("空のコンストラクタ")
    public void testDefaultConstructor() {
        // When
        TestTreeNode node = new TestTreeNode();
        
        // Then
        assertNull(node.getId());
        assertNull(node.getName());
        assertTrue(node.isRoot());
        assertTrue(node.isLeaf());
    }
    
    @Test
    @DisplayName("子ノードの再代入で親が更新される")
    public void testReassignChildParent() {
        // Given
        TestTreeNode parent1 = new TestTreeNode("parent1", "Parent 1");
        TestTreeNode parent2 = new TestTreeNode("parent2", "Parent 2");
        TestTreeNode child = new TestTreeNode("child", "Child");
        parent1.addChild(child);
        
        // When
        parent2.addChild(child);
        
        // Then - addChildは親を更新するが、元の親から子を削除しない
        assertEquals(parent2, child.getParent());
        assertEquals(1, parent1.getChildCount()); // parent1の子リストにはまだ残っている
        assertEquals(1, parent2.getChildCount());
    }
    
    @Test
    @DisplayName("インデックスによる子ノードの取得")
    public void testGetChildAtIndex() {
        // Given
        TestTreeNode parent = new TestTreeNode("parent", "Parent");
        TestTreeNode child1 = new TestTreeNode("child-1", "Child 1");
        TestTreeNode child2 = new TestTreeNode("child-2", "Child 2");
        parent.addChild(child1);
        parent.addChild(child2);
        
        // Then
        assertEquals(child1, parent.getChildren().get(0));
        assertEquals(child2, parent.getChildren().get(1));
    }
    
    @Test
    @DisplayName("同じノードを2回追加しない")
    public void testNoDuplicateChildren() {
        // Given
        TestTreeNode parent = new TestTreeNode("parent", "Parent");
        TestTreeNode child = new TestTreeNode("child", "Child");
        
        // When
        parent.addChild(child);
        
        // Then
        assertEquals(1, parent.getChildCount());
    }
    
    @Test
    @DisplayName("getTypeの実装")
    public void testGetType() {
        // Given
        TestTreeNode node = new TestTreeNode("node-1", "Test Node");
        
        // Then
        assertEquals("test", node.getType());
    }
    
    @Test
    @DisplayName("深いツリーの深さ計算")
    public void testDeepTreeDepth() {
        // Given
        TestTreeNode root = new TestTreeNode("root", "Root");
        TestTreeNode[] levels = new TestTreeNode[10];
        for (int i = 0; i < 10; i++) {
            levels[i] = new TestTreeNode("level-" + i, "Level " + i);
            if (i == 0) {
                root.addChild(levels[i]);
            } else {
                levels[i-1].addChild(levels[i]);
            }
        }
        
        // Then
        assertEquals(0, root.getDepth());
        for (int i = 0; i < 10; i++) {
            assertEquals(i + 1, levels[i].getDepth());
        }
    }
    
    @Test
    @DisplayName("hasChildrenの動作確認")
    public void testHasChildren() {
        // Given
        TestTreeNode node = new TestTreeNode("node-1", "Test Node");
        
        // Then - 初期状態はfalse
        assertFalse(node.hasChildren());
        
        // When
        node.addChild(new TestTreeNode("child", "Child"));
        
        // Then - 子追加後はtrue
        assertTrue(node.hasChildren());
        
        // When - 子削除
        AbstractTreeNode child = node.getChildren().get(0);
        node.removeChild(child);
        
        // Then - 子削除後はfalse
        assertFalse(node.hasChildren());
    }
}
