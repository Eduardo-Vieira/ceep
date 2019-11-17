package br.com.alura.ceep.ui.recyclerview.helper.callback;

import android.content.Context;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.List;

import br.com.alura.ceep.database.AppDatabase;
import br.com.alura.ceep.model.Nota;
import br.com.alura.ceep.ui.recyclerview.adapter.ListaNotasAdapter;

public class NotaItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private final ListaNotasAdapter adapter;
    private final Context context;

    public NotaItemTouchHelperCallback(ListaNotasAdapter adapter, Context context) {
        this.adapter = adapter;
        this.context = context;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
             final int marcacoesDeArrastar = ItemTouchHelper.UP | ItemTouchHelper.DOWN |
                                            ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            final int marcacoesDeDeslize = 0;
            return makeMovementFlags(marcacoesDeArrastar, marcacoesDeDeslize);
        } else {
            final int marcacoesDeArrastar = ItemTouchHelper.UP | ItemTouchHelper.DOWN
                                        | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            final int marcacoesDeDeslize = ItemTouchHelper.START | ItemTouchHelper.END;
            return makeMovementFlags(marcacoesDeArrastar, marcacoesDeDeslize);
        }
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        int posicaoInicial = viewHolder.getAdapterPosition();
        int posicaoFinal = target.getAdapterPosition();
        if(viewHolder.getItemViewType() != target.getItemViewType()){return false;}
        trocaNotas(posicaoInicial, posicaoFinal);
        return true;
    }

    private void trocaNotas(int posicaoInicial, int posicaoFinal) {
        List<Nota> notas =  adapter.troca(posicaoInicial, posicaoFinal);
        atualizaPosicaoNobancoDeDados(notas);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int posicaoDaNotaDeslizada = viewHolder.getAdapterPosition();
        removeNota(posicaoDaNotaDeslizada);
    }

    private void atualizaPosicaoNobancoDeDados(List<Nota> notas){
        AppDatabase.getAppDatabase(context)
                .notaDao().updateAll(notas);
    }

    private void removeNotaNoBancoDeDados(Nota nota){
        AppDatabase.getAppDatabase(context)
                .notaDao().delete(nota);
    }

    private void removeNota(int posicao) {
       Nota nota = adapter.getItemNotaPosition(posicao);
        removeNotaNoBancoDeDados(nota);
        adapter.remove(posicao);
    }
}


