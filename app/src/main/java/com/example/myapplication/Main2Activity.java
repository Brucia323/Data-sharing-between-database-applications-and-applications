package com.example.myapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class Main2Activity extends AppCompatActivity {
    private Button btnInsert, btnDel, btnUpdate, btnSql;
    private EditText edtNo, edtName, edtScore, edtSqlContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);
        initView();
        File file = this.getDatabasePath("database").getParentFile();
        assert file != null;
        if (!file.exists()) {
            file.mkdir();
        }
        final SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(file + File.separator + "testDB.db", MODE_PRIVATE, null);

//        第一次打开需要这两行
//        String createSQL = "create table student(sno text,sname text,sscore float)";
//        sqLiteDatabase.execSQL(createSQL);

        btnInsert.setOnClickListener(view -> {
            String sno = edtNo.getText().toString();
            String sname = edtName.getText().toString();
            float sscore = Float.parseFloat(edtScore.getText().toString());
            ContentValues values = new ContentValues();
            values.put("sno", sno);
            values.put("sname", sname);
            values.put("sscore", sscore);
            long m = sqLiteDatabase.insert("student", null, values);
            if (m > 0) {
                Toast.makeText(Main2Activity.this, "插入成功", Toast.LENGTH_SHORT).show();
            }
        });
        btnDel.setOnClickListener(view -> {
            String whereno = "sno=?";
            String sno = edtNo.getText().toString();
            String[] args = new String[]{sno};
            sqLiteDatabase.delete("student", whereno, args);
        });
        btnUpdate.setOnClickListener(view -> {
            ContentValues values = new ContentValues();
            String sname = edtName.getText().toString();
            float sscore = Float.parseFloat(edtScore.getText().toString());
            values.put("sscore", sscore);
            String whereno = "sname=?";
            String[] args = new String[]{sname};
            sqLiteDatabase.update("student", values, whereno, args);
        });
        btnSql.setOnClickListener(view -> {
            String findno = edtNo.getText().toString();
            Cursor cursor = sqLiteDatabase.query("student", new String[]{"*"}, "sno=?", new String[]{findno}, null, null, null, null);
            while (cursor.moveToNext()) {
                String sno = cursor.getString(0);
                String sname = cursor.getString(1);
                String sscore = cursor.getString(2);
                String content = edtSqlContent.getText().toString() + "\n";
                edtSqlContent.setText(content + sno + ":" + sname + ":" + sscore);
            }
        });
    }

    void initView() {
        btnInsert = (Button) this.findViewById(R.id.btnInsert);
        edtName = (EditText) this.findViewById(R.id.edtName);
        edtNo = (EditText) this.findViewById(R.id.edtNo);
        edtScore = (EditText) this.findViewById(R.id.edtScore);
        edtSqlContent = (EditText) this.findViewById(R.id.edtSqlContent);
        btnDel = (Button) this.findViewById(R.id.btnDel);
        btnUpdate = (Button) this.findViewById(R.id.btnUpdate);
        btnSql = (Button) this.findViewById(R.id.btnSql);
    }
}
