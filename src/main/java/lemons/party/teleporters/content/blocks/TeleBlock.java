package lemons.party.teleporters.content.blocks;

import lemons.party.teleporters.Teleporters;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class TeleBlock extends Block
{

	public TeleBlock(String name, Material materialIn) {
		super(materialIn);
		
		this.setUnlocalizedName(name);
		this.setCreativeTab(CreativeTabs.TRANSPORTATION);
		TeleportersBlocks.blockList.add(this);
	}

	public String getName()
	{
		return this.getUnlocalizedName().substring(5);
	}
}
