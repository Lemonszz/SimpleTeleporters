package teleporters.content.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import teleporters.Teleporters;

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
