package com.dam.sendmeal.repository;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.dam.sendmeal.converter.Converters;
import com.dam.sendmeal.dao.OrderDAO;
import com.dam.sendmeal.dao.PlateDAO;
//import com.dam.sendmeal.dao.PlateOrderRelationDAO;
import com.dam.sendmeal.model.Order;
import com.dam.sendmeal.model.Plate;
//import com.dam.sendmeal.model.PlateOrderRelation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Plate.class, Order.class}, version = 6)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract PlateDAO plateDao();
    public abstract OrderDAO orderDao();

    private static AppDatabase INSTANCE = null;

    public static ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(4);

    static AppDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "send_meal_database").fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
