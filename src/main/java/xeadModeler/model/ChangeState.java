package xeadModeler.model;

/**
 * 変更状態を管理するモデルクラス
 * UI依存を除去した純粋な状態管理を提供します。
 * 
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class ChangeState {
    private boolean changed;
    
    /**
     * 変更状態リスナーインターフェース
     */
    public interface ChangeStateListener {
        /**
         * 変更状態が変更されたときに呼び出されます
         * @param changed 変更状態
         */
        void onStateChanged(boolean changed);
    }
    
    private ChangeStateListener listener;
    
    /**
     * 変更状態を作成します（未変更状態）
     */
    public ChangeState() {
        this.changed = false;
    }
    
    /**
     * 変更状態を設定します
     * @param state 変更状態
     */
    public void setChanged(boolean state) {
        boolean oldState = this.changed;
        this.changed = state;
        if (oldState != state && listener != null) {
            listener.onStateChanged(state);
        }
    }
    
    /**
     * 変更状態を返します
     * @return 変更されている場合true
     */
    public boolean isChanged() {
        return changed;
    }
    
    /**
     * 変更状態をリセットします
     */
    public void reset() {
        this.changed = false;
        if (listener != null) {
            listener.onStateChanged(false);
        }
    }
    
    /**
     * 変更状態リスナーを設定します
     * @param listener リスナー
     */
    public void setListener(ChangeStateListener listener) {
        this.listener = listener;
    }
    
    /**
     * 変更状態リスナーを返します
     * @return リスナー
     */
    public ChangeStateListener getListener() {
        return listener;
    }
}
