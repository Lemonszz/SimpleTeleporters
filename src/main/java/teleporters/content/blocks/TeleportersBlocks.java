package teleporters.content.blocks;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import teleporters.Constants;

public class TeleportersBlocks 
{
	public static List<TeleBlock> blockList = new ArrayList<TeleBlock>();
	public static TeleBlock blockTeleporter = new BlockTeleporter("teleporter", Material.ROCK);
	
	public static void RegisterItems()
	{
		for(TeleBlock item : blockList)
		{
			item.setRegistryName(item.getName());
			ItemBlock block = new ItemBlock(item);
			block.setRegistryName(item.getRegistryName());
			
			GameRegistry.register(item);
			GameRegistry.register(block);
		}
	}
	
	public static void RegisterItemModels()
	{
		for(TeleBlock item: blockList)
		{
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(item), 0, new ModelResourceLocation(Constants.MODID + ":" + item.getName(), "inventory"));
		}
	}
}
