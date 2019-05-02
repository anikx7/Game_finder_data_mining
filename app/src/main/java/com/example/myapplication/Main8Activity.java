package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.Activity;
import android.widget.EditText;



public class Main8Activity extends Activity {
    private Button button1;
    private EditText edit_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main8);
        button1 = (Button) findViewById(R.id.button);

        edit_text = (EditText) findViewById(R.id.editText2);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main8Activity.this,Main2Activity.class);
                intent.putExtra("userName",edit_text.getText().toString());
                startActivity(intent);
            }
        });
    }

}
