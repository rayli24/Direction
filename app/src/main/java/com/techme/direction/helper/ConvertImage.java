package com.techme.direction.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ConvertImage {

    public static byte[] convertImageToByte(Bitmap bitmap) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,0, stream);
        stream.close();
        return stream.toByteArray();
    }

    public static Bitmap convertByteToImage (byte[] array){
        return BitmapFactory.decodeByteArray(array,0, array.length);
    }
}
