package teleporters.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import teleporters.content.blocks.TeleportersBlocks;
import teleporters.content.items.TeleportersItems;

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
