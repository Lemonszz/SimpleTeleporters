package teleporters.content.blocks;

import java.util.Random;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import teleporters.content.items.ItemTeleportCrystal;
import teleporters.content.tileentity.TileEntityTeleporter;

public class BlockTeleporter extends TeleBlockContainer implements ITileEntityProvider{


	protected static final AxisAlignedBB TELE_AABB = new AxisAlignedBB(0D, 0.0D, 0D, 1D, 0.3D, 1D);

	public BlockTeleporter(String name, Material materialIn) {
		super(name, materialIn);
		this.setLightLevel(1F);
		this.setHardness(1);
		this.setResistance(1);
		this.translucent = true;
	}
	
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    /**
     * Used to determine ambient occlusion and culling when rebuilding chunks for render
     */
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
	
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return TELE_AABB;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityTeleporter();
	}

	public int quantityDropped(Random random)
	{
		return 1;
	}

	/**
	 * Get the Item that this Block should drop when harvested.
	 */
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Item.getItemFromBlock(this);
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState blockstate) {
		TileEntityTeleporter te = (TileEntityTeleporter) world.getTileEntity(pos);
		if(te.getStackInSlot(0) != null)
		{
			if(te.getStackInSlot(0).getItem() != Items.BOAT)
			{
				InventoryHelper.dropInventoryItems(world, pos, te);
			}
		}
		super.breakBlock(world, pos, blockstate);
	}

	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{

	}

    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float x, float y, float z)
	{
		TileEntityTeleporter tele = (TileEntityTeleporter) worldIn.getTileEntity(pos);

		if(tele.getStackInSlot(0) == null || tele.getStackInSlot(0).getItem() == Items.BOAT)
		{}
		else
		{
			ItemStack newStack = tele.getStackInSlot(0).copy();
			int i = playerIn.inventory.currentItem;
			if(playerIn.getHeldItemMainhand() == null)
			{
				playerIn.inventory.setInventorySlotContents(i, newStack);
			}else
			{
				playerIn.inventory.addItemStackToInventory(newStack);
			}
			tele.setInventorySlotContents(0, new ItemStack(Items.BOAT));
			playerIn.playSound(SoundEvents.ENTITY_ARROW_SHOOT, 0.5F, 0.4F / (worldIn.rand.nextFloat() * 0.4F + 0.8F));
			this.sendUpdate(worldIn, tele);

			return true;

		}

		if(playerIn.getHeldItemMainhand() != null)
		{
			ItemStack stack = playerIn.getHeldItemMainhand();
			if(stack.getItem() instanceof ItemTeleportCrystal)
			{
				if(stack.getTagCompound() != null)
				{
					if(tele.getStackInSlot(0) == null || tele.getStackInSlot(0).getItem() == Items.BOAT)
					{
						tele.setInventorySlotContents(0, stack.copy());

						stack.func_190918_g(1);

						playerIn.playSound(SoundEvents.ENTITY_ARROW_SHOOT, 0.5F, 0.4F / (worldIn.rand.nextFloat() * 0.4F + 0.8F));
						this.sendUpdate(worldIn, tele);
						return true;
					}
				}
			}
		}



		return false;

	}

	public void sendUpdate(World world, TileEntityTeleporter tile)
	{
		if(!world.isRemote)
		{
			for(EntityPlayer player: world.playerEntities)
			{
				EntityPlayerMP pla = (EntityPlayerMP) player;
				Packet pack = tile.getUpdatePacket();
				pla.connection.sendPacket(pack);
			}
		}
	}
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getSelectedBoundingBox(World worldIn, BlockPos pos)
	{
		return TELE_AABB;
	}

	
	@Override
    public boolean eventReceived(IBlockState state, World worldIn, BlockPos pos, int id, int param)
    {
		//super.eventReceived(worldIn, pos, state, id, param);
		TileEntity tileentity = worldIn.getTileEntity(pos);
		return tileentity == null ? false : tileentity.receiveClientEvent(id, param);
	}
}
