package org.abimon.mods.minecraft.colouredBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.BlockBeacon;
import net.minecraft.block.BlockBrewingStand;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.BlockCocoa;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockDragonEgg;
import net.minecraft.block.BlockEndPortalFrame;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockFlowerPot;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockRedstoneComparator;
import net.minecraft.block.BlockRedstoneDiode;
import net.minecraft.block.BlockRedstoneRepeater;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockWall;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class ColouredRenderer implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId,
			RenderBlocks renderer) 
	{}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		if(getRenderId() != modelId)
			return false;
		else
		{
			if(!(world.getTileEntity(x, y, z) instanceof TileEntityColoured))
				return false;
			int renderID = ((TileEntityColoured) world.getTileEntity(x, y, z)).block.getRenderType();
			boolean shouldRenderAsSpecial = shouldRenderAsSpecial(renderID);
			if(shouldRenderAsSpecial || !((TileEntityColoured) world.getTileEntity(x, y, z)).underIcon)
				renderBlock(renderer, block, x, y, z, renderID);
			else
				if(((TileEntityColoured) world.getTileEntity(x, y, z)).underIcon)
					renderer.renderStandardBlock(Blocks.portal, x, y, z);
				renderer.renderStandardBlock(block, x, y, z);
			return true;
		}
	}

	private boolean renderBlock(RenderBlocks renderer, Block block, int x, int y,
			int z, int renderID) {
		switch(renderID){
		case 0 : return renderer.renderStandardBlock(block, x, y, z) ;
		case 4: return renderer.renderBlockLiquid(block, x, y, z) ;
		case 31: return renderer.renderBlockLog(block, x, y, z) ;
		case 1: return renderer.renderCrossedSquares(block, x, y, z) ;
		case 40: return renderer.renderBlockDoublePlant((BlockDoublePlant)block, x, y, z) ;
		case 2: return renderer.renderBlockTorch(block, x, y, z) ;
		case 20: return renderer.renderBlockVine(block, x, y, z) ;
		case 11: return renderer.renderBlockFence((BlockFence)block, x, y, z) ;
		case 39: return renderer.renderBlockQuartz(block, x, y, z) ;
		case 5: return renderer.renderBlockRedstoneWire(block, x, y, z) ;
		case 13: return renderer.renderBlockCactus(block, x, y, z) ;
		case 9: return renderer.renderBlockMinecartTrack((BlockRailBase)block, x, y, z) ;
		case 19: return renderer.renderBlockStem(block, x, y, z) ;
		case 23: return renderer.renderBlockLilyPad(block, x, y, z) ;
		case 6: return renderer.renderBlockCrops(block, x, y, z) ;
		case 3: return renderer.renderBlockFire((BlockFire)block, x, y, z) ;
		case 8: return renderer.renderBlockLadder(block, x, y, z) ;
		case 7: return renderer.renderBlockDoor(block, x, y, z) ;
		case 10: return renderer.renderBlockStairs((BlockStairs)block, x, y, z) ;
		case 27: return renderer.renderBlockDragonEgg((BlockDragonEgg)block, x, y, z) ;
		case 32: return renderer.renderBlockWall((BlockWall)block, x, y, z) ;
		case 12: return renderer.renderBlockLever(block, x, y, z) ;
		case 29: return renderer.renderBlockTripWireSource(block, x, y, z) ;
		case 30: return renderer.renderBlockTripWire(block, x, y, z) ;
		case 14: return renderer.renderBlockBed(block, x, y, z) ;
		case 15: return renderer.renderBlockRepeater((BlockRedstoneRepeater)block, x, y, z) ;
		case 36: return renderer.renderBlockRedstoneDiode((BlockRedstoneDiode)block, x, y, z) ;
		case 37: return renderer.renderBlockRedstoneComparator((BlockRedstoneComparator)block, x, y, z) ;
		case 16: return renderer.renderPistonBase(block, x, y, z, false) ;
		case 17: return renderer.renderPistonExtension(block, x, y, z, true) ;
		case 18: return renderer.renderBlockPane((BlockPane)block, x, y, z) ;
		case 41: return renderer.renderBlockStainedGlassPane(block, x, y, z) ;
		case 21: return renderer.renderBlockFenceGate((BlockFenceGate)block, x, y, z) ;
		case 24: return renderer.renderBlockCauldron((BlockCauldron)block, x, y, z) ;
		case 33: return renderer.renderBlockFlowerpot((BlockFlowerPot)block, x, y, z) ;
		case 35: return renderer.renderBlockAnvil((BlockAnvil)block, x, y, z) ;
		case 25: return renderer.renderBlockBrewingStand((BlockBrewingStand)block, x, y, z) ;
		case 26: return renderer.renderBlockEndPortalFrame((BlockEndPortalFrame)block, x, y, z) ;
		case 28: return renderer.renderBlockCocoa((BlockCocoa)block, x, y, z) ;
		case 34: return renderer.renderBlockBeacon((BlockBeacon)block, x, y, z) ;
		case 38: return renderer.renderBlockHopper((BlockHopper)block, x, y, z);
		}
		return false;
	}

	public boolean shouldRenderAsSpecial(int renderID) 
	{
		switch(renderID)
		{
		case 40:
			return false;
		case 11:
			return false;
		case 9:
			return false;
		case 3:
			return false;
		case 10:
			return false;
		case 27:
			return false;
		case 32:
			return false;
		case 15:
			return false;
		case 36:
			return false;
		case 37:
			return false;
		case 18:
			return false;
		case 21:
			return false;
		case 24:
			return false;
		case 35:
			return false;
		case 33:
			return false;
		case 25:
			return false;
		case 26:
			return false;
		case 28:
			return false;
		case 34:
			return false;
		case 38:
			return false;
		}
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return false;
	}

	@Override
	public int getRenderId() {
		return ColouredBlocks.RENDER;
	}

}
