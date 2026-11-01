package xeadModeler.service;

import javax.swing.*;
import java.awt.*;

/**
 * ダイアログ操作サービスのインターフェース
 * メッセージダイアログ、確認ダイアログなどの機能を担当します。
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public interface DialogService {
    
    /**
     * メッセージダイアログを表示します。
     * @param parentComponent 親コンポーネント
     * @param message メッセージ
     */
    void showMessageDialog(Component parentComponent, Object message);
    
    /**
     * メッセージダイアログをタイトル付きで表示します。
     * @param parentComponent 親コンポーネント
     * @param message メッセージ
     * @param title タイトル
     * @param messageType メッセージタイプ
     */
    void showMessageDialog(Component parentComponent, Object message, String title, int messageType);
    
    /**
     * 確認ダイアログを表示します。
     * @param parentComponent 親コンポーネント
     * @param message メッセージ
     * @return ユーザーの選択結果
     */
    int showConfirmDialog(Component parentComponent, Object message);
    
    /**
     * 確認ダイアログをタイトル付きで表示します。
     * @param parentComponent 親コンポーネント
     * @param message メッセージ
     * @param title タイトル
     * @param optionType オプションタイプ
     * @return ユーザーの選択結果
     */
    int showConfirmDialog(Component parentComponent, Object message, String title, int optionType);
    
    /**
     * オプションダイアログを表示します。
     * @param parentComponent 親コンポーネント
     * @param message メッセージ
     * @param title タイトル
     * @param messageType メッセージタイプ
     * @param options オプション配列
     * @return ユーザーの選択結果
     */
    int showOptionDialog(Component parentComponent, Object message, String title, int messageType, Object[] options);
}
