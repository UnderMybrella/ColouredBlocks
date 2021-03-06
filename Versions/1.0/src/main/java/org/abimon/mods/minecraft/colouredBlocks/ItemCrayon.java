package org.abimon.mods.minecraft.colouredBlocks;

import java.awt.Color;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemCrayon extends Item implements IColouring
{
	@SideOnly(Side.CLIENT)
	public IIcon crayon;
	@SideOnly(Side.CLIENT)
	public IIcon crayonColour;

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
		par3List.add(EnumChatFormatting.ITALIC + "Uses left: " + (256 - par1ItemStack.getItemDamage()));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister par1IconRegister)
	{
		this.crayon = par1IconRegister.registerIcon(ColouredBlocks.MODID + ":" + "crayon");
		this.crayonColour = par1IconRegister.registerIcon(ColouredBlocks.MODID + ":" + "crayonColour");
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

	/**
	 * Return the correct icon for rendering based on the supplied ItemStack and render pass.
	 *
	 * Defers to {@link #getIconFromDamageForRenderPass(int, int)}
	 * @param stack to render for
	 * @param pass the multi-render pass
	 * @return the icon
	 */
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(ItemStack stack, int pass)
	{
		if(pass == 1)
			return crayonColour;
		else
			return crayon;
	}

	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses()
	{
		return true;
	}
	
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int x, int y, int z, int face, float posX, float posY, float posZ)
	{
		if(!par3World.isRemote && par2EntityPlayer.isSneaking())
		{
			NBTTagCompound nbt = new NBTTagCompound();
			TileEntity te = par3World.getTileEntity(x, y, z);
			Block block = par3World.getBlock(x, y, z);
			Color c = new Color(16777215);
			if(block instanceof IColourable)
			{
				c = ((IColourable) block).getColour(par3World, x, y, z, par2EntityPlayer);
				((IColourable) block).clearColour(par3World, x, y, z, par2EntityPlayer);
			}
			nbt.setInteger("Red", c.getRed());
			nbt.setInteger("Green", c.getGreen());
			nbt.setInteger("Blue", c.getBlue());
			nbt.setInteger("RGB", c.getRGB());
			par1ItemStack.setTagCompound(nbt);
			return true;
		}
		return false;
	}

	public int getMaxDamage()
	{
		return 256;
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
		System.out.println(color);
		return color;
	}

}
