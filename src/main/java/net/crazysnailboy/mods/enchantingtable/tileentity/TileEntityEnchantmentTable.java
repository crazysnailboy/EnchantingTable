package net.crazysnailboy.mods.enchantingtable.tileentity;

import net.crazysnailboy.mods.enchantingtable.inventory.ContainerEnchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityEnchantmentTable extends net.minecraft.tileentity.TileEntityEnchantmentTable
{

	private ItemStackHandler handler;

	public TileEntityEnchantmentTable()
	{
		super();
		this.handler = new ItemStackHandler(1);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		compound.setTag("Inventory", handler.serializeNBT());
		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		this.handler.deserializeNBT(compound.getCompoundTag("Inventory"));
		super.readFromNBT(compound);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return (T)this.handler;
		return super.getCapability(capability, facing);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return true;
		return super.hasCapability(capability, facing);
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
	{
		return new ContainerEnchantment(playerInventory, this.world, this.pos);
	}

	@Override
	public void update()
	{
		super.update();
		this.markDirty();
	}

}