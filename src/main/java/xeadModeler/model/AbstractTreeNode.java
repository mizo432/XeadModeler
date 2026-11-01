package xeadModeler.model;

import java.util.ArrayList;
import java.util.List;

/**
 * XEADツリーノードの基底クラス
 * データモデルの階層構造を表します。
 */
public abstract class AbstractTreeNode implements Model {
    
    private String id;
    private String name;
    private AbstractTreeNode parent;
    private List<AbstractTreeNode> children;
    
    public AbstractTreeNode() {
        this.children = new ArrayList<>();
    }
    
    public AbstractTreeNode(String id, String name) {
        this.id = id;
        this.name = name;
        this.children = new ArrayList<>();
    }
    
    @Override
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public AbstractTreeNode getParent() {
        return parent;
    }
    
    public void setParent(AbstractTreeNode parent) {
        this.parent = parent;
    }
    
    public List<AbstractTreeNode> getChildren() {
        return children;
    }
    
    public void addChild(AbstractTreeNode child) {
        this.children.add(child);
        child.setParent(this);
    }
    
    public void removeChild(AbstractTreeNode child) {
        this.children.remove(child);
        child.setParent(null);
    }
    
    public int getChildCount() {
        return children.size();
    }
    
    public boolean hasChildren() {
        return !children.isEmpty();
    }
    
    /**
     * ノードの深さを返します
     * @return ルートからの深さ
     */
    public int getDepth() {
        int depth = 0;
        AbstractTreeNode current = parent;
        while (current != null) {
            depth++;
            current = current.getParent();
        }
        return depth;
    }
    
    /**
     * ルートノードかどうかを返します
     * @return ルートノードの場合true
     */
    public boolean isRoot() {
        return parent == null;
    }
    
    /**
     * リーフノードかどうかを返します
     * @return リーフノードの場合true
     */
    public boolean isLeaf() {
        return children.isEmpty();
    }
}
