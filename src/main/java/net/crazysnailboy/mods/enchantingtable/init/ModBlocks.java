package net.crazysnailboy.mods.enchantingtable.init;

import net.crazysnailboy.mods.enchantingtable.block.BlockEnderEnchantmentTable;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


@EventBusSubscriber
public class ModBlocks
{

	public static final Block ENDER_ENCHANTING_TABLE = new BlockEnderEnchantmentTable().setHardness(5.0F).setResistance(2000.0F).setRegistryName("ender_enchanting_table").setUnlocalizedName("enderEnchantmentTable");


	@SubscribeEvent
	public static void registerBlocks(final RegistryEvent.Register<Block> event)
	{
		event.getRegistry().register(ENDER_ENCHANTING_TABLE);
	}

	@SubscribeEvent
	public static void registerItems(final RegistryEvent.Register<Item> event)
	{
		event.getRegistry().register(new ItemBlock(ENDER_ENCHANTING_TABLE).setRegistryName(ENDER_ENCHANTING_TABLE.getRegistryName()));
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void registerInventoryModels(final ModelRegistryEvent event)
	{
		Item item = Item.getItemFromBlock(ENDER_ENCHANTING_TABLE);
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}

	public static void registerCraftingRecipes()
	{
		GameRegistry.addShapelessRecipe(new ItemStack(ENDER_ENCHANTING_TABLE), new Object[] { Blocks.ENCHANTING_TABLE, Items.ENDER_EYE });
	}

}