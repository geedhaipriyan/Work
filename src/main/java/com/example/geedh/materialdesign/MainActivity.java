
package com.example.geedh.materialdesign;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.net.URI;

public class MainActivity extends AppCompatActivity {
    ImageView l;
    Toolbar toolbar;
    Button b;
    StringBuilder sc;
    private final int CAPTURE_PHOTO = 20,SELECT_PHOTO=100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.my_toolbar);
        l = findViewById(R.id.imageView);
        b = findViewById(R.id.button4);
        setSupportActionBar(toolbar);
        l.setOnClickListener(new View.OnClickListener(){

            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
             ChooseImage();
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                try{
                Intent i = new Intent(MainActivity.this,mainActivity2.class);
//                BitmapDrawable bitma = (BitmapDrawable)l.getDrawable();
//                Bitmap bitmap = bitma.getBitmap();
//                Bundle ext = new Bundle();
//                ext.putParcelable("img",bitmap);
//                i.putExtras(ext);
                gettextfromimage(v,l);
                i.putExtra("img",sc.toString());
                startActivity(i);} catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(),"PLEASE SELECT PHOTO AND PROCEED",Toast.LENGTH_LONG).show();
                }
                }
        });
        //e = findViewById(R.id.editText);
        //b = findViewById(R.id.button);
//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String s = e.getText().toString();
//                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www."+s+".com"));
//                startActivity(i);
//            }
//        });
    }
    public void gettextfromimage(View v,ImageView li)
    {
        //Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(),path());
        BitmapDrawable bitma = (BitmapDrawable)li.getDrawable();
        Bitmap bitmap = bitma.getBitmap();
        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        if(!textRecognizer.isOperational())
        {
            Toast.makeText(getApplicationContext(),"could not get the text", Toast.LENGTH_SHORT).show();

        }else
        {
            Frame frame = new Frame.Builder().setBitmap(bitmap).build();
            SparseArray<TextBlock> items = textRecognizer.detect(frame);
            sc = new StringBuilder();
            for(int i=0;i<items.size();i++)
            {
                TextBlock myitems = items.valueAt(i);
                sc.append(myitems.getValue());
                sc.append("\n");
            }

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menua,menu);
        return true;
    }

     @Override
    public boolean onOptionsItemSelected(MenuItem menu) {
        //getMenuInflater().inflate(R.menu.menua,(Menu)menu);
        switch (menu.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_favorite:
                Intent photoCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(photoCaptureIntent, CAPTURE_PHOTO);
                return true;
            case R.id.gallery:
                ChooseImage();
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(menu);

        }
    }
    public void ChooseImage(){
        openGallery();
    }

    private void openGallery(){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CAPTURE_PHOTO:
                super.onActivityResult(requestCode, resultCode, data);
                if (requestCode == CAPTURE_PHOTO && resultCode == RESULT_OK) {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    l.setImageBitmap(bitmap);
                }
            case SELECT_PHOTO:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    //        photo = selectedImage;
                    if(selectedImage !=null){
                        l.setImageURI(selectedImage);
                    }
                }
        }
    }
}
