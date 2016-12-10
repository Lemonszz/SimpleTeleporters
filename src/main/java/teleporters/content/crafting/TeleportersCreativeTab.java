package teleporters.content.crafting;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import teleporters.content.blocks.TeleportersBlocks;

public class TeleportersCreativeTab extends CreativeTabs
{

	public TeleportersCreativeTab() {
		super("teleporterTab");
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(Item.getItemFromBlock(TeleportersBlocks.blockTeleporter));
	}

}
