package xeadModeler.service;

import xeadModeler.model.ChangeState;

import org.springframework.stereotype.Service;

/**
 * 変更状態管理サービス
 * ChangeStateモデルをラップし、変更状態の管理を担当します。
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
@Service
public class ChangeStateService {
    
    private final ChangeState changeState;
    
    public ChangeStateService() {
        this.changeState = new ChangeState();
    }
    
    /**
     * 変更状態を設定します
     * @param state 変更状態
     */
    public void setChanged(boolean state) {
        changeState.setChanged(state);
    }
    
    /**
     * 変更状態を返します
     * @return 変更されている場合true
     */
    public boolean isChanged() {
        return changeState.isChanged();
    }
    
    /**
     * 変更状態をリセットします
     */
    public void reset() {
        changeState.reset();
    }
    
    /**
     * 変更状態リスナーを設定します
     * @param listener リスナー
     */
    public void setListener(ChangeState.ChangeStateListener listener) {
        changeState.setListener(listener);
    }
    
    /**
     * 内部のChangeStateインスタンスを返します
     * @return ChangeState
     */
    public ChangeState getChangeState() {
        return changeState;
    }
}
