package com.example.krishna.copydatabase;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class MainActivity extends ActionBarActivity {


    private Button btnCopy;
    private Button btnGet;
    private TextView tvData;
    private EditText etText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCopy = (Button) findViewById(R.id.btn_copy);
        btnGet = (Button) findViewById(R.id.btn_getData);
        tvData = (TextView) findViewById(R.id.tv_db);
        etText = (EditText) findViewById(R.id.et_text);

        File fil=getFilesDir();
        Toast.makeText(this, "Dir:"+fil.getAbsolutePath()+"  "+fil.exists(), Toast.LENGTH_SHORT).show();

        btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // copyDatabase();

                Bitmap mybitmap= BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                mybitmap.compress(Bitmap.CompressFormat.PNG, 90, bytes);
                File fileWithinMyDir = new File(getFilesDir(), "thumbnail.png");
                try {
                    FileOutputStream fos = new FileOutputStream(fileWithinMyDir);
                    fos.write(bytes.toByteArray());
                    fos.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataFromDatabase();
            }
        });

    }

    private void getDataFromDatabase() {


        MyCopiedDb db=new MyCopiedDb(this);
        Toast.makeText(this, "Rows:"+db.getData(),Toast.LENGTH_SHORT).show();
    }

    private void copyDatabase() {

        try {
            InputStream myInput = getAssets().open(MyCopiedDb.DB_NAME);
            File file=new File("/data/data/"+getPackageName()+"/databases/");
            file.mkdirs();


            String outputFileName = file.getAbsolutePath()+"/"+MyCopiedDb.DB_NAME;

            Log.d("MainActivity", "copyDatabase (Line:60) :"+" dbfolder:"+file.getAbsolutePath()+"  dbFile:"+outputFileName);
            OutputStream myOutput = new FileOutputStream(outputFileName);

            byte[] buffer = new byte[1024];
            int length;

            while((length = myInput.read(buffer))>0){
                myOutput.write(buffer, 0, length);
            }

            myOutput.flush();
            myOutput.close();
            myInput.close();

            Log.d("MainActivity", "copyDatabase (Line:69) :"+"successfullyCopied");
        } catch (Exception e) {
            Log.e("tle99 - copyDatabase", e.getMessage());
        }
    }
}
