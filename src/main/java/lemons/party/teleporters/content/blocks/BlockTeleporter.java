package lemons.party.teleporters.content.blocks;

import java.util.Random;

import lemons.party.teleporters.content.config.ModConfig;
import lemons.party.teleporters.content.items.ItemTeleportCrystal;
import lemons.party.teleporters.content.tileentity.TileEntityTeleporter;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTeleporter extends TeleBlockContainer
{
	protected static final AxisAlignedBB TELE_AABB = new AxisAlignedBB(0D, 0.0D, 0D, 1D, 0.3D, 1D);
	
    public static final PropertyInteger ON = PropertyInteger.create("on", 0, 1);
	
	public BlockTeleporter(String name, Material materialIn)
	{
		super(name, materialIn);
		this.setLightLevel(1F);
		this.setHardness(1);
		this.setResistance(1);
        this.setTickRandomly(true);
        this.setDefaultState(this.blockState.getBaseState().withProperty(ON, Integer.valueOf(0)));
	}
	
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float x, float y, float z)
	{
		TileEntityTeleporter tele = (TileEntityTeleporter) worldIn.getTileEntity(pos);
		if(tele.hasCrystal() && !worldIn.isBlockPowered(pos))
		{
			ItemStack stack = tele.getCrystal().copy();
			playerIn.inventory.addItemStackToInventory(stack);
			playerIn.playSound(SoundEvents.ENTITY_ARROW_SHOOT, 0.5F, 0.4F / (worldIn.rand.nextFloat() * 0.4F + 0.8F));
			worldIn.setBlockState(pos, state.withProperty(ON, 0));
			tele = (TileEntityTeleporter) worldIn.getTileEntity(pos);
			tele.setCrystal(ItemStack.EMPTY);
			return true;
		}
		else
		{
			ItemStack stack = playerIn.getHeldItemMainhand();
			if(stack.getItem() instanceof ItemTeleportCrystal)
			{
				if(stack.getTagCompound() != null)
				{
					playerIn.playSound(SoundEvents.ENTITY_ARROW_SHOOT, 0.5F, 0.4F / (worldIn.rand.nextFloat() * 0.4F + 0.8F));
					worldIn.setBlockState(pos, state.withProperty(ON, 1));
					tele = (TileEntityTeleporter) worldIn.getTileEntity(pos);
					tele.setCrystal(stack.copy());
					//stack.func_190918_g(1);
					stack.shrink(1);
					return true;
				}
			}
		}
		
		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
    public void randomDisplayTick(IBlockState state, World worldIn, BlockPos pos, Random rand)
    {
		if(state.getValue(ON) == 1)
		{
			for(int i = 0; i < ModConfig.CONFIG_PARTICLE_AMT_BLOCK; i++)
			{
				worldIn.spawnParticle(EnumParticleTypes.PORTAL, pos.getX() + 0.2F + (rand.nextFloat()/2), pos.getY() + 0.4F, pos.getZ() + 0.2F + (rand.nextFloat()/2), 0, rand.nextFloat(), 0, new int[0]);
			}
		}
	}
    
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(ON, Math.min(2, meta));
    }
	
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(ON);
    }
    
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {ON});
    }
    
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
	
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return TELE_AABB;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityTeleporter();
	}

	public int quantityDropped(Random random)
	{
		return 1;
	}
	
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Item.getItemFromBlock(this);
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState blockstate) {
		TileEntityTeleporter te = (TileEntityTeleporter) world.getTileEntity(pos);
		if(te.getStackInSlot(0) != null)
		{
			if(te.hasCrystal())
			{
				InventoryHelper.dropInventoryItems(world, pos, te);
			}
		}
		super.breakBlock(world, pos, blockstate);
	}
}
