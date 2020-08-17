package com.da.learn.leetcode.lru;

import java.util.HashMap;

public class LRUCache {
    private HashMap<String, DoubleList.Node> map;
    private DoubleList cache;
    private int cap;

    public String get(String key) {
        if (!map.containsKey(key)) {
            return null;
        }
        makeRecently(key);
        return map.get(key).value;
    }

    public void put(String key, String value) {
        if (map.containsKey(key)) {
            deleteKey(key);
            addRecently(key, value);
            return;
        }
        if (cap == cache.size()) {
            removeLeastRecently();
        }
        addRecently(key, value);
    }

    /* 将某个 key 提升为最近使用的 */
    private void makeRecently(String key) {
        DoubleList.Node x = map.get(key);
        // 先从链表中删除这个节点
        cache.remove(x);
        // 重新插到队尾
        cache.addLast(x);
    }

    /* 添加最近使用的元素 */
    private void addRecently(String key, String val) {
        DoubleList.Node x = new DoubleList.Node(key, val);
        // 链表尾部就是最近使用的元素
        cache.addLast(x);
        // 别忘了在 map 中添加 key 的映射
        map.put(key, x);
    }

    /* 删除某一个 key */
    private void deleteKey(String key) {
        DoubleList.Node x = map.get(key);
        // 从链表中删除
        cache.remove(x);
        // 从 map 中删除
        map.remove(key);
    }

    /* 删除最久未使用的元素 */
    private void removeLeastRecently() {
        // 链表头部的第一个元素就是最久未使用的
        DoubleList.Node deletedNode = cache.removeFirst();
        // 同时别忘了从 map 中删除它的 key
        String deletedKey = deletedNode.key;
        map.remove(deletedKey);
    }
}
