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
    private static AdditionLogRepository repository;

    public AdditionLogRepository(Application application) {
        AdditionLogDatabase db = AdditionLogDatabase.getDatabase(application);
        this.additionLogDAO = db.additionLogDAO();
        this.allLogs = (ArrayList<AdditionLog>) this.additionLogDAO.getAdditionRecords();
    }

    public static AdditionLogRepository getRepository(Application application) {
        if(repository != null) {
            return repository;
        }
        Future<AdditionLogRepository> future = AdditionLogDatabase.databaseWriteExecutor.submit(
                new Callable<AdditionLogRepository>() {
                    @Override
                    public AdditionLogRepository call() throws Exception {
                        return new AdditionLogRepository(application);
                    }
                }
        );
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.d(MainActivity.TAG, "Problem getting AdditionLogRepository, thread error.");
        }
        return null;
    }

    public ArrayList<AdditionLog> getAllLogs() {
        Future<ArrayList<AdditionLog>> future = AdditionLogDatabase.databaseWriteExecutor.submit(
                new Callable<ArrayList<AdditionLog>>() {
                    @Override
                    public ArrayList<AdditionLog> call() throws Exception {
                        return (ArrayList<AdditionLog>) additionLogDAO.getAdditionRecords();
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
