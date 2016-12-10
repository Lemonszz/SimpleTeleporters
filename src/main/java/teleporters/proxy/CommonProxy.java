package teleporters.proxy;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import teleporters.content.blocks.TeleportersBlocks;
import teleporters.content.event.TeleporterEvents;
import teleporters.content.items.TeleportersItems;
import teleporters.content.tileentity.TileEntityTeleporter;

public class CommonProxy
{
	public void preInit(FMLPreInitializationEvent event) 
	{
		MinecraftForge.EVENT_BUS.register(new TeleporterEvents());
		GameRegistry.registerTileEntity(TileEntityTeleporter.class, "lTele");
		TeleportersItems.RegisterItems();
		TeleportersBlocks.RegisterItems();

		GameRegistry.addRecipe(new ItemStack(TeleportersBlocks.blockTeleporter),
				" G ",
				"TQT",
				"QQQ", 'G', Blocks.GOLD_BLOCK, 'T', TeleportersItems.teleCrystal, 'Q', Blocks.QUARTZ_BLOCK);	
		
		GameRegistry.addSmelting(Items.ENDER_EYE, new ItemStack(TeleportersItems.teleCrystal), 0.1f);
	}


	public void init(FMLInitializationEvent event) {
	}

	public void postInit(FMLPostInitializationEvent event) {
	}
}
