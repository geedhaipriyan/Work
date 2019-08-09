package com.example.geedh.materialdesign;

import android.content.ActivityNotFoundException;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.jar.Manifest;

public class mainActivity2 extends AppCompatActivity{
    private TextView t;
    private Toolbar tool;
    private ImageView li;
    private ImageButton copy,speak;
    private Button pdf;
    private TextToSpeech tt;
    private static final int STORAGE=1000;
    @Override
    protected void onCreate(Bundle savedInstancestate) {
        super.onCreate(savedInstancestate);
        setContentView(R.layout.activity_main2);
        Intent i = getIntent();
        t = findViewById(R.id.text);
        copy = findViewById(R.id.imageButton3);
        pdf = findViewById(R.id.button2);
        tt = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {

            int result = tt.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                speak.setEnabled(true);
                //speakout();
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }
            }
        });
        speak = findViewById(R.id.imageButton4);
        if(i.hasExtra("img")) {
            t.setText(i.getStringExtra("img"));
        }
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clip = (ClipboardManager)getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
                clip.setText(t.getText().toString());
                Toast.makeText(getApplicationContext(),"TEST COPIED",Toast.LENGTH_SHORT).show();
            }
        });
        pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>Build.VERSION_CODES.M)
                {
                    if(checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED)
                    {
                        String permissions = (android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
                        requestPermissions(new String[]{permissions},STORAGE);

                    }else{
                        savepdf();
                    }
                }else
                {
                    savepdf();
                }
            }
        });
//        li = findViewById(R.id.imageView3);
//        Bundle ext = getIntent().getExtras();
//        Bitmap bitmap = (Bitmap) ext.getParcelable("img");
//        li.setImageBitmap(bitmap);
        //tool = findViewById(R.id.toolbar);
        //setActionBar(tool);
        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakout();

            }
        });
    }
    @Override
    public void onDestroy() {
// Don't forget to shutdown tts!
        if (tt != null) {
            tt.stop();
            tt.shutdown();
        }
        super.onDestroy();
    }

    private void speakout() {
        String text = t.getText().toString();
        tt.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
    private void savepdf() {
        Document doc = new Document();
        String fname = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis());
        String fpath = Environment.getExternalStorageDirectory()+"/"+ fname+".pdf";
        try{
            PdfWriter.getInstance(doc,new FileOutputStream(fpath));
            doc.open();
            String textr = t.getText().toString();
            doc.addAuthor("geedhai");
            doc.add(new Paragraph(textr));
            doc.close();
            Toast.makeText(this,fname+".pdf\n saved to \n"+fpath,Toast.LENGTH_LONG).show();
            File file = null;
            file = new File(fpath);
            Toast.makeText(getApplicationContext(), file.toString() , Toast.LENGTH_LONG).show();
            if(file.exists()) {
                Intent target = new Intent(Intent.ACTION_VIEW);
                target.setDataAndType(Uri.fromFile(file), "application/pdf");
                target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                Intent intent = Intent.createChooser(target, "Open File");
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    // Instruct the user to install a PDF reader here, or something
                }
            }
            else
            {
                Toast.makeText(getApplicationContext(), "File path is incorrect." , Toast.LENGTH_LONG).show();}
        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case STORAGE:{
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    savepdf();
                }else
                {
                    Toast.makeText(this,"permission denied",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}