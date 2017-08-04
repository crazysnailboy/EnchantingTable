package net.crazysnailboy.mods.enchantingtable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.crazysnailboy.mods.enchantingtable.common.network.GuiHandler;
import net.crazysnailboy.mods.enchantingtable.proxy.CommonProxy;
import net.crazysnailboy.mods.enchantingtable.tileentity.TileEntityEnchantmentTable;
import net.minecraft.block.BlockEnchantmentTable;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;


@Mod(modid = EnchantingTable.MODID, version = EnchantingTable.VERSION, name = EnchantingTable.NAME, updateJSON = EnchantingTable.UPDATEJSON, acceptedMinecraftVersions = "[1.11,1.11.2]")
public class EnchantingTable
{

	public static final String MODID = "csb_ench_table";
	public static final String NAME = "Lapis Stays in the Enchanting Table";
	public static final String VERSION = "${version}";
	public static final String UPDATEJSON = "https://raw.githubusercontent.com/crazysnailboy/EnchantingTable/master/update.json";

	private static final String CLIENT_PROXY_CLASS = "net.crazysnailboy.mods.enchantingtable.proxy.ClientProxy";
	private static final String SERVER_PROXY_CLASS = "net.crazysnailboy.mods.enchantingtable.proxy.CommonProxy";


	@Instance(MODID)
	public static EnchantingTable INSTANCE;

	@SidedProxy(clientSide = CLIENT_PROXY_CLASS, serverSide = SERVER_PROXY_CLASS)
	public static CommonProxy proxy;

	public static final Logger LOGGER = LogManager.getLogger(MODID);


	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		proxy.preInit();
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		proxy.init();
	}

	@EventHandler
	public static void postInit(FMLPostInitializationEvent event)
	{
		proxy.postInit();
	}


	@EventBusSubscriber
	public static class EventHandlers
	{

		@SubscribeEvent
		public static void onPlayerBreaksEnchantingTable(BlockEvent.BreakEvent event)
		{
			// get the world and the position of the block being broken
			World world = event.getWorld();
			BlockPos pos = event.getPos();

			// if the block is an enchanting table...
			if (world.getBlockState(pos).getBlock() instanceof BlockEnchantmentTable)
			{
				// if the tile entity at the current position is a custom one...
				TileEntity tileentity = world.getTileEntity(pos);
				if (world.getTileEntity(pos) instanceof TileEntityEnchantmentTable)
				{
					// get the item handler associated with the tile entity
					TileEntityEnchantmentTable tileentityenchantmenttable = (TileEntityEnchantmentTable)tileentity;
					IItemHandler handler = tileentityenchantmenttable.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

					// if there's lapis in the item handler...
					ItemStack lapisStack = handler.getStackInSlot(0);
					if (!lapisStack.isEmpty())
					{
						// spawn it in the world
						lapisStack = lapisStack.copy();
						InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), lapisStack);
					}
				}
			}
		}


		@SubscribeEvent
		public static void onPlayerRightClickEnchantingTable(PlayerInteractEvent.RightClickBlock event)
		{
			// get the world and the position of the block that was clicked
			World world = event.getWorld();
			BlockPos pos = event.getPos();

			// if the block is an enchanting table...
			if (event.getHand() == EnumHand.MAIN_HAND && world.getBlockState(pos).getBlock() instanceof BlockEnchantmentTable)
			{
				// if the tile entity at the current position is a vanilla one...
				if (!(world.getTileEntity(pos) instanceof TileEntityEnchantmentTable))
				{
					// remove the vanilla tile entity and replace it with our custom one
					world.removeTileEntity(pos);
					world.setTileEntity(pos, new TileEntityEnchantmentTable());

					// schedule a block update to fix the animated book rendering
					if (world.isRemote) Minecraft.getMinecraft().renderGlobal.markBlockRangeForRenderUpdate(pos.getX(), pos.getY(), pos.getZ(), pos.getX(), pos.getY(), pos.getZ());
				}

				// cancel the click event
				event.setCanceled(true);

				// open the gui for the enhanced enchantment table
				FMLNetworkHandler.openGui(event.getEntityPlayer(), EnchantingTable.INSTANCE, GuiHandler.GUI_ENCHANTING_TABLE, world, pos.getX(), pos.getY(), pos.getZ());
			}
		}
	}

}