package com.example.poetryapp.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.poetryapp.Api.ApiClient;
import com.example.poetryapp.Api.ApiInterface;
import com.example.poetryapp.Models.PoetryModel;
import com.example.poetryapp.R;
import com.example.poetryapp.Response.DeleteResponse;
import com.example.poetryapp.UpdatePoetry;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PoetryAdapter extends RecyclerView.Adapter<PoetryAdapter.viewHolder> {

    List<PoetryModel> poetryModels;
    Context context;
    ApiInterface apiInterface;

    public PoetryAdapter(List<PoetryModel> poetryModels, Context context) {
        this.poetryModels = poetryModels;
        this.context = context;
        Retrofit retrofit = ApiClient.getClient();
        apiInterface = retrofit
                .create(ApiInterface.class);
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.potery_list_design, null);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        holder.poetName.setText(poetryModels.get(position).getPoet_name());
        holder.poetry.setText(poetryModels.get(position).getPoetry_data());
        holder.date_time.setText(poetryModels.get(position).getDate_time());

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletepoetry(poetryModels.get(position).getId()+"",position);
            }
        });
        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdatePoetry.class);
                intent.putExtra("p_id",poetryModels.get(position).getId());
                intent.putExtra("p_data",poetryModels.get(position).getPoetry_data());
                context.startActivity(intent);
                Toast.makeText(context, poetryModels.get(position).getId()+"\n"+poetryModels.get(position).getPoetry_data(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return poetryModels.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView poetName, poetry, date_time;
        AppCompatButton btnUpdate, btnDelete;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            poetName = itemView.findViewById(R.id.tvPoetName);
            poetry = itemView.findViewById(R.id.tvPoetryData);
            date_time = itemView.findViewById(R.id.tvPoetryDateAndTime);
            btnDelete = itemView.findViewById(R.id.deleteBtn);
            btnUpdate = itemView.findViewById(R.id.updateBtn);
        }
    }

    private void deletepoetry(String id ,int pose){
        apiInterface.deletepoetry(id).enqueue(new Callback<DeleteResponse>() {
            @Override
            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {
                try {
                    if (response!=null) {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    if (response.body().getStatus().equals("1")){
                        poetryModels.remove(pose);
                        notifyDataSetChanged();
                    }

                    }
                }catch (Exception e){
                    Toast.makeText(context, "Failure: "+ e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    Log.e("Failure: ",e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<DeleteResponse> call, Throwable t) {
                Toast.makeText(context, "Failure: "+ t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                Log.e("Failure: ",t.getLocalizedMessage());
            }
        });
    }


}
