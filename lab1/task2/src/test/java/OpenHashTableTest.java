
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

import com.github.arseeenyyy.*;;

public class OpenHashTableTest {
    
    private OpenHashTable table;
    private final int TABLE_SIZE = 7;
    
    @BeforeEach
    public void setUp() {
        table = new OpenHashTable(TABLE_SIZE);
    }
        
    @Test
    public void testCreateEmptyTable() {
        assertNotNull(table);
        for (int i = 0; i < TABLE_SIZE; i++) {
            assertNull(table.getTable().get(i));
        }
        assertEquals(0, table.getElementsCount());
    }
    
    @Test
    public void testInsertAndFind() {
        int index = table.insert("cat");
        assertTrue(index >= 0 && index < TABLE_SIZE);
        assertEquals("cat", table.getTable().get(index).getKey());
        assertEquals(1, table.getElementsCount());
        
        int findIndex = table.find("cat");
        assertEquals(index, findIndex);
    }

    @Test
    public void testStatesInsert() {
        table.clearStateSequence();
        table.insert("cat");
        
        List<State> expected = Arrays.asList(
            State.INSERT
        );
        assertEquals(expected, table.getStateSequence());
    }
    
    @Test
    public void testStatesInsertDuplicate() {
        table.insert("cat");
        table.clearStateSequence();
        
        table.insert("cat");
        
        List<State> expected = Arrays.asList(
            State.INSERT_DUP
        );
        assertEquals(expected, table.getStateSequence());
    }
    
    @Test
    public void testStatesFindSuccess() {
        table.insert("cat");
        table.clearStateSequence();
        
        table.find("cat");
        
        List<State> expected = Arrays.asList(
            State.FIND_SUCCESS
        );
        assertEquals(expected, table.getStateSequence());
    }
    
    @Test
    public void testStatesFindFail() {
        table.clearStateSequence();
        table.find("cat");
        
        List<State> expected = Arrays.asList(
            State.FIND_FAIL
        );
        assertEquals(expected, table.getStateSequence());
    }
    
    @Test
    public void testStatesDeleteSuccess() {
        table.insert("cat");
        table.clearStateSequence();
        
        table.delete("cat");
        
        List<State> expected = Arrays.asList(
            State.DELETE
        );
        assertEquals(expected, table.getStateSequence());
    }
    
    @Test
    public void testStatesDeleteFail() {
        table.clearStateSequence();
        table.delete("cat");
        
        List<State> expected = Arrays.asList(
            State.DELETE_FAIL
        );
        assertEquals(expected, table.getStateSequence());
    }
    
    @Test
    public void testStatesMultipleOperations() {
        table.clearStateSequence();
        
        table.insert("cat");           
        table.insert("cat");           
        table.find("cat");             
        table.find("dog");             
        table.delete("cat");           
        table.delete("cat");           
        
        List<State> expected = Arrays.asList(
            State.INSERT,
            State.INSERT_DUP,
            State.FIND_SUCCESS,
            State.FIND_FAIL,
            State.DELETE,
            State.DELETE_FAIL
        );
        
        assertEquals(expected, table.getStateSequence());
    }
    
    @Test
    public void testStatesWithCollisions() {
        table.clearStateSequence();
        
        table.insert("cat");  
        table.insert("tac");  
        table.find("cat");    
        table.find("tac");    
        table.delete("cat");  
        table.find("cat");    

        List<State> expected = Arrays.asList(
            State.INSERT,
            State.INSERT_DUP,
            State.FIND_SUCCESS,
            State.FIND_SUCCESS,
            State.DELETE,
            State.FIND_FAIL
        );
        
        assertEquals(expected, table.getStateSequence());
    }
    
}