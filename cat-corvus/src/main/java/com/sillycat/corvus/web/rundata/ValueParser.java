package com.sillycat.corvus.web.rundata;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Date;
import java.util.Set;

public interface ValueParser {

	/**
	 * Clear all name/value pairs out of this object.
	 */
	void clear();

	/**
	 * Set the character encoding that will be used by this ValueParser.
	 */
	void setCharacterEncoding(String s);

	/**
	 * Get the character encoding that will be used by this ValueParser.
	 */
	String getCharacterEncoding();

	/**
	 * Trims the string data and applies the conversion specified in the
	 * property given by URL_CASE_FOLDING. It returns a new string so that it
	 * does not destroy the value data.
	 * 
	 * @param value
	 *            A String to be processed.
	 * @return A new String converted to lowercase and trimmed.
	 */
	String convert(String value);

	/**
	 * Add a name/value pair into this object.
	 * 
	 * @param name
	 *            A String with the name.
	 * @param value
	 *            A double with the value.
	 */
	void add(String name, double value);

	/**
	 * Add a name/value pair into this object.
	 * 
	 * @param name
	 *            A String with the name.
	 * @param value
	 *            An int with the value.
	 */
	void add(String name, int value);

	/**
	 * Add a name/value pair into this object.
	 * 
	 * @param name
	 *            A String with the name.
	 * @param value
	 *            An Integer with the value.
	 */
	void add(String name, Integer value);

	/**
	 * Add a name/value pair into this object.
	 * 
	 * @param name
	 *            A String with the name.
	 * @param value
	 *            A long with the value.
	 */
	void add(String name, long value);

	/**
	 * Add a name/value pair into this object.
	 * 
	 * @param name
	 *            A String with the name.
	 * @param value
	 *            A long with the value.
	 */
	void add(String name, String value);

	/**
	 * Add a String parameter. If there are any Strings already associated with
	 * the name, append to the array. This is used for handling parameters from
	 * mulitipart POST requests.
	 * 
	 * @param name
	 *            A String with the name.
	 * @param value
	 *            A String with the value.
	 */
	void append(String name, String value);

	/**
	 * Add an array of Strings for a key. This is simply adding all the elements
	 * in the array one by one.
	 * 
	 * @param name
	 *            A String with the name.
	 * @param value
	 *            A String Array.
	 */
	void add(String name, String[] value);

	/**
	 * Removes the named parameter from the contained hashtable. Wraps to the
	 * contained <code>Hashtable.remove()</code>.
	 * 
	 * 
	 * @return The value that was mapped to the key (a <code>String[]</code>) or
	 *         <code>null</code> if the key was not mapped.
	 */
	Object remove(String name);

	/**
	 * Determine whether a given key has been inserted. All keys are stored in
	 * lowercase strings, so override method to account for this.
	 * 
	 * @param key
	 *            An Object with the key to search for.
	 * @return True if the object is found.
	 */
	boolean containsKey(Object key);

	/**
	 * Gets the keys.
	 * 
	 * @return A <code>Set</code> of the keys.
	 */
	Set<String> keySet();

	/**
	 * Returns all the available parameter names.
	 * 
	 * @return A object array with the keys.
	 */
	Object[] getKeys();

	/**
	 * Return a boolean for the given name. If the name does not exist, return
	 * defaultValue.
	 * 
	 * @param name
	 *            A String with the name.
	 * @param defaultValue
	 *            The default value.
	 * @return A boolean.
	 */
	boolean getBoolean(String name, boolean defaultValue);

	/**
	 * Return a boolean for the given name. If the name does not exist, return
	 * false.
	 * 
	 * @param name
	 *            A String with the name.
	 * @return A boolean.
	 */
	boolean getBoolean(String name);

	/**
	 * Returns a Boolean object for the given name. If the parameter does not
	 * exist or can not be parsed as a boolean, null is returned.
	 * <p>
	 * Valid values for true: true, on, 1, yes<br>
	 * Valid values for false: false, off, 0, no<br>
	 * <p>
	 * The string is compared without reguard to case.
	 * 
	 * @param name
	 *            A String with the name.
	 * @return A Boolean.
	 */
	Boolean getBooleanObject(String name);

	/**
	 * Returns a Boolean object for the given name. If the parameter does not
	 * exist or can not be parsed as a boolean, the defaultValue is returned.
	 * <p>
	 * Valid values for true: true, on, 1, yes<br>
	 * Valid values for false: false, off, 0, no<br>
	 * <p>
	 * The string is compared without reguard to case.
	 * 
	 * @param name
	 *            A String with the name.
	 * @return A Boolean.
	 */
	Boolean getBooleanObject(String name, Boolean defaultValue);

	/**
	 * Return a double for the given name. If the name does not exist, return
	 * defaultValue.
	 * 
	 * @param name
	 *            A String with the name.
	 * @param defaultValue
	 *            The default value.
	 * @return A double.
	 */
	double getDouble(String name, double defaultValue);

	/**
	 * Return a double for the given name. If the name does not exist, return
	 * 0.0.
	 * 
	 * @param name
	 *            A String with the name.
	 * @return A double.
	 */
	double getDouble(String name);

	/**
	 * Return an array of doubles for the given name. If the name does not
	 * exist, return null.
	 * 
	 * @param name
	 *            A String with the name.
	 * @return A double[].
	 */
	double[] getDoubles(String name);

	/**
	 * Return a Double for the given name. If the name does not exist, return
	 * defaultValue.
	 * 
	 * @param name
	 *            A String with the name.
	 * @param defaultValue
	 *            The default value.
	 * @return A double.
	 */
	Double getDoubleObject(String name, Double defaultValue);

	/**
	 * Return a Double for the given name. If the name does not exist, return
	 * null.
	 * 
	 * @param name
	 *            A String with the name.
	 * @return A double.
	 */
	Double getDoubleObject(String name);

	/**
	 * Return an array of doubles for the given name. If the name does not
	 * exist, return null.
	 * 
	 * @param name
	 *            A String with the name.
	 * @return A double[].
	 */
	Double[] getDoubleObjects(String name);

	/**
	 * Return a float for the given name. If the name does not exist, return
	 * defaultValue.
	 * 
	 * @param name
	 *            A String with the name.
	 * @param defaultValue
	 *            The default value.
	 * @return A float.
	 */
	float getFloat(String name, float defaultValue);

	/**
	 * Return a float for the given name. If the name does not exist, return
	 * 0.0.
	 * 
	 * @param name
	 *            A String with the name.
	 * @return A float.
	 */
	float getFloat(String name);

	/**
	 * Return an array of floats for the given name. If the name does not exist,
	 * return null.
	 * 
	 * @param name
	 *            A String with the name.
	 * @return A float[].
	 */
	float[] getFloats(String name);

	/**
	 * Return a Float for the given name. If the name does not exist, return
	 * defaultValue.
	 * 
	 * @param name
	 *            A String with the name.
	 * @param defaultValue
	 *            The default value.
	 * @return A Float.
	 */
	Float getFloatObject(String name, Float defaultValue);

	/**
	 * Return a float for the given name. If the name does not exist, return
	 * null.
	 * 
	 * @param name
	 *            A String with the name.
	 * @return A Float.
	 */
	Float getFloatObject(String name);

	/**
	 * Return an array of floats for the given name. If the name does not exist,
	 * return null.
	 * 
	 * @param name
	 *            A String with the name.
	 * @return A float[].
	 */
	Float[] getFloatObjects(String name);

	/**
	 * Return a BigDecimal for the given name. If the name does not exist,
	 * return 0.0.
	 * 
	 * @param name
	 *            A String with the name.
	 * @param defaultValue
	 *            The default value.
	 * @return A BigDecimal.
	 */
	BigDecimal getBigDecimal(String name, BigDecimal defaultValue);

	/**
	 * Return a BigDecimal for the given name. If the name does not exist,
	 * return <code>null</code>.
	 * 
	 * @param name
	 *            A String with the name.
	 * @return A BigDecimal.
	 */
	BigDecimal getBigDecimal(String name);

	/**
	 * Return an array of BigDecimals for the given name. If the name does not
	 * exist, return null.
	 * 
	 * @param name
	 *            A String with the name.
	 * @return A BigDecimal[].
	 */
	BigDecimal[] getBigDecimals(String name);

	/**
	 * Return an int for the given name. If the name does not exist, return
	 * defaultValue.
	 * 
	 * @param name
	 *            A String with the name.
	 * @param defaultValue
	 *            The default value.
	 * @return An int.
	 */
	int getInt(String name, int defaultValue);

	/**
	 * Return an int for the given name. If the name does not exist, return 0.
	 * 
	 * @param name
	 *            A String with the name.
	 * @return An int.
	 */
	int getInt(String name);

	/**
	 * Return an Integer for the given name. If the name does not exist, return
	 * defaultValue.
	 * 
	 * @param name
	 *            A String with the name.
	 * @param defaultValue
	 *            The default value.
	 * @return An Integer.
	 */
	Integer getIntObject(String name, Integer defaultValue);

	/**
	 * Return an Integer for the given name. If the name does not exist, return
	 * null.
	 * 
	 * @param name
	 *            A String with the name.
	 * @return An Integer.
	 */
	Integer getIntObject(String name);

	/**
	 * Return an array of ints for the given name. If the name does not exist,
	 * return null.
	 * 
	 * @param name
	 *            A String with the name.
	 * @return An int[].
	 */
	int[] getInts(String name);

	/**
	 * Return an array of Integers for the given name. If the name does not
	 * exist, return null.
	 * 
	 * @param name
	 *            A String with the name.
	 * @return An Integer[].
	 */
	Integer[] getIntObjects(String name);

	/**
	 * Return a long for the given name. If the name does not exist, return
	 * defaultValue.
	 * 
	 * @param name
	 *            A String with the name.
	 * @param defaultValue
	 *            The default value.
	 * @return A long.
	 */
	long getLong(String name, long defaultValue);

	/**
	 * Return a long for the given name. If the name does not exist, return 0.
	 * 
	 * @param name
	 *            A String with the name.
	 * @return A long.
	 */
	long getLong(String name);

	/**
	 * Return a Long for the given name. If the name does not exist, return
	 * defaultValue.
	 * 
	 * @param name
	 *            A String with the name.
	 * @param defaultValue
	 *            The default value.
	 * @return A Long.
	 */
	Long getLongObject(String name, Long defaultValue);

	/**
	 * Return a Long for the given name. If the name does not exist, return
	 * null.
	 * 
	 * @param name
	 *            A String with the name.
	 * @return A Long.
	 */
	Long getLongObject(String name);

	/**
	 * Return an array of longs for the given name. If the name does not exist,
	 * return null.
	 * 
	 * @param name
	 *            A String with the name.
	 * @return A long[].
	 */
	long[] getLongs(String name);

	/**
	 * Return an array of Longs for the given name. If the name does not exist,
	 * return null.
	 * 
	 * @param name
	 *            A String with the name.
	 * @return A Long[].
	 */
	Long[] getLongObjects(String name);

	/**
	 * Return a byte for the given name. If the name does not exist, return
	 * defaultValue.
	 * 
	 * @param name
	 *            A String with the name.
	 * @param defaultValue
	 *            The default value.
	 * @return A byte.
	 */
	byte getByte(String name, byte defaultValue);

	/**
	 * Return a byte for the given name. If the name does not exist, return 0.
	 * 
	 * @param name
	 *            A String with the name.
	 * @return A byte.
	 */
	byte getByte(String name);

	/**
	 * Return an array of bytes for the given name. If the name does not exist,
	 * return null. The array is returned according to the HttpRequest's
	 * character encoding.
	 * 
	 * @param name
	 *            A String with the name.
	 * @return A byte[].
	 * @exception UnsupportedEncodingException
	 */
	byte[] getBytes(String name) throws UnsupportedEncodingException;

	/**
	 * Return a byte for the given name. If the name does not exist, return
	 * defaultValue.
	 * 
	 * @param name
	 *            A String with the name.
	 * @param defaultValue
	 *            The default value.
	 * @return A byte.
	 */
	Byte getByteObject(String name, Byte defaultValue);

	/**
	 * Return a byte for the given name. If the name does not exist, return 0.
	 * 
	 * @param name
	 *            A String with the name.
	 * @return A byte.
	 */
	Byte getByteObject(String name);

	/**
	 * Return a String for the given name. If the name does not exist, return
	 * null.
	 * 
	 * @param name
	 *            A String with the name.
	 * @return A String.
	 */
	String getString(String name);

	/**
	 * Return a String for the given name. If the name does not exist, return
	 * null. It is the same as the getString() method however has been added for
	 * simplicity when working with template tools such as Velocity which allow
	 * you to do something like this:
	 * 
	 * <code>$data.Parameters.form_variable_name</code>
	 * 
	 * @param name
	 *            A String with the name.
	 * @return A String.
	 */
	String get(String name);

	/**
	 * Return a String for the given name. If the name does not exist, return
	 * the defaultValue.
	 * 
	 * @param name
	 *            A String with the name.
	 * @param defaultValue
	 *            The default value.
	 * @return A String.
	 */
	String getString(String name, String defaultValue);

	/**
	 * Set a parameter to a specific value.
	 * 
	 * This is useful if you want your action to override the values of the
	 * parameters for the screen to use.
	 * 
	 * @param name
	 *            The name of the parameter.
	 * @param value
	 *            The value to set.
	 */
	void setString(String name, String value);

	/**
	 * Return an array of Strings for the given name. If the name does not
	 * exist, return null.
	 * 
	 * @param name
	 *            A String with the name.
	 * @return A String[].
	 */
	String[] getStrings(String name);

	/**
	 * Return an array of Strings for the given name. If the name does not
	 * exist, return the defaultValue.
	 * 
	 * @param name
	 *            A String with the name.
	 * @param defaultValue
	 *            The default value.
	 * @return A String[].
	 */
	String[] getStrings(String name, String[] defaultValue);

	/**
	 * Set a parameter to a specific value.
	 * 
	 * This is useful if you want your action to override the values of the
	 * parameters for the screen to use.
	 * 
	 * @param name
	 *            The name of the parameter.
	 * @param values
	 *            The value to set.
	 */
	void setStrings(String name, String[] values);

	/**
	 * Return an Object for the given name. If the name does not exist, return
	 * null.
	 * 
	 * @param name
	 *            A String with the name.
	 * @return An Object.
	 */
	Object getObject(String name);

	/**
	 * Return an array of Objects for the given name. If the name does not
	 * exist, return null.
	 * 
	 * @param name
	 *            A String with the name.
	 * @return An Object[].
	 */
	Object[] getObjects(String name);

	/**
	 * Returns a java.util.Date object. String is parsed by supplied DateFormat.
	 * If the name does not exist, return the defaultValue.
	 * 
	 * @param name
	 *            A String with the name.
	 * @param df
	 *            A DateFormat.
	 * @param defaultValue
	 *            The default value.
	 * @return A Date.
	 */
	Date getDate(String name, DateFormat df, Date defaultValue);

	/**
	 * Returns a java.util.Date object. If there are DateSelector style
	 * parameters then these are used. If not and there is a parameter 'name'
	 * then this is parsed by DateFormat. If the name does not exist, return
	 * null.
	 * 
	 * @param name
	 *            A String with the name.
	 * @return A Date.
	 */
	Date getDate(String name);

	/**
	 * Returns a java.util.Date object. String is parsed by supplied DateFormat.
	 * If the name does not exist, return null.
	 * 
	 * @param name
	 *            A String with the name.
	 * @param df
	 *            A DateFormat.
	 * @return A Date.
	 */
	Date getDate(String name, DateFormat df);

	/**
	 * Uses bean introspection to set writable properties of bean from the
	 * parameters, where a (case-insensitive) name match between the bean
	 * property and the parameter is looked for.
	 * 
	 * @param bean
	 *            An Object.
	 * @exception Exception
	 *                a generic exception.
	 */
	void setProperties(Object bean) throws Exception;

	/**
	 * Simple method that attempts to get a toString() representation of this
	 * object. It doesn't do well with String[]'s though.
	 * 
	 * @return A String.
	 */
	String toString();

}
