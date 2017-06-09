package lemons.party.teleporters.content.crafting;

import lemons.party.teleporters.content.items.ItemTeleportCrystal;
import lemons.party.teleporters.content.items.TeleportersItems;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemWrittenBook;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class PositionDupeRecipe implements IRecipe
{
    /**
     * Used to check if a recipe matches current crafting inventory
     */
    public boolean matches(InventoryCrafting inv, World worldIn)
    {
        int i = 0;
        ItemStack itemstack = ItemStack.EMPTY;

        for (int j = 0; j < inv.getSizeInventory(); ++j)
        {
            ItemStack itemstack1 = inv.getStackInSlot(j);

            if (!itemstack1.isEmpty())
            {
                if(itemstack1.getItem() != TeleportersItems.teleCrystal)
                {
                    return false;
                }
                if (itemstack1.getItem() == TeleportersItems.teleCrystal && itemstack1.getTagCompound() != null)
                {
                    if (!itemstack.isEmpty())
                    {
                        return false;
                    }
                    
                    itemstack = itemstack1;
                }
                else
                {
                    if (itemstack1.getItem() != TeleportersItems.teleCrystal && itemstack1.hasTagCompound())
                    {
                        return false;
                    }

                    ++i;
                }
            }
        }

        return !itemstack.isEmpty() && itemstack.hasTagCompound() && i > 0;
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    public ItemStack getCraftingResult(InventoryCrafting inv)
    {
        int i = 0;
        ItemStack itemstack = ItemStack.EMPTY;

        for (int j = 0; j < inv.getSizeInventory(); ++j)
        {
            ItemStack itemstack1 = inv.getStackInSlot(j);

            if (!itemstack1.isEmpty())
            {
				if(itemstack1.getItem() != TeleportersItems.teleCrystal)
				{
					return ItemStack.EMPTY;
				}
                if (itemstack1.getItem() == TeleportersItems.teleCrystal && itemstack1.getTagCompound() != null)
                {
                    if (!itemstack.isEmpty())
                    {
                        return ItemStack.EMPTY;
                    }

                    itemstack = itemstack1;
                }
                else
                {
                    if (itemstack1.getItem() != TeleportersItems.teleCrystal && itemstack1.hasTagCompound())
                    {
                        return ItemStack.EMPTY;
                    }

                    ++i;
                }
            }
        }

        if (!itemstack.isEmpty() && itemstack.hasTagCompound() && i >= 1 && ItemWrittenBook.getGeneration(itemstack) < 2)
        {
        	int totalAmount = i;
        	if(totalAmount >= 1)
			{
				totalAmount += 1;
			}
            ItemStack itemstack2 = new ItemStack(TeleportersItems.teleCrystal, totalAmount);
            itemstack2.setTagCompound(itemstack.getTagCompound().copy());
            
            if (itemstack.hasDisplayName())
            {
                itemstack2.setStackDisplayName(itemstack.getDisplayName());
            }

            return itemstack2;
        }
        else
        {
            return ItemStack.EMPTY;
        }
    }

    /**
     * Returns the size of the recipe area
     */
    public int getRecipeSize()
    {
        return 9;
    }

    public ItemStack getRecipeOutput()
    {
        return ItemStack.EMPTY;
    }

    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv)
    {
        NonNullList<ItemStack> nonnulllist = NonNullList.<ItemStack>withSize(inv.getSizeInventory(), ItemStack.EMPTY);
/*
        for (int i = 0; i < nonnulllist.size(); ++i)
        {
            ItemStack itemstack = inv.getStackInSlot(i);

            if (itemstack.getItem() instanceof ItemTeleportCrystal)
            {
                ItemStack itemstack1 = itemstack.copy();
                itemstack1.setCount(1);
                nonnulllist.set(i, itemstack1);
                break;
            }
        }
*/
        return nonnulllist;
    }
}