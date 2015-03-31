package com.harris.netboss.recordscreator.parsers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.harris.netboss.dxtPerformanceTestingTool.DXTPerformanceTestingTool;
import com.harris.netboss.recordscreator.DxtPerformanceConstants;

/**
 * @author zheltukhin
 * @since 09.04.2012
 */
public class CommonReportsParser {

	/** ValueType: String */
	public static final String COMPUTER_UNIT_NAME = "ComputerUnitName";

	/** ValueType: String */
	public static final String MESSAGE_BUS_NAME = "MessageBusName";

	/** ValueType: Long */
	public static final String UNIT_RESTARTS = "UnitRestarts";

	/** ValueType: Long */
	public static final String ADMIN_RESTARTS = "AdminRestarts";

	/** ValueType: Long */
	public static final String PROCESS_RESTARTS = "ProcessRestarts";

	/** ValueType: Long */
	public static final String SUBORDINATE_PREPROC_RESTARTS = "SubordinatePreprocRestarts";

	/** ValueType: Long */
	public static final String SIMULTANEOUS_RESTARTS = "SimultaneousRestarts";

	/** ValueType: Long */
	public static final String STATE_TIME = "StateTime";

	/** ValueType: Long */
	public static final String NOT_STATE_TIME = "NotStateTime";

	/** ValueType: String */
	public static final String RECORD_CONDITION = "RecordCondition";

	/** ValueType: Double */
	public static final String LOAD_DEGREE = "LoadDegree";

	/** ValueType: Long */
	public static final String PEAK_LOAD_DEGREE = "PeakLoadDegree";

	/** ValueType: String */
	public static final String SAMPLES_OBTAINED = "SamplesObtained";

	/** ValueType: Date */
	public static final String PEAK_LOAD_TIME = "PeakLoadTime";

	static final int START_TIME_BYTE_AVAILABILITY = 33;
	static final int END_TIME_BYTE_OBSERVATION = 18;

	static final int COMP_UNIT_BYTE = 40;
	static final int AVAIL_MEAS_BYTE = 41;

	// Load observations
	static final String COMPUTER_UNIT = "0077";

	/**
	 * Using for creating such reports inside Environmentals/Message Bus folder
	 * */
	public static final String MESSAGE_BUS = "0078";

	// Field reporting
	static final String AVAIL_MEASUREMENT = "0081";

	static private List<Integer> messageBusLoadFormat;
	static {
		messageBusLoadFormat = new ArrayList<Integer>();
		{
			messageBusLoadFormat.add(2);
		}
	}

	static private List<Integer> availabilityFormat;
	static {
		availabilityFormat = new ArrayList<Integer>();
		{
			availabilityFormat.add(5);
		}
	}

	/** Set of all possible common reports */
	static public Map<String, List<Integer>> commonReportsTypes;
	static {
		commonReportsTypes = new HashMap<String, List<Integer>>();
		{
			commonReportsTypes.put(COMPUTER_UNIT,
					DxtPerformanceConstants.format0);
			commonReportsTypes.put(MESSAGE_BUS, messageBusLoadFormat);
			commonReportsTypes.put(AVAIL_MEASUREMENT, availabilityFormat);
		}
	}

	static Integer START_REPORT_INDEX = 30;

	private final DataParser dp;

	private static final Integer COMMON_REPORT_START_INDEX = 47;

	private static final Integer COMP_UNIT_REPORT_START_INDEX = 43;

	private static final Integer REC_NUMBER_INDEX = 45;

	/**
	 * @param manager
	 */
	public CommonReportsParser() {
		this.dp = new DataParser();
	}

	/**
	 * The only field reporting measurement in use is the Availability
	 * performance of the DXT.
	 *
	 * This measurement can be set for all computer units in the DXT and it
	 * provides information on the disturbances detected in the computer units.
	 * Specifically this report provides information regarding the number of
	 * restarts experienced by various computer units.
	 *
	 * @param meDataValues
	 * @return map with Report
	 */
	private Map<String, Map<String, Object>> createAvailabilityReport(
			final List<String> meDataValues) {

		final Map<String, Map<String, Object>> availabilityReports = new HashMap<String, Map<String, Object>>();
		final Date startTime = dp.getCounterTime(meDataValues,
				START_TIME_BYTE_AVAILABILITY);
		final Date endTime = createPeriodTime(startTime, meDataValues,
				AVAIL_MEAS_BYTE, true);
		DXTPerformanceTestingTool.printLine("Availability reports number : "	+ getRecordsNumber(meDataValues) + "\n");
		//System.out.println("Availability reports number : "	+ getRecordsNumber(meDataValues));

		for (int i = 0; i < getRecordsNumber(meDataValues); i++) {

			final int recDiff = COMMON_REPORT_START_INDEX + i * 38;
			final Map<String, Object> availabilityReport = new HashMap<String, Object>();

			final String computerUnitName = dp.createAsciiName(0, 10,
					meDataValues, recDiff);

			availabilityReport.put(DxtPerformanceConstants.DISPLAY_NAME,
					"Availability Management Report");
			availabilityReport.put(DxtPerformanceConstants.REPORT_TYPE,
					AVAIL_MEASUREMENT);
			availabilityReport.put(DxtPerformanceConstants.CUSTOM_CLASS_NAME,
					"UIM_DXTPMAvailMeasurement");
			availabilityReport.put(DxtPerformanceConstants.DESCRIPTION,
					"Field Reporting : Availability Measurement of the DXT");

			availabilityReport.put(DxtPerformanceConstants.COUNTER_TIME,
					startTime);
			availabilityReport.put(DxtPerformanceConstants.START_TIME,
					startTime);
			availabilityReport.put(DxtPerformanceConstants.END_TIME, endTime);

			availabilityReport.put(COMPUTER_UNIT_NAME, computerUnitName);
			availabilityReport.put(UNIT_RESTARTS,
					dp.createLongValue(10, meDataValues, recDiff));
			availabilityReport.put(ADMIN_RESTARTS,
					dp.createLongValue(14, meDataValues, recDiff));
			availabilityReport.put(PROCESS_RESTARTS,
					dp.createLongValue(18, meDataValues, recDiff));
			availabilityReport.put(SUBORDINATE_PREPROC_RESTARTS,
					dp.createLongValue(22, meDataValues, recDiff));
			availabilityReport.put(SIMULTANEOUS_RESTARTS,
					dp.createLongValue(26, meDataValues, recDiff));
			availabilityReport.put(STATE_TIME,
					dp.createLongValue(30, meDataValues, recDiff));
			availabilityReport.put(NOT_STATE_TIME,
					dp.createLongValue(34, meDataValues, recDiff));

			availabilityReports.put(computerUnitName, availabilityReport);
		}

		return availabilityReports;
	}

	/**
	 *
	 * This report provides load observation data related to one or several
	 * objects.
	 *
	 * @param meDataValues
	 * @return map with Report
	 */
	private Map<String, Map<String, Object>> createComputerUnitReport(
			final List<String> meDataValues) {

		final Map<String, Map<String, Object>> compUnitReports = new HashMap<String, Map<String, Object>>();

		final int msgBusNumber = dp.createIntValue(41, meDataValues, 0);

		final Date endTime = dp.getCounterTime(meDataValues,
				END_TIME_BYTE_OBSERVATION);

		final Date startTime = createPeriodTime(endTime, meDataValues,
				COMP_UNIT_BYTE, false);
		DXTPerformanceTestingTool.printLine("Computer unit reports number : " + msgBusNumber + "\n");
		//System.out.println("Computer unit reports number : " + msgBusNumber);

		for (int i = 0; i < msgBusNumber; i++) {

			final int recDiff = COMP_UNIT_REPORT_START_INDEX + i * 26;

			final Map<String, Object> compUnitReport = new HashMap<String, Object>();

			final String computerUnitName = dp.createAsciiName(1, 12,
					meDataValues, recDiff);
			compUnitReport.put(DxtPerformanceConstants.DISPLAY_NAME,
					"Computer Unit Load Report");
			compUnitReport.put(COMPUTER_UNIT_NAME, computerUnitName);
			compUnitReport.put(DxtPerformanceConstants.DESCRIPTION,
					"Load Observations : Computer unit load observation");
			compUnitReport.put(DxtPerformanceConstants.REPORT_TYPE,
					COMPUTER_UNIT);
			compUnitReport.put(DxtPerformanceConstants.CUSTOM_CLASS_NAME,
					"UIM_DXTPMComputerUnitLoad");

			compUnitReport.put(DxtPerformanceConstants.COUNTER_TIME, startTime);
			compUnitReport.put(DxtPerformanceConstants.START_TIME, startTime);
			compUnitReport.put(DxtPerformanceConstants.END_TIME, endTime);

			final String recCondition = meDataValues.get(0 + recDiff);
			compUnitReport.put(RECORD_CONDITION, meDataValues.get(0 + recDiff));

			if (!recCondition.equals("FF")) {
				compUnitReport
						.put(LOAD_DEGREE, (double) dp.createIntValue(13,
								meDataValues, recDiff) / 10);
				compUnitReport.put(SAMPLES_OBTAINED,
						meDataValues.get(15 + recDiff));
				compUnitReport.put(PEAK_LOAD_DEGREE,
						dp.createIntValue(16, meDataValues, recDiff));
				compUnitReport.put(PEAK_LOAD_TIME,
						dp.parseTimeStamp(18, meDataValues, recDiff));
				compUnitReports.put(computerUnitName, compUnitReport);
			} else {
				DXTPerformanceTestingTool.printLine("Report for the " + computerUnitName	+ " incorrect. Do not fill other properties\n");
				//System.out.println("Report for the " + computerUnitName	+ " incorrect. Do not fill other properties");
			}

		}

		return compUnitReports;
	}

	/**
	 * Load observation provides the following information on the message bus of
	 * the DXT:
	 *
	 * Load (%) - The average load rate in percent (0.0-100.0). This is the
	 * average load of message bus during the measurement period. The message
	 * bus load measurement is carried out by an operating system which, every
	 * minute, reads the load values of the message bus in current use.
	 *
	 * Peak load (%) - The peak load rate in percent (0-100). This is the
	 * highest recorded value of the message bus load rate during a measurement
	 * period. The peak load is an average load over a period of sixty seconds.
	 *
	 * Peak load time - The time of the peak load. The time indicates the
	 * moment, when the highest value of the message bus was obtained.
	 *
	 * @param meDataValues
	 * @return map with Report
	 */

	private Map<String, Map<String, Object>> createMessageBusLoadReport(
			final List<String> meDataValues) {

		final Map<String, Map<String, Object>> messageBusReports = new HashMap<String, Map<String, Object>>();

		final Date endTime = dp.getCounterTime(meDataValues,
				END_TIME_BYTE_OBSERVATION);

		final Date startTime = createPeriodTime(endTime, meDataValues,
				COMP_UNIT_BYTE, false);

		final int msgBusNumber = dp.createIntValue(41, meDataValues, 0);
		DXTPerformanceTestingTool.printLine("Message Bus Load reports number : " + msgBusNumber + "\n");
		//System.out.println("Message Bus Load reports number : " + msgBusNumber);

		for (int i = 0; i < msgBusNumber; i++) {

			final int recDiff = COMP_UNIT_REPORT_START_INDEX + i * 26;

			final Map<String, Object> messageBusReport = new HashMap<String, Object>();
			messageBusReport.put(DxtPerformanceConstants.DISPLAY_NAME,
					"MessageBusLoadReport");
			messageBusReport.put(DxtPerformanceConstants.DESCRIPTION,
					"Load Observations : Message bus load observation");

			messageBusReport.put(DxtPerformanceConstants.COUNTER_TIME,
					startTime);
			messageBusReport.put(DxtPerformanceConstants.START_TIME, startTime);
			messageBusReport.put(DxtPerformanceConstants.END_TIME, endTime);
			messageBusReport.put(DxtPerformanceConstants.REPORT_TYPE,
					MESSAGE_BUS);
			messageBusReport.put(DxtPerformanceConstants.CUSTOM_CLASS_NAME,
					"UIM_DXTPMMessageBusLoad");

			final String recCondition = meDataValues.get(0 + recDiff);
			messageBusReport.put(RECORD_CONDITION,
					meDataValues.get(0 + recDiff));
			final String messageBusName = dp.createAsciiName(1, 12,
					meDataValues, recDiff);

			if (!recCondition.equals("FF")) {
				messageBusReport
						.put(LOAD_DEGREE, (double) dp.createIntValue(13,
								meDataValues, recDiff) / 10);
				messageBusReport.put(PEAK_LOAD_DEGREE,
						dp.createIntValue(15, meDataValues, recDiff));
				messageBusReport.put(PEAK_LOAD_TIME,
						dp.parseTimeStamp(17, meDataValues, recDiff));
				messageBusReport.put(MESSAGE_BUS_NAME, messageBusName);
			} else {
				DXTPerformanceTestingTool.printLine("Report for the " + messageBusName + " incorrect. Do not fill other properties\n");
				//System.out.println("Report for the " + messageBusName + " incorrect. Do not fill other properties");
			}

			messageBusReports.put(messageBusName, messageBusReport);
		}

		return messageBusReports;
	}

	/**
	 * @param startTime
	 * @param meDataValues
	 * @param minuteByteValue
	 * @param direction
	 * @return endTime
	 */
	private Date createPeriodTime(final Date startTime,
			final List<String> meDataValues, final int minuteByteValue,
			final boolean direction) {

		final Calendar cal = Calendar.getInstance();

		cal.setTime(startTime);

		try {
			Integer minute = Integer.parseInt(
					meDataValues.get(minuteByteValue), 16);
			if (!direction) {
				minute = -minute;
			}
			cal.add(Calendar.MINUTE, minute);

		} catch (final Exception e) {
			DXTPerformanceTestingTool.printLine("Can't get minute value for : " + meDataValues.get(minuteByteValue) + "\n");
			//System.out.println("Can't get minute value for : " + meDataValues.get(minuteByteValue));
		}

		return cal.getTime();
	}

	/**
	 * @param meDataValues
	 * @param type
	 * @return map for Common reports creation
	 */
	public Map<String, Map<String, Object>> createReport(
			final List<String> meDataValues, final String type) {

		dp.printReportLogInHex(meDataValues);

		Map<String, Map<String, Object>> report = new HashMap<String, Map<String, Object>>();

		if (type.equals(COMPUTER_UNIT)) {

			report = createComputerUnitReport(meDataValues);
		} else if (type.equals(MESSAGE_BUS)) {

			report = createMessageBusLoadReport(meDataValues);

		} else if (type.equals(AVAIL_MEASUREMENT)) {

			report = createAvailabilityReport(meDataValues);
		}

		return report;
	}

	private Integer getRecordsNumber(final List<String> meDataValues) {
		return dp.createIntValue(REC_NUMBER_INDEX, meDataValues, 0);
	}
}
