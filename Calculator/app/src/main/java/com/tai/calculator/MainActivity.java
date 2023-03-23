package com.tai.calculator;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.button.MaterialButton;
import com.tai.calculator.databinding.ActivityMainBinding;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityMainBinding binding;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        switch (item.getItemId()) {
            case R.id.subitem1_1:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                setupActionBarDark();
                editor.putString("selected_theme", "theme_dark");
                editor.apply();
                return true;
            case R.id.subitem1_2:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                setupActionBarLight();
                editor.putString("selected_theme", "theme_light");
                editor.apply();
                return true;
            case R.id.subitem2_1:
                setupLocale("vi");
                recreate();
                editor.putString("selected_language","vi");
                editor.apply();
                return true;
            case R.id.subitem2_2:
                setupLocale("");
                recreate();
                editor.putString("selected_language","");
                editor.apply();
                return true;
            case R.id.subitem2_3:
                setupLocale("ko");
                recreate();
                editor.putString("selected_language","ko");
                editor.apply();
                return true;
            case R.id.subitem2_4:
                setupLocale("ja");
                recreate();
                editor.putString("selected_language","ja");
                editor.apply();
                return true;
            case R.id.subitem2_5:
                setupLocale("zh");
                recreate();
                editor.putString("selected_language","zh");
                editor.apply();
                return true;
            case R.id.item3:
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://policies.google.com/privacy?hl=vi"));
                startActivity(intent);
                return true;
            case R.id.item4:
                Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://support.google.com/android/?hl=vi#topic=7313011"));
                startActivity(intent1);
                return true;
            case R.id.item5:
                Dialog dialog = new ItemDialog(MainActivity.this);
                dialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupLocale(@NonNull String language) {
        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(new Locale(language.toLowerCase()));
        resources.updateConfiguration(configuration, displayMetrics);
    }

    private void setupActionBarLight() {
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(Html.fromHtml(
                "<font color=\"black\">" + getString(R.string.app_name) + "</font>"));
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.backgroundAnsRes)));
    }

    private void setupActionBarDark() {
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(Html.fromHtml(
                "<font color=\"white\">" + getString(R.string.app_name) + "</font>"));
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.backgroundAnsRes)));
    }

    private void saveTheme() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String selectedSubItem = sharedPreferences.getString("selected_theme", "theme_light");
        if (selectedSubItem.equals("theme_dark")) {
            setupActionBarDark();
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            setupActionBarLight();
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    private void saveLanguage() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String selectedSubItem = sharedPreferences.getString("selected_language", "");
        switch (selectedSubItem) {
            case "vi":
                setupLocale("vi");
                break;
            case "ko":
                setupLocale("ko");
                break;
            case "ja":
                setupLocale("ja");
                break;
            case "zh":
                setupLocale("zh");
                break;
            default:
                setupLocale("");
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        saveTheme();
        saveLanguage();
        addEvents();
    }

    private void addEvents() {
        binding.button0.setOnClickListener(this);
        binding.button1.setOnClickListener(this);
        binding.button2.setOnClickListener(this);
        binding.button3.setOnClickListener(this);
        binding.button4.setOnClickListener(this);
        binding.button5.setOnClickListener(this);
        binding.button6.setOnClickListener(this);
        binding.button7.setOnClickListener(this);
        binding.button8.setOnClickListener(this);
        binding.button9.setOnClickListener(this);
        binding.buttonAc.setOnClickListener(this);
        binding.buttonC.setOnClickListener(this);
        binding.buttonCloseBracket.setOnClickListener(this);
        binding.buttonOpenBracket.setOnClickListener(this);
        binding.buttonDivide.setOnClickListener(this);
        binding.buttonDot.setOnClickListener(this);
        binding.buttonEquals.setOnClickListener(this);
        binding.buttonMultiply.setOnClickListener(this);
        binding.buttonMinus.setOnClickListener(this);
        binding.buttonPlus.setOnClickListener(this);
    }

    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = binding.tvSolution.getText().toString();

        switch (buttonText) {
            case "AC":
                binding.tvSolution.setText("");
                binding.tvResult.setText("");
                break;
            case "=":
                binding.tvSolution.setText(binding.tvResult.getText());
                binding.tvResult.setText("");
                break;
            case "C":
                dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length() - 1);
                binding.tvSolution.setText(dataToCalculate);
                String finalResult = getResult(dataToCalculate);
                if (!finalResult.equals("Error")) {
                    binding.tvResult.setText(finalResult);
                }
                break;
            default:
                dataToCalculate = dataToCalculate + buttonText;
                binding.tvSolution.setText(dataToCalculate);
                finalResult = getResult(dataToCalculate);
                if (!finalResult.equals("Error")) {
                    binding.tvResult.setText(finalResult);
                }
                break;
        }
    }

    String getResult(String data) {
        try {
            // used to initialize a context object for executing JavaScript code
            // and it provides you with properties and methods for managing and executing JavaScript code.
            Context context = Context.enter();
            // JavaScript code will be executed more slowly, but it will provide more accurate results.
            context.setOptimizationLevel(-1);
            // It provides methods for accessing and manipulating JavaScript objects, variables, and functions.
            Scriptable scriptable = context.initStandardObjects();
            String finalResult = context.evaluateString(scriptable, data, "Javascript", 1, null).toString();
            if (finalResult.endsWith(".0")) {
                finalResult = finalResult.replace(".0", "");
            }
            return finalResult;
        } catch (Exception e) {
            return "Error";
        }
    }
}