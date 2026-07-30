package betterwithmagnets;

import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.core.util.helper.DyeColor;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.HalpLibe;
import turniplabs.halplibe.helper.RecipeBuilder;
import turniplabs.halplibe.util.RecipeEntrypoint;

public class BetterWithMagnets implements ModInitializer, RecipeEntrypoint {

	public static final String MOD_ID = HalpLibe.registerMod("betterwithmagnets", true);
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static ItemStack magnetStack() {
		ItemStack magnet = new ItemStack(Items.AMMO_FIREBALL, 1, Magnets.MAGNET_META);
		magnet.setCustomName(TextFormatting.RESET + "Magnet");
		return magnet;
	}

	@Override
	public void onInitialize() {
		LOGGER.info("Better with Magnets initializing!");
		LOGGER.info("Better with Magnets initialized!");
	}

	@Override
	public void initNamespaces() {
		RecipeBuilder.initNameSpace(MOD_ID);
	}

	@Override
	public void onRecipesReady() {
		RecipeBuilder.Shaped(MOD_ID)
			.setShape(
				"R L",
				"S S",
				"SSS")
			.addInput('R', Items.DUST_REDSTONE)
			.addInput('L', Items.DYE, DyeColor.BLUE.itemMeta)
			.addInput('S', Items.INGOT_STEEL)
			.create("magnet", magnetStack());
	}
}
