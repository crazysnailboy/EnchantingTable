package net.crazysnailboy.mods.enchantingtable.proxy;

import net.crazysnailboy.mods.enchantingtable.EnchantingTable;
import net.crazysnailboy.mods.enchantingtable.capability.CapabilityHandler;
import net.crazysnailboy.mods.enchantingtable.capability.ILapisHandler;
import net.crazysnailboy.mods.enchantingtable.capability.LapisHandler;
import net.crazysnailboy.mods.enchantingtable.common.network.GuiHandler;
import net.crazysnailboy.mods.enchantingtable.tileentity.TileEntityEnchantmentTable;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;


public class CommonProxy
{

	public void preInit()
	{
		registerTileEntities();
		registerCapabilities();
	}

	public void init()
	{
		registerGuiHandler();
	}

	public void postInit()
	{
	}


	private void registerGuiHandler()
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(EnchantingTable.INSTANCE, new GuiHandler());
	}

	private void registerTileEntities()
	{
		GameRegistry.registerTileEntity(TileEntityEnchantmentTable.class, TileEntityEnchantmentTable.class.getSimpleName());
	}

	private void registerCapabilities()
	{
		CapabilityManager.INSTANCE.register(ILapisHandler.class, new CapabilityHandler.Storage(), LapisHandler.class);
	}

}