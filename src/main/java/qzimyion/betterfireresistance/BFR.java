package qzimyion.betterfireresistance;

import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qzimyion.betterfireresistance.config.BFRConfig;


public class BFR implements ModInitializer {

	public static final String MOD_ID = "bfr";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		MidnightConfig.init(MOD_ID, BFRConfig.class);
	}
}