package com.techme.direction.countries;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.techme.direction.Dao;
import com.techme.direction.R;
import com.techme.direction.Store;
import com.techme.direction.helper.ContextHelper;
import com.techme.direction.helper.StoreNames;


import java.io.IOException;

import static com.techme.direction.helper.ConvertImage.convertImageToByte;
import static com.techme.direction.helper.StoreNames.ARBYS;
import static com.techme.direction.helper.StoreNames.BURGER_KING;
import static com.techme.direction.helper.StoreNames.CHIC_FILL_A;
import static com.techme.direction.helper.StoreNames.CHIPOTLE_MEXICAN_GRILL;
import static com.techme.direction.helper.StoreNames.COSTCO;
import static com.techme.direction.helper.StoreNames.DOMINO_PIZZA;
import static com.techme.direction.helper.StoreNames.DUNK_DONUTS;
import static com.techme.direction.helper.StoreNames.FIVE_GUYS;
import static com.techme.direction.helper.StoreNames.FRED_MEYER;
import static com.techme.direction.helper.StoreNames.JACK_IN_THE_BOX;
import static com.techme.direction.helper.StoreNames.KFC;
import static com.techme.direction.helper.StoreNames.KROGER;
import static com.techme.direction.helper.StoreNames.LITTLE_CAESARS;
import static com.techme.direction.helper.StoreNames.MCDONALD;
import static com.techme.direction.helper.StoreNames.PANDA_EXPRESS;
import static com.techme.direction.helper.StoreNames.PAPA_JOHN;
import static com.techme.direction.helper.StoreNames.PIZZA_HUT;
import static com.techme.direction.helper.StoreNames.POPEYES_CHICKEN;
import static com.techme.direction.helper.StoreNames.QFC;
import static com.techme.direction.helper.StoreNames.SAFEWAY;
import static com.techme.direction.helper.StoreNames.STARBUCKS;
import static com.techme.direction.helper.StoreNames.SUBWAY;
import static com.techme.direction.helper.StoreNames.TACO_BELL;
import static com.techme.direction.helper.StoreNames.TRADER_JOE;
import static com.techme.direction.helper.StoreNames.WALMART;
import static com.techme.direction.helper.StoreNames.WENDY;
import static com.techme.direction.helper.StoreNames.WHOLE_FOODS;
import static com.techme.direction.helper.StoreNames.WINCO_FOODS;
import static com.techme.direction.helper.VariablesHelper.DINING;
import static com.techme.direction.helper.VariablesHelper.GROCERY;
import static com.techme.direction.helper.VariablesHelper.FALSE;
import static com.techme.direction.helper.VariablesHelper.USA;

public class UnitedStatesStores {
    private UnitedStatesStores() {
    }

    public static void Stores(Dao dao) throws IOException {
        grocery(dao);
        dining(dao);
    }

    private static void grocery(Dao dao) throws IOException {
        Bitmap bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.walmart);
        dao.insertStore(new Store(WALMART, 19, convertImageToByte(bitmap),
                GROCERY, USA, FALSE));

        bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.fred_meyer);
        dao.insertStore(new Store(FRED_MEYER, 5, convertImageToByte(bitmap), GROCERY, USA, FALSE));

        bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.kroger);
        dao.insertStore(new Store(KROGER, 10, convertImageToByte(bitmap), GROCERY, USA, FALSE));

        bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.qfc);
        dao.insertStore(new Store(QFC, 30, convertImageToByte(bitmap), GROCERY, USA, FALSE));

        bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.trader_joes);
        dao.insertStore(new Store(TRADER_JOE, 40, convertImageToByte(bitmap), GROCERY, USA, FALSE));

        bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.winco_foods);
        dao.insertStore(new Store(WINCO_FOODS, 32, convertImageToByte(bitmap), GROCERY, USA, FALSE));

        bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.whole_foods);
        dao.insertStore(new Store(WHOLE_FOODS, 18, convertImageToByte(bitmap), GROCERY, USA, FALSE));

        bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.costco_wholesale);
        dao.insertStore(new Store(COSTCO, 45, convertImageToByte(bitmap), GROCERY, USA, FALSE));

        bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.safeway);
        dao.insertStore(new Store(SAFEWAY, 6, convertImageToByte(bitmap), GROCERY, USA, FALSE));

    }

    private static void dining(Dao dao) throws IOException {
        Bitmap bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.pizza_hut);
        dao.insertStore(new Store(PIZZA_HUT, 8, convertImageToByte(bitmap),
                DINING, USA, FALSE));

        bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.mcdonald);
        dao.insertStore(new Store(MCDONALD, 13, convertImageToByte(bitmap),
                DINING, USA, FALSE));

        bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.kfc);
        dao.insertStore(new Store(KFC, 12, convertImageToByte(bitmap),
                DINING, USA, FALSE));

        bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.chic_fil_a);
        dao.insertStore(new Store(CHIC_FILL_A, 18, convertImageToByte(bitmap), DINING, USA, FALSE));

        bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.chipotle_mexican_grill);
        dao.insertStore(new Store(CHIPOTLE_MEXICAN_GRILL, 25, convertImageToByte(bitmap), DINING, USA, FALSE));

        bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.jack_in_the_box);
        dao.insertStore(new Store(JACK_IN_THE_BOX, 30, convertImageToByte(bitmap), DINING, USA, FALSE));

        bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.panda_express);
        dao.insertStore(new Store(PANDA_EXPRESS, 46, convertImageToByte(bitmap), DINING, USA, FALSE));

        bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.popeyes_chicken);
        dao.insertStore(new Store(POPEYES_CHICKEN, 5, convertImageToByte(bitmap), DINING, USA, FALSE));

        bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.domino_pizza);
        dao.insertStore(new Store(DOMINO_PIZZA, 40, convertImageToByte(bitmap), DINING, USA, FALSE));

        bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.wendy);
        dao.insertStore(new Store(WENDY, 10, convertImageToByte(bitmap), DINING, USA, FALSE));

        bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.subway);
        dao.insertStore(new Store(SUBWAY, 28, convertImageToByte(bitmap), DINING, USA, FALSE));

        bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.tacobell);
        dao.insertStore(new Store(TACO_BELL, 33, convertImageToByte(bitmap), DINING, USA, FALSE));

        bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.five_guys);
        dao.insertStore(new Store(FIVE_GUYS, 4, convertImageToByte(bitmap), DINING, USA, FALSE));

        bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.little_caesars);
        dao.insertStore(new Store(LITTLE_CAESARS, 7, convertImageToByte(bitmap), DINING, USA, FALSE));

        bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.dunkin_donuts);
        dao.insertStore(new Store(DUNK_DONUTS, 50, convertImageToByte(bitmap), DINING, USA, FALSE));

        bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.arbys);
        dao.insertStore(new Store(ARBYS, 38, convertImageToByte(bitmap), DINING, USA, FALSE));

        bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.papa_john);
        dao.insertStore(new Store(PAPA_JOHN, 17, convertImageToByte(bitmap), DINING, USA, FALSE));

        bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.burger_king);
        dao.insertStore(new Store(BURGER_KING, 20, convertImageToByte(bitmap), DINING, USA, FALSE));

        bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.starbucks);
        dao.insertStore(new Store(STARBUCKS, 12, convertImageToByte(bitmap), DINING, USA, FALSE));
    }
}
