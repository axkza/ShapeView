package com.blued.shapeview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.blued.shapeview.view.ShapeHelper;
import com.blued.shapeview.view.ShapeModel;
import com.blued.shapeview.view.ShapeTextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        findViewById(R.id.tv_one).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "xxxx", Toast.LENGTH_SHORT).show();
//            }
//        });

        final ShapeTextView shapeTextView = findViewById(R.id.tv_two);
        shapeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShapeHelper.setBgModel(shapeTextView, ShapeHelper.BG_MODEL.SOLID);
            }
        });
    }
}
