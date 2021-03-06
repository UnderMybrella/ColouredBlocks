package org.abimon.mods.minecraft.colouredBlocks;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

public class UpdateThread extends Thread
{
	World w;
	int x, y, z;
	int milliseconds;
	
	public UpdateThread(World w, int x, int y, int z, int milliseconds)
	{
		this.w = w;
		this.x = x;
		this.y = y;
		this.z = z;
		this.milliseconds = milliseconds;
	}
	
	public void run()
	{
		while(true)
		{
			try
			{
				if(milliseconds == 0)
				{
					Minecraft.getMinecraft().renderGlobal.markBlockForUpdate(x, y, z);
					w.markBlockForUpdate(x, y, z);
					break;
				}
				milliseconds--;
				Thread.sleep(1);
			}
			catch(Throwable th){}
		}
	}
}
