package xeadModeler.config;

import xeadModeler.model.XeadDocumentModel;
import xeadModeler.service.FileService;
import xeadModeler.service.FileServiceImpl;
import xeadModeler.service.ChangeStateService;
import xeadModeler.service.XeadUndoManager;
import xeadModeler.service.XmlService;
import xeadModeler.service.XmlServiceImpl;
import xeadModeler.service.DrawingService;
import xeadModeler.service.DrawingServiceImpl;
import xeadModeler.service.TreeService;
import xeadModeler.service.TreeServiceImpl;
import xeadModeler.service.TableService;
import xeadModeler.service.TableServiceImpl;
import xeadModeler.service.DialogService;
import xeadModeler.service.DialogServiceImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MVCアーキテクチャの設定クラス
 * Spring Beanの依存性注入を設定します。
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
@Configuration
public class MvcConfig {
    
    /**
     * XEADドキュメントモデルのBeanを定義します
     * @return XeadDocumentModel
     */
    @Bean
    public XeadDocumentModel xeadDocumentModel() {
        return new XeadDocumentModel();
    }
    
    /**
     * 変更状態サービスのBeanを定義します
     * @return ChangeStateService
     */
    @Bean
    public ChangeStateService changeStateService() {
        return new ChangeStateService();
    }
    
    /**
     * アンドゥマネージャーのBeanを定義します
     * @return XeadUndoManager
     */
    @Bean
    public XeadUndoManager xeadUndoManager() {
        XeadUndoManager undoManager = new XeadUndoManager();
        undoManager.setChangeState(changeStateService().getChangeState());
        return undoManager;
    }
    
    /**
     * ファイル操作サービスのBeanを定義します
     * @return FileService
     */
    @Bean
    public FileService fileService() {
        return new FileServiceImpl(
            xeadDocumentModel(),
            xeadUndoManager(),
            changeStateService()
        );
    }
    
    /**
     * XMLデータ管理サービスのBeanを定義します
     * @return XmlService
     */
    @Bean
    public XmlService xmlService() {
        return new XmlServiceImpl(xeadDocumentModel());
    }
    
    /**
     * 描画サービスのBeanを定義します
     * @return DrawingService
     */
    @Bean
    public DrawingService drawingService() {
        return new DrawingServiceImpl();
    }
    
    /**
     * ツリー操作サービスのBeanを定義します
     * @return TreeService
     */
    @Bean
    public TreeService treeService() {
        return new TreeServiceImpl(xeadDocumentModel());
    }
    
    /**
     * テーブル操作サービスのBeanを定義します
     * @return TableService
     */
    @Bean
    public TableService tableService() {
        return new TableServiceImpl(xeadDocumentModel());
    }
    
    /**
     * ダイアログ操作サービスのBeanを定義します
     * @return DialogService
     */
    @Bean
    public DialogService dialogService() {
        return new DialogServiceImpl();
    }
}
