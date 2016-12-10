package lemons.party.teleporters.content.blocks;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class TeleBlockContainer extends TeleBlock implements ITileEntityProvider
{

	public TeleBlockContainer(String name, Material materialIn) {
		super(name, materialIn);
        this.isBlockContainer = true;
	}
	
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        super.breakBlock(worldIn, pos, state);
        worldIn.removeTileEntity(pos);
    }
}
