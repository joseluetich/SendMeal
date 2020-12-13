package com.dam.sendmeal.repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.dam.sendmeal.dao.PlateDAO;
import com.dam.sendmeal.model.Plate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Plate.class},version = 2)
public abstract class AppDataBase extends RoomDatabase {

    public abstract PlateDAO plateDAO();

    private static AppDataBase INSTANCE;

    public static ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(4);

    public static AppDataBase getInstance(final Context context){
        if(INSTANCE == null){
            synchronized (AppDataBase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDataBase.class, "send_meal_database").fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    /**
     * Populate the database in the background.
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final PlateDAO plateDAO;
        String[] titles = {"Pizza mozzarella", "Cheese Burger", "Hot Dog", "Sandwich"};
        String[] descriptions = {"Sauce, mozzarella cheese and olives", "150g of meat, Cheddar cheese and fries", "With pickles", "White bread, ham, american cheese, lettuce and tomato"};
        Double[] prices = {245.00, 215.00, 130.00, 168.00};
        Integer[] calories = {485, 846, 631, 269};

        PopulateDbAsync(AppDataBase db) {
            plateDAO = db.plateDAO();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate the database
            // when it is first created

            plateDAO.deleteAll();

            for (int i = 0; i <= titles.length - 1; i++) {
                Plate plate = new Plate(titles[i], descriptions[i], prices[i], calories[i]);
                plateDAO.add(plate);
            }
            return null;
        }
    }

}
