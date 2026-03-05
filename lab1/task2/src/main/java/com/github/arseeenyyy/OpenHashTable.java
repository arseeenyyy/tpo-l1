package com.github.arseeenyyy;

import java.util.ArrayList;
import java.util.List;

public class OpenHashTable implements Table<String> {
    
    private final List<ListNode> table;
    private int elementsCount;
    private final int tableSize;
    private List<State> stateSequence;
    
    public OpenHashTable(int tableSize) {
        this.table = new ArrayList<>();
        for (int i = 0; i < tableSize; i++) {
            table.add(null);
        }
        this.tableSize = tableSize;
        this.elementsCount = 0;
        this.stateSequence = new ArrayList<>();
    }
    
    public void clearStateSequence() {
        stateSequence.clear();
    }
    
    public List<State> getStateSequence() {
        return new ArrayList<>(stateSequence);
    }
    
    private int hash(String key) {
        if (key == null || key.isEmpty()) {
            return 0;
        }
        
        int hash = 0;
        for (int i = 0; i < key.length(); i++) {
            hash = hash + (int) key.charAt(i);
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
            stateSequence.add(State.INSERT);
            return index;
        }
        
        ListNode current = head;
        while (current != null) {
            if (current.getKey().equals(key)) {
                stateSequence.add(State.INSERT_DUP);
                return index;
            }
            current = current.getNext();
        }
        
        newNode.setNext(head);
        table.set(index, newNode);
        elementsCount++;
        stateSequence.add(State.INSERT_DUP);
        return index;
    }
    
    @Override
    public int find(String key) {
        int index = hash(key);
        ListNode current = table.get(index);
        
        while (current != null) {
            if (current.getKey().equals(key)) {
                stateSequence.add(State.FIND_SUCCESS);
                return index;
            }
            current = current.getNext();
        }
        
        stateSequence.add(State.FIND_FAIL);
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
                stateSequence.add(State.DELETE);
                return index;
            }
            prev = current;
            current = current.getNext();
        }
        
        stateSequence.add(State.DELETE_FAIL);
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