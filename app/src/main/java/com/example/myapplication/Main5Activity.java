package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Main5Activity extends Activity {
    private Button button1;
    private EditText edit_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        button1 = (Button) findViewById(R.id.button);

        edit_text = (EditText) findViewById(R.id.editText2);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main5Activity.this,Main6Activity.class);
                intent.putExtra("userName",edit_text.getText().toString());
                startActivity(intent);
            }
        });
    }

}


