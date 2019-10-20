package br.com.alura.ceep.ui.recyclerview.helper.callback;

import android.content.Context;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

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
        int marcacoesDeDeslize = ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;
        int marcacoesDeArrastar = ItemTouchHelper.DOWN | ItemTouchHelper.UP
                | ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;
        return makeMovementFlags(marcacoesDeArrastar, marcacoesDeDeslize);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        int posicaoInicial = viewHolder.getAdapterPosition();
        int posicaoFinal = target.getAdapterPosition();
        trocaNotas(posicaoInicial, posicaoFinal);
        return true;
    }

    private void trocaNotas(int posicaoInicial, int posicaoFinal) {
        List<Nota> notas = adapter.troca(posicaoInicial, posicaoFinal);
        AppDatabase.getAppDatabase(context).notaDao().updateAll(notas);
        adapter.notifyDataSetChangedMoved();
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int posicaoDaNotaDeslizada = viewHolder.getAdapterPosition();
        Nota nota = adapter.getItemNotaPosition(posicaoDaNotaDeslizada);
        removeNota(posicaoDaNotaDeslizada, nota);
    }

    private void removeNota(int posicao, Nota nota) {
        AppDatabase.getAppDatabase(context)
        .notaDao().delete(nota);
        adapter.remove(posicao);
    }
}
