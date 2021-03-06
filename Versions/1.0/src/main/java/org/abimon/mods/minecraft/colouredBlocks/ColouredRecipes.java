package org.abimon.mods.minecraft.colouredBlocks;

import java.awt.Color;
import java.util.Arrays;
import java.util.LinkedList;

import cpw.mods.fml.common.registry.GameData;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ColouredRecipes implements IRecipe 
{

	public static final ItemStack[][] ESSENSE_RECIPE = new ItemStack[][]{{new ItemStack(Items.dye), new ItemStack(Items.dye), new ItemStack(Items.dye)}, 
		{new ItemStack(Items.dye), new ItemStack(Items.sugar), new ItemStack(Items.dye)},
		{new ItemStack(Items.dye), new ItemStack(Items.dye), new ItemStack(Items.dye)}};

	public static final ItemStack[][] CRAYON_RECIPE = new ItemStack[][]{
		{new ItemStack(ColouredBlocks.colouringEssense), null, null},
		{null, new ItemStack(Items.iron_ingot), null},
		{null, null, new ItemStack(Items.stick)}
	};

	public static final ItemStack[][] CRAYON_RECIPE_FLIPPED = new ItemStack[][]{
		{null, null, new ItemStack(ColouredBlocks.colouringEssense)},
		{null, new ItemStack(Items.iron_ingot), null},
		{new ItemStack(Items.stick), null, null}
	};
	
	public static final ItemStack[][] UNDER_COLOURED_BLOCK = new ItemStack[][]{
		{new ItemStack(ColouredBlocks.colouringEssense), new ItemStack(ColouredBlocks.colouringEssense), new ItemStack(ColouredBlocks.colouringEssense)},
		{new ItemStack(ColouredBlocks.colouringEssense), new ItemStack(Items.iron_ingot), new ItemStack(ColouredBlocks.colouringEssense)},
		{new ItemStack(ColouredBlocks.colouringEssense), new ItemStack(ColouredBlocks.colouringEssense), new ItemStack(ColouredBlocks.colouringEssense)}
	};

	@Override
	public boolean matches(InventoryCrafting var1, World var2) 
	{
		return getCraftingResult(var1) != null;
	}

	public boolean checkRecipe(ItemStack[][] items, ItemStack[][] recipe, boolean ignoreDamage, boolean ignoreNBT)
	{
		for(int x = 0; x < 3; x++)
			for(int y = 0; y < 3; y++)
			{
				ItemStack item = items[x][y];
				ItemStack recipeItem = recipe[x][y];
				if(item == null)
					if(recipeItem == null)
						continue;
					else
						return false;
				if(recipeItem == null)
					return false;
				boolean sameItem = item.getItem() == recipeItem.getItem();
				boolean sameDamage = item.getItemDamage() == recipeItem.getItemDamage() || ignoreDamage;
				boolean sameNBT = (item.hasTagCompound() ? (item.getTagCompound() + "") : "").equalsIgnoreCase(recipeItem.hasTagCompound() ? (recipeItem.getTagCompound() + "") : "") || ignoreNBT;
				if(sameItem && sameDamage && sameNBT)
					continue;
				else
					return false;
			}
		return true;
	}

	public ItemStack[][] getItems(InventoryCrafting var1)
	{
		ItemStack[][] items = new ItemStack[3][3];
		for(int x = 0; x < 3; x++)
			for(int y = 0; y < 3; y++)
				items[x][y] = var1.getStackInRowAndColumn(x, y);
		return items;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting var1) 
	{
		ItemStack[][] items = getItems(var1);

		boolean essense = checkRecipe(items, ESSENSE_RECIPE, true, true);
		boolean crayon = checkRecipe(items, CRAYON_RECIPE, true, true);
		boolean crayonFlipped = checkRecipe(items, CRAYON_RECIPE_FLIPPED, true, true);
		boolean underColoured = checkRecipe(items, UNDER_COLOURED_BLOCK, true, true);
		
		ItemStack returning = null;

		returning = getEssenseChange(items);
		if(returning != null)
			return returning;
		returning = getCrayonColour(items);
		if(returning != null)
			return returning;
		returning = getEightEssense(items);
		if(returning != null && !ColouredBlocks.useEightBlocks)
			return returning;
		returning = getEightResourceBlocks(items);
		if(returning != null && ColouredBlocks.useEightBlocks)
			return returning;

		if(essense)
		{
			returning = new ItemStack(ColouredBlocks.colouringEssense);
			returning.setTagCompound(new NBTTagCompound());
			returning.getTagCompound().setInteger("Red", 0);
			returning.getTagCompound().setInteger("Green", 0);
			returning.getTagCompound().setInteger("Blue", 0);
			returning.getTagCompound().setInteger("RGB", Color.BLACK.getRGB());
		}

		if(crayon || crayonFlipped)
		{
			ItemStack item = items[0][0];
			if(crayonFlipped)
				item = items[2][0];
			NBTTagCompound nbt = item.getTagCompound();
			returning = new ItemStack(ColouredBlocks.crayon);
			returning.setTagCompound(nbt);
		}
		
		if(underColoured)
		{
			returning = new ItemStack(ColouredBlocks.colouredPlacer);

			returning.setTagCompound(new NBTTagCompound());
			
			returning.getTagCompound().setInteger("Red", 255);
			returning.getTagCompound().setInteger("Green", 255);
			returning.getTagCompound().setInteger("Blue", 255);
			returning.getTagCompound().setInteger("RGB", Color.WHITE.getRGB());
			returning.getTagCompound().setString("Block", "under_icon");
		}
		return returning;
	}
	
	public ItemStack getEightEssense(ItemStack[][] items)
	{
		ItemStack returning = null;
		LinkedList<ItemStack> block = new LinkedList<ItemStack>();
		ItemStack colouring = null;

		boolean breaking = false;

		for(int x = 0; x < 3; x++)
		{
			for(int y = 0; y < 3; y++)
			{
				if(items[x][y] == null)
					return null;
				if(x == 1 && y == 1)
					if(items[x][y].getItem() instanceof ItemBlock)
						colouring = items[x][y];
					else;
				else
				{
					if(items[x][y].getItem() == ColouredBlocks.colouringEssense)
							block.add(items[x][y]);
					else
					{
						colouring = null;
						block = null;
						breaking = true;
						break;
					}

				}
			}
			if(breaking)
				break;
		}

		if(colouring != null && block.size() == 8)
		{
			returning = new ItemStack(ColouredBlocks.colouredPlacer, 8);
			returning.setTagCompound(new NBTTagCompound());
			if(!ColouredBlocks.transferColourToBlock)
			{
				returning.getTagCompound().setInteger("Red", 255);
				returning.getTagCompound().setInteger("Green", 255);
				returning.getTagCompound().setInteger("Blue", 255);
				returning.getTagCompound().setInteger("RGB", Color.WHITE.getRGB());
			}
			else
			{
				int red = 0;
				int green = 0;
				int blue = 0;
				
				for(ItemStack item : block)
				{
					NBTTagCompound nbt = item.getTagCompound();
					red += nbt.getInteger("Red");
					green += nbt.getInteger("Green");
					blue += nbt.getInteger("Blue");
				}
				
				red /= 8;
				green /= 8;
				blue /= 8;
				

				returning.getTagCompound().setInteger("Red", red);
				returning.getTagCompound().setInteger("Green", green);
				returning.getTagCompound().setInteger("Blue", blue);
				returning.getTagCompound().setInteger("RGB", new Color(red, green, blue).getRGB());
			}
			returning.getTagCompound().setString("Block", GameData.getBlockRegistry().getNameForObject(((ItemBlock) colouring.getItem()).field_150939_a));
			returning.getTagCompound().setInteger("Damage", colouring.getItemDamage());
		}

		return returning;
	}

	public ItemStack getEightResourceBlocks(ItemStack[][] items)
	{
		ItemStack returning = null;
		ItemStack block = null;
		ItemStack colouring = null;

		boolean breaking = false;

		for(int x = 0; x < 3; x++)
		{
			for(int y = 0; y < 3; y++)
			{
				if(items[x][y] == null)
					return null;
				if(x == 1 && y == 1)
					if(items[x][y].getItem() == ColouredBlocks.colouringEssense)
						colouring = items[x][y];
					else;
				else
				{
					if(items[x][y].getItem() instanceof ItemBlock)
						if(block != null)
							if(!(ColouredBlocks.represent(items[x][y]).equals(ColouredBlocks.represent(block))))
							{
								colouring = null;
								block = null;
								breaking = true;
								break;
							}
							else
								continue;
						else
							block = items[x][y];
					else
					{
						System.out.println(items[x][y].getItem());
						colouring = null;
						block = null;
						breaking = true;
						break;
					}

				}
			}
			if(breaking)
				break;
		}

		if(colouring != null && block != null)
		{
			returning = new ItemStack(ColouredBlocks.colouredPlacer, 8);
			returning.setTagCompound(new NBTTagCompound());
			if(!ColouredBlocks.transferColourToBlock)
			{
				returning.getTagCompound().setInteger("Red", 255);
				returning.getTagCompound().setInteger("Green", 255);
				returning.getTagCompound().setInteger("Blue", 255);
				returning.getTagCompound().setInteger("RGB", Color.WHITE.getRGB());
			}
			else
			{
				NBTTagCompound nbt = colouring.getTagCompound();
				returning.getTagCompound().setInteger("Red", nbt.getInteger("Red"));
				returning.getTagCompound().setInteger("Green", nbt.getInteger("Green"));
				returning.getTagCompound().setInteger("Blue", nbt.getInteger("Blue"));
				returning.getTagCompound().setInteger("RGB", nbt.getInteger("RGB"));
			}
			returning.getTagCompound().setString("Block", GameData.getBlockRegistry().getNameForObject(((ItemBlock) block.getItem()).field_150939_a));
			returning.getTagCompound().setInteger("Damage", block.getItemDamage());
		}

		return returning;
	}

	public ItemStack getEssenseChange(ItemStack[][] items)
	{
		boolean breaking = false;
		ItemStack returning = null;
		ItemStack colouring = null;
		int redChange = 0;
		int greenChange = 0;
		int blueChange = 0;

		boolean invert = false;

		int dyeUsed = 0;

		for(ItemStack[] itemStacks : items)
		{
			for(ItemStack item : itemStacks)
			{
				if(item == null)
					continue;
				if(item.getItem() == ColouredBlocks.colouringEssense)
					if(colouring == null)
					{
						colouring = item;
						continue;
					}
					else
					{
						colouring = null;
						breaking = true;
						break;
					}
				Integer[] changes = ColouredBlocks.itemstackToColourAdditions.get(ColouredBlocks.represent(item));
				System.out.println(ColouredBlocks.itemstackToIntensity + ", " + item);
				Integer intensity = ColouredBlocks.itemstackToIntensity.get(ColouredBlocks.represent(item));

				if(changes == null)
				{
					colouring = null;
					breaking = true;
					break;
				}
				redChange += changes[0];
				greenChange += changes[1];
				blueChange += changes[2];
				if(changes[0] == 0 && changes[1] == 0 && changes[2] == 0)
					invert = true;
				if(ColouredBlocks.itemstackToIntensity.containsKey(ColouredBlocks.represent(item)))
					dyeUsed += intensity;
				else
					dyeUsed++;
			}
			if(breaking)
				break;
		}

		if(invert)
		{
			redChange *= -1;
			greenChange *= -1;
			blueChange *= -1;
		}

		breaking = false;

		if(colouring != null)
		{
			returning = new ItemStack(ColouredBlocks.colouringEssense);
			returning.setTagCompound(new NBTTagCompound());
			NBTTagCompound nbt = colouring.getTagCompound();
			int oldRed = nbt.getInteger("Red");
			int oldGreen = nbt.getInteger("Green");
			int oldBlue = nbt.getInteger("Blue");
			int red = oldRed + redChange;
			int green = oldGreen + greenChange;
			int blue = oldBlue + blueChange;
			if(redChange == 0 && greenChange == 0 && blueChange == 0)
				if(invert)
				{
					red -= dyeUsed;
					green -= dyeUsed;
					blue -= dyeUsed;
				}
				else
				{
					red += dyeUsed;
					green += dyeUsed;
					blue += dyeUsed;
				}
			if(red > 255)
				red = 255;
			if(red < 0)
				red = 0;
			if(green > 255)
				green = 255;
			if(green < 0)
				green = 0;			
			if(blue > 255)
				blue = 255;
			if(blue < 0)
				blue = 0;

			returning.getTagCompound().setInteger("Red", red);
			returning.getTagCompound().setInteger("Green", green);
			returning.getTagCompound().setInteger("Blue", blue);
			returning.getTagCompound().setInteger("RGB", new Color(red, green, blue).getRGB());
		}
		return returning;
	}

	public ItemStack getCrayonColour(ItemStack[][] items)
	{
		boolean breaking = false;
		ItemStack returning = null;
		ItemStack colouring = null;
		ItemStack coloured = null;

		for(ItemStack[] itemStacks : items)
		{
			for(ItemStack item : itemStacks)
			{
				if(item == null)
					continue;
				if(item.getItem() == ColouredBlocks.colouringEssense)
					if(coloured == null)
					{
						coloured = item;
						continue;
					}
					else
					{
						coloured = null;
						breaking = true;
						break;
					}

				if(item.getItem() == ColouredBlocks.crayon)
					if(colouring == null)
					{
						colouring = item;
						continue;
					}
					else
					{
						colouring = null;
						breaking = true;
						break;
					}
				colouring = null;
				breaking = true;
				break;
			}
			if(breaking)
				break;
		}

		if(colouring != null && coloured != null)
		{
			returning = new ItemStack(ColouredBlocks.crayon);
			returning.setTagCompound(coloured.getTagCompound());
		}
		return returning;
	}


	@Override
	public int getRecipeSize() {
		return 9;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return new ItemStack(Blocks.diamond_block, 64, 0);
	}

}
