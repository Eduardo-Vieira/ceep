package br.com.alura.ceep.model;

import android.util.Log;

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

    @ColumnInfo(name = "position")
    public long position;

    public Nota(long nid, String titulo, String descricao, int cor, long position) {
        this.nid = nid;
        this.titulo = titulo;
        this.descricao = descricao;
        this.cor = cor;
        this.position = position;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getCor() { return cor; }

    public void setNid(long nid){
        this.nid = nid;
    }

    public void setPosition(long position){ this.position = position; }
}
