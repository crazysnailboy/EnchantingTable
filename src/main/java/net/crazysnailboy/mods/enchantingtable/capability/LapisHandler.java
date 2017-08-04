package net.crazysnailboy.mods.enchantingtable.capability;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;


public class LapisHandler extends ItemStackHandler implements ILapisHandler, IItemHandlerModifiable, INBTSerializable<NBTTagCompound>
{
}
