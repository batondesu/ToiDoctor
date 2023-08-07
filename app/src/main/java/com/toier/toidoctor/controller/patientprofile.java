package com.toier.toidoctor.controller;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.toier.toidoctor.R;

import java.util.ArrayList;

public class patientprofile {

    private static ArrayList<patient> patients = new ArrayList<patient>();

    public static Button createButton(Context context, Button btnTag, String id , String name) {
        btnTag.setCompoundDrawablesWithIntrinsicBounds(R.drawable.profile_icon, 0, 0, 0);
        btnTag.setText(name);
        btnTag.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        btnTag.setTypeface(Typeface.DEFAULT);
        int textColor = 0xFF040404;
        btnTag.setTextColor(textColor);
        int whiteColor = 0xFFFFFFFF;
        btnTag.setBackgroundColor(whiteColor);
        int heightInDp = 74;
        float scale = context.getResources().getDisplayMetrics().density;
        int heightInPixels = (int) (heightInDp * scale + 0.5f);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, // Set the desired width (you can use WRAP_CONTENT, MATCH_PARENT, or specific pixel values)
                heightInPixels  // Set the desired height (you can use WRAP_CONTENT, MATCH_PARENT, or specific pixel values)
        );

        int paddingInDp = 10; // Replace this with the desired padding in dp

        int paddingInPixels = (int) (paddingInDp * scale + 0.5f); // Adding 0.5f for rounding to the nearest pixel
        btnTag.setPadding(paddingInPixels, 0, paddingInPixels, 0);

        btnTag.setLayoutParams(params);

        int marginInDp = 5;
        int marginInPixels = (int) (marginInDp * scale + 0.5f);
        params.setMargins(marginInPixels, marginInPixels, marginInPixels, marginInPixels);
        int paddingBetweenIconAndTextInPixels = (int) (8 * scale + 0.5f);
        btnTag.setCompoundDrawablePadding(paddingBetweenIconAndTextInPixels);
        //btnTag.setBackgroundResource(R.drawable.rectangle);

        String tagString = id;
        btnTag.setTag(tagString);
        return btnTag;
    }



}
