# XeadModeler MVC化計画

## 概要
Modeler.java（44,438行）をMVCアーキテクチャに完全にリファクタリングする。

## 現在の状況
- `Modeler.java`: 44,438行のGod Class
  - UIコンポーネント初期化（jbInit*メソッド）
  - イベントハンドラ（ActionListener, ChangeListenerなど）
  - ビジネスロジック
  - 内部クラス（332個のclass宣言）
- 既存のMVC構成:
  - `model/` - XeadDocumentModelなど
  - `service/` - FileService, TreeServiceなど16ファイル
  - `controller/` - FileController, BaseControllerのみ
  - `view/` - Dialog類, XeadPagePainter

## 目標アーキテクチャ

### パッケージ構成
```
xeadModeler/
├── config/          # 設定クラス
├── controller/      # コントローラー層
│   ├── MainController.java          # メインメニュー操作
│   ├── TreeController.java          # ツリー操作
│   ├── TableController.java         # テーブル操作
│   ├── EditController.java          # 編集操作（Undo/Redo）
│   ├── ExportController.java        # エクスポート操作
│   ├── ImportController.java        # インポート操作
│   └── ScanController.java          # スキャン操作
├── model/           # モデル層（既存）
├── service/         # サービス層（既存を拡張）
└── view/            # ビュー層
    ├── MainView.java                # メインフレーム
    ├── MenuBarView.java             # メニューバー
    ├── TreeView.java                # ツリー表示
    ├── ContentView.java             # コンテンツペイン
    │   ├── SystemView.java          # システムノード
    │   ├── SubjectAreaView.java     # 主題領域ノード
    │   ├── TaskView.java            # タスクノード
    │   ├── SubsystemView.java       # サブシステムノード
    │   ├── TableView.java           # テーブルノード
    │   └── FunctionView.java        # 機能ノード
    ├── Dialog/                      # ダイアログ類（既存）
    └── XeadPagePainter.java         # 描画（既存）
```

## リファクタリング段階

### 段階1: インフラストラクチャ準備 ✓
- [x] MVCConfigを作成
- [x] BaseControllerのテンプレート準備
- [x] Service層のインターフェース整理

### 段階2: View層の抽出 ✓完了
- [ ] MainView.java - メインフレームのUIを抽出
- [x] MenuBarView.java - メニューバーを抽出
- [x] TreeView.java - ツリー表示を抽出
- [x] ContentView.java - コンテンツペインのベースを抽出
- [x] Modeler.javaにTreeView/ContentViewを統合
- [x] リストタイプのViewクラスを6つ抽出（SubjectAreaList, RoleList, SubsystemList, FunctionList, TableFieldList, TableKeyList）
- [x] ContentView.javaにリストタイプViewクラスを統合
- [x] SubjectAreaView.java - 主題領域ノードのUIを抽出
- [x] RoleView.java - ロールノードのUIを抽出
- [x] TaskView.java - タスクノードのUIを抽出
- [x] SubsystemView.java - サブシステムノードのUIを抽出
- [x] SystemView.java - システムノードのUIを抽出
- [x] TableListView.java - テーブルリストノードのUIを抽出
- [x] TableView.java - テーブルノードのUIを抽出
- [x] TableFieldView.java - テーブルフィールドノードのUIを抽出
- [x] TableKeyView.java - テーブルキーノードのUIを抽出
- [x] FunctionView.java - 機能ノードのUIを抽出
- [x] IOPanelView.java - IOパネルノードのUIを抽出
- [x] IOSpoolView.java - IOプールノードのUIを抽出
- [x] IOTableView.java - IOテーブルノードのUIを抽出
- [x] IOWebPageView.java - IOウェブページノードのUIを抽出
- [x] ContentView.javaに非リストタイプViewクラスを統合（14ファイル）
- [x] ContentView.javaにゲッターメソッドを追加

### 段階3: Controller層の構築 ✓完了
- [x] MainController.java - メインメニュー操作
- [x] TreeController.java - ツリー操作
- [x] TableController.java - テーブル操作
- [x] EditController.java - Undo/Redo操作
- [x] ExportController.java - エクスポート操作
- [x] ImportController.java - インポート操作
- [x] ScanController.java - スキャン操作

### 段階4: 内部クラスの抽出
- [x] TableModel内部クラスを独立したファイルに抽出（7ファイル）
  - [x] TableModelReadOnlyList.java
  - [x] TableModelEditableList.java
  - [x] TableModelFunctionsUsedByThis.java
  - [x] TableModelIOTableFieldList.java
  - [x] TableModelNativeTableList.java
  - [x] TableModelForeignTableList.java
  - [x] TableModelTableFieldList.java
- [x] Sortableモデル内部クラスを独立したファイルに抽出（4ファイル）
  - [x] SortableXeadTreeNodeComboBoxModel.java
  - [x] SortableXeadTreeNodeListModel.java
  - [x] SortableDomElementListModel.java
  - [x] SortableDomElementFieldListModel.java
- [x] Rendererクラスを独立したファイルに抽出（4ファイル）
  - [x] CheckBoxHeaderListener.java
  - [x] CheckBoxHeaderRenderer.java
  - [x] CheckBoxRenderer.java
  - [x] CustomTreeRenderer.java
- [x] Listenerクラスを独立したファイルに抽出（5ファイル）
  - [x] TabbedPaneChangeListener.java
  - [x] CustomUndoableEditListener.java
  - [x] TextPaneUndoAction.java
  - [x] TextPaneRedoAction.java
  - [x] TaskActionTextFieldListener.java
- [x] CreateStatementクラスをviewパッケージに移動
- [x] ChangeStateクラスをviewパッケージに移動
- [x] TableRowNumberクラスをviewパッケージに移動
- [x] XeadElementクラスをviewパッケージに移動
- [x] XeadFieldElementクラスをviewパッケージに移動
- [x] KanjiTextAreaクラスをviewパッケージに移動
- [x] DataflowBoundaryクラスをviewパッケージに移動
- [x] TableModelRelationshipListクラスをviewパッケージに移動
- [x] XeadPagePainterクラスをviewパッケージに移動
- [x] DataTypeMenuItemクラスをviewパッケージに移動
- [x] KanjiTextAreaListenerクラスをviewパッケージに移動
- [ ] DatamodelRelationshipLineクラスの抽出 - 保留（大きすぎる）
- [ ] ElementLabelクラスの抽出 - 保留（DatamodelEntityBoxの内部クラス）
- [ ] DropAcceptablePanelクラスの抽出 - 保留（DatamodelEntityBoxの内部クラス）
- [ ] Modelクラスをmodelパッケージに移動
- [ ] Utilityメソッドを適切なServiceに移動

### 段階5: 統合とテスト
- [ ] 依存性注入の設定
- [ ] 動作確認
- [ ] コンパイルテスト

## 注意事項
1. **動作の維持**: 各段階でアプリケーションが動作することを確認
2. **インポートの更新**: クラスを移動したら、インポートを適切に更新
3. **Spring Bootとの統合**: 既存の@Service, @Component注釈を活用
4. **エンコーディング**: Shift_JISでエンコードされたファイルに注意
