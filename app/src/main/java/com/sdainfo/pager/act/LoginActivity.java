package com.sdainfo.pager.act;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.sdainfo.pager.R;
import com.sdainfo.pager.config.ConfiguracaoFirebase;
import com.sdainfo.pager.config.Mensagens;
import com.sdainfo.pager.helper.Helper;

public class LoginActivity extends AppCompatActivity {

    private Helper helper;
    private EditText et_email, et_senha;
    private Mensagens mensagens;

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        iniciaComponentes();
    }
    private void iniciaComponentes() {

        mensagens = new Mensagens(getApplicationContext());
        autenticacao = ConfiguracaoFirebase.getAutenticacao();

        et_email = findViewById(R.id.et_email_login);
        et_senha = findViewById(R.id.et_senha_login);

    }
    public void entar(View view) {
        switch (view.getId()) {
            case R.id.bt_entrar_login:
                validarCampos();
                break;
            case R.id.tv_cadastre_login:
                startActivity(new Intent(this, CadastrarActivity.class));
                finish();
                break;
        }
    }
    public void validarCampos() {
        String senha, email;
        email = et_email.getText().toString();
        senha = et_senha.getText().toString();

        if (!email.isEmpty()) {
            if (!senha.isEmpty()) {
                logarFirebase(email, senha);
            } else {
                mensagens.msgCurta("Preencha a senha");
            }

        } else {
            mensagens.msgCurta("Preencha o e_mail");
        }
    }
    private void logarFirebase(String email, String senha) {
        autenticacao.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        limaCampos();
                        abrirPrincipal();
                        finish();
                    } else {
                        mensagens.msgCurta("Erro ao logar usuário.");
                    }
                });
    }
    private void abrirPrincipal(){
        startActivity(new Intent(LoginActivity.this, PrincipalActivity.class));
        finish();
    }
    private void limaCampos() {
        et_email.setText("");
        et_senha.setText("");
    }
    @Override
    protected void onStart() {
        super.onStart();
        if(autenticacao.getCurrentUser() != null){
            abrirPrincipal();
        }
    }
}
