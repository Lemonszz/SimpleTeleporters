package teleporters.content.items;

import net.minecraft.item.Item;
import teleporters.Teleporters;

public class TeleItem extends Item
{
	private String name;
	public TeleItem(String name)
	{
		this.setUnlocalizedName(name);
		this.name = name;
		this.setCreativeTab(Teleporters.tab);
		TeleportersItems.itemList.add(this);
	}
	
	public String getName()
	{
		return name;
	}
}
