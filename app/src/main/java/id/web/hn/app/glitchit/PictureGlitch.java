package id.web.hn.app.glitchit;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.Random;

/**
 * Created by hahn on 18/03/16.
 */
public class PictureGlitch {
    private Bitmap bmp;
    public Bitmap glitchPicture(Bitmap src, int random, int glitch, int toggle){
//        bmp = inImage.copy(inImage.getConfig(),true);
        //dari mainActivity
        ByteArrayOutputStream blob = new ByteArrayOutputStream();
        src.compress(Bitmap.CompressFormat.JPEG, 50, blob);
        byte[] byteArray = blob.toByteArray();
        byte[] byteBaru = new byte[byteArray.length];

        byte ganti;
        for(int i=0;i<byteArray.length;i++){
            if(byteArray[i] == toggle && new Random().nextInt(toggle) == 1) {
                ganti = (byte) (new Random().nextInt(random) + (glitch));
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
        //sampai sini mainActivity
        bmp = BitmapFactory.decodeByteArray(byteBaru, 0, byteBaru.length);
        return bmp;
    }

    public Bitmap rotateImage(Bitmap src, float deg){
        Matrix matrix = new Matrix();
        matrix.postRotate(deg);
        bmp = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
        return bmp;
    }



}
