package com.example.kekoufontandroid;

import org.junit.Test;

import static org.junit.Assert.*;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    private OkHttpClient okHttpClient = new OkHttpClient();
    ;

    @Test
    public void addition_isCorrect() {

        assertEquals(4, 2 + 2);
    }

    @Test
    public void test() throws IOException {



    }
}