package com.loopin.ipos.Utils;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyController {
    private static Context context;
    private static VolleyController volleyController;
    private RequestQueue requestQueue;

    private VolleyController(Context context){
        this.context = context;
        requestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public static VolleyController getInstance(Context context){
        if (volleyController==null){
            volleyController = new VolleyController(context);
        }
        return volleyController;
    }
    public<T> void addToRequestQueue(Request<T> volleyRequest){
        getRequestQueue().add(volleyRequest);
    }
}
