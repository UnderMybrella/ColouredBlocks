package org.abimon.mods.minecraft.colouredBlocks;

import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid = ColouredBlocks.MODID, version = ColouredBlocks.VERSION)
public class ColouredBlocks
{
	public static final String MODID = "colouredblocks";
	public static final String VERSION = "1.0";

	public static final CreativeTabs tabColoured  = new CreativeTabs(CreativeTabs.getNextID(), MODID + ":creativeTab")
	{
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem()
		{
			return ColouredBlocks.crayon;
		}
	};

	public static Block colouredBlock = new BlockColoured();
	public static Block dyeBlock = new BlockDye().setCreativeTab(tabColoured);
	public static Item colouredPlacer = new ItemColouredPlacer().setCreativeTab(tabColoured).setHasSubtypes(true).setUnlocalizedName(MODID + ":colouredPlacer");
	public static Item colouringEssense = new ItemColouringEssense().setCreativeTab(tabColoured).setUnlocalizedName(MODID + ":essense");
	public static Item crayon = new ItemCrayon().setCreativeTab(tabColoured).setUnlocalizedName(MODID + ":crayon");
	public static int RENDER = 33;
	public static boolean doRareNames = true;
	public static boolean useEightBlocks = true;
	public static boolean transferColourToBlock = false;

	public static final HashMap<String, Integer[]> itemstackToColourAdditions = new HashMap<String, Integer[]>();
	/** Only used by Block of Black Dye's at the moment */
	public static final HashMap<String, Integer> itemstackToIntensity = new HashMap<String, Integer>();

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		GameRegistry.registerTileEntity(TileEntityColoured.class, "teColoured");
		GameRegistry.registerBlock(colouredBlock, "colouredBlock");
		GameRegistry.registerBlock(dyeBlock, ItemBlockDye.class, "dyeBlock");
		GameRegistry.registerItem(crayon, "crayon");
		GameRegistry.registerItem(colouredPlacer, "colouredPlacer");
		GameRegistry.registerItem(colouringEssense, "essense");
		MinecraftForgeClient.registerItemRenderer(colouredPlacer, new PlacerRender());
		RENDER = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(RENDER, new ColouredRenderer());

		File config = new File("config" + File.separatorChar + "ColouredBlocks.json");
		if(!config.exists())
		{
			JSONObject json = new JSONObject();
			json.put("enableRareNames", true);
			JSONObject colours = new JSONObject();
			JSONObject array = new JSONObject();
			array.put("Red", 8);
			array.put("Green", 0);
			array.put("Blue", 0);
			colours.put(represent(new ItemStack(Items.dye, 1, 1)), array);
			array = new JSONObject();
			array.put("Red", 0);
			array.put("Green", 8);
			array.put("Blue", 0);
			colours.put(represent(new ItemStack(Items.dye, 1, 2)), array);
			array = new JSONObject();
			array.put("Red", 0);
			array.put("Green", 0);
			array.put("Blue", 8);
			colours.put(represent(new ItemStack(Items.dye, 1, 4)), array);
			array = new JSONObject();
			array.put("Red", 1);
			array.put("Green", 0);
			array.put("Blue", 0);
			colours.put(represent(new ItemStack(Items.dye, 1, 14)), array);
			array = new JSONObject();
			array.put("Red", 0);
			array.put("Green", 1);
			array.put("Blue", 0);
			colours.put(represent(new ItemStack(Items.dye, 1, 10)), array);
			array = new JSONObject();
			array.put("Red", 0);
			array.put("Green", 0);
			array.put("Blue", 1);
			colours.put(represent(new ItemStack(Items.dye, 1, 6)), array);
			array = new JSONObject();
			array.put("Red", 0);
			array.put("Green", 0);
			array.put("Blue", 0);
			colours.put(represent(new ItemStack(Items.dye, 1, 0)), array);
			array = new JSONObject();
			array.put("Red", 64);
			array.put("Green", 0);
			array.put("Blue", 0);
			colours.put(represent(new ItemStack(dyeBlock, 1, 1)), array);
			array = new JSONObject();
			array.put("Red", 0);
			array.put("Green", 64);
			array.put("Blue", 0);
			colours.put(represent(new ItemStack(dyeBlock, 1, 2)), array);
			array = new JSONObject();
			array.put("Red", 0);
			array.put("Green", 0);
			array.put("Blue", 64);
			colours.put(represent(new ItemStack(dyeBlock, 1, 4)), array);
			array = new JSONObject();
			array.put("Red", 8);
			array.put("Green", 0);
			array.put("Blue", 0);
			colours.put(represent(new ItemStack(dyeBlock, 1, 14)), array);
			array = new JSONObject();
			array.put("Red", 0);
			array.put("Green", 8);
			array.put("Blue", 0);
			colours.put(represent(new ItemStack(dyeBlock, 1, 10)), array);
			array = new JSONObject();
			array.put("Red", 0);
			array.put("Green", 0);
			array.put("Blue", 8);
			colours.put(represent(new ItemStack(dyeBlock, 1, 6)), array);
			array = new JSONObject();
			array.put("Red", 0);
			array.put("Green", 0);
			array.put("Blue", 0);
			colours.put(represent(new ItemStack(dyeBlock, 1, 0)), array);
			json.put("colourAddition", colours);
			
			JSONObject intensity = new JSONObject();
			intensity.put(represent(new ItemStack(dyeBlock, 1, 0)), 8);
			json.put("intensity", intensity);
			
			json.put("useEightBlocks", true);
			json.put("transferColourToBlock", false);

			try
			{
				DataOutputStream dos = new DataOutputStream(new FileOutputStream(config));
				dos.write(outputNicely(json).getBytes());
				dos.close();
			}
			catch(Exception e){
				LogManager.getLogger().error("It would appear that we at " + MODID + ", for whatever reason, cannot load the config file. Please note that some features may be disabled as a result");
				String s = e.getLocalizedMessage();
				for(StackTraceElement ste : e.getStackTrace())
					s += "\n\t" + ste.toString();
				LogManager.getLogger().error("Error Message: " + s);
			}
		}
		
		try
		{
			DataInputStream dis = new DataInputStream(new FileInputStream(config));
			byte[] data = new byte[dis.available()];
			dis.readFully(data);
			dis.close();
			
			JSONObject json = (JSONObject) JSONValue.parse(new String(data));
			doRareNames = (Boolean) json.get("enableRareNames");
			
			JSONObject colours = (JSONObject) json.get("colourAddition");
			for(Object o : colours.keySet())
			{
				JSONObject array = (JSONObject) colours.get(o);
				long red = (Long) array.get("Red");
				long green = (Long) array.get("Green");
				long blue = (Long) array.get("Blue");
				
				registerStringWithColouring((String) o, (int) red, (int) green, (int) blue);
			}
			
			JSONObject intensities = (JSONObject) json.get("intensity");
			for(Object o : intensities.keySet())
				itemstackToIntensity.put((String) o, ((Long) intensities.get(o)).intValue());
			useEightBlocks = (Boolean) json.get("useEightBlocks");
			transferColourToBlock = (Boolean) json.get("transferColourToBlock");
		}
		catch(Exception e)
		{
			LogManager.getLogger().error("It would appear that we at " + MODID + ", for whatever reason, cannot load the config file. Please note that some features may be disabled as a result");
			String s = e.getLocalizedMessage();
			for(StackTraceElement ste : e.getStackTrace())
				s += "\n\t" + ste.toString();
			LogManager.getLogger().error("Error Message: " + s);
		}
		
		CraftingManager.getInstance().getRecipeList().add(new ColouredRecipes());
		for(int i = 0; i < 16; i++)
			CraftingManager.getInstance().addRecipe(new ItemStack(dyeBlock, 1, i), new Object[]{"DDD", "DQD", "DDD", 'D', new ItemStack(Items.dye, 1, i), 'Q', Blocks.quartz_block});
		System.out.println(itemstackToColourAdditions);
	}

	public static String outputNicely(JSONObject json)
	{
		String jString = json.toJSONString();
		String nicely = "";
		int level = 0;
		boolean inStatementSingle = false;
		boolean inStatementDouble = false;
		for(char c : jString.toCharArray())
		{
			if(c == '"')
				inStatementDouble = !inStatementDouble;
			if(c == '\'')
				inStatementSingle = !inStatementSingle;
			if(inStatementSingle || inStatementDouble)
			{
				nicely += c;
				continue;
			}
			if(c == '{')
				level++;
			if(c == '}')
			{
				level--;
				nicely += "\n";
				for(int i = 0; i < level; i++)
					nicely += "\t";
			}
			nicely += c;
			if(c == '{'|| c == ',')
			{
				nicely += "\n";

				for(int i = 0; i < level; i++)
					nicely += "\t";
			}
		}
		return nicely;
	}

	public static void registerItemstackWithColouring(ItemStack item, int redAddition, int greenAddition, int blueAddition)
	{
		if(item.getItem() instanceof IColourable)
			return;
		itemstackToColourAdditions.put(represent(item), new Integer[]{redAddition, greenAddition, blueAddition});
	}

	public static void registerStringWithColouring(String item, int redAddition, int greenAddition, int blueAddition)
	{
		itemstackToColourAdditions.put(item, new Integer[]{redAddition, greenAddition, blueAddition});
	}

	public static String represent(ItemStack item)
	{
		if(item == null)
			return "";
		String s = "Name:" + item.getDisplayName() + ", Damage:" + item.getItemDamage() + ", NBT:" + item.getTagCompound();
		return s;
	}
}
