package xeadModeler.service;

import xeadModeler.model.Model;

import java.util.List;

/**
 * モデルサービスの基底インターフェース
 * データモデルに対するCRUD操作を定義します。
 */
public interface ModelService<M extends Model> {
    
    /**
     * IDでモデルを取得します
     * @param id モデルID
     * @return モデルオブジェクト
     */
    M findById(String id);
    
    /**
     * すべてのモデルを取得します
     * @return モデルリスト
     */
    List<M> findAll();
    
    /**
     * 新しいモデルを作成します
     * @param model 作成するモデル
     * @return 作成されたモデル
     */
    M create(M model);
    
    /**
     * モデルを更新します
     * @param model 更新するモデル
     * @return 更新されたモデル
     */
    M update(M model);
    
    /**
     * モデルを削除します
     * @param id 削除するモデルのID
     */
    void delete(String id);
}
