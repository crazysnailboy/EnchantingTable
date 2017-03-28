package net.crazysnailboy.mods.enchantingtable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.crazysnailboy.mods.enchantingtable.common.network.GuiHandler;
import net.crazysnailboy.mods.enchantingtable.tileentity.TileEntityEnchantmentTable;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = EnchantingTable.MODID, version = EnchantingTable.VERSION, updateJSON = EnchantingTable.UPDATEJSON, acceptedMinecraftVersions = "[1.10.2]")
public class EnchantingTable
{
	public static final String MODID = "csb_ench_table";
	public static final String VERSION = "${version}";
	public static final String UPDATEJSON = "https://raw.githubusercontent.com/crazysnailboy/EnchantingTable/master/update.json";

	@Instance(MODID)
	public static EnchantingTable INSTANCE;

	public static final Logger LOGGER = LogManager.getLogger(MODID);


	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		GameRegistry.registerTileEntity(TileEntityEnchantmentTable.class, "TileEntityEnchantmentTable");
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
	}


	@Mod.EventBusSubscriber
	public static class EventHandlers
	{
		@SubscribeEvent
		public static void onPlayerRightClickEnchantingTable(PlayerInteractEvent.RightClickBlock event)
		{
			// get the world and the position of the block that was clicked
			World world = event.getWorld();
			BlockPos pos = event.getPos();

			// if the block being clicked is an enchanting table
			if (event.getHand() == EnumHand.MAIN_HAND && world.getBlockState(pos).getBlock() == Blocks.ENCHANTING_TABLE)
			{
				// replace the vanilla enchanting table tile entity with our custom one, if we haven't done so already for this enchanting table
				TileEntity tileEntity = world.getTileEntity(pos);
				if (!(tileEntity instanceof TileEntityEnchantmentTable))
				{
					tileEntity = new TileEntityEnchantmentTable();
					world.setTileEntity(pos, tileEntity);
				}

				// cancel the click event
				event.setCanceled(true);

				// open the gui for the enhanced enchantment table
				FMLNetworkHandler.openGui(event.getEntityPlayer(), EnchantingTable.INSTANCE, GuiHandler.GUI_ENCHANTING_TABLE, world, pos.getX(), pos.getY(), pos.getZ());

			}

		}
	}

}
