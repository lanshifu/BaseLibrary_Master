package com.lanshifu.demo_module.design_mode;


import java.util.ArrayList;

/**
 * 原型模式的例子
 */
public class PrototypeTest {

    void  test(){
        Document document = new Document();
        ArrayList<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");

        document.setText("123");
        document.setLists(list);
//        LogUtils.debugInfo("list.size" +document.getLists().size());

        Document clone = document.clone();
        list.add("4");
        clone.setLists(list);
        //深拷贝对原始对象不影响
//        LogUtils.debugInfo("list.size" +document.getLists().size());
//        LogUtils.debugInfo("list.size" +clone.getLists().size());
    }


    class Document implements Cloneable{
        private String text;
        private ArrayList<String> lists = new ArrayList<>();

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public ArrayList<String> getLists() {
            return lists;
        }

        public void setLists(ArrayList<String> lists) {
            this.lists = lists;
        }

        @Override
        protected Document clone(){
            try {
                //浅拷贝
//                Document clone = (Document) super.clone();
//                clone.setLists(this.lists);
//                clone.setText(this.text);

                //深拷贝
                Document deepClone = (Document) super.clone();
                deepClone.lists = (ArrayList<String>)(this.lists.clone());
                deepClone.text = this.text;
                return deepClone;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
