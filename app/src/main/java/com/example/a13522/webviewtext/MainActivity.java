package com.example.a13522.webviewtext;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.SAXParserFactory;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    TextView mresponseText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
     /* WebView webView= (WebView) findViewById(R.id.web);
        webView.getSettings().setJavaScriptEnabled(true);//设置浏览器属性，支持java脚本
        webView.setWebViewClient(new WebViewClient());//当网页跳转的时候仍然在当前的web界面显示
        webView.loadUrl("http://tuijian.hao123.com/");*/
    mresponseText= (TextView) findViewById(R.id.text);
     Button send= (Button) findViewById(R.id.butn);
       Button okhttom= (Button) findViewById(R.id.okHttp);
      Button xml= (Button) findViewById(R.id.xml);
       Button sax= (Button) findViewById(R.id.sax);

        send.setOnClickListener(this);
        okhttom.setOnClickListener(this);
        xml.setOnClickListener(this);
        sax.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.butn){
            sendRequestUri();
        }
        if (v.getId()==R.id.okHttp){
            sendRequestUriOkhttp();

        }
        if (v.getId()==R.id.xml){
            parseXml();
        }
        if (v.getId()==R.id.sax){
            parseSAX();
        }
    }

    private void parseSAX() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                OkHttpClient click = new OkHttpClient();
                Request request = new Request.Builder().url("http://10.39.1.16/1.xml").build();
                    Response response= click.newCall(request).execute();
                 String returnData=  response.body().string();
                    parserSaxpull(returnData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void parserSaxpull(String data){
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            XMLReader xmlreader=factory.newSAXParser().getXMLReader();
            ContentHandler hander= new ContenltHandler();
            xmlreader.setContentHandler(hander);//将ContentHandler设置到XMLReader，把材料加到机器中

            //开始解析
            xmlreader.parse(new InputSource(new StringReader(data)));


        }catch (Exception e){}

    }

    private void parseXml() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                OkHttpClient click = new OkHttpClient();
             Request request=  new Request.Builder().url("http://10.39.1.16/1.xml").build();
                    Response response= click.newCall(request).execute();
                  String returnData=  response.body().string();
                    parserXmlpull(returnData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void parserXmlpull(String xmlData){
        try {
         XmlPullParserFactory faction= XmlPullParserFactory.newInstance();
           XmlPullParser xmlpullparser= faction.newPullParser();
            xmlpullparser.setInput(new StringReader(xmlData));
          int eventType=  xmlpullparser.getEventType();
            String body = "";
            while (eventType!=XmlPullParser.END_DOCUMENT){
                String nodeName = xmlpullparser.getName();
                switch (eventType){
                    case XmlPullParser.START_TAG:
                    {
                        if ("id".equals(nodeName)){
                            body = xmlpullparser.nextText();
                        }
                        break;
                    }
                    case XmlPullParser.END_TAG:{
                        if ("student".equals(nodeName)){
                            Log.d("Main",body);
                        }
                        break;
                    }default:
                        break;
                }
                eventType = xmlpullparser.next();
            }

        }catch (Exception e){}
    }




    private void sendRequestUriOkhttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
             OkHttpClient client= new OkHttpClient();
                //发送请求
               Request request= new Request.Builder().url("http://www.baidu.com").build();
                    Response response= client.newCall(request).execute();//execute()发送请求并且得到返回值
                   String responseData= response.body().string();//将返回值转变为数组
                    show(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


    //发送url请求
    private void sendRequestUri() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connsent = null;
                BufferedReader reader = null;

                try {
                    URL uri = new URL("http://tuijian.hao123.com/");//设置uri
                    connsent = (HttpURLConnection) uri.openConnection();//打开所设定的uri
                    connsent.setRequestMethod("GET");//发送get请求
                  InputStream in= connsent.getInputStream();//获取返回的流
                    reader = new BufferedReader(new InputStreamReader(in));//利用buff读取返回的流
                    StringBuilder response = new StringBuilder();//将流转换为字符串
                    String line;
                    while ((line=reader.readLine())!=null){
                        response.append(line);
                    }
                    show(response.toString());

                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    if (reader!=null){
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }if (connsent!=null){
                        connsent.disconnect();
                    }
                }
            }
        }).start();
    }
    public void show(final String respon){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mresponseText.setText(respon);
            }
        });
    }


}
