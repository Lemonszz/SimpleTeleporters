package teleporters.content.blocks;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TeleBlockContainer extends TeleBlock implements ITileEntityProvider
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
    
	public String getName()
	{
		return this.getUnlocalizedName().substring(5);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return null;
	}
	
/*	@Override
    public boolean onBlockEventReceived(World worldIn, BlockPos pos, int id, int param)
    {
        super.onBlockEventReceived(worldIn, pos, id, param);
        TileEntity tileentity = worldIn.getTileEntity(pos);
        return tileentity == null ? false : tileentity.receiveClientEvent(eventID, eventParam);
    }*/
}
