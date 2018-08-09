package com.example.dell.lbstest;

public class Msg {
    public static final int TYPE_RECEIVE = 1;

    public static final int TYPE_SEND = 0;

    public String content;

    public int type;

    public Msg(String content,int type){
        this.content = content;
        this.type = type;
    }

    public int getType(){
        return type;
    }

    public String getContent(){
        return content;
    }
}
