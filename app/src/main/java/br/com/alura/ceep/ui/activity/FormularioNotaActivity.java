package br.com.alura.ceep.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.alura.ceep.R;
import br.com.alura.ceep.model.CoresNota;
import br.com.alura.ceep.model.Nota;
import br.com.alura.ceep.ui.recyclerview.adapter.ListaCoresAdapter;
import br.com.alura.ceep.ui.recyclerview.adapter.listener.OnItemCorClickListener;

import static br.com.alura.ceep.constantes.Constantes.CHAVE_NOTA;
import static br.com.alura.ceep.constantes.Constantes.CHAVE_POSICAO;
import static br.com.alura.ceep.constantes.Constantes.POSICAO_INVALIDA;

public class FormularioNotaActivity extends AppCompatActivity {


    public static final String TITULO_APPBAR_INSERE = "Insere nota";
    public static final String TITULO_APPBAR_ALTERA = "Altera nota";
    private int posicaoRecibida = POSICAO_INVALIDA;
    private TextView titulo;
    private TextView descricao;
    private int cor;
    private List<CoresNota> coresNotaList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_nota);

        setTitle(TITULO_APPBAR_INSERE);
        inicializaCampos();

        Intent dadosRecebidos = getIntent();
        if(dadosRecebidos.hasExtra(CHAVE_NOTA)){
            setTitle(TITULO_APPBAR_ALTERA);
            Nota notaRecebida = (Nota) dadosRecebidos
                    .getSerializableExtra(CHAVE_NOTA);
            posicaoRecibida = dadosRecebidos.getIntExtra(CHAVE_POSICAO, POSICAO_INVALIDA);
            preencheCampos(notaRecebida);
            mudarCorBackground(notaRecebida.getCor());
        }else{
            mudarCorBackground(R.color.BRANCO); //Padrão
        }

        inicializarRecyclerView(this);
    }

    private void prepararCores(){
        coresNotaList.add(new CoresNota(R.color.AZUL));
        coresNotaList.add(new CoresNota(R.color.BRANCO));
        coresNotaList.add(new CoresNota(R.color.VERMELHO));
        coresNotaList.add(new CoresNota(R.color.VERDE));
        coresNotaList.add(new CoresNota(R.color.AMARELO));
        coresNotaList.add(new CoresNota(R.color.LILÁS));
        coresNotaList.add(new CoresNota(R.color.CINZA));
        coresNotaList.add(new CoresNota(R.color.MARROM));
        coresNotaList.add(new CoresNota(R.color.ROXO));
    }

    private String colorName(int cor){
        String name;
        switch (cor){
            case R.color.AZUL:
                name = this.getResources().getString(R.string.AZUL);
                break;
            case R.color.BRANCO:
                name = this.getResources().getString(R.string.BRANCO);
                break;
            case R.color.VERMELHO:
                name = this.getResources().getString(R.string.VERMELHO);
                break;
            case R.color.VERDE:
                name = this.getResources().getString(R.string.VERDE);
                break;
            case R.color.AMARELO:
                name = this.getResources().getString(R.string.AMARELO);
                break;
            case R.color.LILÁS:
                name = this.getResources().getString(R.string.LILÁS);
                break;
            case R.color.CINZA:
                name = this.getResources().getString(R.string.CINZA);
                break;
            case R.color.MARROM:
                name = this.getResources().getString(R.string.MARROM);
                break;
            case R.color.ROXO:
                name = this.getResources().getString(R.string.ROXO);
                break;
            default:
                name = "Cor não encontrada";
        }
        return name;
    }

    private void inicializarRecyclerView(Context context){
        prepararCores();
        RecyclerView listaNotas = findViewById(R.id.lista_color_recyclerview);
        ListaCoresAdapter adapter = new ListaCoresAdapter(context, coresNotaList);
        listaNotas.setAdapter(adapter);
        adapter.setOnItemCorClickListener(new OnItemCorClickListener() {
            @Override
            public void onItemClick(int cor) {
               mudarCorBackground(cor);
            }
        });
    }

    private void mudarCorBackground(int cor){
        this.cor = cor;
        View viewById = findViewById(R.id.formNota);
        viewById.setBackgroundColor(this.getResources().getColor(cor));
    }

    private void preencheCampos(Nota notaRecebida) {
        titulo.setText(notaRecebida.getTitulo());
        descricao.setText(notaRecebida.getDescricao());
    }

    private void inicializaCampos() {
        titulo = findViewById(R.id.formulario_nota_titulo);
        descricao = findViewById(R.id.formulario_nota_descricao);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario_nota_salva, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(ehMenuSalvaNota(item)){
            Nota notaCriada = criaNota();
            retornaNota(notaCriada);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void retornaNota(Nota nota) {
        Intent resultadoInsercao = new Intent();
        resultadoInsercao.putExtra(CHAVE_NOTA, nota);
        resultadoInsercao.putExtra(CHAVE_POSICAO, posicaoRecibida);
        setResult(Activity.RESULT_OK,resultadoInsercao);
        Toast.makeText(this, colorName(nota.getCor()), Integer.parseInt("3000")).show();
    }

    @NonNull
    private Nota criaNota() {
        return new Nota(titulo.getText().toString(),
                        descricao.getText().toString(),
                        cor);
    }

    private boolean ehMenuSalvaNota(MenuItem item) {
        return item.getItemId() == R.id.menu_formulario_nota_ic_salva;
    }
}
