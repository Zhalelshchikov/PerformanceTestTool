package com.harris.netboss.recordscreator.parsers;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.harris.netboss.dxtPerformanceTestingTool.DXTPerformanceTestingTool;

/**
 * @author zheltukhin
 * @since 09.04.2012
 */
public class DataParser {

	static Integer FORMAT_VERSION_BYTE = 17;

	private static int INT_SIZE = 1;

	private static int LONG_SIZE = 3;

	private static String EMPTY_HEX = "00";

	/**
	 * @param manager
	 */
	public DataParser() {
	}

	/**
	 * @param startIndex
	 * @param endIndex
	 * @param hexValues
	 * @param difference
	 * @return ascii string
	 */
	public String createAsciiName(final int startIndex, final int endIndex,
			final List<String> hexValues, final int difference) {

		final String hexName = "";
		String name = "";

		try {
			final StringBuilder sb = new StringBuilder();

			for (int i = startIndex + difference; i < endIndex + difference + 1; i++) {
				final int decimal = Integer.parseInt(hexValues.get(i), 16);
				sb.append((char) decimal);
			}

			name = sb.toString().trim();

		} catch (final NumberFormatException e) {
			DXTPerformanceTestingTool.printLine("Can't parse " + hexName + " : "	+ e.getMessage() + "\n");
			//System.out.println("Can't parse " + hexName + " : "	+ e.getMessage());
		} catch (final IndexOutOfBoundsException e) {
			DXTPerformanceTestingTool.printLine("Can't get value for interval ( " + (startIndex + difference) + "," + (endIndex + difference) + " ) : " + e.getMessage() + "\n");
			//System.out.println("Can't get value for interval ( " + (startIndex + difference) + "," + (endIndex + difference) + " ) : " + e.getMessage());
		}

		return name;
	}

	/**
	 * @param startIndex
	 * @param endIndex
	 * @param hexValues
	 * @param difference
	 * @return date in String format
	 */
	public String createFormattedDate(final int startIndex, final int endIndex,
			final List<String> hexValues, final int difference) {

		String date = "";

		final int length = endIndex - startIndex;

		// format is sec,min,hour,day -> day:hour:min:sec
		for (int i = 0; i <= length; i++) {

			int value = 0;

			try {
				value = Integer.parseInt(
						hexValues.get(endIndex - i + difference), 16);
			} catch (final Exception e) {
				DXTPerformanceTestingTool.printLine("Not defined value : " + e.getMessage() + "\n");
				//System.out.println("Not defined value : " + e.getMessage());
			}

			if (i == 0) {
				date += value + " DAYS ";
			} else {
				if (value < 10) {
					date += "0" + value;
				} else {
					date += value;
				}

				if (i != length) {
					date += ":";
				}
			}
		}

		return date;
	}

	/**
	 * @param startIndex
	 * @param hexValues
	 * @param difference
	 * @return hexValue
	 */
	public String createHexValue(final int startIndex,
			final List<String> hexValues, final int difference) {

		String hexValue = "";

		final int parsingIndex = startIndex + difference + LONG_SIZE;

		for (int i = parsingIndex; i >= startIndex + difference; i--) {
			hexValue += hexValues.get(i);
		}

		if (hexValue.startsWith(EMPTY_HEX)) {
			return hexValue.substring(2);
		}

		return hexValue;
	}

	/**
	 * @param startIndex
	 * @param hexValues
	 * @param difference
	 * @return int from hex
	 */
	public Integer createIntValue(final int startIndex,
			final List<String> hexValues, final int difference) {

		Integer intValue = null;
		String hexIntNumber = "";

		final int parsingIndex = startIndex + difference + INT_SIZE;

		for (int i = parsingIndex; i >= startIndex + difference; i--) {
			hexIntNumber += hexValues.get(i);
		}

		try {
			if (isDataCorrect(hexIntNumber)) {
				intValue = Integer.parseInt(hexIntNumber, 16);
			}
		} catch (final NumberFormatException exc) {
			DXTPerformanceTestingTool.printLine("Can't parse " + hexIntNumber + " setting it to empty value" + "\n");
			//System.out.println("Can't parse " + hexIntNumber + " setting it to empty value");
		}

		return intValue;
	}

	/**
	 * @param startIndex
	 * @param hexValues
	 * @param difference
	 * @return long from hex
	 */
	public Long createLongValue(final int startIndex,
			final List<String> hexValues, final int difference) {

		Long longValue = null;
		String hexLongNumber = "";

		final int parsingIndex = startIndex + difference + LONG_SIZE;

		try {
			for (int i = parsingIndex; i >= startIndex + difference; i--) {
				hexLongNumber += hexValues.get(i);
			}
			if (isDataCorrect(hexLongNumber)) {
				longValue = Long.parseLong(hexLongNumber, 16);
			}
		} catch (final NumberFormatException exc) {
			DXTPerformanceTestingTool.printLine("Can't parse " + hexLongNumber + " setting it to empty value" + "\n");
			//System.out.println("Can't parse " + hexLongNumber + " setting it to empty value");
		} catch (final IndexOutOfBoundsException exc) {
			DXTPerformanceTestingTool.printLine("Can't get value for interval ( "+ (startIndex + difference) + "," + parsingIndex + " ) .Setting it to empty value" + "\n");
			//System.out.println("Can't get value for interval ( "+ (startIndex + difference) + "," + parsingIndex + " ) .Setting it to empty value");
		}

		return longValue;
	}

	/**
	 * @param hexValues
	 * @param startByte
	 * @return date from hex
	 */
	public Date getCounterTime(final List<String> hexValues, final int startByte) {
		final Date date = parseTimeStamp(startByte, hexValues, 0);

		return date;
	}

	/**
	 * @param hexValues
	 * @return formatVersion
	 */
	public Integer getFormatVersion(final List<String> hexValues) {

		int formatVersion = -1;

		try {
			formatVersion = Integer
					.parseInt(hexValues.get(FORMAT_VERSION_BYTE));
		} catch (final Exception e) {
			DXTPerformanceTestingTool.printLine("Can't parse format version " + hexValues.get(FORMAT_VERSION_BYTE) + " : " + e.getMessage() + "\n");
			//System.out.println("Can't parse format version " + hexValues.get(FORMAT_VERSION_BYTE) + " : " + e.getMessage());
		}

		return formatVersion;
	}

	private Boolean isDataCorrect(final String value) {
		if (value.equals("FFFFFFFF") || value.equals("FFFF")
				|| value.equals("FF")) {
			return false;
		}
		return true;
	}

	/**
	 * @param startIndex
	 * @param hexValues
	 * @param difference
	 * @return date
	 */
	public Date parseTimeStamp(final int startIndex,
			final List<String> hexValues, final int difference) {

		Date date = null;

		try {

			final int i = startIndex + difference;
			// don't count milliseconds
			final int yearFirstPart = Integer
					.parseInt(hexValues.get(i + 7), 16);
			final int yearSecPart = Integer.parseInt(hexValues.get(i + 6), 16);
			final int year = yearFirstPart * 100 + yearSecPart;
			final int month = Integer.parseInt(hexValues.get(i + 5), 16);
			final int day = Integer.parseInt(hexValues.get(i + 4), 16);
			final int hour = Integer.parseInt(hexValues.get(i + 3), 16);
			final int minutes = Integer.parseInt(hexValues.get(i + 2), 16);
			final int seconds = Integer.parseInt(hexValues.get(i + 1), 16);
			final Calendar cal = Calendar.getInstance();
			cal.set(year, month - 1, day, hour, minutes, seconds);
			date = cal.getTime();
			DXTPerformanceTestingTool.printLine("Report date =  " + date.toString()+ "\n");
			//System.out.println("Report date =  " + date.toString());

		} catch (final Exception e) {
			DXTPerformanceTestingTool.printLine("Can't parse the date " + hexValues.subList(18, 25) + " error: " + e.getMessage()+ "\n");
			//System.out.println("Can't parse the date " + hexValues.subList(18, 25) + " error: " + e.getMessage());
		}
		return date;
	}

	/**
	 * @param hexValues
	 */
	public void printReportLogInHex(final List<String> hexValues) {
		DXTPerformanceTestingTool.printLine(" Size = " + hexValues.size() + "\n");
		//System.out.println(" Size = " + hexValues.size());

		for (int i = 0; i < (hexValues.size() / 16) + 1; i++) {

			if (i * 16 + 16 < hexValues.size()) {
				DXTPerformanceTestingTool.printLine(" Values ( " + i + " ) = " + hexValues.subList(i * 16, i * 16 + 16) + "\n");
				//System.out.println(" Values ( " + i + " ) = " + hexValues.subList(i * 16, i * 16 + 16));
			} else {
				DXTPerformanceTestingTool.printLine(" Values ( " + i + " ) = " + hexValues.subList(i * 16, hexValues.size()) + "\n");
				//System.out.println(" Values ( " + i + " ) = " + hexValues.subList(i * 16, hexValues.size()));
			}
		}
	}
}
