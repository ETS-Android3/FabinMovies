package com.fmoreno.fabinmovies.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.fmoreno.fabinmovies.db.Dao.MovieDao;
import com.fmoreno.fabinmovies.db.Entity.Movie;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class DataBase extends RoomDatabase {
    private static Context mContext;
    // Exposición de DAOs
    public abstract MovieDao movieDao();

    private static final String DATABASE_NAME = "database-movie-db";

    private static DataBase INSTANCE;

    private static final int THREADS = 4;

    public static final ExecutorService dbExecutor = Executors.newFixedThreadPool(THREADS);

    public static DataBase getInstance(final Context context) {
        mContext = context;
        if (INSTANCE == null) {
            synchronized (DataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(), DataBase.class,
                            DATABASE_NAME)
                            .addCallback(mRoomCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // close database
    public static void destroyInstance(){
        if (INSTANCE.isOpen()) INSTANCE.close();
        INSTANCE = null;
    }

    // Prepoblar base de datos con callbackl
    private static final RoomDatabase.Callback mRoomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            dbExecutor.execute(() -> {
                /*ShoppingListDao dao = INSTANCE.shoppingListDao();

                ShoppingList list1 = new ShoppingList("1", "Lista de ejemplo");
                ShoppingList list2 = new ShoppingList("2", "Banquete de Navidad");
                ShoppingList list3 = new ShoppingList("3", "Alessandro Moreno");

                dao.insert(list1);
                dao.insert(list2);
                dao.insert(list3);
                */
            });
        }
    };

}
