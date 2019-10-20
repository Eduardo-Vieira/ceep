package br.com.alura.ceep.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.List;

import br.com.alura.ceep.R;
import br.com.alura.ceep.database.AppDatabase;
import br.com.alura.ceep.database.CeepSharedPreferences;
import br.com.alura.ceep.model.Nota;
import br.com.alura.ceep.ui.recyclerview.adapter.ListaNotasAdapter;
import br.com.alura.ceep.ui.recyclerview.adapter.listener.OnItemClickListener;
import br.com.alura.ceep.ui.recyclerview.helper.callback.NotaItemTouchHelperCallback;

import static br.com.alura.ceep.constantes.Constantes.CEEP_SHARED_PREF_DEFVALUE;
import static br.com.alura.ceep.constantes.Constantes.CHAVE_NOTA;
import static br.com.alura.ceep.constantes.Constantes.CHAVE_POSICAO;
import static br.com.alura.ceep.constantes.Constantes.CODIGO_REQUISICAO_ALTERA_NOTA;
import static br.com.alura.ceep.constantes.Constantes.CODIGO_REQUISICAO_INSERE_NOTA;
import static br.com.alura.ceep.constantes.Constantes.ITEM_ICON_MENU_DEFVALUE;
import static br.com.alura.ceep.constantes.Constantes.ITEM_ICON_MENU_LIST;
import static br.com.alura.ceep.constantes.Constantes.ITEM_ICON_MENU_STAGGERD;
import static br.com.alura.ceep.constantes.Constantes.POSICAO_INVALIDA;

public class ListaNotasActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR = "Notas";
    private ListaNotasAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,
            StaggeredGridLayoutManager.VERTICAL);
    private String typeRecyclerVLayoutView = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);

        setTitle(TITULO_APPBAR);

        List<Nota> todasNotas = pegaTodasNotas();
        configuraRecyclerView(todasNotas);
        configuraBotaoInsereNota();

    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        linearLayoutManager = new LinearLayoutManager(context);
        return super.onCreateView(name, context, attrs);
    }

    private void configuraBotaoInsereNota() {
        TextView botaoInsereNota = findViewById(R.id.lista_notas_insere_nota);
        botaoInsereNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vaiParaFormularioNotaActivityInsere();
            }
        });

    }

    private void vaiParaFormularioFeedback(){
        Intent iniciaFormularioFeddback =
                new Intent(ListaNotasActivity.this,
                        FeedbackActivity.class);
        startActivity(iniciaFormularioFeddback);
    }

    private void vaiParaFormularioNotaActivityInsere() {
        long posicao = adapter.getItemCount();
        Intent iniciaFormularioNota =
                new Intent(ListaNotasActivity.this,
                        FormularioNotaActivity.class);
        iniciaFormularioNota.putExtra(CHAVE_POSICAO, posicao);
        startActivityForResult(iniciaFormularioNota,
                CODIGO_REQUISICAO_INSERE_NOTA);
    }

    private List<Nota> pegaTodasNotas() {
        return AppDatabase.getAppDatabase(this).notaDao().getAll();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_nota, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.lista_notas_recyclerview:
                if(typeRecyclerVLayoutView.equals(CEEP_SHARED_PREF_DEFVALUE)) {
                    setIconMenuItem(item, ITEM_ICON_MENU_STAGGERD);
                    setRecyclerLayoutView(ITEM_ICON_MENU_STAGGERD);
                }else{
                    setIconMenuItem(item, ITEM_ICON_MENU_LIST);
                    setRecyclerLayoutView(ITEM_ICON_MENU_LIST);
                }
                break;
            default:
                vaiParaFormularioFeedback();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setRecyclerLayoutView(String typeView){
        RecyclerView listaNotas = findViewById(R.id.lista_notas_recyclerview);
        typeRecyclerVLayoutView = CeepSharedPreferences.setTypeRecyclerVLayoutView(getApplicationContext(),typeView);
        if(typeView.equals("Staggered")) {
            layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
            listaNotas.setLayoutManager(layoutManager);
            listaNotas.setHasFixedSize(true);
        }else{
            listaNotas.setLayoutManager(linearLayoutManager);
        }
    }

    private void setIconMenuItem(MenuItem item, String type){
        if(type.equals(ITEM_ICON_MENU_DEFVALUE)) {
            item.setIcon(R.drawable.ic_view_list);
        }else{
            item.setIcon(R.drawable.ic_view_quilt);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mudarTipoDoLayoutView(menu);
        return super.onPrepareOptionsMenu(menu);
    }

    private void mudarTipoDoLayoutView(Menu menu){
        typeRecyclerVLayoutView = CeepSharedPreferences.getTypeRecyclerVLayoutView(getApplicationContext());
        setRecyclerLayoutView(typeRecyclerVLayoutView);
        setIconMenuItem(menu.findItem(R.id.lista_notas_recyclerview), typeRecyclerVLayoutView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (ehResultadoInsereNota(requestCode, data)) {

            if (resultadoOk(resultCode)) {
                Nota notaRecebida = (Nota) data.getSerializableExtra(CHAVE_NOTA);
                adiciona(notaRecebida);
            }

        }

        if (ehResultadoAlteraNota(requestCode, data)) {
            if (resultadoOk(resultCode)) {
                Nota notaRecebida = (Nota) data.getSerializableExtra(CHAVE_NOTA);
                int posicaoRecebida = data.getIntExtra(CHAVE_POSICAO, POSICAO_INVALIDA);
                if (ehPosicaoValida(posicaoRecebida)) {
                    altera(notaRecebida, posicaoRecebida);
                }
            }
        }
    }

    private static Nota addNota(final AppDatabase db, Nota nota) {
       long id = db.notaDao().insert(nota);
       nota.setNid(id);
       return nota;
    }

    private static Nota updateNota(final AppDatabase db, Nota nota) {
        db.notaDao().update(nota);
        return nota;
    }

    private void altera(Nota nota, int posicao) {
        adapter.altera(posicao, updateNota(AppDatabase.getAppDatabase(this), nota));
    }

    private void adiciona(Nota nota) {
        adapter.adiciona(addNota(AppDatabase.getAppDatabase(this), nota));
    }

    private boolean ehPosicaoValida(int posicaoRecebida) {
        return posicaoRecebida > POSICAO_INVALIDA;
    }

    private boolean ehResultadoAlteraNota(int requestCode, Intent data) {
        return ehCodigoRequisicaoAlteraNota(requestCode) &&
                temNota(data);
    }

    private boolean ehCodigoRequisicaoAlteraNota(int requestCode) {
        return requestCode == CODIGO_REQUISICAO_ALTERA_NOTA;
    }

    private boolean ehResultadoInsereNota(int requestCode, Intent data) {
        return ehCodigoRequisicaoInsereNota(requestCode) &&
                temNota(data);
    }

    private boolean temNota(Intent data) {
        return data != null && data.hasExtra(CHAVE_NOTA);
    }

    private boolean resultadoOk(int resultCode) {
        return resultCode == Activity.RESULT_OK;
    }

    private boolean ehCodigoRequisicaoInsereNota(int requestCode) {
        return requestCode == CODIGO_REQUISICAO_INSERE_NOTA;
    }

    private void configuraRecyclerView(List<Nota> todasNotas ) {

        RecyclerView listaNotas = findViewById(R.id.lista_notas_recyclerview);
        configuraAdapter(todasNotas, listaNotas);
        configuraItemTouchHelper(listaNotas);
    }

    private void configuraItemTouchHelper(RecyclerView listaNotas) {
        ItemTouchHelper itemTouchHelper =
                new ItemTouchHelper(new NotaItemTouchHelperCallback(adapter, this));
        itemTouchHelper.attachToRecyclerView(listaNotas);
    }

    private void configuraAdapter(List<Nota> todasNotas, RecyclerView listaNotas) {
        adapter = new ListaNotasAdapter(this, todasNotas);
        listaNotas.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Nota nota, int posicao) {
                vaiParaFormularioNotaActivityAltera(nota, posicao);
            }
        });
    }

    private void vaiParaFormularioNotaActivityAltera(Nota nota, int posicao) {
        Intent abreFormularioComNota = new Intent(ListaNotasActivity.this,
                FormularioNotaActivity.class);
        abreFormularioComNota.putExtra(CHAVE_NOTA, nota);
        abreFormularioComNota.putExtra(CHAVE_POSICAO, posicao);
        startActivityForResult(abreFormularioComNota, CODIGO_REQUISICAO_ALTERA_NOTA);
    }

}
