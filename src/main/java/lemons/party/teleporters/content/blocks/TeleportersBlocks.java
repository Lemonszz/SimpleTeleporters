package lemons.party.teleporters.content.blocks;

import java.util.ArrayList;
import java.util.List;

import lemons.party.teleporters.Constants;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber
@GameRegistry.ObjectHolder(Constants.MODID)
public class TeleportersBlocks 
{
	@GameRegistry.ObjectHolder("teleporter")
	public static final TeleBlock  blockTeleporter = null;

	public static List<TeleBlock> blockList = new ArrayList<TeleBlock>();

	 @SubscribeEvent
	 public static void onRegisterBlock(RegistryEvent.Register<Block> event)
	 {
		event.getRegistry().register(new BlockTeleporter("teleporter", Material.ROCK).setRegistryName("teleporter"));
	 }

	 @SubscribeEvent
	 public static void onRegisterItem(RegistryEvent.Register<Item> event)
	 {
	 	event.getRegistry().register(new ItemBlock(blockTeleporter).setRegistryName(blockTeleporter.getRegistryName()));
	 }

	 @SubscribeEvent
	 public static void onRegisterModels(ModelRegistryEvent event)
	 {
		 ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(blockTeleporter), 0, new ModelResourceLocation(Constants.MODID + ":" + blockTeleporter.getName(), "inventory"));
	 }
}
