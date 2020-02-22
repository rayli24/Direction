package com.techme.direction.countries;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.techme.direction.Dao;
import com.techme.direction.R;
import com.techme.direction.Store;
import com.techme.direction.helper.ContextHelper;

import java.io.IOException;

import static com.techme.direction.helper.ConvertImage.convertImageToByte;
import static com.techme.direction.helper.VariablesHelper.DINING;
import static com.techme.direction.helper.VariablesHelper.GROCERY;
import static com.techme.direction.helper.VariablesHelper.FALSE;
import static com.techme.direction.helper.VariablesHelper.CANADA;

public class CanadaStores {
    private static final String COUNTRY = CANADA;

    private CanadaStores(){}

    public static void stores(Dao dao) throws IOException {
        grocery(dao);
        dining(dao);
    }

    private static void grocery(Dao dao) throws IOException {
        Bitmap bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.sobey);
        dao.insertStore(new Store("Sobey's", 12, convertImageToByte(bitmap),
                GROCERY, COUNTRY, FALSE));

        bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.walmart);
        dao.insertStore(new Store("Walmart", 9, convertImageToByte(bitmap),
                GROCERY, COUNTRY, FALSE));
    }

    private static void dining(Dao dao) throws IOException {
        Bitmap bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.pizzahut);
        dao.insertStore(new Store("Pizza Hut", 17, convertImageToByte(bitmap),
                DINING, COUNTRY, FALSE));

        bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.mcdonald);
        dao.insertStore(new Store("McDonald's", 23, convertImageToByte(bitmap),
                DINING, COUNTRY, FALSE));

        bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.kfc);
        dao.insertStore(new Store("K.F.C", 11, convertImageToByte(bitmap),
                DINING, COUNTRY, FALSE));
    }


}
