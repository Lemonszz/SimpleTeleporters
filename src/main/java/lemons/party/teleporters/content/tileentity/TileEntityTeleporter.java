package lemons.party.teleporters.content.tileentity;

import java.util.List;
import java.util.Random;

import lemons.party.teleporters.Constants;
import lemons.party.teleporters.content.items.ItemTeleportCrystal;
import lemons.party.teleporters.content.items.TeleportersItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;

public class TileEntityTeleporter extends TileEntityLockable 
implements ISidedInventory, ITickable, IInventory
{
	public void update()
	{
		List<EntityPlayer> players = this.world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1));
		for(EntityPlayer entityIn : players)
		{
			if(entityIn instanceof EntityPlayer)
			{
				if(entityIn.isSneaking())
				{
					if(world.getTileEntity(pos) != null && world.getTileEntity(pos) instanceof TileEntityTeleporter)
					{
						TileEntityTeleporter tile = (TileEntityTeleporter)world.getTileEntity(pos);
						if(tile.getStackInSlot(0) != null)
						{
							NBTTagCompound tag = tile.getStackInSlot(0).getTagCompound();
							if(tag != null)
							{
								if(tag.getInteger("dim") == entityIn.dimension)
								{
									if(!world.isRemote)
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
		inv[0] = ItemStack.EMPTY;
	}

	public void updateContainingBlockInfo()
	{
		super.updateContainingBlockInfo();
	}
	
	public boolean hasCrystal()
	{
		return this.getCrystal().getItem() == TeleportersItems.teleCrystal;
	}

	@Override
	public int getSizeInventory() {
		return inv.length;
	}

	public ItemStack getCrystal()
	{
		return inv[0];
	}
	
	public void setCrystal(ItemStack stack)
	{
		inv[0] = stack;
	}
	
	@Override
	public ItemStack getStackInSlot(int slot) {
		return inv[slot];
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		inv[slot] = stack;
		if (stack != null && stack.getCount() > getInventoryStackLimit()) {
			stack.setCount(getInventoryStackLimit());
		}    
		this.markDirty();

	}

	@Override
	public ItemStack decrStackSize(int slot, int amt) {
		ItemStack stack = getStackInSlot(slot);
		if (stack != null)
		{
			if (stack.getCount() <= amt) 
			{
				setInventorySlotContents(slot, null);
				this.markDirty();
			} 
			else 
			{
				stack = stack.splitStack(amt);
				if (stack.getCount() == 0) {
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
		return false;
	}

	@Override
	public ITextComponent getDisplayName() {
		return null;
	}

	@Override
	public void openInventory(EntityPlayer player) {
	}

	@Override
	public void closeInventory(EntityPlayer player) {
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return stack.getItem() instanceof ItemTeleportCrystal;
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {

	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {
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
		return null;
	}

	@Override
	public String getGuiID() {
		return Constants.MODID + ":unusedguiid";
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return world.getTileEntity(new BlockPos(this.pos.getX(), this.pos.getY(), this.pos.getZ())) == this &&
				player.getDistanceSq(this.pos.getX() + 0.5, this.pos.getY() + 0.5, this.pos.getZ() + 0.5) < 64;
	}
}
