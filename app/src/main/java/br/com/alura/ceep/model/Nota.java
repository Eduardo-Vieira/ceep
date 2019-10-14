package br.com.alura.ceep.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Nota implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public long nid;

    @ColumnInfo(name = "titulo")
    public String titulo;

    @ColumnInfo(name = "descricao")
    public String descricao;

    @ColumnInfo(name = "cor")
    public int cor;

    public Nota(String titulo, String descricao, int cor) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.cor = cor;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getCor() { return cor; }

    public long getNid() {
        return nid;
    }

    public void setNid(long nid){
        this.nid = nid;
    }
}
