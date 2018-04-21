package net.crazysnailboy.mods.enchantingtable.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class BlockEnderEnchantmentTable extends net.minecraft.block.BlockEnchantmentTable
{

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		List<ItemStack> ret = new ArrayList<ItemStack>();

		ret.add(new ItemStack(Item.getItemFromBlock(Blocks.ENCHANTING_TABLE), 1));
		ret.add(new ItemStack(Items.ENDER_EYE, 1));

		return ret;
	}


	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
	{
		super.randomDisplayTick(stateIn, worldIn, pos, rand);

		for (int i = 0; i < 3; ++i)
		{
			int j = rand.nextInt(2) * 2 - 1;
			int k = rand.nextInt(2) * 2 - 1;
			double d0 = pos.getX() + 0.5D + 0.25D * j;
			double d1 = pos.getY() + rand.nextFloat();
			double d2 = pos.getZ() + 0.5D + 0.25D * k;
			double d3 = rand.nextFloat() * j;
			double d4 = (rand.nextFloat() - 0.5D) * 0.125D;
			double d5 = rand.nextFloat() * k;
			worldIn.spawnParticle(EnumParticleTypes.PORTAL, d0, d1, d2, d3, d4, d5, new int[0]);
		}
	}

}
