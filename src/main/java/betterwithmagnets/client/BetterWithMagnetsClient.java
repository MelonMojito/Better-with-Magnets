package betterwithmagnets.client;

import betterwithmagnets.BetterWithMagnets;
import betterwithmagnets.Magnets;
import net.minecraft.client.gui.options.components.BooleanOptionComponent;
import net.minecraft.client.gui.options.components.OptionsCategory;
import net.minecraft.client.gui.options.data.OptionsPage;
import net.minecraft.client.gui.options.data.OptionsPages;
import net.minecraft.client.option.GameSettings;
import net.minecraft.client.option.OptionBoolean;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.item.Items;
import turniplabs.halplibe.util.ClientStartEntrypoint;
import turniplabs.halplibe.util.OptionsInitEntrypoint;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Client-only half of the mod: the in-game toggle, the magnet textures, and the
 * held/dropped item model. Registered under client-only entrypoints so a
 * dedicated server never loads this class.
 */
public class BetterWithMagnetsClient implements OptionsInitEntrypoint, ClientStartEntrypoint {

	public static OptionBoolean magnetsEnabled;

	@Override
	public void initOptions() {
		magnetsEnabled = GameSettings.register(new OptionBoolean("betterwithmagnets.magnets.enabled", true));
		//singleplayer honors the toggle; a dedicated server always has magnets on
		Magnets.enabled = () -> magnetsEnabled.value;
	}

	@Override
	public void beforeClientStart() {
		try {
			TextureRegistry.initializeAllFiles(BetterWithMagnets.MOD_ID, TextureRegistry.worldAtlas, true);
		} catch (URISyntaxException | IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void afterClientStart() {
		//textures are stitched by now, so the model can resolve its icons.
		//registered straight on the game's dispatcher: halplibe's ModelHelper is deprecated and
		//its dispatcher fields are never assigned, so setItemModel NPEs on 6.1.x
		ItemModelDispatcher.getInstance().addDispatch(new ItemModelMagnet(Items.AMMO_FIREBALL, "minecraft"));

		OptionsPages.register(
			new OptionsPage("options.betterwithmagnets.title", BetterWithMagnets.magnetStack())
				.withComponent(new OptionsCategory("options.betterwithmagnets.category.magnets")
					.withComponent(new BooleanOptionComponent(magnetsEnabled))
				)
		);
	}
}
