package com.example.projectsaraAldegaither;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {

    String productN,productQuan,productR;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bt1 = (Button)findViewById(R.id.button);
        Button bt2 = (Button)findViewById(R.id.button2);
        Button bt3 = (Button)findViewById(R.id.button3);

        final DatabaseHelper MyDB = new DatabaseHelper(this);

        EditText ed1 = (EditText) findViewById(R.id.productName);
        EditText ed2 = (EditText) findViewById(R.id.productQuantity);
        EditText ed3 = (EditText) findViewById(R.id.productReview);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productN = ed1.getText().toString();
                productQuan = ed2.getText().toString();
                productR = ed3.getText().toString();
                if(Integer.parseInt(productQuan)>=1000){
                    Toast.makeText(getApplicationContext(), "The value should be way less than 1000.", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(getApplicationContext(), "Thanks!", Toast.LENGTH_LONG).show();

                MyDB.AddProduct(productN,productQuan,productR);
                Log.d("SarahD", "log message");

                Toasty.info(getBaseContext(), "The Product is added.", Toast.LENGTH_SHORT, true).show();
                Cursor cursor = MyDB.getListContents();
                //find out how to read cursor data.
                //Log.d("sara",cursor)
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.error(getBaseContext(), "This is an error toast.", Toast.LENGTH_SHORT, true).show();
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDB.deleteProduct(productN,productQuan,productR);
                Toasty.success(getBaseContext(), "Item is deleted.", Toast.LENGTH_SHORT, true).show();
            }
        });
    }

}