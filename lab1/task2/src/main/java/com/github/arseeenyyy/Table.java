package com.github.arseeenyyy;

public interface Table<K> {
    int insert(K k);
    int find(K k);
    int delete(K k);
}