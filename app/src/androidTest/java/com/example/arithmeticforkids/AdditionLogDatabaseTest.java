package com.example.arithmeticforkids;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


import com.example.arithmeticforkids.database.AdditionLogDAO;
import com.example.arithmeticforkids.database.AdditionLogDatabase;
import com.example.arithmeticforkids.database.AdditionLogRepository;
import com.example.arithmeticforkids.database.DivisionLogDAO;
import com.example.arithmeticforkids.database.MultiplicationLogDAO;
import com.example.arithmeticforkids.database.SubtractionLogDAO;
import com.example.arithmeticforkids.database.UserDAO;
import com.example.arithmeticforkids.database.entities.AdditionLog;
import com.example.arithmeticforkids.database.entities.DivisionLog;
import com.example.arithmeticforkids.database.entities.MultiplicationLog;
import com.example.arithmeticforkids.database.entities.SubtractionLog;
import com.example.arithmeticforkids.database.entities.User;

import java.util.List;

public class AdditionLogDatabaseTest {

//    private AdditionLogDatabase additionLogDatabase;
    private AdditionLogDAO additionLogDAO;
    private SubtractionLogDAO subtractionLogDAO;
    private MultiplicationLogDAO multiplicationLogDAO;
    private DivisionLogDAO divisionLogDAO;
//    private UserDAO userDAO;

    @Rule
    public  InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private AdditionLogDatabase additionLogDatabase;
    private UserDAO userDAO;
    @Before
    public void createDatabase() {
        additionLogDatabase = Room.inMemoryDatabaseBuilder(
                        ApplicationProvider.getApplicationContext(),
                        AdditionLogDatabase.class)
                .allowMainThreadQueries()
                .build();

        additionLogDAO = additionLogDatabase.additionLogDAO();
        subtractionLogDAO = additionLogDatabase.subtractionLogDAO();
        multiplicationLogDAO = additionLogDatabase.multiplicationLogDAO();
        divisionLogDAO = additionLogDatabase.divisionLogDAO();
        userDAO = additionLogDatabase.userDAO();
    }

    @After
    public void closeDatabase() {
        additionLogDatabase.close();
    }

    @Test
    public void testUserInsert() {
        User user = new User("test","test");
        user.setId(1);

        userDAO.insert(user);

        final User[] retrievedUser = new User[1];
        Observer<User> observer = new Observer<User>() {
            @Override
            public void onChanged(User user) {
                retrievedUser[0] = user;
            }
        };

        userDAO.getUserByUserId(1).observeForever(observer);
        userDAO.getUserByUserId(1).removeObserver(observer);

        assertNotNull(retrievedUser[0]);
        assertEquals(user.getId(), retrievedUser[0].getId());
        assertEquals(user.getUsername(), retrievedUser[0].getUsername());
        assertEquals(user.getPassword(), retrievedUser[0].getPassword());
    }

    @Test
    public void testUserUpdate() {
        User user = new User("test","test");
        user.setId(1);

        userDAO.insert(user);

        final User[] retrievedUser = new User[1];
        Observer<User> observer = new Observer<User>() {
            @Override
            public void onChanged(User user) {
                retrievedUser[0] = user;
            }
        };

        userDAO.getUserByUserId(1).observeForever(observer);
        userDAO.getUserByUserId(1).removeObserver(observer);

        assertNotNull(retrievedUser[0]);
        assertEquals(user.getId(), retrievedUser[0].getId());
        assertEquals(user.getUsername(), retrievedUser[0].getUsername());
        assertEquals(user.getPassword(), retrievedUser[0].getPassword());

        AdditionLogRepository.update(user);
        assertEquals(3,user.getId());
        assertEquals("test2", user.getUsername());
        assertEquals("test2", user.getPassword());
    }

    @Test
    public void testUserDelete() {
        User user = new User("test","test");
        user.setId(1);

        userDAO.insert(user);

        final User[] retrievedUser = new User[1];
        Observer<User> observer = new Observer<User>() {
            @Override
            public void onChanged(User user) {
                retrievedUser[0] = user;
            }
        };

        userDAO.getUserByUserId(1).observeForever(observer);
        userDAO.getUserByUserId(1).removeObserver(observer);

        assertNotNull(retrievedUser[0]);
        assertEquals(user.getId(), retrievedUser[0].getId());
        assertEquals(user.getUsername(), retrievedUser[0].getUsername());
        assertEquals(user.getPassword(), retrievedUser[0].getPassword());

        AdditionLogRepository.delete(user);
        assertEquals(-1,user.getId());
        assertNull(user.getUsername());
        assertNull(user.getPassword());
    }

    @Test
    public void testAdditionLogInsert() {
        AdditionLog additionLog = new AdditionLog(10,1);

        additionLogDAO.insert(additionLog);

        List<AdditionLog> AdditionLogs = additionLogDAO.getAdditionRecordByUserId(1);

        assertNotNull(AdditionLogs);
        assertFalse(AdditionLogs.isEmpty());

        AdditionLog retrievedAdditionLog = additionLogDAO.getAdditionRecordByUserId(1).get(0);

        assertEquals(additionLog.getUserId(), retrievedAdditionLog.getId());
        assertEquals(additionLog.getBestScore(), retrievedAdditionLog.getBestScore());
    }

    @Test
    public void testAdditionLogUpdate() {
        AdditionLog additionLog = new AdditionLog(10,1);

        additionLogDAO.insert(additionLog);

        List<AdditionLog> AdditionLogs = additionLogDAO.getAdditionRecordByUserId(1);

        assertNotNull(AdditionLogs);
        assertFalse(AdditionLogs.isEmpty());

        AdditionLog retrievedAdditionLog = additionLogDAO.getAdditionRecordByUserId(1).get(0);

        retrievedAdditionLog.setId(2);
        retrievedAdditionLog.setBestScore(11);

        assertEquals(2, retrievedAdditionLog.getId());
        assertEquals(11, retrievedAdditionLog.getBestScore());
    }

    @Test
    public void testAdditionLogDelete() {
        AdditionLog additionLog = new AdditionLog(10,1);

        additionLogDAO.insert(additionLog);

        List<AdditionLog> AdditionLogs = additionLogDAO.getAdditionRecordByUserId(1);

        assertNotNull(AdditionLogs);
        assertFalse(AdditionLogs.isEmpty());

        AdditionLog retrievedAdditionLog = additionLogDAO.getAdditionRecordByUserId(1).get(0);

        AdditionLogRepository.deleteAdditionLog(retrievedAdditionLog);

        assertEquals(-0, retrievedAdditionLog.getUserId());
        assertEquals(-0, retrievedAdditionLog.getBestScore());
    }

    @Test
    public void testSubtractionLogInsert() {
        SubtractionLog subtractionLog = new SubtractionLog(10,1);

        subtractionLogDAO.insert(subtractionLog);

        List<SubtractionLog> SubtractionLogs = subtractionLogDAO.getSubtractionRecordByUserId(1);

        assertNotNull(SubtractionLogs);
        assertFalse(SubtractionLogs.isEmpty());

        SubtractionLog retrievedSubtractionLog = subtractionLogDAO.getSubtractionRecordByUserId(1).get(0);

        assertEquals(subtractionLog.getUserId(), retrievedSubtractionLog.getId());
        assertEquals(subtractionLog.getBestScore(), retrievedSubtractionLog.getBestScore());
    }

    @Test
    public void testSubtractionLogUpdate() {
        SubtractionLog subtractionLog = new SubtractionLog(10,1);

        subtractionLogDAO.insert(subtractionLog);

        List<SubtractionLog> SubtractionLogs = subtractionLogDAO.getSubtractionRecordByUserId(1);

        assertNotNull(SubtractionLogs);
        assertFalse(SubtractionLogs.isEmpty());

        SubtractionLog retrievedSubtractionLog = subtractionLogDAO.getSubtractionRecordByUserId(1).get(0);

        retrievedSubtractionLog.setId(2);
        retrievedSubtractionLog.setBestScore(11);

        assertEquals(2, retrievedSubtractionLog.getId());
        assertEquals(11, retrievedSubtractionLog.getBestScore());
    }

    @Test
    public void testSubtractionLogDelete() {
        SubtractionLog subtractionLog = new SubtractionLog(10,1);

        subtractionLogDAO.insert(subtractionLog);

        List<SubtractionLog> SubtractionLogs = subtractionLogDAO.getSubtractionRecordByUserId(1);

        assertNotNull(SubtractionLogs);
        assertFalse(SubtractionLogs.isEmpty());

        SubtractionLog retrievedSubtractionLog = subtractionLogDAO.getSubtractionRecordByUserId(1).get(0);

        AdditionLogRepository.deleteSubtractionLog(retrievedSubtractionLog);

        assertEquals(-0, retrievedSubtractionLog.getUserId());
        assertEquals(-0, retrievedSubtractionLog.getBestScore());
    }

    @Test
    public void testMultiplicationLogInsert() {
        MultiplicationLog multiplicationLog = new MultiplicationLog(10,1);

        multiplicationLogDAO.insert(multiplicationLog);

        List<MultiplicationLog> MultiplicationLogs = multiplicationLogDAO.getMultiplicationRecordByUserId(1);

        assertNotNull(MultiplicationLogs);
        assertFalse(MultiplicationLogs.isEmpty());

        MultiplicationLog retrievedMultiplicationLog = multiplicationLogDAO.getMultiplicationRecordByUserId(1).get(0);

        assertEquals(multiplicationLog.getUserId(), retrievedMultiplicationLog.getId());
        assertEquals(multiplicationLog.getBestScore(), retrievedMultiplicationLog.getBestScore());
    }

    @Test
    public void testMultiplicationLogUpdate() {
        MultiplicationLog multiplicationLog = new MultiplicationLog(10,1);

        multiplicationLogDAO.insert(multiplicationLog);

        List<MultiplicationLog> MultiplicationLogs = multiplicationLogDAO.getMultiplicationRecordByUserId(1);

        assertNotNull(MultiplicationLogs);
        assertFalse(MultiplicationLogs.isEmpty());

        MultiplicationLog retrievedMultiplicationLog = multiplicationLogDAO.getMultiplicationRecordByUserId(1).get(0);

        retrievedMultiplicationLog.setId(2);
        retrievedMultiplicationLog.setBestScore(11);

        assertEquals(2, retrievedMultiplicationLog.getId());
        assertEquals(11, retrievedMultiplicationLog.getBestScore());
    }

    @Test
    public void testMultiplicationLogDelete() {
        MultiplicationLog multiplicationLog = new MultiplicationLog(10,1);

        multiplicationLogDAO.insert(multiplicationLog);

        List<MultiplicationLog> MultiplicationLogs = multiplicationLogDAO.getMultiplicationRecordByUserId(1);

        assertNotNull(MultiplicationLogs);
        assertFalse(MultiplicationLogs.isEmpty());

        MultiplicationLog retrievedMultiplicationLog = multiplicationLogDAO.getMultiplicationRecordByUserId(1).get(0);

        AdditionLogRepository.deleteMultiplicationLog(retrievedMultiplicationLog);

        assertEquals(-0, retrievedMultiplicationLog.getUserId());
        assertEquals(-0, retrievedMultiplicationLog.getBestScore());
    }

    @Test
    public void testDivisionLogInsert() {
        DivisionLog divisionLog = new DivisionLog(10,1);

        divisionLogDAO.insert(divisionLog);

        List<DivisionLog> DivisionLogs = divisionLogDAO.getDivisionRecordByUserId(1);

        assertNotNull(DivisionLogs);
        assertFalse(DivisionLogs.isEmpty());

        DivisionLog retrievedDivisionLog = divisionLogDAO.getDivisionRecordByUserId(1).get(0);

        assertEquals(divisionLog.getUserId(), retrievedDivisionLog.getId());
        assertEquals(divisionLog.getBestScore(), retrievedDivisionLog.getBestScore());
    }

    @Test
    public void testDivisionLogUpdate() {
        DivisionLog divisionLog = new DivisionLog(10,1);

        divisionLogDAO.insert(divisionLog);

        List<DivisionLog> DivisionLogs = divisionLogDAO.getDivisionRecordByUserId(1);

        assertNotNull(DivisionLogs);
        assertFalse(DivisionLogs.isEmpty());

        DivisionLog retrievedDivisionLog = divisionLogDAO.getDivisionRecordByUserId(1).get(0);

        retrievedDivisionLog.setId(2);
        retrievedDivisionLog.setBestScore(11);

        assertEquals(2, retrievedDivisionLog.getId());
        assertEquals(11, retrievedDivisionLog.getBestScore());
    }

    @Test
    public void testDivisionLogDelete() {
        DivisionLog divisionLog = new DivisionLog(10,1);

        divisionLogDAO.insert(divisionLog);

        List<DivisionLog> DivisionLogs = divisionLogDAO.getDivisionRecordByUserId(1);

        assertNotNull(DivisionLogs);
        assertFalse(DivisionLogs.isEmpty());

        DivisionLog retrievedDivisionLog = divisionLogDAO.getDivisionRecordByUserId(1).get(0);

        AdditionLogRepository.deleteDivisionLog(retrievedDivisionLog);

        assertEquals(-0, retrievedDivisionLog.getUserId());
        assertEquals(-0, retrievedDivisionLog.getBestScore());
    }
}
