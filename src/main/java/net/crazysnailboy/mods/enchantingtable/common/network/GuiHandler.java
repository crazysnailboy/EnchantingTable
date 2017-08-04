package net.crazysnailboy.mods.enchantingtable.common.network;

import net.crazysnailboy.mods.enchantingtable.client.gui.GuiEnchantment;
import net.crazysnailboy.mods.enchantingtable.inventory.ContainerEnchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldNameable;
import net.minecraft.world.World;


public class GuiHandler implements net.minecraftforge.fml.common.network.IGuiHandler
{

	public static final int GUI_ENCHANTING_TABLE = 0;

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		if (id == GUI_ENCHANTING_TABLE)
		{
			BlockPos pos = new BlockPos(x, y, z);
			return new ContainerEnchantment(player.inventory, world, pos);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		if (id == GUI_ENCHANTING_TABLE)
		{
			BlockPos pos = new BlockPos(x, y, z);
			return new GuiEnchantment(player.inventory, world, pos, (IWorldNameable)world.getTileEntity(pos));
		}
		return null;
	}

}
