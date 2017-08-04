package net.crazysnailboy.mods.enchantingtable.util;

import java.lang.reflect.Field;


public class ReflectionHelper
{

	/**
	 * Obtain a field by name from class declaringClass.
	 * Wrapper around {@link net.minecraftforge.fml.relauncher.ReflectionHelper#findField(Class, String...)}, included for convenience/competeness.
	 */
	public static final Field getDeclaredField(Class<?> declaringClass, String... fieldNames)
	{
		return net.minecraftforge.fml.relauncher.ReflectionHelper.findField(declaringClass, fieldNames);
	}


	/**
	 * Obtain the value of Field fieldToAccess from an Object instance.
	 */
	@SuppressWarnings("unchecked")
	public static final <T, E> T getFieldValue(final Field fieldToAccess, E instance)
	{
		try
		{
			return (T)fieldToAccess.get(instance);
		}
		catch (Exception ex)
		{
			throw new UnableToAccessFieldException(fieldToAccess, ex);
		}
	}

	/**
	 * Set the value of Field fieldToAccess on an Object instance.
	 */
	public static final <T, E> void setFieldValue(final Field fieldToAccess, E instance, T value)
	{
		try
		{
			fieldToAccess.set(instance, value);
		}
		catch (Exception ex)
		{
			throw new UnableToAccessFieldException(fieldToAccess, ex);
		}
	}


	public static class UnableToAccessFieldException extends net.minecraftforge.fml.relauncher.ReflectionHelper.UnableToAccessFieldException
	{

		public UnableToAccessFieldException(String[] fieldNames, Exception ex)
		{
			super(fieldNames, ex);
		}

		public UnableToAccessFieldException(Field field, Exception ex)
		{
			this(new String[] { field.getName() }, ex);
		}

	}
}
