package lemons.party.teleporters.content.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemTeleportCrystal extends TeleItem {

	public ItemTeleportCrystal() {
		super("endercrystal");

		this.setMaxStackSize(1);
	}

    public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float x, float y, float z)
    {
		if(playerIn.isSneaking())
		{
			ItemStack stack = playerIn.getHeldItem(hand);
			NBTTagCompound tags = stack.getTagCompound();
			if(tags == null)
			{
				stack.setTagCompound(new NBTTagCompound());
				tags = stack.getTagCompound();
			}
			BlockPos offPos = pos.offset(facing);
			tags.setInteger("x", offPos.getX());
			tags.setInteger("y", offPos.getY());
			tags.setInteger("z", offPos.getZ());
			tags.setInteger("dim", playerIn.dimension);

			if(!worldIn.isRemote)
			{
				playerIn.addChatComponentMessage(new TextComponentTranslation(TextFormatting.GREEN + "This crystal is linked at:" + " " + 
						tags.getInteger("x") + ", " +
						tags.getInteger("y") + ", " +
						tags.getInteger("z")), false);
			}
			playerIn.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1, 1);

			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.FAIL;
	}
    
    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
		if(!playerIn.isSneaking() && hand == EnumHand.MAIN_HAND)
		{
			NBTTagCompound tags = stack.getTagCompound();
			if(tags == null)
			{
				playerIn.addChatComponentMessage(new TextComponentTranslation(TextFormatting.RED + "This crystal is unlinked."), false);
			}else
			{
				playerIn.addChatComponentMessage(new TextComponentTranslation(TextFormatting.GREEN + "This crystal is linked at:" + " " + 
						tags.getInteger("x") + ", " +
						tags.getInteger("y") + ", " +
						tags.getInteger("z")), false);
			}
		}
		return new ActionResult(EnumActionResult.PASS, stack);
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		NBTTagCompound tags = stack.getTagCompound();
		if(tags == null)
		{
			tooltip.add(TextFormatting.RED + "Unlinked");
			tooltip.add(TextFormatting.BLUE + "" + TextFormatting.ITALIC + "Sneak right click a block to link.");

		}else
		{
			tooltip.add(TextFormatting.GREEN + "Linked: " + tags.getInteger("x") + ", " + tags.getInteger("y") + ", " + tags.getInteger("z"));
		}
	}
}
