package com.nut2014.newtech.testAidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.nut2014.baselibrary.utils.FLog;
import com.nut2014.newtech.R;

import java.util.List;
import java.util.Random;

public class TestAidlActivity extends AppCompatActivity {
    private static final String TAG = "TestAidlActivity";
    private BookController bookController;
    private boolean connected;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            FLog.d(TAG, "onServiceConnected");
            bookController = BookController.Stub.asInterface(service);
            connected = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            connected = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_aidl);
        bindService();
        findViewById(R.id.add_btn).setOnClickListener(v -> {
            addBook();
        });
        findViewById(R.id.show_btn).setOnClickListener(v -> {
            showBook();
        });
    }

    private void bindService() {
        Intent intent = new Intent();
        intent.setPackage("com.nut2014.newtech");
        intent.setAction("com.nut2014.newtech.action");
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private void showBook() {
        if (connected) {
            try {
                List<Book> bookList = bookController.getBookList();
                for (Book book : bookList) {
                    System.out.println(book.getName() + ">>");
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }


    }

    private void addBook() {
        if (connected) {
            Book book = new Book("这是一本新书" + new Random().nextInt());
            try {
                bookController.addBookInOut(book);
                Log.e(TAG, "success");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (connected) {
            unbindService(serviceConnection);
        }
    }
}