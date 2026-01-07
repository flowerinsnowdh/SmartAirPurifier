package cn.flowerinsnow.smartairpurifier;

import cn.flowerinsnow.smartairpurifier.config.Config;
import net.fabricmc.api.ClientModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmartAirPurifier implements ClientModInitializer {
	public static final String MOD_ID = "smart-air-purifier";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitializeClient() {
		Config.init();
	}
}
