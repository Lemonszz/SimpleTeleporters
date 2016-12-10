package teleporters.content.config;

import net.minecraftforge.common.config.Configuration;

public class ModConfig 
{
	public static int CONFIG_PARTICLE_AMT_BLOCK;
	public static int CONFIG_RECIPE_HARDNESS;
	
	public static void loadConfig(Configuration config)
	{
		config.load();
		
		CONFIG_PARTICLE_AMT_BLOCK = config.getInt("Teleporter Particle Amount", "Block", 5, 0, Integer.MAX_VALUE, "The amount of portal particles that will come out of the teleporter every display tick");
		CONFIG_RECIPE_HARDNESS = config.getInt("Teleporter Crafting Difficulty", "Crafting", 0, 0, 3, "The difficulty of crafting a teleporter block. 0 = Quartz, 1 = End Stone, 2 = Nether Star, 3 = Uncraftable");
		
		config.save();
	}
}
