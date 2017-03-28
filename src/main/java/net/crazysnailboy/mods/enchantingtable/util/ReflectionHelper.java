package net.crazysnailboy.mods.enchantingtable.util;

import java.lang.reflect.Field;
import net.crazysnailboy.mods.enchantingtable.EnchantingTable;

public class ReflectionHelper
{

	public static Field getDeclaredField(Class classToAccess, String... fieldNames)
	{
		Field declaredField = null;
		Exception ex = null;

		for ( String fieldName : fieldNames )
		{
			try
			{
				declaredField = classToAccess.getDeclaredField(fieldName);
				declaredField.setAccessible(true);
				break;
			}
			catch (Exception e)
			{
				ex = e;
			}
		}

		if (declaredField == null)
		{
			EnchantingTable.LOGGER.catching(ex);
		}

		return declaredField;
	}


	@SuppressWarnings("unchecked")
	public static <T,E> T getFieldValue(Field fieldToAccess, E instance)
	{
		T result = null;
		try
		{
			result = (T)fieldToAccess.get(instance);
		}
		catch (Exception ex)
		{
			EnchantingTable.LOGGER.catching(ex);
		}
		return result;
	}

	public static <T,E> void setFieldValue(Field fieldToAccess, E instance, T value)
	{
		try
		{
			fieldToAccess.set(instance, value);
		}
		catch (Exception ex)
		{
			EnchantingTable.LOGGER.catching(ex);
		}
	}

}
