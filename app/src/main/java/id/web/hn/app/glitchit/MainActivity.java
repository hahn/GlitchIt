package id.web.hn.app.glitchit;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;


import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

//cuma buat coba-coba. MainActivity sama activity_main ga dipakai
public class MainActivity extends AppCompatActivity {

    FileOutputStream fout;

    int TOGGLE = 15;
    int INDEX = 12;
    int RAND_GLITCH = 100;
    int GLITCH = 30;

    private ImageView img;
    private Button btnOk;
    private Bitmap bmp, bmpo, bmp2, btmp;
    private SeekBar seekBarToggle, seekBarRandom, seekBarGlitch;

    private static String appDirName = "GlitchIt";

    private static final int PIC_IMAGE = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        setContentView(R.layout.layout_front);

        img = (ImageView) findViewById(R.id.img);
        btnOk = (Button) findViewById(R.id.btn_ok);

        seekBarToggle = (SeekBar) findViewById(R.id.seekBar_toggle);
        seekBarRandom = (SeekBar) findViewById(R.id.seekBar_random);
        seekBarGlitch = (SeekBar) findViewById(R.id.seekBar_glitch);

        setToggleListener();
        setRandomListener();
        setGlitchListener();
        getImage();

    }

    public void getImage(){
        //ambil gambar dari imageView
        BitmapDrawable abmp = (BitmapDrawable) img.getDrawable();
        bmp2 = abmp.getBitmap();
    }

    public void getRandomGlitch(){
        //pake gambar salinan, jadi yang aslinya aman
        bmp = bmp2.copy(bmp2.getConfig(), true);
        ByteArrayOutputStream blob = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, blob);
        byte[] byteArray = blob.toByteArray();
        byte[] byteBaru = new byte[byteArray.length];

        byte ganti;
        for(int i=0;i<byteArray.length;i++){
            if(byteArray[i] == TOGGLE && new Random().nextInt(TOGGLE) == 1) {
                ganti = (byte) (new Random().nextInt(RAND_GLITCH) + (GLITCH));
                byteBaru[i] = ganti;
            }
            else{
                byteBaru[i] = byteArray[i];
            }
        }
        int sama = 0, beda = 0;
        for(int i = 0; i < byteArray.length; i++){
            if(byteArray[i] == byteBaru[i]){
                sama++;
            }else{
//                Log.d("PUSING", "byteArray:"+ byteArray[i]+ " | byteBaru: " + byteBaru[i]);
                beda++;
            }
        }
        Log.d("Sama:", "" + sama + " | beda: " + beda);

        bmpo = BitmapFactory.decodeByteArray(byteBaru, 0, byteBaru.length);
        img.setImageBitmap(bmpo);

    }



    public void addToGallery() throws FileNotFoundException {
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
            Toast.makeText(this, "disave di: "+ root + "/" + fname, Toast.LENGTH_LONG).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //fungsi seekbar
    public void setRandomListener(){
        final int step = 10;
        final int max = 150;
        final int min = 1;

        seekBarRandom.setMax((max-min)/step);
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
                getRandomGlitch();
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
                getRandomGlitch();
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
                getRandomGlitch();
            }
        });
    }
    //--fungsi seekbar

    public void onGlitch(View view){
        btnOk.setEnabled(false);
        getRandomGlitch();
        btnOk.setEnabled(true);

    }

    public void onLoadPicture(View view){
        //TODO load picture here
//        picImage();
    //ini mah tes layout main sajah
        Intent intent = new Intent(this, FrontActivity.class);
        this.startActivity(intent);

    }

    public void onSavePicture(View view){
        //TODO save picture here
        try {
            addToGallery();
        } catch (FileNotFoundException e) {
            Log.e("savePicture()", "error filenotfoundexception");
            e.printStackTrace();
        }

    }

    //load image from gallery
    public void picImage(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PIC_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PIC_IMAGE && resultCode == Activity.RESULT_OK){
            if(data == null){
                //error
                return;
            }
            try {
                InputStream inputStream = getContentResolver().openInputStream(data.getData());

                BufferedInputStream bfs = new BufferedInputStream(inputStream);
                btmp = BitmapFactory.decodeStream(bfs);
                img.setImageBitmap(btmp);

                getImage();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }



//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

}
