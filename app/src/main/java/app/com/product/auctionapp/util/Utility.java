package app.com.product.auctionapp.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by shimonaj on 5/12/2016.
 */
public class Utility {
    public static void putKeyValInSharedPref(Context mContext,String key,String value) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }
    public static String getValFromSharedPref(Context mContext,String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
      return prefs.getString(key,"");

    }
    public static int getUserInfo(Context mContext) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        int userInfo = prefs.getInt(Config.USERID, 0);
        JSONObject userObj = null;
//if(userInfo!="") {
//
//    try {
//        userObj = new JSONObject(userInfo);
//
//    } catch (JSONException e) {
//        e.printStackTrace();
//    }
//}
        return userInfo;
    }

    public static  Typeface regularRobotoFont;
    public static  Typeface mediumRobotoFont;
    public static void initAllFonts(Context mContext){
        regularRobotoFont =  Typeface.createFromAsset(mContext.getResources().getAssets(), "Roboto-Regular.ttf");
        mediumRobotoFont =  Typeface.createFromAsset(mContext.getResources().getAssets(), "Roboto-Medium.ttf");
    }
}
