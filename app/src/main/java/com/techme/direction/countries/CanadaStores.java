package com.techme.direction.countries;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.techme.direction.Dao;
import com.techme.direction.R;
import com.techme.direction.Store;
import com.techme.direction.helper.ContextHelper;

import java.io.IOException;

import static com.techme.direction.helper.ConvertImage.convertImageToByte;
import static com.techme.direction.helper.StoreNames.ARBYS;
import static com.techme.direction.helper.StoreNames.ATLANTIC_SUPERSTORE;
import static com.techme.direction.helper.StoreNames.AW;
import static com.techme.direction.helper.StoreNames.BULK_BARN;
import static com.techme.direction.helper.StoreNames.BURGER_KING;
import static com.techme.direction.helper.StoreNames.CHIC_FILL_A;
import static com.techme.direction.helper.StoreNames.COSTCO;
import static com.techme.direction.helper.StoreNames.DOMINO_PIZZA;
import static com.techme.direction.helper.StoreNames.FIVE_GUYS;
import static com.techme.direction.helper.StoreNames.GIANT_TIGER;
import static com.techme.direction.helper.StoreNames.KFC;
import static com.techme.direction.helper.StoreNames.KRISPY_KREME;
import static com.techme.direction.helper.StoreNames.LOBLAWS;
import static com.techme.direction.helper.StoreNames.MARY_BROWN;
import static com.techme.direction.helper.StoreNames.MCDONALD;
import static com.techme.direction.helper.StoreNames.MM_FOOD_MARKET;
import static com.techme.direction.helper.StoreNames.NO_FRILLS;
import static com.techme.direction.helper.StoreNames.PIZZA_HUT;
import static com.techme.direction.helper.StoreNames.SOBEY;
import static com.techme.direction.helper.StoreNames.STARBUCKS;
import static com.techme.direction.helper.StoreNames.SUBWAY;
import static com.techme.direction.helper.StoreNames.TACO_BELL;
import static com.techme.direction.helper.StoreNames.TIM_HORTON;
import static com.techme.direction.helper.StoreNames.WALMART;
import static com.techme.direction.helper.StoreNames.WENDY;
import static com.techme.direction.helper.StoreNames.WHOLESALE_CLUB;
import static com.techme.direction.helper.VariablesHelper.DINING;
import static com.techme.direction.helper.VariablesHelper.GROCERY;
import static com.techme.direction.helper.VariablesHelper.FALSE;
import static com.techme.direction.helper.VariablesHelper.CANADA;

public class CanadaStores {

    private CanadaStores() {
    }

    public static void stores(Dao dao) throws IOException {
        grocery(dao);
        dining(dao);
    }

    private static void grocery(Dao dao) throws IOException {
        Bitmap bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.sobey);
        dao.insertStore(new Store(SOBEY, 12, convertImageToByte(bitmap),
                GROCERY, CANADA, FALSE));

        bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.walmart);
        dao.insertStore(new Store(WALMART, 9, convertImageToByte(bitmap),
                GROCERY, CANADA, FALSE));

        bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.giant_tiger);
        dao.insertStore(new Store(GIANT_TIGER, 9, convertImageToByte(bitmap), GROCERY, CANADA, FALSE));

        bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.atlantic_superstore);
        dao.insertStore(new Store(ATLANTIC_SUPERSTORE, 22, convertImageToByte(bitmap), GROCERY, CANADA, FALSE));

        bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.costco_wholesale);
        dao.insertStore(new Store(COSTCO, 45, convertImageToByte(bitmap), GROCERY, CANADA, FALSE));

        bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.bulk_barn);
        dao.insertStore(new Store(BULK_BARN, 16, convertImageToByte(bitmap), GROCERY, CANADA, FALSE));

        bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.no_frills);
        dao.insertStore(new Store(NO_FRILLS, 31, convertImageToByte(bitmap), GROCERY, CANADA, FALSE));

        bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.wholesale_club);
        dao.insertStore(new Store(WHOLESALE_CLUB, 15, convertImageToByte(bitmap), GROCERY, CANADA, FALSE));

        bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.m_m_food_market);
        dao.insertStore(new Store(MM_FOOD_MARKET, 41, convertImageToByte(bitmap), GROCERY, CANADA, FALSE));

        bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.loblaws);
        dao.insertStore(new Store(LOBLAWS, 10, convertImageToByte(bitmap), GROCERY, CANADA, FALSE));
    }

    private static void dining(Dao dao) throws IOException {
        Bitmap bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.pizza_hut);
        dao.insertStore(new Store(PIZZA_HUT, 17, convertImageToByte(bitmap),
                DINING, CANADA, FALSE));

        bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.mcdonald);
        dao.insertStore(new Store(MCDONALD, 23, convertImageToByte(bitmap),
                DINING, CANADA, FALSE));

        bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.kfc);
        dao.insertStore(new Store(KFC, 11, convertImageToByte(bitmap),
                DINING, CANADA, FALSE));

        bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.burger_king);
        dao.insertStore(new Store(BURGER_KING, 20, convertImageToByte(bitmap), DINING, CANADA, FALSE));

        bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.domino_pizza);
        dao.insertStore(new Store(DOMINO_PIZZA, 15, convertImageToByte(bitmap), DINING, CANADA, FALSE));

        bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.wendy);
        dao.insertStore(new Store(WENDY, 33, convertImageToByte(bitmap), DINING, CANADA, FALSE));

        bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.subway);
        dao.insertStore(new Store(SUBWAY, 18, convertImageToByte(bitmap), DINING, CANADA, FALSE));

        bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.tim_horton);
        dao.insertStore(new Store(TIM_HORTON, 6, convertImageToByte(bitmap), DINING, CANADA, FALSE));

        bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.a_w);
        dao.insertStore(new Store(AW, 25, convertImageToByte(bitmap), DINING, CANADA, FALSE));

        bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.mary_brown);
        dao.insertStore(new Store(MARY_BROWN, 46, convertImageToByte(bitmap), DINING, CANADA, FALSE));

        bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.krispy_kreme);
        dao.insertStore(new Store(KRISPY_KREME, 38, convertImageToByte(bitmap), DINING, CANADA, FALSE));

        bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.arbys);
        dao.insertStore(new Store(ARBYS, 17, convertImageToByte(bitmap), DINING, CANADA, FALSE));

        bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.chic_fil_a);
        dao.insertStore(new Store(CHIC_FILL_A, 40, convertImageToByte(bitmap), DINING, CANADA, FALSE));

        bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.tacobell);
        dao.insertStore(new Store(TACO_BELL, 11, convertImageToByte(bitmap), DINING, CANADA, FALSE));

        bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.five_guys);
        dao.insertStore(new Store(FIVE_GUYS, 8, convertImageToByte(bitmap), DINING, CANADA, FALSE));

        bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.starbucks);
        dao.insertStore(new Store(STARBUCKS, 30, convertImageToByte(bitmap), DINING, CANADA, FALSE));
    }


}
