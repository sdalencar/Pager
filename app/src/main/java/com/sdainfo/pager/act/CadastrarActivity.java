package com.sdainfo.pager.act;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.sdainfo.pager.R;
import com.sdainfo.pager.config.ConfiguracaoFirebase;
import com.sdainfo.pager.config.Mensagens;
import com.sdainfo.pager.model.Usuario;

public class CadastrarActivity extends AppCompatActivity {

    private EditText et_nome, et_email, et_senha;
    private Mensagens mensagens;

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        iniciaComponentes();


    }

    private void iniciaComponentes() {
        mensagens = new Mensagens(getApplicationContext());

        et_nome = findViewById(R.id.et_nome_cad);
        et_email = findViewById(R.id.et_email_cad);
        et_senha = findViewById(R.id.et_senha_cad);
    }

    public void cadastrar(View view) {
        validarCampos();
    }

    public void validarCampos() {
        String nome, senha, email;
        nome = et_nome.getText().toString();
        email = et_email.getText().toString();
        senha = et_senha.getText().toString();

        if (!nome.isEmpty()) {
            if (!email.isEmpty()) {
                if (!senha.isEmpty()) {
                    salvarFireBase(nome, email, senha);
                } else {
                    mensagens.msgCurta("Preencha a senha");
                }
            } else {
                mensagens.msgCurta("Preencha o e_mail");
            }
        } else {
            mensagens.msgCurta("Preencha o nome");
        }
        limaCampos();
    }


    private void salvarFireBase(String nome, String email, String senha) {
        autenticacao = ConfiguracaoFirebase.getAutenticacao();
        autenticacao.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            mensagens.msgCurta("Usuário cadastrado com sucesso.");

                            Usuario usuario = new Usuario();
                            String idUser = autenticacao.getCurrentUser().getUid();
                            usuario.setId(idUser);
                            usuario.salvar(idUser, nome, email);

                            startActivity(new Intent(CadastrarActivity.this, PrincipalActivity.class));
                            finish();
                        } else {
                            String excecao;
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                excecao = "Senha muito fácil";
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                excecao = "E-mail inválido";
                            } catch (FirebaseAuthUserCollisionException e) {
                                excecao = "E-mail já cadastrado";
                            } catch (Exception e) {
                                excecao = "Erro ao cadastrar e-mail" + e.getMessage();
                                e.printStackTrace();
                            }
                            mensagens.msgCurta(excecao);
                        }
                    }
                });

    }

    private void limaCampos() {
        et_nome.setText("");
        et_email.setText("");
        et_senha.setText("");
    }
}


















