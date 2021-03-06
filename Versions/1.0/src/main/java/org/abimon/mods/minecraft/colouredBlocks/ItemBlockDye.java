package org.abimon.mods.minecraft.colouredBlocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemColored;
import net.minecraft.item.ItemStack;

public class ItemBlockDye extends ItemColored
{
	public ItemBlockDye(Block p_i45326_1_) {
		super(p_i45326_1_, true);
	}

	public String getUnlocalizedName(ItemStack par1ItemStack)
	{
		int damage = par1ItemStack.getItemDamage();
		return ColouredBlocks.MODID + ":dyeBlock:" + damage + (ColouredBlocks.doRareNames ? ".rare" : "");
	}
	
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
    {
        return this.field_150939_a.getRenderColor(par1ItemStack.getItemDamage());
    }

}
