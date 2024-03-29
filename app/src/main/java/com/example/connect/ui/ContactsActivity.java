package com.example.connect.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.connect.R;
import com.example.connect.adapters.UsersAdapter;
import com.example.connect.users.network.UsersApiClient;
import com.example.connect.users.response.UserListResponse;
import com.example.connect.models.UserModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactsActivity extends AppCompatActivity {

    //variables
    private ImageView mBackButton, mSettingsButton;
    private TextView mContactsLength;
    private RecyclerView mRecyclerView;

    private ArrayList<UserListResponse> mUserModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        mBackButton = findViewById(R.id.contacts_back_btn);
        mContactsLength = findViewById(R.id.my_contacts_text);
        mSettingsButton = findViewById(R.id.settings_btn);
        mRecyclerView = findViewById(R.id.users_recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        callPreviousScreen();
        callSettingsScreen();

        getAllUsers();
    }

    private void callSettingsScreen() {
        mSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactsActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void callPreviousScreen() {
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactsActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getAllUsers() {
        Call<UserListResponse> usersList = UsersApiClient.getService().getAllUsers(UsersApiClient.API_KEY);
        usersList.enqueue(new Callback<UserListResponse>() {
            @Override
            public void onResponse(Call<UserListResponse> call, Response<UserListResponse> response) {
                if (response.isSuccessful()) {
                    List<UserModel> userListResponse = response.body().getUsersList();

                    for (int i=0; i<userListResponse.size(); i++) {
                        UsersAdapter usersAdapter = new UsersAdapter((ArrayList<UserModel>) userListResponse);
                        mRecyclerView.setAdapter(usersAdapter);
                        usersAdapter.notifyDataSetChanged();
                    }

                    int length = Integer.parseInt(response.body().getLength()) -1;
                    mContactsLength.setText("My Contacts (" + length + ")");
                }
            }

            @Override
            public void onFailure(Call<UserListResponse> call, Throwable t) {
                Toast.makeText(ContactsActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}