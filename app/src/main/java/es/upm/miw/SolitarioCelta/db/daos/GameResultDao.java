package es.upm.miw.SolitarioCelta.db.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import es.upm.miw.SolitarioCelta.db.entities.GameResult;

@Dao
public interface GameResultDao {
    @Query("SELECT * FROM GameResult")
    List<GameResult> readAll();

    @Insert
    void save(GameResult gameResult);

    @Query("DELETE FROM GameResult")
    void deleteAll();
}