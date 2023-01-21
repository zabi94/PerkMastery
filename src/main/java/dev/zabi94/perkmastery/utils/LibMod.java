package dev.zabi94.perkmastery.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.util.Identifier;

public class LibMod {

	public static final String MOD_NAME = "Perk Mastery";
	public static final String MOD_ID = "perkmastery";
	
	private static final Logger logger = LogManager.getLogger(LibMod.MOD_NAME);

	public static Identifier id(String name) {
		return new Identifier(MOD_ID, name);
	}
	
	public static void ILog(Object obj) {
		logger.info(obj);
	}
	
	public static void ELog(Object obj) {
		logger.error(obj);
	}
	
	public static void DLog(Object obj) {
		logger.debug(obj);
	}
	
	public static void WLog(Object obj) {
		logger.warn(obj);
	}
	
	public static void TLog(Object obj) {
		logger.trace(obj);
	}
	
}
