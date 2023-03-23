package com.tai.calculator;

import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;

import com.tai.calculator.databinding.ItemDialogBinding;

public class ItemDialog extends Dialog {
    private ItemDialogBinding itemDialogBinding;

    public ItemDialog(@NonNull Context context) {
        super(context);
        itemDialogBinding = ItemDialogBinding.inflate(getLayoutInflater());
        setContentView(itemDialogBinding.getRoot());
    }
}
