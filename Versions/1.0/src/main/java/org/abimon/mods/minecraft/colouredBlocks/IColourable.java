package org.abimon.mods.minecraft.colouredBlocks;

import java.awt.Color;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public interface IColourable 
{
	public void clearColour(World world, int x, int y, int z, EntityPlayer player);
	public Color getColour(World world, int x, int y, int z, EntityPlayer player);
}
