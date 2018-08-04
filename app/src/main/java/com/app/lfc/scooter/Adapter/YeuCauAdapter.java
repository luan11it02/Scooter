package com.app.lfc.scooter.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.app.lfc.scooter.Model.YeuCau;
import com.app.lfc.scooter.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class YeuCauAdapter extends RecyclerView.Adapter<YeuCauAdapter.ViewHolder> {

    private Context context;
    private List<YeuCau> list;
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference("yeucau");

    public YeuCauAdapter(Context context, List<YeuCau> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public YeuCauAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_yeucau, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull YeuCauAdapter.ViewHolder holder, int position) {
        final YeuCau yeuCau = list.get(position);
        holder.txtName.setText(yeuCau.getName());
        holder.txtEmailSdt.setText(yeuCau.getEmailsdt());
        holder.txtContent.setText(yeuCau.getContent());
        holder.txtDate.setText(yeuCau.getDate());
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.child(String.valueOf(yeuCau.getId())).child("duyet").setValue(true);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtName, txtEmailSdt, txtContent, txtDate;
        Button btnDelete;

        ViewHolder(View itemView) {
            super(itemView);
            txtName         = itemView.findViewById(R.id.txt_name_yeucau);
            txtEmailSdt     = itemView.findViewById(R.id.txt_email_sdt_yeucau);
            txtContent      = itemView.findViewById(R.id.txt_content_yeucau);
            btnDelete       = itemView.findViewById(R.id.btn_delete_yeucau);
            txtDate         = itemView.findViewById(R.id.txt_date_yeucau);
        }
    }
}
