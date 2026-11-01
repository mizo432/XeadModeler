package xeadModeler.controller;

import xeadModeler.model.Model;
import xeadModeler.service.ModelService;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * コントローラーの基底クラス
 * サービス層との連携を提供します。
 */
public abstract class BaseController<M extends Model> {
    
    @Autowired
    protected ModelService<M> modelService;
    
    /**
     * サービスを取得します
     * @return モデルサービス
     */
    protected ModelService<M> getService() {
        return modelService;
    }
    
    /**
     * サービスを設定します（テスト用）
     * @param service モデルサービス
     */
    public void setService(ModelService<M> service) {
        this.modelService = service;
    }
}
