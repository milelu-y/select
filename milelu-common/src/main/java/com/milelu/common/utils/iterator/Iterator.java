package com.milelu.common.utils.iterator;

public interface Iterator {

    /**
     * 是否存在下一个属性
     * @return
     */
    boolean hasNext();

    /**
     * 获取下一个属性
     * @return
     */
    Object next();
}
