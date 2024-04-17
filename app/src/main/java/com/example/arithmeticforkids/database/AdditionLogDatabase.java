package com.example.arithmeticforkids.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.arithmeticforkids.MainActivity;
import com.example.arithmeticforkids.database.entities.AdditionLog;
import com.example.arithmeticforkids.database.entities.DivisionLog;
import com.example.arithmeticforkids.database.entities.MultiplicationLog;
import com.example.arithmeticforkids.database.entities.SubtractionLog;
import com.example.arithmeticforkids.database.entities.User;
import com.example.arithmeticforkids.database.typeConverters.LocalDateTypeConverter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@TypeConverters(LocalDateTypeConverter.class)
@Database(entities = {AdditionLog.class, SubtractionLog.class, MultiplicationLog.class, DivisionLog.class, User.class}, version = 1, exportSchema = false)
public abstract class AdditionLogDatabase extends RoomDatabase {

    public static final String SUBTRACTION_LOG_TABLE = "SubtractionLogTable";
    public static final String MULTIPLICATION_LOG_TABLE = "MultiplicationLogTable";
    public static final String DIVISION_LOG_TABLE = "DivisionLogTable";
    public static final String USER_TABLE = "userTable";
    private static final String DATABASE_NAME = "AdditionLogDatabase";
    public static final String ADDITION_LOG_TABLE = "additionLogTable";

    private static volatile AdditionLogDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    static AdditionLogDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (AdditionLogDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AdditionLogDatabase.class,
                                    DATABASE_NAME
                            )
                            .fallbackToDestructiveMigration()
                            .addCallback(addDefaultValues)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.d(MainActivity.TAG, "DATABASE CREATED!");
            databaseWriteExecutor.execute(() -> {
                UserDAO dao = INSTANCE.userDAO();
                dao.deleteAll();
                User admin = new User("admin1", "admin1");
                admin.setAdmin(true);
                dao.insert(admin);

                User testUser1 = new User("testuser1", "testuser1");
                dao.insert(testUser1);
            });
        }
    };

    public abstract AdditionLogDAO additionLogDAO();

    public abstract SubtractionLogDAO subtractionLogDAO();

    public abstract MultiplicationLogDAO multiplicationLogDAO();

    public abstract DivisionLogDAO divisionLogDAO();

    public abstract UserDAO userDAO();
}
