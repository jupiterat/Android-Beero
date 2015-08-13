package com.au.beero.beero.utility;

import android.content.Context;

import com.au.beero.beero.R;
import com.framework.plistparser.xml.plist.PListXMLHandler;
import com.framework.plistparser.xml.plist.PListXMLParser;
import com.framework.plistparser.xml.plist.domain.*;
import com.framework.plistparser.xml.plist.domain.String;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shintabmt@gmai.com on 8/14/2015.
 */
public class PlistParsing {
    PListXMLParser parser = new PListXMLParser();
    PListXMLHandler handler = new PListXMLHandler();
    private Context mContext;

    public PlistParsing(Context context) {
        this.mContext = context;
    }

    public void getSupportArea() {
        parser.setHandler(handler);
        InputStream raw = null;
        try {
            InputStream file = mContext.getResources().openRawResource(R.raw.supported_area);
//            raw = mContext.getAssets().open("supported_area.plist");
            parser.parse(file);
            PList pList = ((PListXMLHandler) parser.getHandler()).getPlist();
            Array root = (Array) pList.getRootElement();
            List<Map<java.lang.String, PListObject>> mapList = new ArrayList<>();
            for (PListObject obj : root) {
                switch (obj.getType()) {
                    case DICT:
                        Dict dictionaryObj = (Dict) obj;
                        mapList.add(dictionaryObj.getConfigMap());
                        dictionaryObj.getConfigMap();
                         String topLat = dictionaryObj.getConfigurationObject("_TOP_LAT");
                        // dictionaryObj.getConfigurationInteger(key);
                        // dictionaryObj.getConfiguration(key);
                        // dictionaryObj.getConfigurationArray(key)
                        break;
                    case STRING:
                        com.framework.plistparser.xml.plist.domain.String stringObj = ( com.framework.plistparser.xml.plist.domain.String) obj;
                        break;

                    default:
                        break;
                }
            }
            System.out.println("done");
//            Reader is = new BufferedReader(new InputStreamReader(raw, "UTF8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("done");
    }
}
