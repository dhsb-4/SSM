package com.util;

import com.util.port.ILink;

/**
 * 链表工具类
 * @param <E>
 */
@SuppressWarnings("all")
public class LinkList<E> implements ILink<E> {
    private class Node<E>{
        private E data; 		//存储的数据
        private Node next;		//下一个节点

        public Node(E data){	//构造方法,在实例化的时候存储数据
            this.data = data;
        }

        //添加数据
        public void addNode(Node next){	//保存新的数据
            if (this.next == null) {
                this.next = next;
            }else{
                this.next.addNode(next);
            }
        }

        //设置成数组
        public void toArrayNode(){
            //从内部类获取外部类的私有成员 没次加一保证下一次拿到的是加一的foot
            LinkList.this.returnData[LinkList.this.foot++] = this.data;
            if (this.next != null) {//如果还有下一个节点  继续调这个方法
                this.next.toArrayNode();
            }
        }

        //根据索引查询数据
        public E getNode(int index){
            //从内部类获取外部类的私有成员 没次加一保证下一次拿到的是加一的foot
            if(LinkList.this.foot++ == index){	//索引相同
                return this.data;	//返回当前数据
            }else{
                return (E) this.next.getNode(index);	//递归调用
            }
        }

        //根据索引修改数据
        public void setNode(int index,E data){
            //从内部类获取外部类的私有成员 没次加一保证下一次拿到的是加一的foot
            if(LinkList.this.foot++ == index){	//索引相同
                this.data = data;
            }else{
                this.next.setNode(index, data);//递归调用
            }
        }

        //判断数据是否存在
        public boolean containsNode(E data){
            if (this.data.equals(data)) {	//判断数据是否相等
                return true;		//返回true
            }else{
                if (this.next == null) {	//判断是否还有下一个节点
                    return false;	//返回false
                }
            }
            return this.next.containsNode(data);	//递归调用
        }

        //删除节点
        public void removeNode(Node previous,E data){
            if (this.data.equals(data)) {
                previous.next = this.next;	//上一个节点的下一个节点变成要删除节点的下一个
            }else{
                if (this.next != null) {	//有后续节点
                    this.next.removeNode(this, data);	//向后继续寻找data数据相等的节点
                }
            }
        }
    }
    //-----------------以下是LinkList的成员属性--------------
    private Node root;			//作为链表的根节点
    private int count;			//作为链表的长度/数量
    private int foot;			//数组下标
    private Object[] returnData;	//返回的数组

    //-----------------以下是ILink方法---------------------
    //添加
    @Override
    public void add(E data) {
        if (data == null) {		//保存数据为null
            return;				//结束方法直接返回
        }

        Node<E> next = new Node<E>(data);		//创建一个新的节点
        if (this.root == null) {		//没有根节点
            this.root = next;			//第一个节点做为根节点
        }else{	//根节点存在
            this.root.addNode(next);	//当有了根节点，就往下一个节点添加
        }

        this.count++;		//每添加一个对象都会加一
    }

    //长度
    @Override
    public int size(){
        return this.count;
    }

    //判断是否为空
    @Override
    public boolean isEmpty() {
        return this.count == 0;		//true为空，false非空
    }

    //数据集合
    @Override
    public Object[] toArray() {
        if (this.isEmpty()) {		//判断谁为空
            return null;			//直接返回
        }

        this.foot = 0;				//下标归零
        this.returnData = new Object[this.count];	//因为记录了长度 直接用就行了
        this.root.toArrayNode();	//利用Node进行递归数据获取
        //因为数组是引用类型  所以在上面这个方法获取就行了
        return this.returnData;
    }

    //遍历
    @Override
    public void printList() {
        Node<E> next = this.root;		//获取第一个数据

        while (true) {					//无限循环
            if (next == null) {			//为null
                break;					//跳出循环
            }

            System.out.println(next.data);	//输出数据
            next = next.next;			//获取下一个节点
        }
    }

    //根据下标获取数据
    @Override
    public E get(int index){
        if(index >= this.count){	//索引应该在指定范围之内
            return null;
        }
        //索引数据的获取应该有Node类完成
        this.foot = 0;//重置索引的下标
        return (E) this.root.getNode(index);
    }

    //根据下标修改数据
    @Override
    public void set(int index, E data) {
        if(index >= this.count){	//索引应该在指定范围之内
            return;
        }
        //索引数据的获取应该有Node类完成
        this.foot = 0;//重置索引的下标
        this.root.setNode(index, data);
    }

    //判断数据是否存在
    @Override
    public boolean contains(E data) {
        if (data == null) {		//为null返回false
            return false;
        }
        return this.root.containsNode(data);
    }

    //删除数据
    @Override
    public void remove(E data) {
        if (this.contains(data)) {		//判断是否存在
            if (this.root.data.equals(data)) {		//根节点处理
                this.root = this.root.next;			//根引用根的下一个节点
            }else{					//如果不是根节点
                this.root.removeNode(this.root, data);
            }
        }
    }

    //清空
    @Override
    public void clean() {
        this.root =null;	//后续的节点都没了
        this.count = 0;		//数组长度清零
    }
}
