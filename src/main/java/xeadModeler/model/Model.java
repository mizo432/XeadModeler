package xeadModeler.model;

/**
 * モデルの基底インターフェース
 * すべてのデータモデルが実装する基本契約を定義します。
 */
public interface Model {
    
    /**
     * モデルの一意な識別子を返します
     * @return モデルID
     */
    String getId();
    
    /**
     * モデルの名前を返します
     * @return モデル名
     */
    String getName();
    
    /**
     * モデルのタイプを返します
     * @return モデルタイプ
     */
    String getType();
}
