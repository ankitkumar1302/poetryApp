package com.example.poetryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.poetryapp.Api.ApiClient;
import com.example.poetryapp.Api.ApiInterface;
import com.example.poetryapp.Response.DeleteResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UpdatePoetry extends AppCompatActivity {

    Toolbar toolbar;
    EditText poetryData;
    AppCompatButton updateSubmitBtn;
    int poetryId;
    String poetryDataString;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_poetry);
        initialization();
        setUpToolbar();

        updateSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String p_data = poetryData.getText().toString();
                if (p_data.equals("")) {
                    poetryData.setError("Field is empty");
                } else {
                    callApi(p_data, poetryId + "");
                    Toast.makeText(UpdatePoetry.this, "Call Api", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void initialization() {

        toolbar = findViewById(R.id.updatePoetryToolbar);
        poetryData = findViewById(R.id.etUpdatePoetryData);
        updateSubmitBtn = findViewById(R.id.updateBtn);

        poetryId = getIntent().getIntExtra("p_id", 0);
        poetryDataString = getIntent().getStringExtra("p_data");
        poetryData.setText(poetryDataString);
        Retrofit retrofit = ApiClient.getClient();
        apiInterface = retrofit.create(ApiInterface.class);
    }

    private void setUpToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void callApi(String pData, String pid) {

        apiInterface.updatepoetry(pData, pid).enqueue(new Callback<DeleteResponse>() {
            @Override
            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {

                try {

                    if (response.body().getStatus().equals("1")) {

                        Toast.makeText(UpdatePoetry.this, "Data is Updated", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(UpdatePoetry.this, "Data is not Updated", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(UpdatePoetry.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("Failure", e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<DeleteResponse> call, Throwable t) {
                Toast.makeText(UpdatePoetry.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Failure", t.getLocalizedMessage());

            }
        });
    }

}