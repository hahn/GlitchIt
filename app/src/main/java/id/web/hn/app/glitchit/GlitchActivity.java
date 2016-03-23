package id.web.hn.app.glitchit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by hahn on 21/03/16.
 */
public class GlitchActivity extends AppCompatActivity {
    private Context context;
    private ImageView img;
    private Button btnOk;
    private SeekBar seekBarToggle, seekBarRandom, seekBarGlitch;
    private Bitmap bmp, bmpo, bmp2, btmp;
    FileOutputStream fout;

    //buat random glitch etc
    int TOGGLE = 15;
    int INDEX = 12;
    int RAND_GLITCH = 100;
    int GLITCH = 30;

    private static String appDirName = "GlitchIt";

    private static final int PIC_IMAGE = 0;

    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_glitch);

        //coba
        Uri imguri = this.getIntent().getParcelableExtra("img_key");
        //sampai sini

        img = (ImageView) findViewById(R.id.img);
        img.setImageURI(imguri);

        btnOk = (Button) findViewById(R.id.btn_ok);

        seekBarToggle = (SeekBar) findViewById(R.id.seekBar_toggle);
        seekBarRandom = (SeekBar) findViewById(R.id.seekBar_random);
        seekBarGlitch = (SeekBar) findViewById(R.id.seekBar_glitch);

        setToggleListener();
        setRandomListener();
        setGlitchListener();
        getImage();
    }

    public void setPicGlitch(){
        PictureGlitch pc = new PictureGlitch();
        //pakai yang salinannya
        bmp = bmp2.copy(bmp2.getConfig(), true);
        bmpo = pc.glitchPicture(bmp, RAND_GLITCH, GLITCH, TOGGLE);
        img.setImageBitmap(bmpo);
//        getImage();
    }

    public void getImage(){
        //ambil gambar dari imageView
        BitmapDrawable abmp = (BitmapDrawable) img.getDrawable();
        bmp2 = abmp.getBitmap();
    }

    public void onGlitch(View view){
        btnOk.setEnabled(false);
//        getRandomGlitch();
        setPicGlitch();
        btnOk.setEnabled(true);

    }

    public void onLoadPicture(View view){
        //TODO load picture here
//        picImage();


    }

    public void onSavePicture(View view){
        //TODO save picture here
                    addToGallery();

    }

    //fungsi seekbar
    public void setRandomListener(){
        final int step = 10;
        final int max = 150;
        final int min = 1;

        seekBarRandom.setMax((max - min) / step);
        seekBarRandom.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                RAND_GLITCH = min + (progress * step);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                setPicGlitch();
            }
        });
    }

    public void setToggleListener(){

        final int step = 2;
        final int max = 100;
        final int min = 10;
        seekBarToggle.setMax((max-min)/ step);
        seekBarToggle.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                TOGGLE = min + (progress * step);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                setPicGlitch();
            }
        });
    }

    public void setGlitchListener(){

        final int step = 3;
        final int max = 60;
        final int min = -30;
        seekBarGlitch.setMax((max - min) / step);

        seekBarGlitch.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                GLITCH = min + (progress * step);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                setPicGlitch();
            }
        });
    }
    //--fungsi seekbar

    public void addToGallery(){
        Bitmap image;
        image = bmpo.copy(bmpo.getConfig(), true);
        String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        File myDir = new File(root +  "/" + appDirName);
        myDir.mkdirs();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String fname = "IMG_glitched_"+sdf.format(date)+".jpg";
        File file = new File(myDir, fname);
        if(file.exists()){
            file.delete();
        }
        try{
            Log.d("addtogalery()", ""+ root);
            fout = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.JPEG, 50, fout);
            fout.flush();
            fout.close();
            Toast.makeText(this, "disave di: " + root + "/" + fname, Toast.LENGTH_LONG).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }



}
