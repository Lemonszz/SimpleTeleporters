package teleporters.content.event;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import teleporters.content.blocks.TeleportersBlocks;
import teleporters.content.items.ItemTeleportCrystal;
import teleporters.content.items.TeleportersItems;

public class TeleporterEvents 
{
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onPlayerTickClient(PlayerTickEvent event)
	{
		Minecraft mc = Minecraft.getMinecraft();
		if(mc.thePlayer != null)
		if(mc.thePlayer.getHeldItemMainhand() != null)
		{
			ItemStack stack = mc.thePlayer.getHeldItemMainhand();
			if(stack.getItem() instanceof ItemTeleportCrystal)
			{
				NBTTagCompound tags = stack.getTagCompound();
				if(tags != null)
				{
					if(tags.getInteger("dim") == mc.thePlayer.dimension)
					{
						BlockPos telePos = new BlockPos(tags.getInteger("x"), tags.getInteger("y"), tags.getInteger("z"));
						if(mc.thePlayer.getDistance(telePos.getX(), telePos.getY(), telePos.getZ()) < 15)
						{
							mc.theWorld.spawnParticle(EnumParticleTypes.TOWN_AURA,
									telePos.getX() + (1 - mc.theWorld.rand.nextFloat()) + 0.2F,
									telePos.getY() + (1 - mc.theWorld.rand.nextFloat()) + 0.0F,
									telePos.getZ() + (1 - mc.theWorld.rand.nextFloat()) + 0.2F
									, 0, 0, 0, new int[0]);
						}
					}
				}
			}
		}
		
		//make this non duplicated code later
		if(mc.thePlayer != null)
		if(mc.thePlayer.getHeldItemOffhand() != null)
		{
			ItemStack stack = mc.thePlayer.getHeldItemOffhand();
			if(stack.getItem() instanceof ItemTeleportCrystal)
			{
				NBTTagCompound tags = stack.getTagCompound();
				if(tags != null)
				{
					if(tags.getInteger("dim") == mc.thePlayer.dimension)
					{
						BlockPos telePos = new BlockPos(tags.getInteger("x"), tags.getInteger("y"), tags.getInteger("z"));
						if(mc.thePlayer.getDistance(telePos.getX(), telePos.getY(), telePos.getZ()) < 15)
						{
							mc.theWorld.spawnParticle(EnumParticleTypes.TOWN_AURA,
									telePos.getX() + (1 - mc.theWorld.rand.nextFloat()) + 0.2F,
									telePos.getY() + (1 - mc.theWorld.rand.nextFloat()) + 0.0F,
									telePos.getZ() + (1 - mc.theWorld.rand.nextFloat()) + 0.2F
									, 0, 0, 0, new int[0]);
						}
					}
				}
			}
		}
	}
}
