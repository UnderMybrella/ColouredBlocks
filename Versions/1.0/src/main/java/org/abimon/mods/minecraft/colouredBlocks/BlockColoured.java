package org.abimon.mods.minecraft.colouredBlocks;

import static net.minecraftforge.common.util.ForgeDirection.UP;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockColoured extends Block implements ITileEntityProvider, IColourable{

	public BlockColoured() {
		super(Material.cake);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityColoured();
	}

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
        this.blockIcon = Blocks.portal.getIcon(0, 0);
    }
	
	@Override
	public boolean onBlockEventReceived(World par1World, int par2, int par3, int par4, int par5, int par6)
	{
		super.onBlockEventReceived(par1World, par2, par3, par4, par5, par6);
		TileEntity tileentity = par1World.getTileEntity(par2, par3, par4);
		return tileentity != null ? tileentity.receiveClientEvent(par5, par6) : false;
	}


	public void updateTick(World world, int x, int y, int z, Random p_149674_5_)
	{
		Minecraft.getMinecraft().renderGlobal.markBlockForUpdate(x, y, z);
		world.markBlockForUpdate(x, y, z);
	}

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }
    

    public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side)
    {
    	return true;
    }



	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int face, float posX, float posY, float posZ)
	{
		ItemStack item = player.getHeldItem();
		if(item == null)
			return false;
		if(!(item.getItem() instanceof IColouring))
			return false;
		if(!world.isRemote)
			if(!player.isSneaking())
			{
				TileEntity te = world.getTileEntity(x, y, z);
				if(te instanceof TileEntityColoured)
				{
					Color c = ((TileEntityColoured) world.getTileEntity(x, y, z)).color;
					Color c2 = ((IColouring) item.getItem()).getColour(item);
					((TileEntityColoured) world.getTileEntity(x, y, z)).setColor(((IColouring) item.getItem()).getColour(item));
					Minecraft.getMinecraft().renderGlobal.markBlockForUpdate(x, y, z);
					world.markBlockForUpdate(x, y, z);
					if(!player.capabilities.isCreativeMode)
					{
						if(c.getRGB() == c2.getRGB())
							return false;
						player.getHeldItem().setItemDamage(player.getHeldItem().getItemDamage() + 1);
						if(player.getHeldItem().getMaxDamage() <= player.getHeldItem().getItemDamage())
							player.getHeldItem().stackSize--;
					}
					return true;
				}
			}
			else;
		UpdateThread ut = new UpdateThread(world, x, y, z, 100);
		ut.start();
		return false;
	}

	@Override
	public void clearColour(World world, int x, int y, int z, EntityPlayer player) 
	{	
		if(!world.isRemote)
		{
			TileEntity te = world.getTileEntity(x, y, z);
			if(te instanceof TileEntityColoured)
				((TileEntityColoured) te).setColor(Color.WHITE);
		}
	}

	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess access, int x, int y, int z)
	{
		TileEntity te = access.getTileEntity(x, y, z);
		if(te instanceof TileEntityColoured)
		{
			return ((TileEntityColoured) te).getColor();
		}
		return 16777215;
	}

	@Override
	public Color getColour(World world, int x, int y, int z, EntityPlayer player) {
		if(!world.isRemote)
		{
			TileEntity te = world.getTileEntity(x, y, z);
			if(te instanceof TileEntityColoured)
				return ((TileEntityColoured) te).color;
		}
		return Color.WHITE;
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess access, int x, int y, int z, int side)
	{
		TileEntity te = access.getTileEntity(x, y, z);
		if(te == null || !(te instanceof TileEntityColoured))
			return this.blockIcon;
		if(!((TileEntityColoured) te).underIcon)
			return ((TileEntityColoured) te).block.getIcon(side, access.getBlockMetadata(x, y, z));
		else
		{
			y--;
			Block under = access.getBlock(x, y, z);
			if(under == null)
				return this.blockIcon;
			else
				return under.getIcon(access, x, y, z, side);
		}
	}
	
	@Override
	public int getRenderType(){
		return ColouredBlocks.RENDER;
	}
	
    /**
     * This returns a complete list of items dropped from this block.
     *
     * @param world The current world
     * @param x X Position
     * @param y Y Position
     * @param z Z Position
     * @param metadata Current metadata
     * @param fortune Breakers fortune level
     * @return A ArrayList containing all items this block drops
     */
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
    {
    	
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

        ItemStack item = new ItemStack(ColouredBlocks.colouredPlacer, 1, 0);
        NBTTagCompound nbt = new NBTTagCompound();
        if(world.getTileEntity(x, y, z) == null)
        {
			nbt.setInteger("Red", 255);
			nbt.setInteger("Green", 255);
			nbt.setInteger("Blue", 255);
			nbt.setInteger("RGB", Color.WHITE.getRGB());
			nbt.setInteger("Damage", metadata);
			nbt.setString("Block", GameData.getBlockRegistry().getNameForObject(Blocks.dirt));
	        item.setTagCompound(nbt);
	        ret.add(item);
	        
	        return ret;
        }
        world.getTileEntity(x, y, z).writeToNBT(nbt);
        NBTTagCompound n = new NBTTagCompound();
        n.setInteger("Red", nbt.getInteger("Red"));
        n.setInteger("Green", nbt.getInteger("Green"));
        n.setInteger("Blue", nbt.getInteger("Blue"));
        n.setInteger("RGB", nbt.getInteger("RGB"));
        n.setInteger("Damage", nbt.getInteger("Damage"));
        n.setString("Block", nbt.getString("Block"));
        item.setTagCompound(n);
        ret.add(item);
        
        world.removeTileEntity(x, y, z);
        
        return ret;
    }
    
    public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_){}

}
