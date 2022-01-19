package com.wang.base.generics;

import java.util.Collection;


public interface TreeModel {


    <T extends TreeModel> void setChildren(Collection<T> children);

    Number getSelfId();

    Number getSelfParentId();

    boolean getSelfRoot();

}
