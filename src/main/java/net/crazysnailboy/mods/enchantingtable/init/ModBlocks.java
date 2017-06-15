package net.crazysnailboy.mods.enchantingtable.init;

import net.crazysnailboy.mods.enchantingtable.EnchantingTable;
import net.crazysnailboy.mods.enchantingtable.block.BlockEnderEnchantmentTable;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks
{

	public static final Block ENDER_ENCHANTING_TABLE = new BlockEnderEnchantmentTable().setHardness(5.0F).setResistance(2000.0F).setRegistryName("ender_enchanting_table").setUnlocalizedName("enderEnchantmentTable");


	public static void registerBlocks()
	{
		GameRegistry.register(ENDER_ENCHANTING_TABLE);
		GameRegistry.register(new ItemBlock(ENDER_ENCHANTING_TABLE).setRegistryName(ENDER_ENCHANTING_TABLE.getRegistryName()));
	}

	public static void registerInventoryModels()
	{
		Item item = Item.getItemFromBlock(ENDER_ENCHANTING_TABLE);
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}

	public static void registerCraftingRecipes()
	{
		addShapelessRecipe("ender_enchanting_table", new ItemStack(ENDER_ENCHANTING_TABLE), new Object[] { Blocks.ENCHANTING_TABLE, Items.ENDER_EYE });
	}



	private static void addShapelessRecipe(String name, ItemStack stack, Object... recipeComponents)
	{
		try
		{
			name = EnchantingTable.MODID + ":" + name;
			NonNullList<Ingredient> list = NonNullList.create();

			for (Object object : recipeComponents)
			{
				if (object instanceof ItemStack)
				{
					list.add(Ingredient.fromStacks(((ItemStack)object).copy()));
				}
				else if (object instanceof Item)
				{
					list.add(Ingredient.fromStacks(new ItemStack((Item)object)));
				}
				else
				{
					if (!(object instanceof Block))
					{
						throw new IllegalArgumentException("Invalid shapeless recipe: unknown type " + object.getClass().getName() + "!");
					}

					list.add(Ingredient.fromStacks(new ItemStack((Block)object)));
				}
			}
			GameRegistry.register(new ShapelessRecipes(name, stack, list).setRegistryName(new ResourceLocation(name)));
		}
		catch (Exception ex)
		{
			EnchantingTable.LOGGER.catching(ex);
		}
	}

}
