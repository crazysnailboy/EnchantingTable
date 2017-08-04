package net.crazysnailboy.mods.enchantingtable.capability;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;


public interface ILapisHandler extends IItemHandler, IItemHandlerModifiable, INBTSerializable<NBTTagCompound>
{
}
