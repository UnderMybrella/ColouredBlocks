package org.abimon.mods.minecraft.colouredBlocks;

import java.awt.Color;

import cpw.mods.fml.common.registry.GameData;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityColoured extends TileEntity 
{
	Color color = new Color(255, 255, 255);
	int rgb = color.getRGB();
	Block block = Blocks.quartz_block;
	boolean underIcon = false;
	
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		color = new Color(nbt.getInteger("Red"), nbt.getInteger("Green"), nbt.getInteger("Blue"));
		rgb = color.getRGB();
		if(!nbt.getString("Block").equals("under_icon"))
			block = GameData.getBlockRegistry().getObject(nbt.getString("Block"));
		else
			underIcon = true;
	}
	
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setInteger("Red", color.getRed());
		nbt.setInteger("Green", color.getGreen());
		nbt.setInteger("Blue", color.getBlue());
		if(!underIcon)
			nbt.setString("Block", GameData.getBlockRegistry().getNameForObject(block));
		else
			nbt.setString("Block", "under_icon");
	}
	
	public int getColor()
	{
		return rgb;
	}
	
	public void setColor(Color color)
	{
		this.color = color;
		rgb = color.getRGB();
		NBTTagCompound nbt = new NBTTagCompound();
		writeToNBT(nbt);
		nbt.setInteger("Red", color.getRed());
		nbt.setInteger("Green", color.getGreen());
		nbt.setInteger("Blue", color.getBlue());
		readFromNBT(nbt);
	}
	

	public Packet getDescriptionPacket()
	{
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		this.writeToNBT(nbttagcompound);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 17, nbttagcompound);
	}

	/**
	 * Called when you receive a TileEntityData packet for the location this
	 * TileEntity is currently in. On the client, the NetworkManager will always
	 * be the remote server. On the server, it will be whomever is responsible for
	 * sending the packet.
	 *
	 * @param net The NetworkManager the packet originated from
	 * @param pkt The data packet
	 */
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
	{
		readFromNBT(pkt.func_148857_g());
	}
}
