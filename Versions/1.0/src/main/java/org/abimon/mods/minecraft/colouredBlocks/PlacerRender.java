package org.abimon.mods.minecraft.colouredBlocks;

import java.awt.Color;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.common.registry.GameData;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.BlockHopper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererChestHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.src.FMLRenderAccessLibrary;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;

public class PlacerRender implements IItemRenderer {

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) 
	{
		RenderBlocks blocks = (RenderBlocks) data[0];
		TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
        texturemanager.bindTexture(texturemanager.getResourceLocation(0));
        String block = "";
        Color color = Color.WHITE;
        int damage = 0;
        if(item.hasTagCompound())
        {
        	block = item.getTagCompound().getString("Block");
        	if(block.equals("under_icon"))
        		block = GameData.getBlockRegistry().getNameForObject(Blocks.portal);
        	damage = item.getTagCompound().getInteger("Damage");
        	color = new Color(item.getTagCompound().getInteger("Red"), item.getTagCompound().getInteger("Green"), item.getTagCompound().getInteger("Blue"));
        }
		renderBlockAsItem(blocks, GameData.getBlockRegistry().getObject(block), damage, 1.0f, color);
	}
	
    public void renderBlockAsItem(RenderBlocks blocks, Block p_147800_1_, int p_147800_2_, float p_147800_3_, Color color)
    {
        Tessellator tessellator = Tessellator.instance;
        boolean flag = p_147800_1_ == Blocks.grass;

        if (p_147800_1_ == Blocks.dispenser || p_147800_1_ == Blocks.dropper || p_147800_1_ == Blocks.furnace)
        {
            p_147800_2_ = 3;
        }

        int j;
        float f1;
        float f2;
        float f3;

        if (blocks.useInventoryTint)
        {
            if (flag)
            {
                j = 16777215;
            }

            f1 = (float)color.getRed() / 255.0F;
            f2 = (float)color.getGreen() / 255.0F;
            f3 = (float)color.getBlue() / 255.0F;
            GL11.glColor4f(f1 * p_147800_3_, f2 * p_147800_3_, f3 * p_147800_3_, 1.0F);
        }

        j = p_147800_1_.getRenderType();
        blocks.setRenderBoundsFromBlock(p_147800_1_);
        int k;

        if (j != 0 && j != 31 && j != 39 && j != 16 && j != 26)
        {
            if (j == 1)
            {
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, -1.0F, 0.0F);
                IIcon iicon = blocks.getBlockIconFromSideAndMetadata(p_147800_1_, 0, p_147800_2_);
                blocks.drawCrossedSquares(iicon, -0.5D, -0.5D, -0.5D, 1.0F);
                tessellator.draw();
            }
            else if (j == 19)
            {
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, -1.0F, 0.0F);
                p_147800_1_.setBlockBoundsForItemRender();
                blocks.renderBlockStemSmall(p_147800_1_, p_147800_2_, blocks.renderMaxY, -0.5D, -0.5D, -0.5D);
                tessellator.draw();
            }
            else if (j == 23)
            {
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, -1.0F, 0.0F);
                p_147800_1_.setBlockBoundsForItemRender();
                tessellator.draw();
            }
            else if (j == 13)
            {
                p_147800_1_.setBlockBoundsForItemRender();
                GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
                f1 = 0.0625F;
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, -1.0F, 0.0F);
                blocks.renderFaceYNeg(p_147800_1_, 0.0D, 0.0D, 0.0D, blocks.getBlockIconFromSide(p_147800_1_, 0));
                tessellator.draw();
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, 1.0F, 0.0F);
                blocks.renderFaceYPos(p_147800_1_, 0.0D, 0.0D, 0.0D, blocks.getBlockIconFromSide(p_147800_1_, 1));
                tessellator.draw();
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, 0.0F, -1.0F);
                tessellator.addTranslation(0.0F, 0.0F, f1);
                blocks.renderFaceZNeg(p_147800_1_, 0.0D, 0.0D, 0.0D, blocks.getBlockIconFromSide(p_147800_1_, 2));
                tessellator.addTranslation(0.0F, 0.0F, -f1);
                tessellator.draw();
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, 0.0F, 1.0F);
                tessellator.addTranslation(0.0F, 0.0F, -f1);
                blocks.renderFaceZPos(p_147800_1_, 0.0D, 0.0D, 0.0D, blocks.getBlockIconFromSide(p_147800_1_, 3));
                tessellator.addTranslation(0.0F, 0.0F, f1);
                tessellator.draw();
                tessellator.startDrawingQuads();
                tessellator.setNormal(-1.0F, 0.0F, 0.0F);
                tessellator.addTranslation(f1, 0.0F, 0.0F);
                blocks.renderFaceXNeg(p_147800_1_, 0.0D, 0.0D, 0.0D, blocks.getBlockIconFromSide(p_147800_1_, 4));
                tessellator.addTranslation(-f1, 0.0F, 0.0F);
                tessellator.draw();
                tessellator.startDrawingQuads();
                tessellator.setNormal(1.0F, 0.0F, 0.0F);
                tessellator.addTranslation(-f1, 0.0F, 0.0F);
                blocks.renderFaceXPos(p_147800_1_, 0.0D, 0.0D, 0.0D, blocks.getBlockIconFromSide(p_147800_1_, 5));
                tessellator.addTranslation(f1, 0.0F, 0.0F);
                tessellator.draw();
                GL11.glTranslatef(0.5F, 0.5F, 0.5F);
            }
            else if (j == 22)
            {
                GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
                GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
                TileEntityRendererChestHelper.instance.renderChest(p_147800_1_, p_147800_2_, p_147800_3_);
                GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            }
            else if (j == 6)
            {
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, -1.0F, 0.0F);
                blocks.renderBlockCropsImpl(p_147800_1_, p_147800_2_, -0.5D, -0.5D, -0.5D);
                tessellator.draw();
            }
            else if (j == 2)
            {
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, -1.0F, 0.0F);
                blocks.renderTorchAtAngle(p_147800_1_, -0.5D, -0.5D, -0.5D, 0.0D, 0.0D, 0);
                tessellator.draw();
            }
            else if (j == 10)
            {
                for (k = 0; k < 2; ++k)
                {
                    if (k == 0)
                    {
                        blocks.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.5D);
                    }

                    if (k == 1)
                    {
                        blocks.setRenderBounds(0.0D, 0.0D, 0.5D, 1.0D, 0.5D, 1.0D);
                    }

                    GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0.0F, -1.0F, 0.0F);
                    blocks.renderFaceYNeg(p_147800_1_, 0.0D, 0.0D, 0.0D, blocks.getBlockIconFromSide(p_147800_1_, 0));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0.0F, 1.0F, 0.0F);
                    blocks.renderFaceYPos(p_147800_1_, 0.0D, 0.0D, 0.0D, blocks.getBlockIconFromSide(p_147800_1_, 1));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0.0F, 0.0F, -1.0F);
                    blocks.renderFaceZNeg(p_147800_1_, 0.0D, 0.0D, 0.0D, blocks.getBlockIconFromSide(p_147800_1_, 2));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0.0F, 0.0F, 1.0F);
                    blocks.renderFaceZPos(p_147800_1_, 0.0D, 0.0D, 0.0D, blocks.getBlockIconFromSide(p_147800_1_, 3));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(-1.0F, 0.0F, 0.0F);
                    blocks.renderFaceXNeg(p_147800_1_, 0.0D, 0.0D, 0.0D, blocks.getBlockIconFromSide(p_147800_1_, 4));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(1.0F, 0.0F, 0.0F);
                    blocks.renderFaceXPos(p_147800_1_, 0.0D, 0.0D, 0.0D, blocks.getBlockIconFromSide(p_147800_1_, 5));
                    tessellator.draw();
                    GL11.glTranslatef(0.5F, 0.5F, 0.5F);
                }
            }
            else if (j == 27)
            {
                k = 0;
                GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
                tessellator.startDrawingQuads();

                for (int l = 0; l < 8; ++l)
                {
                    byte b0 = 0;
                    byte b1 = 1;

                    if (l == 0)
                    {
                        b0 = 2;
                    }

                    if (l == 1)
                    {
                        b0 = 3;
                    }

                    if (l == 2)
                    {
                        b0 = 4;
                    }

                    if (l == 3)
                    {
                        b0 = 5;
                        b1 = 2;
                    }

                    if (l == 4)
                    {
                        b0 = 6;
                        b1 = 3;
                    }

                    if (l == 5)
                    {
                        b0 = 7;
                        b1 = 5;
                    }

                    if (l == 6)
                    {
                        b0 = 6;
                        b1 = 2;
                    }

                    if (l == 7)
                    {
                        b0 = 3;
                    }

                    float f5 = (float)b0 / 16.0F;
                    float f6 = 1.0F - (float)k / 16.0F;
                    float f7 = 1.0F - (float)(k + b1) / 16.0F;
                    k += b1;
                    blocks.setRenderBounds((double)(0.5F - f5), (double)f7, (double)(0.5F - f5), (double)(0.5F + f5), (double)f6, (double)(0.5F + f5));
                    tessellator.setNormal(0.0F, -1.0F, 0.0F);
                    blocks.renderFaceYNeg(p_147800_1_, 0.0D, 0.0D, 0.0D, blocks.getBlockIconFromSide(p_147800_1_, 0));
                    tessellator.setNormal(0.0F, 1.0F, 0.0F);
                    blocks.renderFaceYPos(p_147800_1_, 0.0D, 0.0D, 0.0D, blocks.getBlockIconFromSide(p_147800_1_, 1));
                    tessellator.setNormal(0.0F, 0.0F, -1.0F);
                    blocks.renderFaceZNeg(p_147800_1_, 0.0D, 0.0D, 0.0D, blocks.getBlockIconFromSide(p_147800_1_, 2));
                    tessellator.setNormal(0.0F, 0.0F, 1.0F);
                    blocks.renderFaceZPos(p_147800_1_, 0.0D, 0.0D, 0.0D, blocks.getBlockIconFromSide(p_147800_1_, 3));
                    tessellator.setNormal(-1.0F, 0.0F, 0.0F);
                    blocks.renderFaceXNeg(p_147800_1_, 0.0D, 0.0D, 0.0D, blocks.getBlockIconFromSide(p_147800_1_, 4));
                    tessellator.setNormal(1.0F, 0.0F, 0.0F);
                    blocks.renderFaceXPos(p_147800_1_, 0.0D, 0.0D, 0.0D, blocks.getBlockIconFromSide(p_147800_1_, 5));
                }

                tessellator.draw();
                GL11.glTranslatef(0.5F, 0.5F, 0.5F);
                blocks.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
            }
            else if (j == 11)
            {
                for (k = 0; k < 4; ++k)
                {
                    f2 = 0.125F;

                    if (k == 0)
                    {
                        blocks.setRenderBounds((double)(0.5F - f2), 0.0D, 0.0D, (double)(0.5F + f2), 1.0D, (double)(f2 * 2.0F));
                    }

                    if (k == 1)
                    {
                        blocks.setRenderBounds((double)(0.5F - f2), 0.0D, (double)(1.0F - f2 * 2.0F), (double)(0.5F + f2), 1.0D, 1.0D);
                    }

                    f2 = 0.0625F;

                    if (k == 2)
                    {
                        blocks.setRenderBounds((double)(0.5F - f2), (double)(1.0F - f2 * 3.0F), (double)(-f2 * 2.0F), (double)(0.5F + f2), (double)(1.0F - f2), (double)(1.0F + f2 * 2.0F));
                    }

                    if (k == 3)
                    {
                        blocks.setRenderBounds((double)(0.5F - f2), (double)(0.5F - f2 * 3.0F), (double)(-f2 * 2.0F), (double)(0.5F + f2), (double)(0.5F - f2), (double)(1.0F + f2 * 2.0F));
                    }

                    GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0.0F, -1.0F, 0.0F);
                    blocks.renderFaceYNeg(p_147800_1_, 0.0D, 0.0D, 0.0D, blocks.getBlockIconFromSide(p_147800_1_, 0));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0.0F, 1.0F, 0.0F);
                    blocks.renderFaceYPos(p_147800_1_, 0.0D, 0.0D, 0.0D, blocks.getBlockIconFromSide(p_147800_1_, 1));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0.0F, 0.0F, -1.0F);
                    blocks.renderFaceZNeg(p_147800_1_, 0.0D, 0.0D, 0.0D, blocks.getBlockIconFromSide(p_147800_1_, 2));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0.0F, 0.0F, 1.0F);
                    blocks.renderFaceZPos(p_147800_1_, 0.0D, 0.0D, 0.0D, blocks.getBlockIconFromSide(p_147800_1_, 3));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(-1.0F, 0.0F, 0.0F);
                    blocks.renderFaceXNeg(p_147800_1_, 0.0D, 0.0D, 0.0D, blocks.getBlockIconFromSide(p_147800_1_, 4));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(1.0F, 0.0F, 0.0F);
                    blocks.renderFaceXPos(p_147800_1_, 0.0D, 0.0D, 0.0D, blocks.getBlockIconFromSide(p_147800_1_, 5));
                    tessellator.draw();
                    GL11.glTranslatef(0.5F, 0.5F, 0.5F);
                }

                blocks.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
            }
            else if (j == 21)
            {
                for (k = 0; k < 3; ++k)
                {
                    f2 = 0.0625F;

                    if (k == 0)
                    {
                        blocks.setRenderBounds((double)(0.5F - f2), 0.30000001192092896D, 0.0D, (double)(0.5F + f2), 1.0D, (double)(f2 * 2.0F));
                    }

                    if (k == 1)
                    {
                        blocks.setRenderBounds((double)(0.5F - f2), 0.30000001192092896D, (double)(1.0F - f2 * 2.0F), (double)(0.5F + f2), 1.0D, 1.0D);
                    }

                    f2 = 0.0625F;

                    if (k == 2)
                    {
                        blocks.setRenderBounds((double)(0.5F - f2), 0.5D, 0.0D, (double)(0.5F + f2), (double)(1.0F - f2), 1.0D);
                    }

                    GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0.0F, -1.0F, 0.0F);
                    blocks.renderFaceYNeg(p_147800_1_, 0.0D, 0.0D, 0.0D, blocks.getBlockIconFromSide(p_147800_1_, 0));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0.0F, 1.0F, 0.0F);
                    blocks.renderFaceYPos(p_147800_1_, 0.0D, 0.0D, 0.0D, blocks.getBlockIconFromSide(p_147800_1_, 1));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0.0F, 0.0F, -1.0F);
                    blocks.renderFaceZNeg(p_147800_1_, 0.0D, 0.0D, 0.0D, blocks.getBlockIconFromSide(p_147800_1_, 2));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0.0F, 0.0F, 1.0F);
                    blocks.renderFaceZPos(p_147800_1_, 0.0D, 0.0D, 0.0D, blocks.getBlockIconFromSide(p_147800_1_, 3));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(-1.0F, 0.0F, 0.0F);
                    blocks.renderFaceXNeg(p_147800_1_, 0.0D, 0.0D, 0.0D, blocks.getBlockIconFromSide(p_147800_1_, 4));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(1.0F, 0.0F, 0.0F);
                    blocks.renderFaceXPos(p_147800_1_, 0.0D, 0.0D, 0.0D, blocks.getBlockIconFromSide(p_147800_1_, 5));
                    tessellator.draw();
                    GL11.glTranslatef(0.5F, 0.5F, 0.5F);
                }
            }
            else if (j == 32)
            {
                for (k = 0; k < 2; ++k)
                {
                    if (k == 0)
                    {
                        blocks.setRenderBounds(0.0D, 0.0D, 0.3125D, 1.0D, 0.8125D, 0.6875D);
                    }

                    if (k == 1)
                    {
                        blocks.setRenderBounds(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D);
                    }

                    GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0.0F, -1.0F, 0.0F);
                    blocks.renderFaceYNeg(p_147800_1_, 0.0D, 0.0D, 0.0D, blocks.getBlockIconFromSideAndMetadata(p_147800_1_, 0, p_147800_2_));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0.0F, 1.0F, 0.0F);
                    blocks.renderFaceYPos(p_147800_1_, 0.0D, 0.0D, 0.0D, blocks.getBlockIconFromSideAndMetadata(p_147800_1_, 1, p_147800_2_));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0.0F, 0.0F, -1.0F);
                    blocks.renderFaceZNeg(p_147800_1_, 0.0D, 0.0D, 0.0D, blocks.getBlockIconFromSideAndMetadata(p_147800_1_, 2, p_147800_2_));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0.0F, 0.0F, 1.0F);
                    blocks.renderFaceZPos(p_147800_1_, 0.0D, 0.0D, 0.0D, blocks.getBlockIconFromSideAndMetadata(p_147800_1_, 3, p_147800_2_));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(-1.0F, 0.0F, 0.0F);
                    blocks.renderFaceXNeg(p_147800_1_, 0.0D, 0.0D, 0.0D, blocks.getBlockIconFromSideAndMetadata(p_147800_1_, 4, p_147800_2_));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(1.0F, 0.0F, 0.0F);
                    blocks.renderFaceXPos(p_147800_1_, 0.0D, 0.0D, 0.0D, blocks.getBlockIconFromSideAndMetadata(p_147800_1_, 5, p_147800_2_));
                    tessellator.draw();
                    GL11.glTranslatef(0.5F, 0.5F, 0.5F);
                }

                blocks.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
            }
            else if (j == 35)
            {
                GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
                blocks.renderBlockAnvilOrient((BlockAnvil)p_147800_1_, 0, 0, 0, p_147800_2_ << 2, true);
                GL11.glTranslatef(0.5F, 0.5F, 0.5F);
            }
            else if (j == 34)
            {
                for (k = 0; k < 3; ++k)
                {
                    if (k == 0)
                    {
                        blocks.setRenderBounds(0.125D, 0.0D, 0.125D, 0.875D, 0.1875D, 0.875D);
                        blocks.setOverrideBlockTexture(blocks.getBlockIcon(Blocks.obsidian));
                    }
                    else if (k == 1)
                    {
                        blocks.setRenderBounds(0.1875D, 0.1875D, 0.1875D, 0.8125D, 0.875D, 0.8125D);
                        blocks.setOverrideBlockTexture(blocks.getBlockIcon(Blocks.beacon));
                    }
                    else if (k == 2)
                    {
                        blocks.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
                        blocks.setOverrideBlockTexture(blocks.getBlockIcon(Blocks.glass));
                    }

                    GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0.0F, -1.0F, 0.0F);
                    blocks.renderFaceYNeg(p_147800_1_, 0.0D, 0.0D, 0.0D, blocks.getBlockIconFromSideAndMetadata(p_147800_1_, 0, p_147800_2_));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0.0F, 1.0F, 0.0F);
                    blocks.renderFaceYPos(p_147800_1_, 0.0D, 0.0D, 0.0D, blocks.getBlockIconFromSideAndMetadata(p_147800_1_, 1, p_147800_2_));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0.0F, 0.0F, -1.0F);
                    blocks.renderFaceZNeg(p_147800_1_, 0.0D, 0.0D, 0.0D, blocks.getBlockIconFromSideAndMetadata(p_147800_1_, 2, p_147800_2_));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0.0F, 0.0F, 1.0F);
                    blocks.renderFaceZPos(p_147800_1_, 0.0D, 0.0D, 0.0D, blocks.getBlockIconFromSideAndMetadata(p_147800_1_, 3, p_147800_2_));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(-1.0F, 0.0F, 0.0F);
                    blocks.renderFaceXNeg(p_147800_1_, 0.0D, 0.0D, 0.0D, blocks.getBlockIconFromSideAndMetadata(p_147800_1_, 4, p_147800_2_));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(1.0F, 0.0F, 0.0F);
                    blocks.renderFaceXPos(p_147800_1_, 0.0D, 0.0D, 0.0D, blocks.getBlockIconFromSideAndMetadata(p_147800_1_, 5, p_147800_2_));
                    tessellator.draw();
                    GL11.glTranslatef(0.5F, 0.5F, 0.5F);
                }

                blocks.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
                blocks.clearOverrideBlockTexture();
            }
            else if (j == 38)
            {
                GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
                blocks.renderBlockHopperMetadata((BlockHopper)p_147800_1_, 0, 0, 0, 0, true);
                GL11.glTranslatef(0.5F, 0.5F, 0.5F);
            }
            else
            {
                FMLRenderAccessLibrary.renderInventoryBlock(blocks, p_147800_1_, p_147800_2_, j);
            }
        }
        else
        {
            if (j == 16)
            {
                p_147800_2_ = 1;
            }

            p_147800_1_.setBlockBoundsForItemRender();
            blocks.setRenderBoundsFromBlock(p_147800_1_);
            GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, -1.0F, 0.0F);
            blocks.renderFaceYNeg(p_147800_1_, 0.0D, 0.0D, 0.0D, blocks.getBlockIconFromSideAndMetadata(p_147800_1_, 0, p_147800_2_));
            tessellator.draw();

            if (flag && blocks.useInventoryTint)
            {
                k = p_147800_1_.getRenderColor(p_147800_2_);
                f2 = (float)(k >> 16 & 255) / 255.0F;
                f3 = (float)(k >> 8 & 255) / 255.0F;
                float f4 = (float)(k & 255) / 255.0F;
                GL11.glColor4f(f2 * p_147800_3_, f3 * p_147800_3_, f4 * p_147800_3_, 1.0F);
            }

            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, 1.0F, 0.0F);
            blocks.renderFaceYPos(p_147800_1_, 0.0D, 0.0D, 0.0D, blocks.getBlockIconFromSideAndMetadata(p_147800_1_, 1, p_147800_2_));
            tessellator.draw();

            if (flag && blocks.useInventoryTint)
            {
                GL11.glColor4f(p_147800_3_, p_147800_3_, p_147800_3_, 1.0F);
            }

            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, 0.0F, -1.0F);
            blocks.renderFaceZNeg(p_147800_1_, 0.0D, 0.0D, 0.0D, blocks.getBlockIconFromSideAndMetadata(p_147800_1_, 2, p_147800_2_));
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, 0.0F, 1.0F);
            blocks.renderFaceZPos(p_147800_1_, 0.0D, 0.0D, 0.0D, blocks.getBlockIconFromSideAndMetadata(p_147800_1_, 3, p_147800_2_));
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(-1.0F, 0.0F, 0.0F);
            blocks.renderFaceXNeg(p_147800_1_, 0.0D, 0.0D, 0.0D, blocks.getBlockIconFromSideAndMetadata(p_147800_1_, 4, p_147800_2_));
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(1.0F, 0.0F, 0.0F);
            blocks.renderFaceXPos(p_147800_1_, 0.0D, 0.0D, 0.0D, blocks.getBlockIconFromSideAndMetadata(p_147800_1_, 5, p_147800_2_));
            tessellator.draw();
            GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        }
    }


}
