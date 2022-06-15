package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText edtName, edtPwd;
    private Button btnLogin;
    private CheckBox chkSave, chkLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        read();
        btnLogin.setOnClickListener(v -> {
            boolean save = chkSave.isChecked();
            boolean auto = chkLogin.isChecked();
            String username = edtName.getText().toString();
            String userpwd = edtPwd.getText().toString();
            if (save) {
                write(username, userpwd, auto);
                Toast.makeText(MainActivity.this, "登录信息已经保存", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
            }
            Intent intent = new Intent(MainActivity.this, Main2Activity.class);
            MainActivity.this.startActivity(intent);
        });
    }

    void initView() {
        edtName = this.findViewById(R.id.edtName);
        edtPwd = this.findViewById(R.id.edtPwd);
        btnLogin = this.findViewById(R.id.btnLogin);
        chkLogin = this.findViewById(R.id.chkLogin);
        chkSave = this.findViewById(R.id.chkSave);
    }

    void read() {
        SharedPreferences sharedPreferences = getSharedPreferences("userconfig", MODE_PRIVATE);
        String userName = sharedPreferences.getString("loginname", "");
        String userPwd = sharedPreferences.getString("loginpwd", "");
        boolean isSave = sharedPreferences.getBoolean("loginsave", false);
        boolean isAuto = sharedPreferences.getBoolean("loginauto", false);
        if (isSave) {
            edtName.setText(userName);
            edtPwd.setText(userPwd);
        }
        if (isAuto) {
            Intent intent = new Intent(MainActivity.this, Main2Activity.class);
            MainActivity.this.startActivity(intent);
        }
    }

    void write(String userName, String userPwd, boolean isAuto) {
        SharedPreferences sharedPreferences = getSharedPreferences("userconfig", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("loginname", userName);
        editor.putString("loginpwd", userPwd);
        editor.putBoolean("loginsave", true);
        editor.putBoolean("loginauto", isAuto);
        editor.apply();
    }
}
