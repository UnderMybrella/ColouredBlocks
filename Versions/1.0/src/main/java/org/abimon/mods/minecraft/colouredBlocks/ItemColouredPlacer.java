package org.abimon.mods.minecraft.colouredBlocks;

import java.awt.Color;
import java.util.List;

import org.abimon.mods.minecraft.deco.DecoTileEntity;

import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemColouredPlacer extends Item
{

	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		ItemStack itm = new ItemStack(item);
		itm.setTagCompound(new NBTTagCompound());
		itm.getTagCompound().setInteger("Red", 255);
		itm.getTagCompound().setInteger("Green", 255);
		itm.getTagCompound().setInteger("Blue", 255);
		itm.getTagCompound().setInteger("RGB", Color.WHITE.getRGB());
		itm.getTagCompound().setString("Block", GameData.getBlockRegistry().getNameForObject(Blocks.quartz_block));
		list.add(itm);
	}

	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) 
	{
		NBTTagCompound nbt = par1ItemStack.getTagCompound();
		
		if(nbt.getString("Block").equals("under_icon"))
		{	
			par3List.add("This block will mimic the ");
			par3List.add("texture the texture of the block below it!");
		}

		par3List.add(EnumChatFormatting.RED + "Red: " + nbt.getInteger("Red"));
		par3List.add(EnumChatFormatting.GREEN + "Green: " + nbt.getInteger("Green"));
		par3List.add(EnumChatFormatting.AQUA + "Blue: " + nbt.getInteger("Blue"));
	}

	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
	{
		Block block = par3World.getBlock(par4, par5, par6);

		if (block == Blocks.snow_layer && (par3World.getBlockMetadata(par4, par5, par6) & 7) < 1)
		{
			par7 = 1;
		}
		else if (block != Blocks.vine && block != Blocks.tallgrass && block != Blocks.deadbush && !block.isReplaceable(par3World, par4, par5, par6))
		{
			if (par7 == 0)
			{
				--par5;
			}

			if (par7 == 1)
			{
				++par5;
			}

			if (par7 == 2)
			{
				--par6;
			}

			if (par7 == 3)
			{
				++par6;
			}

			if (par7 == 4)
			{
				--par4;
			}

			if (par7 == 5)
			{
				++par4;
			}
		}

		if (par1ItemStack.stackSize == 0)
		{
			return false;
		}
		else if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack))
		{
			return false;
		}
		else if (par5 == 255 && ColouredBlocks.colouredBlock.getMaterial().isSolid())
		{
			return false;
		}
		else if (par3World.canPlaceEntityOnSide(ColouredBlocks.colouredBlock, par4, par5, par6, false, par7, par2EntityPlayer, par1ItemStack))
		{
			int i1 = par1ItemStack.getTagCompound().getInteger("Damage");
			int j1 = ColouredBlocks.colouredBlock.onBlockPlaced(par3World, par4, par5, par6, par7, par8, par9, par10, i1);

			if (placeBlockAt(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10, j1))
			{
				par3World.playSoundEffect((double)((float)par4 + 0.5F), (double)((float)par5 + 0.5F), (double)((float)par6 + 0.5F), ColouredBlocks.colouredBlock.stepSound.func_150496_b(), (ColouredBlocks.colouredBlock.stepSound.getVolume() + 1.0F) / 2.0F, ColouredBlocks.colouredBlock.stepSound.getPitch() * 0.8F);
				--par1ItemStack.stackSize;
			}

			TileEntity te = par3World.getTileEntity(par4, par5, par6);
			if(te == null)
				te = new TileEntityColoured();
			if(!(te instanceof TileEntityColoured))
			{
				par3World.removeTileEntity(par4, par5, par6);
				te = new TileEntityColoured();
			}
			TileEntityColoured det = (TileEntityColoured) te;
			NBTTagCompound item = par1ItemStack.getTagCompound();
			det.readFromNBT(item);
			par3World.setTileEntity(par4, par5, par6, det);
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * Called to actually place the block, after the location is determined
	 * and all permission checks have been made.
	 *
	 * @param stack The item stack that was used to place the block. This can be changed inside the method.
	 * @param player The player who is placing the block. Can be null if the block is not being placed by a player.
	 * @param side The side the player (or machine) right-clicked on.
	 */
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata)
	{

		if (!world.setBlock(x, y, z, ColouredBlocks.colouredBlock, metadata, 3))
		{
			return false;
		}

		if (world.getBlock(x, y, z) == ColouredBlocks.colouredBlock)
		{
			ColouredBlocks.colouredBlock.onBlockPlacedBy(world, x, y, z, player, stack);
			ColouredBlocks.colouredBlock.onPostBlockPlaced(world, x, y, z, metadata);
		}

		return true;
	}

	public String getItemStackDisplayName(ItemStack par1ItemStack)
	{
		if(!par1ItemStack.getTagCompound().getString("Block").equals("under_icon"))
			return ("" + StatCollector.translateToLocal(this.getUnlocalizedNameInefficiently(par1ItemStack) + ".name")).trim() + " "+ new ItemStack(GameData.getBlockRegistry().getObject(par1ItemStack.getTagCompound().getString("Block"))).getDisplayName(); 
		else
			return "Coloured Underside Icon Block";
	}

}
