package com.sdainfo.pager.helper;


import android.util.Base64;

public class Base64Custom {

    public static String codificar(String msg) {
        return Base64.encodeToString(msg.getBytes(),Base64.DEFAULT).replaceAll("(\n | \r)", "");
    }

    public static String decodificar(String msg) {
        return new String (Base64.decode(msg, Base64.DEFAULT));
    }


}
