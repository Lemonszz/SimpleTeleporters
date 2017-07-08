package lemons.party.teleporters.proxy;

import lemons.party.teleporters.Constants;
import lemons.party.teleporters.content.config.ModConfig;
import lemons.party.teleporters.content.crafting.PositionDupeRecipe;
import lemons.party.teleporters.content.items.TeleportersItems;
import lemons.party.teleporters.content.tileentity.TileEntityTeleporter;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber
public class CommonProxy
{
	public void preInit(FMLPreInitializationEvent event) 
	{
		ModConfig.loadConfig(new Configuration(event.getSuggestedConfigurationFile()));

		GameRegistry.registerTileEntity(TileEntityTeleporter.class, "lTele");
	}

	@SubscribeEvent
	public static void registerRec(RegistryEvent.Register<IRecipe> event)
	{
		event.getRegistry().register(new PositionDupeRecipe().setRegistryName(Constants.MODID, "posdupe"));
	}

	public void init(FMLInitializationEvent event)
	{
		GameRegistry.addSmelting(Items.ENDER_EYE, new ItemStack(TeleportersItems.teleCrystal), 0.1f);
	}

	public void postInit(FMLPostInitializationEvent event) {
	}
}
