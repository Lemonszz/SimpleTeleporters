package lemons.party.teleporters.content.items;

import java.util.ArrayList;
import java.util.List;

import lemons.party.teleporters.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TeleportersItems
{
	public static List<TeleItem> itemList = new ArrayList<TeleItem>();
	public static TeleItem teleCrystal = new ItemTeleportCrystal();
	
	public static void RegisterItems()
	{
		for(TeleItem item : itemList)
		{
			item.setRegistryName(item.getName());
			GameRegistry.register(item);
		}
	}
	
	public static void RegisterItemModels()
	{
		for(TeleItem item: itemList)
		{
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(Constants.MODID + ":" + item.getName(), "inventory"));
		}
	}
}
