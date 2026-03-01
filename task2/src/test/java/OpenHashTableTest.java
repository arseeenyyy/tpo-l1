
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.github.arseeenyyy.*;

public class OpenHashTableTest {
    
    private OpenHashTable table;
    private final int TABLE_SIZE = 7;
    
    @BeforeEach
    public void setUp() {
        table = new OpenHashTable(TABLE_SIZE);
    }
    
    @Test
    public void testCreateEmptyTable() {
        Assertions.assertNotNull(table);
        for (int i = 0; i < TABLE_SIZE; i++) {
            Assertions.assertNull(table.getTable().get(i));
        }
    }
    
    @Test
    public void testInsertSingleElement() {
        int index = table.insert("cat");
        Assertions.assertTrue(index >= 0 && index < TABLE_SIZE);
        Assertions.assertEquals("cat", table.getTable().get(index).getKey());
        Assertions.assertEquals(1, table.getElementsCount());
    }
    
    @Test
    public void testInsertMultipleElements() {
        table.insert("cat");
        table.insert("dog");
        table.insert("bird");
        table.insert("fish");
        
        Assertions.assertEquals(4, table.getElementsCount());
    }
    
    @Test
    public void testInsertDuplicateElement() {
        int index1 = table.insert("cat");
        int index2 = table.insert("cat");
        
        Assertions.assertEquals(index1, index2);
        Assertions.assertEquals(1, table.getElementsCount());
        
        ListNode head = table.getTable().get(index1);
        int count = 0;
        while (head != null) {
            count++;
            head = head.getNext();
        }
        Assertions.assertEquals(1, count);
    }
    
    @Test
    public void testFindExistingElement() {
        int expectedIndex = table.insert("cat");
        int actualIndex = table.find("cat");
        
        Assertions.assertEquals(expectedIndex, actualIndex);
    }
    
    @Test
    public void testFindNonExistingElement() {
        table.insert("cat");
        int index = table.find("dog");
        
        Assertions.assertEquals(-1, index);
    }
    
    @Test
    public void testDeleteExistingElement() {
        table.insert("cat");
        table.insert("dog");
        
        int deleteIndex = table.delete("cat");
        Assertions.assertTrue(deleteIndex >= 0);
        Assertions.assertEquals(-1, table.find("cat"));
        Assertions.assertNotEquals(-1, table.find("dog"));
        Assertions.assertEquals(1, table.getElementsCount());
    }
    
    @Test
    public void testDeleteNonExistingElement() {
        table.insert("cat");
        int result = table.delete("dog");
        
        Assertions.assertEquals(-1, result);
        Assertions.assertEquals(1, table.getElementsCount());
    }
    
    @Test
    public void testCollisionAndChaining() {
        String str1 = "cat";
        String str2 = "tac";
        
        int index1 = table.insert(str1);
        int index2 = table.insert(str2);
        
        Assertions.assertEquals(index1, index2);
        
        ListNode head = table.getTable().get(index1);
        Assertions.assertEquals(str2, head.getKey());
        Assertions.assertEquals(str1, head.getNext().getKey());
        Assertions.assertNull(head.getNext().getNext());
        
        Assertions.assertEquals(2, table.getElementsCount());
    }
    
    @Test
    public void testMultipleCollisions() {
        String[] words = {"cat", "tac", "act", "atc"};
        
        int baseIndex = -1;
        for (String word : words) {
            int index = table.insert(word);
            if (baseIndex == -1) baseIndex = index;
            Assertions.assertEquals(baseIndex, index);
        }
        
        Assertions.assertEquals(words.length, table.getElementsCount());
        
        ListNode head = table.getTable().get(baseIndex);
        int chainLength = 0;
        while (head != null) {
            chainLength++;
            head = head.getNext();
        }
        Assertions.assertEquals(words.length, chainLength);
    }
    
    @Test
    public void testFindInChain() {
        table.insert("cat");
        table.insert("tac");
        table.insert("act");
        
        Assertions.assertNotEquals(-1, table.find("cat"));
        Assertions.assertNotEquals(-1, table.find("tac"));
        Assertions.assertNotEquals(-1, table.find("act"));
        Assertions.assertEquals(-1, table.find("dog"));
    }
    
    @Test
    public void testDeleteFromChain() {
        int index = table.insert("cat");
        table.insert("tac");
        table.insert("act");
        
        Assertions.assertEquals(3, table.getElementsCount());
        
        int deleteIndex = table.delete("tac");
        Assertions.assertEquals(index, deleteIndex);
        Assertions.assertEquals(2, table.getElementsCount());
        
        Assertions.assertNotEquals(-1, table.find("cat"));
        Assertions.assertEquals(-1, table.find("tac"));
        Assertions.assertNotEquals(-1, table.find("act"));
        
        ListNode head = table.getTable().get(index);
        Assertions.assertEquals("act", head.getKey());
        Assertions.assertEquals("cat", head.getNext().getKey());
        Assertions.assertNull(head.getNext().getNext());
    }
    
    @Test
    public void testDeleteFirstInChain() {
        int index = table.insert("cat");
        table.insert("tac");
        table.insert("act");
        
        int deleteIndex = table.delete("act");
        Assertions.assertEquals(index, deleteIndex);
        Assertions.assertEquals(2, table.getElementsCount());
        
        ListNode head = table.getTable().get(index);
        Assertions.assertEquals("tac", head.getKey());
        Assertions.assertEquals("cat", head.getNext().getKey());
        Assertions.assertNull(head.getNext().getNext());
    }
    
    @Test
    public void testDeleteLastInChain() {
        int index = table.insert("cat");
        table.insert("tac");
        table.insert("act");
        
        int deleteIndex = table.delete("cat");
        Assertions.assertEquals(index, deleteIndex);
        Assertions.assertEquals(2, table.getElementsCount());
        
        ListNode head = table.getTable().get(index);
        Assertions.assertEquals("act", head.getKey());
        Assertions.assertEquals("tac", head.getNext().getKey());
        Assertions.assertNull(head.getNext().getNext());
    }
    
    @Test
    public void testHashFunction() {
        String str1 = "aaa";
        String str2 = "aaa";
        
        int index1 = table.insert(str1);
        int index2 = table.insert(str2);
        
        Assertions.assertEquals(index1, index2);
    }
    
    @Test
    public void testInsertAndFindAfterDelete() {
        table.insert("cat");
        table.insert("tac");
        table.delete("cat");
        
        int findIndex = table.find("tac");
        Assertions.assertNotEquals(-1, findIndex);
        
        int newIndex = table.insert("dog");
        Assertions.assertNotEquals(-1, newIndex);
    }
    
    @Test
    public void testMultipleOperations() {
        table.insert("cat");
        table.insert("dog");
        table.insert("tac");
        table.insert("bird");
        
        Assertions.assertEquals(4, table.getElementsCount());
        
        table.delete("tac");
        Assertions.assertEquals(3, table.getElementsCount());
        
        table.insert("fish");
        table.insert("cat");
        
        Assertions.assertEquals(4, table.getElementsCount());
        
        Assertions.assertNotEquals(-1, table.find("cat"));
        Assertions.assertNotEquals(-1, table.find("dog"));
        Assertions.assertEquals(-1, table.find("tac"));
        Assertions.assertNotEquals(-1, table.find("bird"));
        Assertions.assertNotEquals(-1, table.find("fish"));
    }
    
    @Test
    public void testEmptyString() {
        int index = table.insert("");
        Assertions.assertTrue(index >= 0 && index < TABLE_SIZE);
        Assertions.assertEquals("", table.getTable().get(index).getKey());
        Assertions.assertNotEquals(-1, table.find(""));
    }
    
    @Test
    public void testSameHashDifferentStrings() {
        String str1 = "ab";
        String str2 = "ba";
        
        int index1 = table.insert(str1);
        int index2 = table.insert(str2);
        
        if (index1 == index2) {
            ListNode head = table.getTable().get(index1);
            Assertions.assertEquals(str2, head.getKey());
            Assertions.assertEquals(str1, head.getNext().getKey());
        }
    }
}