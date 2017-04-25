package net.crazysnailboy.mods.enchantingtable.inventory;

import java.lang.reflect.Field;
import net.crazysnailboy.mods.enchantingtable.block.BlockEnderEnchantmentTable;
import net.crazysnailboy.mods.enchantingtable.capability.CapabilityHandler;
import net.crazysnailboy.mods.enchantingtable.capability.ILapisHandler;
import net.crazysnailboy.mods.enchantingtable.tileentity.TileEntityEnchantmentTable;
import net.crazysnailboy.mods.enchantingtable.util.ReflectionHelper;
import net.minecraft.block.BlockEnchantmentTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class ContainerEnchantment extends net.minecraft.inventory.ContainerEnchantment
{

	private static final Field worldField = ReflectionHelper.getDeclaredField(net.minecraft.inventory.ContainerEnchantment.class, "worldPointer","field_75172_h");
	private static final Field positionField = ReflectionHelper.getDeclaredField(net.minecraft.inventory.ContainerEnchantment.class, "position","field_178150_j");


//	@SideOnly(Side.CLIENT)
//	public ContainerEnchantment(InventoryPlayer playerInventory, World world)
//	{
//		this(playerInventory, world, BlockPos.ORIGIN);
//	}

	public ContainerEnchantment(InventoryPlayer playerInventory, World world, BlockPos pos)
	{
		super(playerInventory, world, pos);

		if (world.getBlockState(pos).getBlock() instanceof BlockEnderEnchantmentTable)
		{
			loadLapisFromPlayer(playerInventory.player);
		}
		else
		{
			loadLapisFromTable(world, pos);
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		World world = ReflectionHelper.getFieldValue(worldField, this);
		BlockPos pos = ReflectionHelper.getFieldValue(positionField, this);

		if (world.getBlockState(pos).getBlock() instanceof BlockEnchantmentTable)
		{
			return player.getDistanceSq((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D) <= 64.0D;
		}
		else
		{
			return false;
		}
	}

	@Override
	public void onContainerClosed(EntityPlayer player)
	{
		World world = ReflectionHelper.getFieldValue(worldField, this);
		BlockPos pos = ReflectionHelper.getFieldValue(positionField, this);

		// *** copied from net.minecraft.inventory.Container.onContainerClosed ***
		InventoryPlayer inventoryplayer = player.inventory;
		if (!inventoryplayer.getItemStack().isEmpty())
		{
			player.dropItem(inventoryplayer.getItemStack(), false);
			inventoryplayer.setItemStack(ItemStack.EMPTY);
		}
		// *** copied from net.minecraft.inventory.Container.onContainerClosed ***

		// *** modified from net.minecraft.inventory.ContainerEnchantment.onContainerClosed ***
		if (!world.isRemote)
		{
			ItemStack itemstack = this.tableInventory.removeStackFromSlot(0);
			if (!itemstack.isEmpty())
			{
				player.dropItem(itemstack, false);
			}
		}
		// *** modified from net.minecraft.inventory.ContainerEnchantment.onContainerClosed ***


		if (world.getBlockState(pos).getBlock() instanceof BlockEnderEnchantmentTable)
		{
			saveLapisToPlayer(player);
		}
		else
		{
			saveLapisToTable(world, pos);
		}
	}


	private void loadLapisFromTable(World world, BlockPos pos)
	{
		TileEntity tileentity = world.getTileEntity(pos);
		if (tileentity instanceof TileEntityEnchantmentTable)
		{
			TileEntityEnchantmentTable tileentityenchantmenttable = (TileEntityEnchantmentTable)tileentity;
			IItemHandler handler = tileentityenchantmenttable.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

			ItemStack lapisStack = handler.getStackInSlot(0); if (!lapisStack.isEmpty()) lapisStack = lapisStack.copy();
			this.tableInventory.setInventorySlotContents(1, lapisStack);
			handler.insertItem(0, ItemStack.EMPTY, false);
		}
	}

	private void loadLapisFromPlayer(EntityPlayer player)
	{
		boolean hasCapability = player.hasCapability(CapabilityHandler.LAPIS_HANDLER_CAPABILITY, null);
		if (hasCapability)
		{
			ILapisHandler handler = player.getCapability(CapabilityHandler.LAPIS_HANDLER_CAPABILITY, null);

			ItemStack lapisStack = handler.getStackInSlot(0); if (!lapisStack.isEmpty()) lapisStack = lapisStack.copy();
			this.tableInventory.setInventorySlotContents(1, lapisStack);
			handler.insertItem(0, ItemStack.EMPTY, false);
		}
	}

	private void saveLapisToTable(World world, BlockPos pos)
	{
		TileEntity tileentity = world.getTileEntity(pos);
		if (tileentity instanceof TileEntityEnchantmentTable)
		{
			TileEntityEnchantmentTable tileentityenchantmenttable = (TileEntityEnchantmentTable)tileentity;
			IItemHandler handler = tileentityenchantmenttable.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

			handler.extractItem(0, 64, false);
			ItemStack lapisStack = this.tableInventory.removeStackFromSlot(1);
			handler.insertItem(0, lapisStack, false);
		}
	}

	private void saveLapisToPlayer(EntityPlayer player)
	{
		boolean hasCapability = player.hasCapability(CapabilityHandler.LAPIS_HANDLER_CAPABILITY, null);
		if (hasCapability)
		{
			ILapisHandler handler = player.getCapability(CapabilityHandler.LAPIS_HANDLER_CAPABILITY, null);

			handler.extractItem(0, 64, false);
			ItemStack lapisStack = this.tableInventory.removeStackFromSlot(1);
			handler.insertItem(0, lapisStack, false);
		}
	}

}
