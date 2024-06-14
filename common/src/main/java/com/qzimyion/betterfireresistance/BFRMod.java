package com.qzimyion.betterfireresistance;

import com.qzimyion.betterfireresistance.platform.Services;
import eu.midnightdust.lib.config.MidnightConfig;


public class BFRMod {

    public static final String MOD_ID = "bfr";
    public static void init() {

        MidnightConfig.init(MOD_ID, BFRConfig.class);

        Services.PLATFORM.isModLoaded(MOD_ID);
    }
}