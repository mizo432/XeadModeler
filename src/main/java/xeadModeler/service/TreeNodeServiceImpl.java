package xeadModeler.service;

import xeadModeler.model.AbstractTreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

/**
 * ツリーノードサービスの基本実装
 * メモリ上のツリー構造を管理します。
 */
@Service
public class TreeNodeServiceImpl implements ModelService<AbstractTreeNode> {
    
    private final Map<String, AbstractTreeNode> repository = new ConcurrentHashMap<>();
    
    @Override
    public AbstractTreeNode findById(String id) {
        return repository.get(id);
    }
    
    @Override
    public List<AbstractTreeNode> findAll() {
        return new ArrayList<>(repository.values());
    }
    
    @Override
    public AbstractTreeNode create(AbstractTreeNode node) {
        if (node.getId() == null || node.getId().isEmpty()) {
            throw new IllegalArgumentException("Node ID must not be null or empty");
        }
        repository.put(node.getId(), node);
        return node;
    }
    
    @Override
    public AbstractTreeNode update(AbstractTreeNode node) {
        if (!repository.containsKey(node.getId())) {
            throw new IllegalStateException("Node not found: " + node.getId());
        }
        repository.put(node.getId(), node);
        return node;
    }
    
    @Override
    public void delete(String id) {
        AbstractTreeNode node = repository.remove(id);
        if (node != null) {
            // 子ノードも再帰的に削除
            for (AbstractTreeNode child : node.getChildren()) {
                delete(child.getId());
            }
        }
    }
    
    /**
     * リポジトリをクリアします
     */
    public void clear() {
        repository.clear();
    }
    
    /**
     * リポジトリのサイズを返します
     * @return ノード数
     */
    public int size() {
        return repository.size();
    }
}
