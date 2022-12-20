package com.example.bmichecker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BmiCheck extends AppCompatActivity {
    EditText weightText, heightText;
    CardView resCard;
    TextView resultIndex, resultDisc, info, tvNameRes;
    Button btnCalculate, btnClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_check);

        weightText = findViewById(R.id.etWeight);
        heightText = findViewById(R.id.etHeight);
        btnCalculate = findViewById(R.id.btnCalculate);
        btnClear = findViewById(R.id.btnClear);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String w = weightText.getText().toString();
                String h = heightText.getText().toString();

                if(validateInput(w, h)) {
                    double weight = Double.parseDouble(w);
                    double height = Double.parseDouble(h);

                    // height in meter square
                    // bmi formula
                    height = height / 100;
                    double bmi = weight / (height * height);
                    displayResult(bmi);
                }
                hideKeyboard(btnCalculate);
            }
            public void hideKeyboard(View view) {
                try {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                } catch(Exception ignored) {
                }
            }

        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weightText.setText("");
                heightText.setText("");
                resCard = findViewById(R.id.resCard);
                resCard.setVisibility(resCard.GONE);
                Toast.makeText(BmiCheck.this, "All fields cleared", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateInput(String weight, String height) {
        if(weight.length() == 0) {
            Toast.makeText(this, "weight is empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(height.length() == 0) {
            Toast.makeText(this, "height is empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void displayResult(double bmi) {
        Intent intent = getIntent();
        String name =  intent.getStringExtra(MainActivity.MSG);

        resCard = findViewById(R.id.resCard);
        resCard.setVisibility(resCard.VISIBLE);
        tvNameRes = findViewById(R.id.tvNameRes);

        String string = getString(R.string.fit_report);
        string = name + string;
        tvNameRes.setText(string);

        resultIndex = findViewById(R.id.tvIndex);
        resultDisc = findViewById(R.id.tvResult);
        info = findViewById(R.id.tvInfo);

        String bmi_text = String.format("%.2f", bmi);
        resultIndex.setText(bmi_text);
        info.setText("(Normal rage is 18.5 - 24.9)");

        String resultText = "";
        int color=0;
        if(bmi < 18.50) {
            resultText = "Underweight";
            color = R.color.under_weight;
        }
        else if(bmi >= 18.50 && bmi <= 24.9) {
            resultText = "Healthy";
            color = R.color.normal;
        }
        else if(bmi >= 25.0 && bmi <= 29.9) {
            resultText = "Overweight";
            color = R.color.over_weight;
        }
        else {
            resultText = "Obese";
            color = R.color.obese;
        }



        resultDisc.setTextColor(ContextCompat.getColor(this, color));
        resultDisc.setText(resultText);
    }
}