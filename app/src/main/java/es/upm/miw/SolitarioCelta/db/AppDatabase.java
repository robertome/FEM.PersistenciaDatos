package es.upm.miw.SolitarioCelta.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import es.upm.miw.SolitarioCelta.db.converters.DateConverter;
import es.upm.miw.SolitarioCelta.db.daos.GameResultDao;
import es.upm.miw.SolitarioCelta.db.entities.GameResult;

@Database(entities = {GameResult.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "solitario_celta_database";
    private static volatile AppDatabase INSTANCE;

    public abstract GameResultDao gameResultDao();

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {

                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DATABASE_NAME)
                            .allowMainThreadQueries() // TODO: SHOULD NOT BE USED IN PRODUCTION !!!
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}