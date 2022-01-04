package com.loopin.ipos.Utils;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.widget.Spinner;
import android.widget.TextView;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;

import java.util.List;

public class IposConst {

    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;
    public static String sp_name = "Ipos";
    public static String ftdId = "ftdId";
    public static String firstDate = "fd";
    public static int pro;

    public static String retroUrl = "http://78.47.56.220/loopin/android/";
    public static String promoUrl = "https://path2way.iposindia.com/android/";
    //http://15.207.4.72/android/
    public static String volleyUrl = "http://78.47.56.220/loopin/android/index.php";

    public static int storageReqCode = 101;

    public static final Font titleFont = new Font(Font.getFamily("TIMES_ROMAN"),
            20,Font.BOLD);
    public static final Font underLine = new Font(Font.getFamily("TIMES_ROMAN"),
            20,Font.BOLD|Font.UNDERLINE);
    public static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 14,
            Font.BOLD);
    public static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.RED);
    public static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    public static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);

    public static Font miniFont2 = new Font(Font.FontFamily.TIMES_ROMAN, 2,
            Font.NORMAL);
    public static Font miniFont5 = new Font(Font.FontFamily.TIMES_ROMAN, 5,
            Font.NORMAL);
    public static Font miniFont8 = new Font(Font.FontFamily.TIMES_ROMAN, 8,
            Font.NORMAL);
    public static Font miniFont10 = new Font(Font.FontFamily.TIMES_ROMAN, 10,
            Font.NORMAL);

    @SuppressLint("StaticFieldLeak")
    public static TextView totalReceive;
    @SuppressLint("StaticFieldLeak")
    public static TextView totalExcise;
    @SuppressLint("StaticFieldLeak")
    public static TextView totalWsp;
    @SuppressLint("StaticFieldLeak")
    public static TextView totalAmount;
    @SuppressLint("StaticFieldLeak")
    public static TextView totalReceiving;
    @SuppressLint("StaticFieldLeak")
    public static TextView totalCustom;
    @SuppressLint("StaticFieldLeak")
    public static TextView totalVat;
    @SuppressLint("StaticFieldLeak")
    public static TextView totalTcs;
    @SuppressLint("StaticFieldLeak")
    public static TextView totalSale;
    @SuppressLint("StaticFieldLeak")
    public static TextView totalQuantity;
    @SuppressLint("StaticFieldLeak")
    public static TextView totalGross;
    @SuppressLint("StaticFieldLeak")
    public static TextView totalClosing;
    @SuppressLint("StaticFieldLeak")
    public static TextView totalBreakage;
    @SuppressLint("StaticFieldLeak")
    public static TextView totalBreakageSale;
    @SuppressLint("StaticFieldLeak")
    public static TextView totalIssueStock;
    @SuppressLint("StaticFieldLeak")
    public static TextView totalCase;
    @SuppressLint("StaticFieldLeak")
    public static TextView totalPurAmt;
    @SuppressLint("StaticFieldLeak")
    public static TextView totalPurVat;
    @SuppressLint("StaticFieldLeak")
    public static TextView totalSaleVat;
    @SuppressLint("StaticFieldLeak")
    public static TextView totalVatDiff;
    @SuppressLint("StaticFieldLeak")
    public static TextView totalSaleIMFL;
    @SuppressLint("StaticFieldLeak")
    public static TextView totalBearSale;

    @SuppressLint("StaticFieldLeak")
    public static TextView totalSVQ;
    @SuppressLint("StaticFieldLeak")
    public static TextView totalSVP;
    @SuppressLint("StaticFieldLeak")
    public static TextView totalSVN;

    public static String date, fDate, tDate, lt, lc;

    @SuppressLint("StaticFieldLeak")
    public static List<String> typeList;
    public static List<String> lcList;
    public static List<String> bList;
    public static List<String> snList;
    @SuppressLint("StaticFieldLeak")
    public static Spinner typeSpinner, catSpinner, brandSpinner, splrSpinner;

    public interface Keys {
        String USER_NAME = "USER_NAME";
        String VEND_ID = "VEND_ID";
        String USER_TYPE  = "";
        String SHOP_NAME = "SHOP_NAME";
        String SHOP_ADDRESS = "SHOP_ADDRESS";
        String PIN_CODE = "PIN_CODE";
        String SHOP_KEY = "SHOP_KEY";
        String PHONE = "PHONE";
        String ZONE_ID = "ZONE_ID";
        String ZONE_NAME = "ZONE_NAME";
    }
}
