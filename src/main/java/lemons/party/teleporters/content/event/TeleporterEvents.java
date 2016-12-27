package lemons.party.teleporters.content.event;

import lemons.party.teleporters.content.items.ItemTeleportCrystal;
import lemons.party.teleporters.content.items.TeleportersItems;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TeleporterEvents 
{
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onPlayerTickClient(PlayerTickEvent event)
	{
		Minecraft mc = Minecraft.getMinecraft();
		if(mc.player != null)
		{
			for(EnumHand hnd : EnumHand.values())
			{
				ItemStack stack = mc.player.getHeldItem(hnd);
				if(stack.getItem() == TeleportersItems.teleCrystal)
				{
					NBTTagCompound tags = stack.getTagCompound();
					if(tags != null)
					{
						if(tags.getInteger("dim") == mc.player.dimension)
						{
							BlockPos telePos = new BlockPos(tags.getInteger("x"), tags.getInteger("y"), tags.getInteger("z"));
							if(mc.player.getDistance(telePos.getX(), telePos.getY(), telePos.getZ()) < 15)
							{
								mc.world.spawnParticle(EnumParticleTypes.TOWN_AURA,
										telePos.getX() + (1 - mc.world.rand.nextFloat()),
										telePos.getY() + (1 - mc.world.rand.nextFloat()),
										telePos.getZ() + (1 - mc.world.rand.nextFloat())
										, 0, 0, 0, new int[0]);
							}
						}
					}
				}
			}
		}
	}
}
