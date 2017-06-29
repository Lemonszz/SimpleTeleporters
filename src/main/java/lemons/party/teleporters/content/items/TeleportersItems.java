package lemons.party.teleporters.content.items;

import java.util.ArrayList;
import java.util.List;

import lemons.party.teleporters.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber
@GameRegistry.ObjectHolder(Constants.MODID)
public class TeleportersItems
{
	public static List<TeleItem> itemList = new ArrayList<TeleItem>();
	@GameRegistry.ObjectHolder("endercrystal")
	public static final TeleItem teleCrystal = null;

	@SubscribeEvent
	public static void onRegisterItems(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().register(new ItemTeleportCrystal().setRegistryName("endercrystal"));
	}

	@SubscribeEvent
	public static void onRegisterModels(ModelRegistryEvent event)
	{
		ModelLoader.setCustomModelResourceLocation(teleCrystal, 0, new ModelResourceLocation(Constants.MODID + ":" + teleCrystal.getName(), "inventory"));
	}

}
