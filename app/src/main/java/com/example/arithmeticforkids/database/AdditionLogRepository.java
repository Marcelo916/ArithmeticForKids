package com.example.arithmeticforkids.database;

import android.app.Application;
import android.util.Log;

import com.example.arithmeticforkids.MainActivity;
import com.example.arithmeticforkids.database.entities.AdditionLog;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AdditionLogRepository {
    private AdditionLogDAO additionLogDAO;
    private ArrayList<AdditionLog> allLogs;

    public AdditionLogRepository(Application application) {
        AdditionLogDatabase db = AdditionLogDatabase.getDatabase(application);
        this.additionLogDAO = db.additionLogDAO();
        this.allLogs = this.additionLogDAO.getAdditionRecords();
    }

    public ArrayList<AdditionLog> getAllLogs() {
        Future<ArrayList<AdditionLog>> future = AdditionLogDatabase.databaseWriteExecutor.submit(
                new Callable<ArrayList<AdditionLog>>() {
                    @Override
                    public ArrayList<AdditionLog> call() throws Exception {
                        return additionLogDAO.getAdditionRecords();
                    }
                }
        );
        try{
            return  future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.i(MainActivity.TAG, "Problem when getting all AdditionLogs in the repository");
        }
        return null;
    }

    public void insertAdditionLog(AdditionLog additionLog) {
        AdditionLogDatabase.databaseWriteExecutor.execute(() ->
                {
                    additionLogDAO.insert(additionLog);
                }
        );
    }

}
