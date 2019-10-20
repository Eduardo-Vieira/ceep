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

    @Query("SELECT * FROM nota order by position")
    List<Nota> getAll();

    @Insert
    long insert(Nota nota);

    @Update
    void update(Nota nota);

    @Update
    void updateAll(List<Nota> notas);

    @Delete
    void delete(Nota nota);
}
