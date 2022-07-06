package com.example.quantumsocialapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quantumsocialapp.Model.MainClass;
import com.example.quantumsocialapp.Model.ModelClass;
import com.example.quantumsocialapp.Retrofit.ApiUtilies;
import com.example.quantumsocialapp.databinding.ActivityMainBinding;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    GoogleSignInClient mGoogleSignInClient;
    ActivityMainBinding binding;
    String api = "ef6ff7ca71494d9eb9bc092d61167a10";
    String country= "in";
    ArrayList<ModelClass> list;
    RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(com.firebase.ui.auth.R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        list = new ArrayList<>();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(this, list);
        binding.recyclerView.setAdapter(adapter);

        findNews();
    }


    private void findNews() {

        ApiUtilies.getApiInterface().getNews(country,100,api).enqueue(new Callback<MainClass>() {
            @Override
            public void onResponse(Call<MainClass> call, Response<MainClass> response) {
                if(response.isSuccessful())
                {
                    list.addAll(response.body().getArticles());
                    adapter.notifyDataSetChanged();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Something went Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MainClass> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator = getMenuInflater();
        inflator.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){

            case R.id.settings:
                Toast.makeText(MainActivity.this, "Settings Option Selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.logout:
                auth.getInstance().signOut();
                mGoogleSignInClient.signOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
        }
        return true;
    }
}