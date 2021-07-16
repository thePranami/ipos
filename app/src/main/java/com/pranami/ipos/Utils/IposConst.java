package com.pranami.ipos.Utils;

import android.content.SharedPreferences;

public class IposConst {

    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;
    public static String sp_name = "Ipos";

    public interface Keys {
        String NAME = "NAME";
        String CID = "CID";
        String MOBILE = "MOBILE";
    }
}
