package com.sdainfo.pager.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfiguracaoFirebase {

    public static DatabaseReference referencia_banco_dados;
    public static FirebaseAuth referencia_autenticacao;


    public static DatabaseReference getBanco_dados(){
        if (referencia_banco_dados == null){
            referencia_banco_dados = FirebaseDatabase.getInstance().getReference();
        }
        return referencia_banco_dados;
    }

    public static FirebaseAuth getAutenticacao(){
        if (referencia_autenticacao == null){
            referencia_autenticacao = FirebaseAuth.getInstance();
        }
        return referencia_autenticacao;
    }

}
