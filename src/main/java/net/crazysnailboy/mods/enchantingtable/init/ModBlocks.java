package net.crazysnailboy.mods.enchantingtable.init;

import java.util.UUID;
import net.crazysnailboy.mods.enchantingtable.block.BlockEnderEnchantmentTable;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;
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
		addShapelessRecipe(new ItemStack(ENDER_ENCHANTING_TABLE), new Object[] { Blocks.ENCHANTING_TABLE, Items.ENDER_EYE });
	}
	
	
	
	private static void addShapelessRecipe(ItemStack stack, Object... recipeComponents)
	{
		String name = UUID.randomUUID().toString();
		NonNullList<Ingredient> list = NonNullList.create();

		for (Object object : recipeComponents)
		{
			if (object instanceof ItemStack)
			{
				list.add(Ingredient.func_193369_a(((ItemStack)object).copy()));
			}
			else if (object instanceof Item)
			{
				list.add(Ingredient.func_193369_a(new ItemStack((Item)object)));
			}
			else
			{
				if (!(object instanceof Block))
				{
					throw new IllegalArgumentException("Invalid shapeless recipe: unknown type " + object.getClass().getName() + "!");
				}

				list.add(Ingredient.func_193369_a(new ItemStack((Block)object)));
			}
		}

		CraftingManager.func_193379_a(name, new ShapelessRecipes(name, stack, list));
	}	

}
