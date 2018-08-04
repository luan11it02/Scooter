package com.app.lfc.scooter.Activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;

import com.app.lfc.scooter.Model.Post;
import com.app.lfc.scooter.Model.User;
import com.app.lfc.scooter.Model.YeuCau;
import com.app.lfc.scooter.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HoTroActivity extends AppCompatActivity implements View.OnClickListener{

    ImageButton btnBack;
    AppCompatEditText edtName, edtEmailSDT, edtNoiDung;
    Button btnGui, btnQuayLai;
    Notification.Builder myNotification;
    int NOTIFICATION_ID = 282;
    DatabaseReference database;
    FirebaseUser auth = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ho_tro);
        initView();
        btnBack.setOnClickListener(this);
        btnGui.setOnClickListener(this);
        btnQuayLai.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        database = FirebaseDatabase.getInstance().getReference("user");
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.child(auth.getUid()).getValue(User.class);
                edtEmailSDT.setText(user.getEmailsdt());
                edtName.setText(user.getHoten());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        v.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_click));
        switch (v.getId()){
            case R.id.btn_back_hotro:
                this.finish();
                overridePendingTransition(R.anim.enter, R.anim.exit);
                break;
            case R.id.btn_gui_hotro:
                if (checkInfoInput()) {
                    pushNotification();
                    new AlertDialog.Builder(this)
                            .setTitle("Thông báo")
                            .setMessage("Gửi thành công!!")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    finish();
                                }
                            }).show();
                    SimpleDateFormat df = new SimpleDateFormat("HH:mm  dd/MM/yyyy");
                    final String date = df.format(Calendar.getInstance().getTime());
                    database = FirebaseDatabase.getInstance().getReference("yeucau");
                    database.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            int key = (int) dataSnapshot.getChildrenCount();
                            YeuCau yeuCau = new YeuCau(key, edtName.getText().toString(), edtEmailSDT.getText().toString(), edtNoiDung.getText().toString(), false, date);
                            database.child(String.valueOf(key)).setValue(yeuCau);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
                else new AlertDialog.Builder(this)
                        .setTitle("Thông báo")
                        .setMessage("Vui lòng nhập đầy đủ thông tin yêu cầu!!")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }).show();
                break;
            case R.id.btn_quaylai_hotro:
                this.finish();
                overridePendingTransition(R.anim.enter, R.anim.exit);
                break;
        }
    }

    private void pushNotification(){
        myNotification = new Notification.Builder(this);
        myNotification.setAutoCancel(true);
        myNotification.setSmallIcon(R.drawable.icon_scooter);
        myNotification.setStyle(new Notification.BigPictureStyle());
        myNotification.setContentTitle("Có 1 yêu cầu hỗ trợ mới");
        myNotification.setContentText(edtNoiDung.getText().toString());
        myNotification.setDefaults(Notification.DEFAULT_SOUND);

        NotificationManager notificationService = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification =  myNotification.build();
        notificationService.notify(NOTIFICATION_ID, notification);
    }

    private boolean checkInfoInput(){
        if (edtName.getText().toString().length() > 0 && edtEmailSDT.getText().toString().length() > 0
                && edtNoiDung.getText().toString().length() > 0)
            return true;
        return false;
    }

    private void initView() {
        btnBack     = findViewById(R.id.btn_back_hotro);
        edtName     = findViewById(R.id.edt_name_hotro);
        edtEmailSDT = findViewById(R.id.edt_email_sdt_hotro);
        edtNoiDung  = findViewById(R.id.edt_noidung_hotro);
        btnGui      = findViewById(R.id.btn_gui_hotro);
        btnQuayLai  = findViewById(R.id.btn_quaylai_hotro);
    }


}
