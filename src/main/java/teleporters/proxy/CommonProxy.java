package teleporters.proxy;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import teleporters.content.blocks.TeleportersBlocks;
import teleporters.content.config.ModConfig;
import teleporters.content.event.TeleporterEvents;
import teleporters.content.items.TeleportersItems;
import teleporters.content.tileentity.TileEntityTeleporter;

public class CommonProxy
{
	public void preInit(FMLPreInitializationEvent event) 
	{
		ModConfig.loadConfig(new Configuration(event.getSuggestedConfigurationFile()));
		
		MinecraftForge.EVENT_BUS.register(new TeleporterEvents());
		GameRegistry.registerTileEntity(TileEntityTeleporter.class, "lTele");
		TeleportersItems.RegisterItems();
		TeleportersBlocks.RegisterItems();

		switch(ModConfig.CONFIG_RECIPE_HARDNESS)
		{
		case 0:
			GameRegistry.addRecipe(new ItemStack(TeleportersBlocks.blockTeleporter),
					" G ",
					"TQT",
					"QQQ", 'G', Blocks.GOLD_BLOCK, 'T', TeleportersItems.teleCrystal, 'Q', Blocks.QUARTZ_BLOCK);
			break;
			
		case 1:
			GameRegistry.addRecipe(new ItemStack(TeleportersBlocks.blockTeleporter),
					" G ",
					"TQT",
					"QQQ", 'G', Blocks.GOLD_BLOCK, 'T', TeleportersItems.teleCrystal, 'Q', Blocks.END_STONE);
			break;
			
		case 2:
			GameRegistry.addRecipe(new ItemStack(TeleportersBlocks.blockTeleporter),
					" G ",
					"TQT",
					"QQQ", 'G', Items.NETHER_STAR, 'T', TeleportersItems.teleCrystal, 'Q', Blocks.QUARTZ_BLOCK);
			break;
		
		default:
			break;
		}
		
		GameRegistry.addSmelting(Items.ENDER_EYE, new ItemStack(TeleportersItems.teleCrystal), 0.1f);
	}


	public void init(FMLInitializationEvent event) {
	}

	public void postInit(FMLPostInitializationEvent event) {
	}
}
