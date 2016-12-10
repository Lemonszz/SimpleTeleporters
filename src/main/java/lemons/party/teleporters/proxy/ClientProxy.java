package lemons.party.teleporters.proxy;

import lemons.party.teleporters.content.blocks.TeleportersBlocks;
import lemons.party.teleporters.content.items.TeleportersItems;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy
{
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		TeleportersItems.RegisterItemModels();
		TeleportersBlocks.RegisterItemModels();
	}

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}
}
