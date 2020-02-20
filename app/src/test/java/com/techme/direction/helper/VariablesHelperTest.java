package com.techme.direction.helper;

import org.junit.Test;

import static org.junit.Assert.*;

public class VariablesHelperTest {


    @Test
    public void stringToUri() {
        String name = "atlantic super store";
        String[] list = name.split(" ");
        String uriName = "";
        for(int i = 0; i < list.length; i++){
            if(i < list.length -1){
                uriName += list[i] + "+";
            }else {
                uriName += list[i];
            }
        }
        assertEquals("atlantic+super+store",uriName);
    }


}