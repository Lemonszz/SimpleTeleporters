package lemons.party.teleporters.content.items;

import lemons.party.teleporters.Teleporters;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class TeleItem extends Item
{
	private String name;
	public TeleItem(String name)
	{
		this.setUnlocalizedName(name);
		this.name = name;
		this.setCreativeTab(CreativeTabs.TRANSPORTATION);
		TeleportersItems.itemList.add(this);
	}
	
	public String getName()
	{
		return name;
	}
}
