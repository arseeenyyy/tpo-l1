package com.github.arseeenyyy;


public class ListNode {
    private String key;
    private ListNode next;
    
    public ListNode(String key) {
        this.key = key;
        this.next = null;
    }
    
    public String getKey() {
        return key;
    }
    
    public void setKey(String key) {
        this.key = key;
    }
    
    public ListNode getNext() {
        return next;
    }
    
    public void setNext(ListNode next) {
        this.next = next;
    }
}