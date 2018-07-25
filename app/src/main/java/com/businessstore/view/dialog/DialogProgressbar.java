package com.businessstore.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ProgressBar;

import activity.com.businessstore.R;

public class DialogProgressbar extends Dialog {
    private ProgressBar progressBar_circle;
    public DialogProgressbar(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_progressbar);
        progressBar_circle=findViewById(R.id.progressBar_circle);


    }

    @Override
    public void show() {
        super.show();
    }
}
