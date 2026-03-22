package com.showguan.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CalculatorActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_result;

    private String firstNum = "";
    private String operator = "";
    private String secondNum = "";
    private String result = "";
    private String showText = "";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        tv_result = findViewById(R.id.tv_result);
        findViewById(R.id.btn_cancel).setOnClickListener(this);
        findViewById(R.id.btn_divide).setOnClickListener(this);
        findViewById(R.id.btn_multiply).setOnClickListener(this);
        findViewById(R.id.btn_clear).setOnClickListener(this);
        findViewById(R.id.btn_seven).setOnClickListener(this);
        findViewById(R.id.btn_eight).setOnClickListener(this);
        findViewById(R.id.btn_nine).setOnClickListener(this);
        findViewById(R.id.btn_add).setOnClickListener(this);
        findViewById(R.id.btn_four).setOnClickListener(this);
        findViewById(R.id.btn_five).setOnClickListener(this);
        findViewById(R.id.btn_six).setOnClickListener(this);
        findViewById(R.id.btn_minus).setOnClickListener(this);
        findViewById(R.id.btn_one).setOnClickListener(this);
        findViewById(R.id.btn_two).setOnClickListener(this);
        findViewById(R.id.btn_three).setOnClickListener(this);
        findViewById(R.id.btn_sqrt).setOnClickListener(this);
        findViewById(R.id.btn_reciprocal).setOnClickListener(this);
        findViewById(R.id.btn_zero).setOnClickListener(this);
        findViewById(R.id.btn_dot).setOnClickListener(this);
        findViewById(R.id.btn_equal).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        String inputText;
        if (v.getId() == R.id.btn_sqrt) {
            inputText = "√";
        } else {
            inputText = ((TextView) v).getText().toString();
        }

        int id = v.getId();
        if (id == R.id.btn_cancel) {

        } else if (id == R.id.btn_clear) {
            clear();
        } else if (id == R.id.btn_add) {
            operator = inputText;
            refreshText(showText + inputText);
        } else if (id == R.id.btn_minus) {
            operator = inputText;
            refreshText(showText + inputText);
        } else if (id == R.id.btn_multiply) {
            operator = inputText;
            refreshText(showText + inputText);
        } else if (id == R.id.btn_divide) {
            operator = inputText;
            refreshText(showText + inputText);
        } else if (id == R.id.btn_zero) {
            operator = inputText;
            refreshText(showText + inputText);
        } else if (id == R.id.btn_equal) {
            double calculateResult = calculateFour();
            refreshOperator(String.valueOf(calculateResult));
            refreshText(showText + "=" + result);

        } else if (id == R.id.btn_sqrt) {
            double sqrt_result = Math.sqrt(Double.parseDouble(firstNum));
            refreshOperator(String.valueOf(sqrt_result));
            refreshText(showText + "√=" + result);
        } else if (id == R.id.btn_reciprocal) {
            double reciprocal_result = 1.0 / Double.parseDouble(firstNum);
            refreshOperator(String.valueOf(reciprocal_result));
            refreshText(showText + "/=" + result);
        } else{
            //计算出上次计算结果，并且没有输入运算符就输入了数字
            if(result.length() != 0 && operator == ""){
                clear();
            }


            if(operator.equals("")){
                firstNum += inputText;
            }else{
                secondNum += inputText;
            }

            if(showText.equals("0") && !inputText.equals(".")){
                refreshText(inputText);
            }else{
                refreshText(showText + inputText);
            }
        }

    }

    private double calculateFour() {
        switch (operator){
            case "+":
                return Double.parseDouble(firstNum) + Double.parseDouble(secondNum);
            case "-":
                return Double.parseDouble(firstNum) - Double.parseDouble(secondNum);
            case "×":
                return Double.parseDouble(firstNum) * Double.parseDouble(secondNum);
            case "÷":
                return Double.parseDouble(firstNum) / Double.parseDouble(secondNum);
            default:
                break;
        }
        return 0;
    }

    private void clear() {
        refreshOperator("");
        refreshText("");
    }

    private void refreshOperator(String new_result){
        result = new_result;
        firstNum = result;
        secondNum = "";
        operator = "";
    }

    public void refreshText(String text){
        showText = text;
        tv_result.setText(showText);
    }
}