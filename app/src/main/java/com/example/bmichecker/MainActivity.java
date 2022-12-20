package com.example.bmichecker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String MSG = "com.BMIChecker.MSG";
    private EditText etName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
    }

    public void checkBMI(View view) {
        Intent intent = new Intent(MainActivity.this, BmiCheck.class);
        String name = etName.getText().toString();
        if(name.length() == 0) {
            Toast.makeText(this, "Name can't be empty!", Toast.LENGTH_LONG).show();
        }
       else {
            intent.putExtra(MSG, name);
            MainActivity.this.startActivity(intent);
        }
    }
}