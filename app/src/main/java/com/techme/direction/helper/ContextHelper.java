package com.techme.direction.helper;

import android.content.res.Resources;

/**
 * class: created to hold context data from activities
 */
public class ContextHelper {

    private ContextHelper() {} // so that no one can create an instance of it

    public static class Helper
    {
        private static Resources resource;

        public Helper(Resources resources)
        {
             resource = resources;
        }

        public static Resources getResource()
        {
            return resource;
        }

        public static void setResource(Resources resources)
        {
            if(resource == null)
                resource = resources;
        }
    }

}
