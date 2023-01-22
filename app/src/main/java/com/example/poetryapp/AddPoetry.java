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

public class AddPoetry extends AppCompatActivity {

    Toolbar toolbar;
    EditText poetName, poetryData;
    AppCompatButton submitBtn;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_poetry);
        intilization();
        setUpToolbar();


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String poetryDataString = poetryData.getText().toString();
                String poetryNameString = poetName.getText().toString();

                if (poetryDataString.equals("")) {
                    poetryData.setError("Field is empty");

                } else {
                    if (poetryNameString.isEmpty()) {
                        poetName.setError("Poet name is empty");
                    } else {
                        Toast.makeText(AddPoetry.this, "Call Api", Toast.LENGTH_SHORT).show();
                        callapi(poetryDataString,poetryNameString);
                    }
                }
            }
        });
    }

    private void intilization() {
        toolbar = findViewById(R.id.addPoetryToolbar);
        poetName = findViewById(R.id.etAddPoetName);
        poetryData = findViewById(R.id.etAddPoetryData);
        submitBtn = findViewById(R.id.submitBtn);
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

    private void callapi(String poetryData, String poetName) {
        apiInterface.addpoetry(poetryData, poetName).enqueue(new Callback<DeleteResponse>() {
            @Override
            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {

                try {

                    if (response.body().getStatus().equals("1")) {
                        Toast.makeText(AddPoetry.this, "Added Successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddPoetry.this, "Not Added Successfully", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(AddPoetry.this, "Failure " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    Log.e("Failure: ", e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<DeleteResponse> call, Throwable t) {
                Toast.makeText(AddPoetry.this, "Failure " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                Log.e("Failure: ", t.getLocalizedMessage());
            }
        });
    }

}