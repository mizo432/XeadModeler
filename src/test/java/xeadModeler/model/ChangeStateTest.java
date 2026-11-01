package xeadModeler.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ChangeStateのテスト
 */
@DisplayName("ChangeStateテスト")
public class ChangeStateTest {
    
    private ChangeState changeState;
    
    @BeforeEach
    public void setUp() {
        changeState = new ChangeState();
    }
    
    @Test
    @DisplayName("初期状態は未変更")
    public void testInitialState() {
        assertFalse(changeState.isChanged());
    }
    
    @Test
    @DisplayName("変更状態を設定")
    public void testSetChanged() {
        changeState.setChanged(true);
        assertTrue(changeState.isChanged());
        
        changeState.setChanged(false);
        assertFalse(changeState.isChanged());
    }
    
    @Test
    @DisplayName("リセット機能")
    public void testReset() {
        changeState.setChanged(true);
        assertTrue(changeState.isChanged());
        
        changeState.reset();
        assertFalse(changeState.isChanged());
    }
    
    @Test
    @DisplayName("リスナー通知")
    public void testListenerNotification() {
        final boolean[] called = {false};
        final boolean[] stateValue = {false};
        
        changeState.setListener(new ChangeState.ChangeStateListener() {
            @Override
            public void onStateChanged(boolean changed) {
                called[0] = true;
                stateValue[0] = changed;
            }
        });
        
        changeState.setChanged(true);
        assertTrue(called[0]);
        assertTrue(stateValue[0]);
        
        called[0] = false;
        changeState.setChanged(false);
        assertTrue(called[0]);
        assertFalse(stateValue[0]);
    }
    
    @Test
    @DisplayName("同じ状態を設定すると通知されない")
    public void testNoNotificationOnSameState() {
        final int[] callCount = {0};
        
        changeState.setListener(new ChangeState.ChangeStateListener() {
            @Override
            public void onStateChanged(boolean changed) {
                callCount[0]++;
            }
        });
        
        changeState.setChanged(false); // 初期状態と同じ
        assertEquals(0, callCount[0]);
        
        changeState.setChanged(true);
        assertEquals(1, callCount[0]);
        
        changeState.setChanged(true); // 同じ状態
        assertEquals(1, callCount[0]);
    }
    
    @Test
    @DisplayName("リスナー設定と取得")
    public void testSetAndGetListener() {
        ChangeState.ChangeStateListener listener = changed -> {};
        changeState.setListener(listener);
        assertSame(listener, changeState.getListener());
    }
    
    @Test
    @DisplayName("リスナーがnullの場合は通知されない")
    public void testNoListenerNoNotification() {
        // Given - リスナーを設定しない
        
        // When
        changeState.setChanged(true);
        
        // Then - 例外が発生しない
        assertTrue(changeState.isChanged());
    }
    
    @Test
    @DisplayName("resetは変更状態をfalseに設定し通知する")
    public void testResetNotifiesListener() {
        // Given
        final boolean[] notifiedState = {false};
        changeState.setListener(changed -> notifiedState[0] = changed);
        changeState.setChanged(true);
        
        // When
        changeState.reset();
        
        // Then
        assertFalse(changeState.isChanged());
        assertFalse(notifiedState[0]);
    }
    
    @Test
    @DisplayName("リスナーの更新")
    public void testListenerUpdate() {
        // Given
        final int[] callCount1 = {0};
        final int[] callCount2 = {0};
        
        ChangeState.ChangeStateListener listener1 = changed -> callCount1[0]++;
        ChangeState.ChangeStateListener listener2 = changed -> callCount2[0]++;
        
        changeState.setListener(listener1);
        changeState.setChanged(true);
        assertEquals(1, callCount1[0]);
        assertEquals(0, callCount2[0]);
        
        // When - リスナーを更新
        changeState.setListener(listener2);
        changeState.setChanged(false);
        
        // Then - 新しいリスナーのみ通知される
        assertEquals(1, callCount1[0]);
        assertEquals(1, callCount2[0]);
    }
    
    @Test
    @DisplayName("複数の状態変更")
    public void testMultipleStateChanges() {
        // Given
        final int[] trueCount = {0};
        final int[] falseCount = {0};
        changeState.setListener(changed -> {
            if (changed) trueCount[0]++;
            else falseCount[0]++;
        });
        
        // When
        changeState.setChanged(true);
        changeState.setChanged(false);
        changeState.setChanged(true);
        
        // Then
        assertEquals(2, trueCount[0]);
        assertEquals(1, falseCount[0]);
        assertTrue(changeState.isChanged());
    }
}
