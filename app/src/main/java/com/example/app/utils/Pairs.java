package com.example.app.utils;

import java.util.ArrayList;

public class Pairs<K,V> {

    private ArrayList<K> key;
    private ArrayList<V> val;

    public Pairs() {
        this.key = new ArrayList<>();
        this.val = new ArrayList<>();
    }

    public void add(K k, V v){
        key.add(k);
        val.add(v);
    }

    public void clear(){
        key.clear();
        val.clear();
    }

    public ArrayList<K> getKey(){
        return key;
    }

    public ArrayList<V> getVal(){
        return val;
    }

    public K getOneKey(int pos){
        return key.get(pos);
    }

    public V getOneVal(int pos){
        return val.get(pos);
    }

    public void setKey(K k, int pos){
        key.set(pos,k);
    }

    public void setVal(V v, int pos){
        val.set(pos,v);
    }
}
