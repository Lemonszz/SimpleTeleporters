package teleporters.content.tileentity;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.storage.WorldInfo;
import teleporters.content.items.ItemTeleportCrystal;

public class TileEntityTeleporter extends TileEntityLockable 
implements ISidedInventory, ITickable, IInventory
{
	public void update()
	{
		Random rand = this.worldObj.rand;
		if(getStackInSlot(0) != null)
		{
			if(getStackInSlot(0).getItem() != Items.BOAT)
			{
				for(int i = 0; i < 5; i++)
				{
					this.worldObj.spawnParticle(EnumParticleTypes.PORTAL, pos.getX() + 0.2F + (rand.nextFloat()/2), pos.getY() + 0.4F, pos.getZ() + 0.2F + (rand.nextFloat()/2), 0, rand.nextFloat(), 0, new int[0]);
				}
			}
		}

		List<EntityPlayer> players = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1));
		for(EntityPlayer entityIn : players)
		{

			if(entityIn instanceof EntityPlayer)
			{
				if(entityIn.isSneaking())
				{
					if(worldObj.getTileEntity(pos) != null && worldObj.getTileEntity(pos) instanceof TileEntityTeleporter)
					{
						TileEntityTeleporter tile = (TileEntityTeleporter)worldObj.getTileEntity(pos);
						if(tile.getStackInSlot(0) != null)
						{
							NBTTagCompound tag = tile.getStackInSlot(0).getTagCompound();
							if(tag != null)
							{
								if(tag.getInteger("dim") == entityIn.dimension)
								{
									if(!worldObj.isRemote)
									{
										EntityPlayerMP emp = (EntityPlayerMP)entityIn;
										emp.connection.setPlayerLocation(tag.getInteger("x") + 0.5F, tag.getInteger("y") + 1, tag.getInteger("z") + 0.5F, emp.rotationYaw, emp.prevRotationPitch);
										entityIn.motionY = 0.5F;
									}
									entityIn.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1, 1);
									entityIn.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1, 1);
								}
							}
						}
					}
				}
			}
		}
	}

	private ItemStack[] inv;
	public TileEntityTeleporter(){
		inv = new ItemStack[1];
	}

	public void updateContainingBlockInfo()
	{
		super.updateContainingBlockInfo();
	}

	@Override
	public int getSizeInventory() {
		return inv.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return inv[slot];
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		inv[slot] = stack;
		if (stack != null && stack.func_190916_E() > getInventoryStackLimit()) {
			stack.func_190920_e(getInventoryStackLimit());
		}    
		this.markDirty();

	}

	@Override
	public ItemStack decrStackSize(int slot, int amt) {
		ItemStack stack = getStackInSlot(slot);
		if (stack != null)
		{
			if (stack.func_190916_E() <= amt) 
			{
				setInventorySlotContents(slot, null);
				this.markDirty();
			} 
			else 
			{
				stack = stack.splitStack(amt);
				if (stack.func_190916_E() == 0) {
					setInventorySlotContents(slot, null);
					this.markDirty();

				}
			}
		}
		return stack;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return worldObj.getTileEntity(new BlockPos(this.pos.getX(), this.pos.getY(), this.pos.getZ())) == this &&
				player.getDistanceSq(this.pos.getX() + 0.5, this.pos.getY() + 0.5, this.pos.getZ() + 0.5) < 64;
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);

		NBTTagList tagList = tagCompound.getTagList("Inventory", 10);
		for (int i = 0; i < tagList.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound) tagList.getCompoundTagAt(i);
			byte slot = tag.getByte("Slot");
			if (slot >= 0 && slot < inv.length) {
				inv[slot] = new ItemStack(tag);
			}
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {

		NBTTagList itemList = new NBTTagList();
		for (int i = 0; i < inv.length; i++) {
			ItemStack stack = inv[i];
			if (stack != null) {
				NBTTagCompound tag = new NBTTagCompound();
				tag.setByte("Slot", (byte) i);
				stack.writeToNBT(tag);
				itemList.appendTag(tag);
			}
		}
		tagCompound.setTag("Inventory", itemList);
		
		return 	super.writeToNBT(tagCompound);
	}

	@Override
    public SPacketUpdateTileEntity getUpdatePacket()
	{
		NBTTagCompound syncData = new NBTTagCompound();
		this.writeToNBT(syncData);
		return new SPacketUpdateTileEntity(pos, 1, syncData);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
	{
		readFromNBT(pkt.getNbtCompound());
	}

	@Override
	public String getName() {
		return "lTele";
	}

	@Override
	public boolean hasCustomName() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ITextComponent getDisplayName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void openInventory(EntityPlayer player) {
		// TODO Auto-generated method stub

	}

	@Override
	public void closeInventory(EntityPlayer player) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		//worldObj.markBlockForUpdate(pos);

		return stack.getItem() instanceof ItemTeleportCrystal;
	}

	@Override
	public int getField(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getFieldCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return new int[]{0};
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {

		if(index == 0)
		{
			return isItemValidForSlot(index, itemStackIn);
		}
		return false;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		return true;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {

		if (this.inv[index] != null)
		{
			ItemStack itemstack = this.inv[index];
			this.inv[index] = null;
			this.markDirty();

			return itemstack;
		}
		else
		{
			return null;
		}
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getGuiID() {
		// TODO Auto-generated method stub
		return "lelele";
	}

	@Override
	public boolean func_191420_l()
	{
		// TODO Auto-generated method stub
		return false;
	}


}
