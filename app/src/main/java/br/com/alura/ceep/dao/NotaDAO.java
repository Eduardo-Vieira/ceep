package br.com.alura.ceep.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.alura.ceep.model.Nota;

@Dao
public interface NotaDAO {

    @Query("SELECT * FROM nota")
    List<Nota> getAll();

    @Query("SELECT * FROM nota WHERE nid IN (:notaIds)")
    List<Nota> loadAllByIds(int[] notaIds);

    @Query("DELETE FROM nota WHERE nid = :nIds")
    void deleteId(long nIds);

    @Insert
    long insertAll(Nota notas);

    @Update
    void updateNota(Nota nota);

    @Delete
    void delete(Nota nota);
}
