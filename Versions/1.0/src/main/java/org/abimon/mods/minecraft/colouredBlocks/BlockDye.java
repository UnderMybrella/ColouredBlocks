package org.abimon.mods.minecraft.colouredBlocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDye extends Block 
{

	protected BlockDye() {
		super(Material.cake);
	}
	
    public int damageDropped(int meta)
    {
    	return meta;
    }

    /**
     * Gets the block's texture. Args: side, meta
     */
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int p_149691_1_, int p_149691_2_)
    {
        return Blocks.quartz_block.getIcon(p_149691_1_, 0);
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list)
    {
    	for(int i = 0; i < 16; i++)
    		list.add(new ItemStack(item, 1, i));
    }
    
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess access, int x, int y, int z)
    {
    	switch(access.getBlockMetadata(x, y, z))
    	{
    	case 0:
    		return 1644825;
    	case 1:
    		return 10040115;
    	case 2:
    		return 6717235;
    	case 3:
    		return 6704179;
    	case 4:
    		return 3361970;
    	case 5:
    		return 8339378;
    	case 6:
    		return 5013401;
    	case 7:
    		return 10066329;
    	case 8:
    		return 5000268;
    	case 9:
    		return 15892389;
    	case 10:
    		return 8375321;
    	case 11:
    		return 15066419;
    	case 12:
    		return 6724056;
    	case 13:
    		return 11685080;
    	case 14:
    		return 14188339;
    	default:
    		return 16777215;
    	}
    }
    
    @SideOnly(Side.CLIENT)
    public int getRenderColor(int meta)
    {
    	int returning = 0;
    	switch(meta)
    	{
    	case 0:
    		returning = 1644825;
    		break;
    	case 1:
    		returning = 10040115;
    		break;
    	case 2:
    		returning = 6717235;
    		break;
    	case 3:
    		returning = 6704179;
    		break;
    	case 4:
    		returning = 3361970;
    		break;
    	case 5:
    		returning = 8339378;
    		break;
    	case 6:
    		returning = 5013401;
    		break;
    	case 7:
    		returning = 10066329;
    		break;
    	case 8:
    		returning = 5000268;
    		break;
    	case 9:
    		returning = 15892389;
    		break;
    	case 10:
    		returning = 8375321;
    		break;
    	case 11:
    		returning = 15066419;
    		break;
    	case 12:
    		returning = 6724056;
    		break;
    	case 13:
    		returning = 11685080;
    		break;
    	case 14:
    		returning = 14188339;
    		break;
    	default:
    		returning = 16777215;
    		break;
    	}
    	return returning;
    }
    

	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int face, float posX, float posY, float posZ)
	{
		if(!world.isRemote)
		{
			if(player.isSneaking())
				if(world.canMineBlock(player, x, y, z))
				{
					int meta = world.getBlockMetadata(x, y, z);
					world.setBlockToAir(x, y, z);
					EntityItem dye = new EntityItem(world, player.posX, player.posY, player.posZ, new ItemStack(Items.dye, 8, meta));
					EntityItem quartz = new EntityItem(world, player.posX, player.posY, player.posZ, new ItemStack(Blocks.quartz_block, 1));
					return true;
				}
		}
		return false;
	}
    
    public int quantityDropped(Random p_149745_1_)
    {
        return 1;
    }

    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return Item.getItemFromBlock(this);
    }
	
}

