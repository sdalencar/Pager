package com.sdainfo.pager.act;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.sdainfo.pager.R;
import com.sdainfo.pager.config.ConfiguracaoFirebase;
import com.sdainfo.pager.fragment.ContatosFragment;
import com.sdainfo.pager.fragment.ConversasFragment;

public class PrincipalActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        Toolbar toolbar = findViewById(R.id.toolbar_principal);
        toolbar.setTitle(" Pager");
        setSupportActionBar(toolbar);

        firebaseAuth = ConfiguracaoFirebase.getAutenticacao();

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(),
                FragmentPagerItems.with(this)
                .add(getString(R.string.conversa), ConversasFragment.class)
                .add(getString(R.string.contato), ContatosFragment.class)
                .create()
        );

        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);

        SmartTabLayout smartTabLayout = findViewById(R.id.viewSmartTab);
        smartTabLayout.setViewPager(viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_pesquisa:
                break;
            case R.id.menu_configuracao:
                startActivity(new Intent(PrincipalActivity.this, ConfiguracoesActivity.class));
                break;
            case R.id.menu_sair:
                deslogar();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deslogar() {
        if ((firebaseAuth != null)) {
            firebaseAuth.signOut();
            finish();
        }
    }
}



































