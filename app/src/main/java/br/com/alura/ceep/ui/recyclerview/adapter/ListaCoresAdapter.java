package br.com.alura.ceep.ui.recyclerview.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.alura.ceep.R;
import br.com.alura.ceep.model.CoresNota;
import br.com.alura.ceep.ui.recyclerview.adapter.listener.OnItemCorClickListener;

public class ListaCoresAdapter extends RecyclerView.Adapter<ListaCoresAdapter.CoresViewHolder> {
    private List<CoresNota> coresNotaList;
    private Context context;
    private OnItemCorClickListener onItemCorClickListener;

    public ListaCoresAdapter(Context context, List<CoresNota> coresNotaList ){
        this.coresNotaList = coresNotaList;
        this.context = context;
    }

    public void setOnItemCorClickListener(OnItemCorClickListener onItemCorClickListener) {
        this.onItemCorClickListener = onItemCorClickListener;
    }

    @Override
    public CoresViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_color,parent,false);
        return new CoresViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CoresViewHolder holder, int position) {
        int cor = coresNotaList.get(position).getColor();
        holder.icColor.getDrawable()
                .setColorFilter(context.getResources().getColor(cor), PorterDuff.Mode.SRC);
        holder.setCor(cor);
    }

    @Override
    public int getItemCount() {
        return coresNotaList.size();
    }

    class CoresViewHolder extends RecyclerView.ViewHolder {
        private final  ImageView icColor;
        private int cor;

        public CoresViewHolder(View itemView) {
            super(itemView);
            icColor = itemView.findViewById(R.id.ic_color);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemCorClickListener.onItemClick(cor);
                }
            });
        }

        public void setCor(int cor){
            this.cor = cor;
        }

    }
}
