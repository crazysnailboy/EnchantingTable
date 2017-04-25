package net.crazysnailboy.mods.enchantingtable.capability;

import net.crazysnailboy.mods.enchantingtable.EnchantingTable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CapabilityHandler
{

	@CapabilityInject(ILapisHandler.class)
	public static final Capability<ILapisHandler> LAPIS_HANDLER_CAPABILITY = null;

	public static final EnumFacing DEFAULT_FACING = null;




	public static class Provider implements ICapabilitySerializable<NBTBase>
	{
		private ILapisHandler instance = LAPIS_HANDLER_CAPABILITY.getDefaultInstance();


		@Override
		public boolean hasCapability(Capability<?> capability, EnumFacing facing)
		{
			return capability == LAPIS_HANDLER_CAPABILITY;
		}

		@Override
		public <T> T getCapability(Capability<T> capability, EnumFacing facing)
		{
			return capability == LAPIS_HANDLER_CAPABILITY ? LAPIS_HANDLER_CAPABILITY.<T>cast(this.instance) : null;
		}

		@Override
		public NBTBase serializeNBT()
		{
			return LAPIS_HANDLER_CAPABILITY.getStorage().writeNBT(LAPIS_HANDLER_CAPABILITY, this.instance, null);
		}

		@Override
		public void deserializeNBT(NBTBase nbt)
		{
			LAPIS_HANDLER_CAPABILITY.getStorage().readNBT(LAPIS_HANDLER_CAPABILITY, this.instance, null, nbt);
		}

	}


	public static class Storage implements Capability.IStorage<ILapisHandler>
	{

		@Override
		public NBTBase writeNBT(Capability<ILapisHandler> capability, ILapisHandler instance, EnumFacing side)
		{
			return instance.serializeNBT();
		}

		@Override
		public void readNBT(Capability<ILapisHandler> capability, ILapisHandler instance, EnumFacing side, NBTBase nbt)
		{
			instance.deserializeNBT((NBTTagCompound)nbt);
		}
	}


	@EventBusSubscriber
	private static class EventHandler
	{
		@SubscribeEvent
		public static void onAttachCapabilities(final AttachCapabilitiesEvent<Entity> event)
		{
			if (event.getObject() instanceof EntityLivingBase)
			{
				event.addCapability(new ResourceLocation(EnchantingTable.MODID, "PlayerLapis"), new CapabilityHandler.Provider());
			}
		}

		@SubscribeEvent
		public static void onPlayerClone(PlayerEvent.Clone event)
		{
			final ILapisHandler oldHandler = event.getOriginal().getCapability(LAPIS_HANDLER_CAPABILITY, null);
			final ILapisHandler newHandler = event.getEntityPlayer().getCapability(LAPIS_HANDLER_CAPABILITY, null);

			ItemStack lapisStack = oldHandler.extractItem(0, 64, false);
			if (lapisStack != null) newHandler.insertItem(0, lapisStack, false);
		}

	}

}
