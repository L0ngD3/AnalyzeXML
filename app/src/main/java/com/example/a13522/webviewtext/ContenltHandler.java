package com.example.a13522.webviewtext;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by 13522 on 2017/7/18.
 */

public class ContenltHandler extends DefaultHandler {

    private String mnodeName;
    private StringBuilder mid;


    @Override
    public void startDocument() throws SAXException {
        //初始化节点
        mid = new StringBuilder();
//        mname = new StringBuilder();
//        mversion =new StringBuilder();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
       //获取节点的名字
        mnodeName = localName;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
       //根据当前节点名判断内容  添加到对应的StringBuilder对象中
        if ("id".equals(mnodeName)){
            mid.append(ch,start,length);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
            if ("student".equals(localName)){
                Log.d("id",mid.toString().trim());
                //将StringBuilder里面的数据清空
                mid.setLength(0);
        }
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }
}
