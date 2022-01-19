package com.wang.base.generics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class TreeUtils {


    public static <T extends TreeModel> Collection<T> buildTree(Collection<T> regionNodes) {
        Collection<T> rootNodes = new ArrayList<>();
        //根节点
        Iterator<T> iterator = regionNodes.iterator();
        while (iterator.hasNext()) {
            T next = iterator.next();
            if (next.getSelfRoot()||next.getSelfParentId().equals(0)) {
                rootNodes.add(next);
                iterator.remove();
            }
        }
        //遍历，找到二级节点
        for (T rootNode : rootNodes) {
            Collection<T> children = getChildren(rootNode, regionNodes);
            rootNode.setChildren(children);
        }
        return rootNodes;
    }


    public static <T extends TreeModel> Collection<T> getChildren(T rootNode, Collection<T> regionNodes) {


        //子节点列表
        Collection<T> childList = new ArrayList<>();
        Iterator<T> it = regionNodes.iterator();

        while (it.hasNext()) {
            T node = it.next();
            if (rootNode.getSelfId().equals(node.getSelfParentId())) {
                childList.add(node);
                it.remove();
            }
        }

        //遍历 递归获取子节点的子节点
        for (T childNode : childList) {
            Collection<T> child = getChildren(childNode, regionNodes);
            childNode.setChildren(child);
        }
        //递归出口  childList长度为0
        if (childList.size() == 0) {
            return new ArrayList<>();
        }

        return childList;
    }

}
