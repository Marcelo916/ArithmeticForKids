package com.example.arithmeticforkids.database;

import android.app.Application;
import android.util.Log;

import com.example.arithmeticforkids.MainActivity;
import com.example.arithmeticforkids.database.entities.AdditionLog;
import com.example.arithmeticforkids.database.entities.DivisionLog;
import com.example.arithmeticforkids.database.entities.MultiplicationLog;
import com.example.arithmeticforkids.database.entities.SubtractionLog;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AdditionLogRepository {
    private final AdditionLogDAO additionLogDAO;
    private final SubtractionLogDAO subtractionLogDAO;
    private final MultiplicationLogDAO multiplicationLogDAO;
    private final DivisionLogDAO divisionLogDAO;
    private ArrayList<AdditionLog> allLogs;
    private static AdditionLogRepository repository;

    public AdditionLogRepository(Application application) {
        AdditionLogDatabase db = AdditionLogDatabase.getDatabase(application);
        this.additionLogDAO = db.additionLogDAO();
        this.subtractionLogDAO = db.subtractionLogDAO();
        this.multiplicationLogDAO = db.multiplicationLogDAO();
        this.divisionLogDAO = db.divisionLogDAO();
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

    public void insertSubtractionLog(SubtractionLog subtractionLog) {
        AdditionLogDatabase.databaseWriteExecutor.execute(() ->
                {
                    subtractionLogDAO.insert(subtractionLog);
                }
        );
    }

    public void insertMultiplicationLog(MultiplicationLog multiplicationLog) {
        AdditionLogDatabase.databaseWriteExecutor.execute(() ->
        {
            multiplicationLogDAO.insert(multiplicationLog);
        }
        );
    }

    public void insertDivisionLog(DivisionLog divisionLog) {
        AdditionLogDatabase.databaseWriteExecutor.execute(() ->
        {
            divisionLogDAO.insert(divisionLog);
        }
        );
    }

    public ArrayList<SubtractionLog> getAllLogsSubtraction() {
        Future<ArrayList<SubtractionLog>> future = AdditionLogDatabase.databaseWriteExecutor.submit(new Callable<ArrayList<SubtractionLog>>() {
            @Override
            public ArrayList<SubtractionLog> call() throws Exception {
                return (ArrayList<SubtractionLog>) subtractionLogDAO.getSubtractionRecords();
            }
        });
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.i(MainActivity.TAG, "Problem when getting all SubtractionLogs in the repository");
        }
        return null;
    }

    public ArrayList<MultiplicationLog> getAllLogsMultiplication() {
        Future<ArrayList<MultiplicationLog>> future = AdditionLogDatabase.databaseWriteExecutor.submit(new Callable<ArrayList<MultiplicationLog>>() {
            @Override
            public ArrayList<MultiplicationLog> call() throws Exception {
                return (ArrayList<MultiplicationLog>) multiplicationLogDAO.getMultiplicationRecords();
            }
        });
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.i(MainActivity.TAG, "Problem when getting all MultiplicationLogs in the repository");
        }
        return null;
    }

    public ArrayList<DivisionLog> getAllLogsDivision() {
        Future<ArrayList<DivisionLog>> future = AdditionLogDatabase.databaseWriteExecutor.submit(new Callable<ArrayList<DivisionLog>>() {
            @Override
            public ArrayList<DivisionLog> call() throws Exception {
                return (ArrayList<DivisionLog>) divisionLogDAO.getDivisionRecords();
            }
        });
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.i(MainActivity.TAG, "Problem when getting all DivisionLogs in the repository");
        }
        return null;
    }
}
