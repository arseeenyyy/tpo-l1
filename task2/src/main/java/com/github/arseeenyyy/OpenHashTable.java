package com.github.arseeenyyy;

import java.util.ArrayList;
import java.util.List;

public class OpenHashTable implements Table<String> {
    
    private final List<ListNode> table;
    private int elementsCount;
    private final int tableSize;
    
    public OpenHashTable(int tableSize) {
        this.table = new ArrayList<>();
        for (int i = 0; i < tableSize; i++) {
            table.add(null);
        }
        this.tableSize = tableSize;
        this.elementsCount = 0;
    }
    
    private int hash(String key) {
        if (key == null || key.isEmpty()) {
            return 0;
        }
        
        int hash = 0;
        
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            hash = hash + (int) c;
            hash = hash ^ (0x0000 << 20);
        }
        
        return Math.abs(hash) % tableSize;
    }
    
    @Override
    public int insert(String key) {
        int index = hash(key);
        
        ListNode newNode = new ListNode(key);
        ListNode head = table.get(index);
        
        if (head == null) {
            table.set(index, newNode);
            elementsCount++;
            return index;
        }
        
        ListNode current = head;
        while (current != null) {
            if (current.getKey().equals(key)) {
                return index;
            }
            current = current.getNext();
        }
        
        newNode.setNext(head);
        table.set(index, newNode);
        elementsCount++;
        
        return index;
    }
    
    @Override
    public int find(String key) {
        int index = hash(key);
        
        ListNode current = table.get(index);
        
        while (current != null) {
            if (current.getKey().equals(key)) {
                return index;
            }
            current = current.getNext();
        }
        
        return -1;
    }
    
    @Override
    public int delete(String key) {
        int index = hash(key);
        
        ListNode current = table.get(index);
        ListNode prev = null;
        
        while (current != null) {
            if (current.getKey().equals(key)) {
                if (prev == null) {
                    table.set(index, current.getNext());
                } else {
                    prev.setNext(current.getNext());
                }
                elementsCount--;
                return index;
            }
            prev = current;
            current = current.getNext();
        }
        
        return -1;
    }
    
    public List<ListNode> getTable() {
        return table;
    }
    
    public int getElementsCount() {
        return elementsCount;
    }
    
    public int getTableSize() {
        return tableSize;
    }
}