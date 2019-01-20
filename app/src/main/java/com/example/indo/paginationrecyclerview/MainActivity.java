package com.example.indo.paginationrecyclerview;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.indo.paginationrecyclerview.adapter.ContactAdapter;
import com.example.indo.paginationrecyclerview.model.Contact;
import com.example.indo.paginationrecyclerview.model.OnLoadMoreListerner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    private List<Contact> contacts;
    private ContactAdapter contactAdapter;
    private Random random;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         contacts = new ArrayList<>();
         random = new Random();

         for (int i = 0 ; i < 30; i++){
             Contact contact = new Contact();
             contact.setPhone("123456");
             contact.setEmail("infoo@gmail"+ i+"@gmail.com");
             contacts.add(contact);
         }
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.rv_contact);
         recyclerView.setLayoutManager(new LinearLayoutManager(this));
         contactAdapter = new ContactAdapter(recyclerView, contacts, this);
         recyclerView.setAdapter(contactAdapter);
         contactAdapter.setOnLoadMoreListerner(new OnLoadMoreListerner() {
             @Override
             public void onLoadMore() {
                 if (contacts.size() <= 40){
                     contacts.add(null);
                     contactAdapter.notifyItemInserted(contacts.size() - 1);
                     new Handler().postDelayed(new Runnable() {
                         @Override
                         public void run() {
                             contacts.remove(contacts.size() -1);
                              contactAdapter.notifyItemRemoved(contacts.size());

                              int index = contacts.size();
                              int end = index +30;

                              for (int i = index; i < end; i++){
                                  Contact contact = new Contact();
                                  contact.setPhone("1234");
                                  contact.setEmail("info"+i+"@gmail.com");
                                  contacts.add(contact);
                              }
                              contactAdapter.notifyDataSetChanged();
                              contactAdapter.setLoaded();
                         }
                     }, 5000);
                 }else {
                     Toast.makeText(MainActivity.this, "Loading data completed", Toast.LENGTH_LONG).show();
                 }
             }
         });

    }
}















