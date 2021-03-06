package org.abimon.mods.minecraft.colouredBlocks;

import java.awt.Color;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;

public class ItemColouringEssense extends Item implements IColouring
{
	public static final Color[] creativeColours = new Color[]{Color.BLACK, Color.BLUE, Color.CYAN, Color.DARK_GRAY, Color.GRAY, Color.GREEN, Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE,
		Color.PINK, Color.RED, Color.WHITE, Color.YELLOW};
	
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) 
	{
		NBTTagCompound nbt = par1ItemStack.getTagCompound();
		if(nbt == null)
		{
			nbt = new NBTTagCompound();
			Color c = new Color(16777215);
			nbt.setInteger("Red", c.getRed());
			nbt.setInteger("Green", c.getGreen());
			nbt.setInteger("Blue", c.getBlue());
			nbt.setInteger("RGB", c.getRGB());
			par1ItemStack.setTagCompound(nbt);
		}
		par3List.add(EnumChatFormatting.RED + "Red: " + nbt.getInteger("Red"));
		par3List.add(EnumChatFormatting.GREEN + "Green: " + nbt.getInteger("Green"));
		par3List.add(EnumChatFormatting.AQUA + "Blue: " + nbt.getInteger("Blue"));
		par3List.add(EnumChatFormatting.DARK_PURPLE + this.getItemStackDisplayName(par1ItemStack) + " is used to create crayons.");
		par3List.add(EnumChatFormatting.DARK_PURPLE + "You can use this to colour blocks");
	}
	
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses()
	{
		return true;
	}
	
	public int getMaxDamage()
	{
		return 1;
	}

	@Override
	public Color getColour(ItemStack item) {
		NBTTagCompound nbt = item.getTagCompound();
		if(nbt == null)
		{
			nbt = new NBTTagCompound();
			Color c = new Color(16777215);
			nbt.setInteger("Red", c.getRed());
			nbt.setInteger("Green", c.getGreen());
			nbt.setInteger("Blue", c.getBlue());
			nbt.setInteger("RGB", c.getRGB());
			item.setTagCompound(nbt);
		}
		Color color = new Color(nbt.getInteger("Red"), nbt.getInteger("Green"), nbt.getInteger("Blue"));
		return color;
	}
	
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		for(Color c : creativeColours)
		{
			NBTTagCompound nbt = new NBTTagCompound();
			ItemStack stack = new ItemStack(item, 1, 0);
			nbt = setColour(c, nbt);
			stack.setTagCompound(nbt);
			list.add(stack);
		}
	}
	
	public NBTTagCompound setColour(Color colour, NBTTagCompound nbt)
	{
		nbt.setInteger("Red", colour.getRed());
		nbt.setInteger("Green", colour.getGreen());
		nbt.setInteger("Blue", colour.getBlue());
		nbt.setInteger("RGB", colour.getRGB());
		return nbt;
	}
	
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack par1ItemStack, int pass)
	{
		NBTTagCompound nbt = par1ItemStack.getTagCompound();
		if(nbt == null)
		{
			nbt = new NBTTagCompound();
			Color c = new Color(16777215);
			nbt.setInteger("Red", c.getRed());
			nbt.setInteger("Green", c.getGreen());
			nbt.setInteger("Blue", c.getBlue());
			nbt.setInteger("RGB", c.getRGB());
			par1ItemStack.setTagCompound(nbt);
		}
		if(!nbt.hasKey("RGB"))
			nbt.setInteger("RGB", new Color(nbt.getInteger("Red"), nbt.getInteger("Green"), nbt.getInteger("Blue")).getRGB());
		if(pass == 0)
			return 16777215;
		else
			return nbt.getInteger("RGB");
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(ItemStack stack, int pass)
	{
		return Items.sugar.getIcon(stack, pass);
	}
}
