package id.web.hn.app.glitchit;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by hahn on 20/03/16.
 */
public class FrontActivity extends AppCompatActivity {

    private ImageButton btnLoad, btnCamera;
    private static int PIC_IMAGE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.layout_front);
        btnLoad = (ImageButton) findViewById(R.id.img_btn_load);
        btnCamera = (ImageButton) findViewById(R.id.img_btn_camera);


    }

    public void onClickLoadPicture(View view){
        Toast.makeText(this, "loading picture", Toast.LENGTH_SHORT).show();
        picImage();
    }

    public void onClickCamera(View view){
        Toast.makeText(this, "loading camera...", Toast.LENGTH_SHORT).show();
        //belum da yeeyy
    }

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
                //cara baru, lebih hemat baris
                Uri imgUri = data.getData();
                Log.d("onActivityResult", "loc:" + imgUri.toString());
                //kirim ke activity lain
                Intent intent = new Intent(this, GlitchActivity.class);
                intent.putExtra("img_key", imgUri);
                startActivity(intent);
                //sampai sini cara barunya

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
