package org.crosa.android.popularmovies.database.dao;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.persistence.room.Room;
import android.support.annotation.Nullable;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.crosa.android.popularmovies.database.MoviesLocalDB;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@RunWith(AndroidJUnit4.class)
public class AbstractDBTest {
    protected MovieDao movieDao;
    protected MoviesLocalDB db;
    // This rule forces livedata instant updates.
    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Before
    public void createDb() {
        db = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), MoviesLocalDB.class)
                .allowMainThreadQueries().build();
        movieDao = db.movieDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    //Credits here: https://stackoverflow.com/questions/44270688/unit-testing-room-and-livedata
    public static <T> T getOrAwaitValue(final LiveData<T> liveData) throws InterruptedException {
        final Object[] data = new Object[1];
        final CountDownLatch latch = new CountDownLatch(1);
        Observer<T> observer = new Observer<T>() {
            @Override
            public void onChanged(@Nullable T o) {
                data[0] = o;
                latch.countDown();
                liveData.removeObserver(this);
            }
        };
        liveData.observeForever(observer);
        latch.await(2, TimeUnit.SECONDS);
        //noinspection unchecked
        return (T) data[0];
    }
}
