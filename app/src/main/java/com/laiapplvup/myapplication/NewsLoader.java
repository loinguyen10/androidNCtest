package com.laiapplvup.myapplication;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class NewsLoader {
    List<Entry> tinTucList = new ArrayList<Entry>();
    Entry tinTuc;
    public static final ArrayList<String> titleList = new ArrayList<>();
    String textContent;

    public List<Entry> getTinTucList(InputStream inputStream) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(inputStream, null);

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG: {
                        if (tagName.equalsIgnoreCase("item")) {
                            tinTuc = new Entry();
                        }
                        break;
                    }
                    case XmlPullParser.TEXT: {
                        textContent = parser.getText();
                        break;
                    }
                    case XmlPullParser.END_TAG: {
                        if (tinTuc != null) {
                            if (tagName.equalsIgnoreCase("item")) {
                                tinTucList.add(tinTuc);
                            }
                            if (tagName.equalsIgnoreCase("title")) {
                                tinTuc.setTitle(textContent);
                                titleList.add(textContent);
                            }
                            if (tagName.equalsIgnoreCase("link")) {
                                tinTuc.setLink(textContent);
                            }
                            if (tagName.equalsIgnoreCase("description")) {
                                int s = textContent.indexOf("https://i1-");
                                int e = textContent.indexOf("\" >",s);
                                if(s==-1 && e==-1){
                                    tinTuc.setId("https://s.vnecdn.net/vnexpress/i/v20/logos/vne_logo_rss.png");
                                }else{
                                    tinTuc.setId(textContent.substring(s,e));
                                }
                                tinTuc.setDescription(textContent);
                            }
                            if (tagName.equalsIgnoreCase("pubDate")) {
                                tinTuc.setDate(textContent.substring(0,25));
                            }
                        } break;
                    }
                    default: {
                        Log.d("zzzz", "eventType khác: " + eventType + ", tag = " + tagName);
                        break;
                    }
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tinTucList;
    }
}
