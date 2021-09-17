package com.util.port;

public interface ILink<E> {
    public void add(E data);		//添加
    public int size();				//长度
    public boolean isEmpty();		//判断链表是否为空
    public Object[] toArray();		//数据集合
    public E get(int index);		//根据索引取得数据
    public void set(int index,E data);	//根据索引修改数据
    public boolean contains(E data);	//判断数据是否存在
    public void remove(E data);		//删除数据
    public void clean();			//清空

    public void printList();		//遍历
}
