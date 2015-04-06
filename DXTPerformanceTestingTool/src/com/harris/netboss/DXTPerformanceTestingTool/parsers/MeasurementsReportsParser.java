package com.harris.netboss.dxtPerformanceTestingTool.parsers;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.harris.netboss.dxtPerformanceTestingTool.DXTPerformanceTestingTool;
import com.harris.netboss.dxtPerformanceTestingTool.DxtPerformanceConstants;

/**
 * @author zheltukhin
 * @since 24.02.2012
 */
public class MeasurementsReportsParser {

	private static int START_TIME_BYTE = 38;

	private static int DURATION_BYTE = 46;

	private static int MEAS_NUMBER = 36;

	// Traffic Measurements
	public static final String RADIO_NETWORK_REPORT = "0100";
	public static final String INDIVIDUAL_CALL_REPORT = "0101";
	public static final String GROUP_CALL_REPORT = "0102";
	public static final String GROUP_MANAGEMENT_REPORT = "0103";
	public static final String HANDOVERS_REPORT = "0104";
	public static final String PACKET_DATA_REPORT = "0105";
	public static final String SDS1_REPORT = "0106";
	public static final String SPEECH_LINE_REPORT = "0107";
	public static final String MOBILITY_MANAGEMENT_REPORT = "0108";
	public static final String ISDN_REPORT = "0109";
	public static final String FNIM_REPORT = "010A";
	public static final String HLR_REPORT = "010B";
	public static final String DXT_TBS_REPORT = "010C";
	public static final String RADIO_NETWORK2_REPORT = "010D";
	public static final String SCCH_REPORT = "010E";
	public static final String SDS2_REPORT = "010F";
	public static final String SDS3_REPORT = "0110";
	public static final String TEDS25_REPORT = "0111";
	public static final String TEDS50_REPORT = "0112";

	public static final String RADIO_NETWORK_REPORT_128 = "0113";
	public static final String RADIO_NETWORK2_REPORT_128 = "0114";
	public static final String SCCH_REPORT_128 = "0115";
	public static final String GROUP_MANAGEMENT_REPORT_128 = "0116";
	public static final String HANDOVERS_REPORT_128 = "0117";
	public static final String PACKET_DATA_REPORT_128 = "0118";
	public static final String SDS1_REPORT_128 = "0119";
	public static final String SDS3_REPORT_128 = "011A";
	public static final String MOBILITY_MANAGEMENT_REPORT_128 = "011B";
	public static final String DXT_TBS_REPORT_128 = "011C";
	public static final String TEDS25_REPORT_128 = "011D";
	public static final String TEDS50_REPORT_128 = "011E";

	/**
	 * Set of all possible traffic measurements reports with all supported
	 * format versions
	 */
	static public Map<String, List<Integer>> measReportsTypes;
	static {
		measReportsTypes = new HashMap<String, List<Integer>>();
		{
			measReportsTypes.put(RADIO_NETWORK_REPORT,
					DxtPerformanceConstants.format4);
			measReportsTypes.put(RADIO_NETWORK_REPORT_128,
					DxtPerformanceConstants.format4);

			measReportsTypes.put(RADIO_NETWORK2_REPORT,
					DxtPerformanceConstants.format2);
			measReportsTypes.put(RADIO_NETWORK2_REPORT_128,
					DxtPerformanceConstants.format2);

			measReportsTypes.put(INDIVIDUAL_CALL_REPORT,
					DxtPerformanceConstants.format2);

			measReportsTypes.put(GROUP_CALL_REPORT,
					DxtPerformanceConstants.format3);

			measReportsTypes.put(GROUP_MANAGEMENT_REPORT,
					DxtPerformanceConstants.format2);
			measReportsTypes.put(GROUP_MANAGEMENT_REPORT_128,
					DxtPerformanceConstants.format2);

			measReportsTypes.put(HANDOVERS_REPORT,
					DxtPerformanceConstants.format2);
			measReportsTypes.put(HANDOVERS_REPORT_128,
					DxtPerformanceConstants.format2);

			measReportsTypes.put(PACKET_DATA_REPORT,
					DxtPerformanceConstants.format3);
			measReportsTypes.put(PACKET_DATA_REPORT_128,
					DxtPerformanceConstants.format3);

			measReportsTypes.put(SDS1_REPORT, DxtPerformanceConstants.format2);
			measReportsTypes.put(SDS1_REPORT_128,
					DxtPerformanceConstants.format2);

			measReportsTypes.put(SPEECH_LINE_REPORT,
					DxtPerformanceConstants.format2);

			measReportsTypes.put(MOBILITY_MANAGEMENT_REPORT,
					DxtPerformanceConstants.format3);
			measReportsTypes.put(MOBILITY_MANAGEMENT_REPORT_128,
					DxtPerformanceConstants.format3);

			measReportsTypes.put(ISDN_REPORT, DxtPerformanceConstants.format1);
			measReportsTypes.put(FNIM_REPORT, DxtPerformanceConstants.format1);
			measReportsTypes.put(HLR_REPORT, DxtPerformanceConstants.format1);

			measReportsTypes.put(DXT_TBS_REPORT,
					DxtPerformanceConstants.format2);
			measReportsTypes.put(DXT_TBS_REPORT_128,
					DxtPerformanceConstants.format2);

			measReportsTypes.put(SCCH_REPORT, DxtPerformanceConstants.format2);
			measReportsTypes.put(SCCH_REPORT_128,
					DxtPerformanceConstants.format2);

			measReportsTypes.put(SDS2_REPORT, DxtPerformanceConstants.format1);

			measReportsTypes.put(SDS3_REPORT, DxtPerformanceConstants.format2);
			measReportsTypes.put(SDS3_REPORT_128,
					DxtPerformanceConstants.format2);

			measReportsTypes
					.put(TEDS25_REPORT, DxtPerformanceConstants.format2);
			measReportsTypes.put(TEDS25_REPORT_128,
					DxtPerformanceConstants.format2);

			measReportsTypes
					.put(TEDS50_REPORT, DxtPerformanceConstants.format2);
			measReportsTypes.put(TEDS50_REPORT_128,
					DxtPerformanceConstants.format2);

		}
	}
	static final String tbsFirstPart = "TBS-";

	private static Integer usefulDataCounter = 57;

	private final DataParser dp;

	private final int validityByte = 56;

	/**
	 * @param manager
	 */
	public MeasurementsReportsParser() {
		this.dp = new DataParser();
	}

	/**
	 * Do not create performance element with TbsNumber > Max. Such data should
	 * be logged and analyzed.
	 *
	 * @param tbsIdentifier
	 * @param meDataValues
	 */
	private Boolean checkTbsNumberValidity(final int tbsIdentifier,
			final List<String> meDataValues) {

		if (tbsIdentifier > DxtPerformanceConstants.MAX_TBS_NUMBER) {
			System.err.println("Found invalid TBS number : " + tbsIdentifier);
			System.err.println("Report data : " + meDataValues);

			return false;
		}

		return true;
	}

	/**
	 * @param startTime
	 * @param meDataValues
	 * @return startTime+duration
	 */
	private Date createEndTime(final Date startTime,
			final List<String> meDataValues) {

		final Calendar cal = Calendar.getInstance();

		cal.setTime(startTime);

		try {
			final int minute = dp
					.createIntValue(DURATION_BYTE, meDataValues, 0);
			cal.add(Calendar.MINUTE, minute);

		} catch (final Exception e) {
			DXTPerformanceTestingTool.printLine("Can't get minute value for : "
					+ meDataValues.get(DURATION_BYTE) + "\n");
		}

		return cal.getTime();
	}

	/**
	 * The FNIM Resource Management of DXT report provides DXT scale measurement
	 * data of reservation of FNIM resources. The FNIM Resource Management of
	 * DXT record contains information of the DXT where measurement was started
	 * and thus there is only one record in the report.
	 *
	 * @param meDataValues
	 * @return map for report creation
	 */
	private Map<String, Map<String, Object>> createFNIMReport(
			final List<String> meDataValues) {

		final Map<String, Object> fnimReport = new HashMap<String, Object>();
		final Map<String, Map<String, Object>> fnimReports = new HashMap<String, Map<String, Object>>();

		final Date startTime = dp.getCounterTime(meDataValues, START_TIME_BYTE);
		final Date endTime = createEndTime(startTime, meDataValues);

		fnimReport.put(DxtPerformanceConstants.DISPLAY_NAME, "FNIM Report");
		fnimReport.put(DxtPerformanceConstants.CUSTOM_CLASS_NAME,
				"UIM_DXTPMFNIM");
		fnimReport.put(DxtPerformanceConstants.FORMAT_VERSION, 1);
		fnimReport.put(DxtPerformanceConstants.REPORT_TYPE, FNIM_REPORT);
		fnimReport.put(DxtPerformanceConstants.DESCRIPTION,
				"Traffic Measurements : FNIM Resource Management");

		fnimReport.put(DxtPerformanceConstants.COUNTER_TIME, startTime);
		fnimReport.put(DxtPerformanceConstants.START_TIME, startTime);
		fnimReport.put(DxtPerformanceConstants.END_TIME, endTime);

		fnimReport.put("AttemptsCount",
				dp.createLongValue(0, meDataValues, usefulDataCounter));
		fnimReport.put("FailedAttemptsCount",
				dp.createLongValue(4, meDataValues, usefulDataCounter));
		fnimReport.put("IncomingCallsCount",
				dp.createLongValue(8, meDataValues, usefulDataCounter));
		fnimReport.put("IncomingCallsSum",
				dp.createLongValue(12, meDataValues, usefulDataCounter));
		fnimReport.put("OutgoingCallsCount",
				dp.createLongValue(16, meDataValues, usefulDataCounter));
		fnimReport.put("OutgoingCallsSum",
				dp.createLongValue(20, meDataValues, usefulDataCounter));
		fnimReport.put("FailedIncomingCallsCount",
				dp.createLongValue(24, meDataValues, usefulDataCounter));
		fnimReport.put("FailedOutgoingCallsCount",
				dp.createLongValue(28, meDataValues, usefulDataCounter));
		fnimReport.put("SuccessfulReservationsCount",
				dp.createLongValue(32, meDataValues, usefulDataCounter));

		fnimReports.put(DxtPerformanceConstants.DXT_IDENTIFIER, fnimReport);

		return fnimReports;
	}

	/**
	 * The Group Call report provides DXT scale measurement data of group calls.
	 * The Group Call record contains information from the DXT where the
	 * measurement was initiated and thus there is only one record per report.
	 * Time in a Group Call record is expressed in ticks where one tick equals
	 * 10 milliseconds. Question! We need to count formatVersion, but don't know
	 * which element need to be added
	 *
	 * @param meDataValues
	 * @return map for report creation
	 */
	private Map<String, Map<String, Object>> createGroupCallReport(
			final List<String> meDataValues) {

		final int formatVersion = dp.getFormatVersion(meDataValues);

		final Date startTime = dp.getCounterTime(meDataValues, START_TIME_BYTE);
		final Date endTime = createEndTime(startTime, meDataValues);

		final Map<String, Map<String, Object>> groupCallReports = new HashMap<String, Map<String, Object>>();
		final Map<String, Object> groupCallReport = new HashMap<String, Object>();

		groupCallReport.put(DxtPerformanceConstants.DISPLAY_NAME,
				"Group Call Report");
		groupCallReport.put(DxtPerformanceConstants.DESCRIPTION,
				"Traffic Measurements : Group Call");
		groupCallReport.put(DxtPerformanceConstants.FORMAT_VERSION,
				formatVersion);
		groupCallReport.put(DxtPerformanceConstants.REPORT_TYPE,
				GROUP_CALL_REPORT);
		groupCallReport.put(DxtPerformanceConstants.CUSTOM_CLASS_NAME,
				"UIM_DXTPMGroupCall");

		groupCallReport.put(DxtPerformanceConstants.COUNTER_TIME, startTime);
		groupCallReport.put(DxtPerformanceConstants.START_TIME, startTime);
		groupCallReport.put(DxtPerformanceConstants.END_TIME, endTime);

		groupCallReport.put("MembersCount",
				dp.createLongValue(0, meDataValues, usefulDataCounter));
		groupCallReport.put("NormalShiftingPriorityCount",
				dp.createLongValue(4, meDataValues, usefulDataCounter));
		groupCallReport.put("EmergencyShiftingPriorityCount",
				dp.createLongValue(8, meDataValues, usefulDataCounter));
		groupCallReport.put("NormalFixedPriorityCount",
				dp.createLongValue(12, meDataValues, usefulDataCounter));
		groupCallReport.put("EmergencyFixedPriorityCount",
				dp.createLongValue(16, meDataValues, usefulDataCounter));
		groupCallReport.put("NormalShiftingPriorityTime",
				dp.createLongValue(20, meDataValues, usefulDataCounter));
		groupCallReport.put("EmergencyShiftingPriorityTime",
				dp.createLongValue(24, meDataValues, usefulDataCounter));
		groupCallReport.put("NormalFixedPriorityTime",
				dp.createLongValue(28, meDataValues, usefulDataCounter));
		groupCallReport.put("EmergencyFixedPriorityTime",
				dp.createLongValue(32, meDataValues, usefulDataCounter));
		groupCallReport.put("NormalShiftingUnsucCount",
				dp.createLongValue(36, meDataValues, usefulDataCounter));
		groupCallReport.put("EmergencyShiftingUnsucCount",
				dp.createLongValue(40, meDataValues, usefulDataCounter));
		groupCallReport.put("NormalFixedUnsucCount",
				dp.createLongValue(44, meDataValues, usefulDataCounter));
		groupCallReport.put("EmergencyFixedUnsucCount",
				dp.createLongValue(48, meDataValues, usefulDataCounter));

		if (formatVersion >= 2) {
			groupCallReport.put("NormalInitSuccessCount",
					dp.createLongValue(52, meDataValues, usefulDataCounter));
			groupCallReport.put("EmergencyInitSuccessCount",
					dp.createLongValue(56, meDataValues, usefulDataCounter));
			groupCallReport.put("ActiveGroupPeakValue",
					dp.createLongValue(60, meDataValues, usefulDataCounter));
			groupCallReport.put("NormalPriorityTime",
					dp.createLongValue(64, meDataValues, usefulDataCounter));
			groupCallReport.put("EmergencyPriorityTime",
					dp.createLongValue(68, meDataValues, usefulDataCounter));
			groupCallReport.put("GrantedSpeechCount",
					dp.createLongValue(72, meDataValues, usefulDataCounter));
			groupCallReport.put("RequestedSpeechCount",
					dp.createLongValue(76, meDataValues, usefulDataCounter));

			if (formatVersion == 3) {
				groupCallReport
						.put("NormalInitPartSuccessCount", dp.createLongValue(
								80, meDataValues, usefulDataCounter));
				groupCallReport
						.put("EmergencyInitPartSuccessCount", dp
								.createLongValue(84, meDataValues,
										usefulDataCounter));
			}
		}
		groupCallReports.put(DxtPerformanceConstants.DXT_IDENTIFIER,
				groupCallReport);

		return groupCallReports;
	}

	/**
	 * This report provides TBS scale measurement data of group attachments and
	 * detachments. One Group Management record contains information of group
	 * management operations concerning one TBS and thus there can be one or
	 * several such records in the report.
	 *
	 * @param meDataValues
	 * @return map for report creation
	 */
	private Map<String, Map<String, Object>> createGroupManagementReport(
			final List<String> meDataValues) {

		final int formatVersion = dp.getFormatVersion(meDataValues);

		final Date startTime = dp.getCounterTime(meDataValues, START_TIME_BYTE);
		final Date endTime = createEndTime(startTime, meDataValues);

		final Map<String, Map<String, Object>> groupManagementReports = new HashMap<String, Map<String, Object>>();

		int recLength = 44;

		if (formatVersion == 2) {
			recLength = 46;
		}

		for (int i = 0; i < getMeasurementsNumber(meDataValues); i++) {

			final int recDiff = usefulDataCounter + i * recLength;
			final Map<String, Object> groupManagementReport = new HashMap<String, Object>();

			try {
				final int tbsIdentifier = dp.createIntValue(2, meDataValues,
						recDiff);

				if (checkTbsNumberValidity(tbsIdentifier, meDataValues)) {
					final String tbsId = tbsFirstPart + tbsIdentifier;

					groupManagementReport.put(
							DxtPerformanceConstants.DISPLAY_NAME,
							"Group Management Report");
					groupManagementReport.put(
							DxtPerformanceConstants.FORMAT_VERSION,
							formatVersion);
					groupManagementReport.put(
							DxtPerformanceConstants.REPORT_TYPE,
							GROUP_MANAGEMENT_REPORT);
					groupManagementReport.put(
							DxtPerformanceConstants.CUSTOM_CLASS_NAME,
							"UIM_DXTPMTBSGroupManagement");

					groupManagementReport.put(
							DxtPerformanceConstants.COUNTER_TIME, startTime);
					groupManagementReport.put(
							DxtPerformanceConstants.START_TIME, startTime);
					groupManagementReport.put(DxtPerformanceConstants.END_TIME,
							endTime);

					groupManagementReport.put(
							DxtPerformanceConstants.DESCRIPTION,
							"Traffic Measurements : Group Management");
					groupManagementReport.put(
							DxtPerformanceConstants.TBS_IDENTIFIER,
							tbsIdentifier);
					groupManagementReport.put("AttachSucceededDXTGroupCount",
							dp.createLongValue(4, meDataValues, recDiff));
					groupManagementReport.put("DeattachSucceededDXTGroupCount",
							dp.createLongValue(8, meDataValues, recDiff));
					groupManagementReport.put("AttachFailedDXTGroupCount",
							dp.createLongValue(12, meDataValues, recDiff));
					groupManagementReport.put("DeattachFailedDXTGroupCount",
							dp.createLongValue(16, meDataValues, recDiff));
					groupManagementReport.put("AttachSucceededMSGroupCount",
							dp.createLongValue(20, meDataValues, recDiff));
					groupManagementReport.put("DeattachSucceededMSGroupCount",
							dp.createLongValue(24, meDataValues, recDiff));
					groupManagementReport.put("AttachFailedMSGroupCount",
							dp.createLongValue(28, meDataValues, recDiff));
					groupManagementReport.put("DeattachFailedMSGroupCount",
							dp.createLongValue(32, meDataValues, recDiff));
					groupManagementReport.put("AssignedDGNACount",
							dp.createLongValue(36, meDataValues, recDiff));
					groupManagementReport.put("DeassignedDGNACount",
							dp.createLongValue(40, meDataValues, recDiff));

					if (formatVersion == 2) {
						groupManagementReport.put(
								DxtPerformanceConstants.LOCATION_AREA_ID,
								dp.createIntValue(44, meDataValues, recDiff));
					}

					groupManagementReports.put(tbsId, groupManagementReport);
				}
			} catch (final Exception e) {
				DXTPerformanceTestingTool.printLine("Can't get values for " + i
						+ " record : " + e.getMessage() + "\n");
			}
		}
		return groupManagementReports;
	}

	/**
	 * The Handover report provides DXT and TBS scale measurement data of
	 * handovers, where a handover is registered only in the target element.
	 * Thus there are two different record types in this report, the DXT
	 * Handover record and the TBS Handover record. They are organized so that
	 * the DXT Handover record appears first, followed by one or more TBS
	 * Handover records. Was renamed to Handovers report by the Customers
	 * request.
	 *
	 * @param meDataValues
	 * @return map for report creation
	 */
	private Map<String, Map<String, Object>> createHandoversRecordReport(
			final List<String> meDataValues) {

		final int formatVersion = dp.getFormatVersion(meDataValues);

		int recLength = 28;

		if (formatVersion == 2) {
			recLength = 30;
		}

		final Date startTime = dp.getCounterTime(meDataValues, START_TIME_BYTE);
		final Date endTime = createEndTime(startTime, meDataValues);

		final Map<String, Object> handoversDXTReport = new HashMap<String, Object>();

		final Map<String, Map<String, Object>> handoversReports = new HashMap<String, Map<String, Object>>();

		handoversDXTReport.put(DxtPerformanceConstants.DISPLAY_NAME,
				"Handovers Report");
		handoversDXTReport.put(DxtPerformanceConstants.DESCRIPTION,
				"Traffic Measurements : Handovers");
		handoversDXTReport.put(DxtPerformanceConstants.FORMAT_VERSION, 1);
		handoversDXTReport.put(DxtPerformanceConstants.REPORT_TYPE,
				HANDOVERS_REPORT);
		handoversDXTReport.put(DxtPerformanceConstants.CUSTOM_CLASS_NAME,
				"UIM_DXTPMHandovers");

		handoversDXTReport.put(DxtPerformanceConstants.COUNTER_TIME, startTime);
		handoversDXTReport.put(DxtPerformanceConstants.START_TIME, startTime);
		handoversDXTReport.put(DxtPerformanceConstants.END_TIME, endTime);

		handoversDXTReport.put("SpeechIndividualFailsCount",
				dp.createLongValue(0, meDataValues, usefulDataCounter));
		handoversDXTReport.put("SignallingIndividualFailsCount",
				dp.createLongValue(4, meDataValues, usefulDataCounter));
		handoversDXTReport.put("SpeechGroupFailsCount",
				dp.createLongValue(8, meDataValues, usefulDataCounter));
		handoversDXTReport.put("SignallingGroupFailsCount",
				dp.createLongValue(12, meDataValues, usefulDataCounter));
		handoversReports.put(DxtPerformanceConstants.DXT_IDENTIFIER,
				handoversDXTReport);

		for (int i = 0; i < getMeasurementsNumber(meDataValues); i++) {

			final int recDiff = i * recLength + 16 + usefulDataCounter;
			final Map<String, Object> handoversTBSReport = new HashMap<String, Object>();

			try {
				final int tbsIdentifier = dp.createIntValue(2, meDataValues,
						recDiff);

				if (checkTbsNumberValidity(tbsIdentifier, meDataValues)) {
					final String tbsId = tbsFirstPart + tbsIdentifier;

					handoversTBSReport.put(
							DxtPerformanceConstants.DISPLAY_NAME,
							"Handovers Report");
					handoversTBSReport.put(DxtPerformanceConstants.DESCRIPTION,
							"Traffic Measurements : Handovers");

					handoversTBSReport.put(
							DxtPerformanceConstants.FORMAT_VERSION,
							formatVersion);
					handoversTBSReport.put(DxtPerformanceConstants.REPORT_TYPE,
							HANDOVERS_REPORT);
					handoversTBSReport.put(
							DxtPerformanceConstants.CUSTOM_CLASS_NAME,
							"UIM_DXTPMTBSHandover");
					handoversTBSReport.put(
							DxtPerformanceConstants.COUNTER_TIME, startTime);
					handoversTBSReport.put(DxtPerformanceConstants.START_TIME,
							startTime);
					handoversTBSReport.put(DxtPerformanceConstants.END_TIME,
							endTime);
					handoversTBSReport.put(
							DxtPerformanceConstants.TBS_IDENTIFIER,
							tbsIdentifier);
					handoversTBSReport.put("TrafficChannelIndFailsCount",
							dp.createLongValue(4, meDataValues, recDiff));
					handoversTBSReport.put("OtherIndividualFailsCount",
							dp.createLongValue(8, meDataValues, recDiff));
					handoversTBSReport.put("SuccessfulIndividualCallsCount",
							dp.createLongValue(12, meDataValues, recDiff));
					handoversTBSReport.put("TrafficChannelGroupFailsCount",
							dp.createLongValue(16, meDataValues, recDiff));
					handoversTBSReport.put("OtherGroupFailsCount",
							dp.createLongValue(20, meDataValues, recDiff));
					handoversTBSReport.put("SuccessfulGroupCallsCount",
							dp.createLongValue(24, meDataValues, recDiff));

					if (formatVersion == 2) {
						handoversTBSReport.put(
								DxtPerformanceConstants.LOCATION_AREA_ID,
								dp.createIntValue(28, meDataValues, recDiff));
					}

					handoversReports.put(tbsId, handoversTBSReport);
				}
			} catch (final Exception e) {
				DXTPerformanceTestingTool.printLine("Can't get values for " + i
						+ " record : " + e.getMessage() + "\n");
			}
		}

		return handoversReports;
	}

	/**
	 * The HLR counters report provides DXT scale measurement data of usage of
	 * HLR resources.
	 *
	 * @param meDataValues
	 * @return map for report creation
	 */
	private Map<String, Map<String, Object>> createHLRReport(
			final List<String> meDataValues) {

		final Map<String, Object> hlrReport = new HashMap<String, Object>();
		final Map<String, Map<String, Object>> hlrReports = new HashMap<String, Map<String, Object>>();

		final Date startTime = dp.getCounterTime(meDataValues, START_TIME_BYTE);
		final Date endTime = createEndTime(startTime, meDataValues);

		hlrReport.put(DxtPerformanceConstants.DISPLAY_NAME, "HLR Report");
		hlrReport.put(DxtPerformanceConstants.FORMAT_VERSION, 1);
		hlrReport.put(DxtPerformanceConstants.REPORT_TYPE, HLR_REPORT);
		hlrReport
				.put(DxtPerformanceConstants.CUSTOM_CLASS_NAME, "UIM_DXTPMHLR");
		hlrReport.put(DxtPerformanceConstants.DESCRIPTION,
				"Traffic Measurements : HLR Counters");

		hlrReport.put(DxtPerformanceConstants.COUNTER_TIME, startTime);
		hlrReport.put(DxtPerformanceConstants.START_TIME, startTime);
		hlrReport.put(DxtPerformanceConstants.END_TIME, endTime);

		hlrReport.put("LocationUpdatesCount",
				dp.createLongValue(0, meDataValues, usefulDataCounter));
		hlrReport.put("LocationQueriesCount",
				dp.createLongValue(4, meDataValues, usefulDataCounter));
		hlrReport.put("SubscribersCount",
				dp.createLongValue(8, meDataValues, usefulDataCounter));
		hlrReport.put("SubscribersEndedCount",
				dp.createLongValue(12, meDataValues, usefulDataCounter));
		hlrReport.put("HighestCount",
				dp.createLongValue(16, meDataValues, usefulDataCounter));
		hlrReport.put("ITSIAttachCount",
				dp.createLongValue(20, meDataValues, usefulDataCounter));
		hlrReport.put("ITSIDetachCount",
				dp.createLongValue(24, meDataValues, usefulDataCounter));
		hlrReport.put("TripletGenerationsCount",
				dp.createLongValue(28, meDataValues, usefulDataCounter));
		hlrReport.put("TrackingSelectionsCount",
				dp.createLongValue(32, meDataValues, usefulDataCounter));
		hlrReport.put("TrackingDeselectionsCount",
				dp.createLongValue(36, meDataValues, usefulDataCounter));
		hlrReport.put("MgmtOperationsCount",
				dp.createLongValue(40, meDataValues, usefulDataCounter));

		hlrReports.put(DxtPerformanceConstants.DXT_IDENTIFIER, hlrReport);

		return hlrReports;
	}

	/**
	 * This report provides DXT scale measurement data of individual calls. The
	 * Individual Call record contains information of the DXT where the
	 * measurement was started and thus there is only one record in each report.
	 * The counter from byte 48 to byte 51 applies only if the version of format
	 * in the header is 2.
	 *
	 * @param meDataValues
	 * @return map for report creation
	 */
	private Map<String, Map<String, Object>> createIndividualCallReport(
			final List<String> meDataValues) {

		final int formatVersion = dp.getFormatVersion(meDataValues);

		final Date startTime = dp.getCounterTime(meDataValues, START_TIME_BYTE);
		final Date endTime = createEndTime(startTime, meDataValues);

		final Map<String, Map<String, Object>> individualCallReports = new HashMap<String, Map<String, Object>>();
		final Map<String, Object> individualCallReport = new HashMap<String, Object>();

		individualCallReport.put(DxtPerformanceConstants.DISPLAY_NAME,
				"Individual Call Report");
		individualCallReport.put(DxtPerformanceConstants.FORMAT_VERSION,
				formatVersion);
		individualCallReport.put(DxtPerformanceConstants.REPORT_TYPE,
				INDIVIDUAL_CALL_REPORT);
		individualCallReport.put(DxtPerformanceConstants.CUSTOM_CLASS_NAME,
				"UIM_DXTPMIndividualCall");
		individualCallReport.put(DxtPerformanceConstants.DESCRIPTION,
				"Traffic Measurements : Individual Call");

		individualCallReport.put(DxtPerformanceConstants.COUNTER_TIME,
				startTime);
		individualCallReport.put(DxtPerformanceConstants.START_TIME, startTime);
		individualCallReport.put(DxtPerformanceConstants.END_TIME, endTime);

		individualCallReport.put("SuccessfulCallsCount",
				dp.createLongValue(0, meDataValues, usefulDataCounter));
		individualCallReport.put("SuccessfulEmergencyCallsCount",
				dp.createLongValue(4, meDataValues, usefulDataCounter));
		individualCallReport.put("SuccessfulDuplexCallsCount",
				dp.createLongValue(8, meDataValues, usefulDataCounter));
		individualCallReport.put("SuccessfulHookCallsCount",
				dp.createLongValue(12, meDataValues, usefulDataCounter));
		individualCallReport.put("SuccessfulDirectCallsCount",
				dp.createLongValue(16, meDataValues, usefulDataCounter));
		individualCallReport.put("SuccessfulHookCallsTime",
				dp.createLongValue(20, meDataValues, usefulDataCounter));
		individualCallReport.put("SuccessfulDirectCallsTime",
				dp.createLongValue(24, meDataValues, usefulDataCounter));
		individualCallReport.put("SuccessfulDuplexCallsTime",
				dp.createLongValue(28, meDataValues, usefulDataCounter));
		individualCallReport.put("UnsuccessfulCallsCount",
				dp.createLongValue(32, meDataValues, usefulDataCounter));
		individualCallReport.put("UnsuccessfulDuplexCallsCount",
				dp.createLongValue(36, meDataValues, usefulDataCounter));
		individualCallReport.put("UnsuccessfulHookCallsCount",
				dp.createLongValue(40, meDataValues, usefulDataCounter));
		individualCallReport.put("UnsuccessfulDirectCallsCount",
				dp.createLongValue(44, meDataValues, usefulDataCounter));

		if (formatVersion == 2) {
			individualCallReport.put("UnsucEmergencyCallsCount",
					dp.createLongValue(48, meDataValues, usefulDataCounter));
		}

		individualCallReports.put(DxtPerformanceConstants.DXT_IDENTIFIER,
				individualCallReport);

		return individualCallReports;
	}

	/**
	 * The ISDN Resource Management of DXT report provides DXT scale measurement
	 * data of reservation of ISDN resources. The ISDN Resource Management of
	 * DXT record contains information of the logical PAU computer units
	 * associated to DXT where measurement was started. There can be 1 to 10 PAU
	 * records in the report.
	 *
	 * @param meDataValues
	 * @return map for report creation
	 */
	private Map<String, Map<String, Object>> createISDNReport(
			final List<String> meDataValues) {

		final Map<String, Map<String, Object>> isdnReports = new HashMap<String, Map<String, Object>>();

		final int statusByte = 4;

		final Date startTime = dp.getCounterTime(meDataValues, START_TIME_BYTE);
		final Date endTime = createEndTime(startTime, meDataValues);

		// length from requirements
		final Integer recLength = 41;

		for (int i = 0; i < getMeasurementsNumber(meDataValues); i++) {

			final Map<String, Object> isdnReport = new HashMap<String, Object>();

			// ISDN report doesn't use 56 status byte
			final int recDiff = (usefulDataCounter - 1) + recLength * i;

			try {
				final long pauId = dp.createLongValue(0, meDataValues, recDiff);

				isdnReport.put(DxtPerformanceConstants.DISPLAY_NAME, "PAU"
						+ pauId);

				isdnReport.put(DxtPerformanceConstants.DESCRIPTION,
						"Traffic Measurements : ISDN Resource Management");
				isdnReport.put(DxtPerformanceConstants.FORMAT_VERSION, 1);
				isdnReport
						.put(DxtPerformanceConstants.REPORT_TYPE, ISDN_REPORT);
				isdnReport.put(DxtPerformanceConstants.CUSTOM_CLASS_NAME,
						"UIM_DXTPMISDN");

				isdnReport.put(DxtPerformanceConstants.COUNTER_TIME, startTime);
				isdnReport.put(DxtPerformanceConstants.START_TIME, startTime);
				isdnReport.put(DxtPerformanceConstants.END_TIME, endTime);

				isdnReport.put("PauId", pauId);
				isdnReport.put("Status", Integer.parseInt(meDataValues
						.get(recDiff + statusByte)));
				isdnReport.put("AttemptsCount",
						dp.createLongValue(5, meDataValues, recDiff));
				isdnReport.put("FailedAttemptsCount",
						dp.createLongValue(9, meDataValues, recDiff));
				isdnReport.put("INCount",
						dp.createLongValue(13, meDataValues, recDiff));
				isdnReport.put("INReservationTime",
						dp.createLongValue(17, meDataValues, recDiff));
				isdnReport.put("OUTCount",
						dp.createLongValue(21, meDataValues, recDiff));
				isdnReport.put("OUTReservationTime",
						dp.createLongValue(25, meDataValues, recDiff));
				isdnReport.put("FailedINCount",
						dp.createLongValue(29, meDataValues, recDiff));
				isdnReport.put("FailedOUTCount",
						dp.createLongValue(33, meDataValues, recDiff));
				isdnReport.put("SuccessfulCount",
						dp.createLongValue(37, meDataValues, recDiff));

				isdnReports.put("PauId" + pauId, isdnReport);

			} catch (final Exception e) {
				DXTPerformanceTestingTool.printLine("Can't get values for " + i
						+ " record : " + e.getMessage() + "\n");
			}
		}
		return isdnReports;
	}

	/**
	 * The Mobility Management report provides DXT and TBS scale measurement
	 * data of ITSI attachments/detachments, location updates and Radio User
	 * Assignments and Disassignments. If Version of format is 1, then report is
	 * only TBS scale. if Version of format is 2, then report contains DXT and
	 * TBS scale measurement data.
	 *
	 * @param meDataValues
	 * @return map for report creation
	 */
	private Map<String, Map<String, Object>> createMobilityManagementReport(
			final List<String> meDataValues) {

		final Date startTime = dp.getCounterTime(meDataValues, START_TIME_BYTE);
		final Date endTime = createEndTime(startTime, meDataValues);

		final int formatVersion = dp.getFormatVersion(meDataValues);

		final Map<String, Map<String, Object>> mobilityManagementReports = new HashMap<String, Map<String, Object>>();

		int tbsIdentifier = 0;

		// format version - 1, tbs only
		if (formatVersion == 1) {

			final Integer recLength = 20;

			for (int i = 0; i < getMeasurementsNumber(meDataValues); i++) {

				final int recDiff = i * recLength + usefulDataCounter;

				final Map<String, Object> mobilityManagementReport = new HashMap<String, Object>();

				try {
					tbsIdentifier = dp.createIntValue(2, meDataValues, recDiff);

					if (checkTbsNumberValidity(tbsIdentifier, meDataValues)) {

						final String tbsId = tbsFirstPart + tbsIdentifier;

						mobilityManagementReport.put(
								DxtPerformanceConstants.DISPLAY_NAME,
								"Mobility Management Report");
						mobilityManagementReport.put(
								DxtPerformanceConstants.DESCRIPTION,
								"Traffic Measurements : Mobility Management");
						mobilityManagementReport.put(
								DxtPerformanceConstants.FORMAT_VERSION,
								formatVersion);
						mobilityManagementReport.put(
								DxtPerformanceConstants.REPORT_TYPE,
								MOBILITY_MANAGEMENT_REPORT);
						mobilityManagementReport.put(
								DxtPerformanceConstants.CUSTOM_CLASS_NAME,
								"UIM_DXTPMTBSMobility");

						mobilityManagementReport
								.put(DxtPerformanceConstants.COUNTER_TIME,
										startTime);
						mobilityManagementReport.put(
								DxtPerformanceConstants.START_TIME, startTime);
						mobilityManagementReport.put(
								DxtPerformanceConstants.END_TIME, endTime);

						mobilityManagementReport.put(
								DxtPerformanceConstants.TBS_IDENTIFIER,
								tbsIdentifier);
						mobilityManagementReport.put("ITSIAttachedCount",
								dp.createLongValue(4, meDataValues, recDiff));
						mobilityManagementReport.put("LocationUpdatesInCount",
								dp.createLongValue(8, meDataValues, recDiff));
						mobilityManagementReport.put("LocationUpdatesOutCount",
								dp.createLongValue(12, meDataValues, recDiff));

						mobilityManagementReports.put(tbsId,
								mobilityManagementReport);
					}
				} catch (final Exception e) {
					System.err.println("Can't get value for record : "
							+ e.getMessage());
				}
			}

		} else {
			final Map<String, Object> mobilityManagementReportDXT = new HashMap<String, Object>();

			mobilityManagementReportDXT.put(
					DxtPerformanceConstants.DISPLAY_NAME,
					"Mobility Management Report");

			mobilityManagementReportDXT.put(
					DxtPerformanceConstants.DESCRIPTION,
					"Traffic Measurements : Mobility Management");
			mobilityManagementReportDXT.put(
					DxtPerformanceConstants.FORMAT_VERSION, formatVersion);
			mobilityManagementReportDXT.put(
					DxtPerformanceConstants.REPORT_TYPE,
					MOBILITY_MANAGEMENT_REPORT);
			mobilityManagementReportDXT.put(
					DxtPerformanceConstants.CUSTOM_CLASS_NAME,
					"UIM_DXTPMMobility");

			mobilityManagementReportDXT.put(
					DxtPerformanceConstants.COUNTER_TIME, startTime);
			mobilityManagementReportDXT.put(DxtPerformanceConstants.START_TIME,
					startTime);
			mobilityManagementReportDXT.put(DxtPerformanceConstants.END_TIME,
					endTime);

			mobilityManagementReportDXT.put("PeakValue",
					dp.createLongValue(0, meDataValues, usefulDataCounter));
			mobilityManagementReportDXT.put("UnsuccessfulAuthCount",
					dp.createLongValue(4, meDataValues, usefulDataCounter));
			mobilityManagementReportDXT.put("SuccessfulAuthCount",
					dp.createLongValue(8, meDataValues, usefulDataCounter));
			mobilityManagementReportDXT.put("Succesful3ptyCount",
					dp.createLongValue(12, meDataValues, usefulDataCounter));
			mobilityManagementReportDXT.put("SuccessfulSubscriberCount",
					dp.createLongValue(16, meDataValues, usefulDataCounter));
			mobilityManagementReportDXT.put("InitiatedSubscriberCount",
					dp.createLongValue(20, meDataValues, usefulDataCounter));
			mobilityManagementReportDXT.put("Initiated3ptyAssignCount",
					dp.createLongValue(24, meDataValues, usefulDataCounter));
			mobilityManagementReportDXT.put("Initiated3ptyDisassignCount",
					dp.createLongValue(28, meDataValues, usefulDataCounter));
			mobilityManagementReportDXT.put("InitSubscriberDisassignCount",
					dp.createLongValue(32, meDataValues, usefulDataCounter));

			// length from requirements
			int tbsReportLength = 28;

			if (formatVersion == 3) {
				tbsReportLength = 34;
			}

			// length with dxt part
			final Integer tbsStartByte = usefulDataCounter + 36;

			for (int i = 0; i < getMeasurementsNumber(meDataValues); i++) {

				try {

					final int recDiff = tbsStartByte + tbsReportLength * i;

					final Map<String, Object> mobilityManagementReportTBS = new HashMap<String, Object>();

					tbsIdentifier = dp.createIntValue(2, meDataValues, recDiff);

					if (checkTbsNumberValidity(tbsIdentifier, meDataValues)) {
						final String tbsId = tbsFirstPart + tbsIdentifier;

						mobilityManagementReportTBS.put(
								DxtPerformanceConstants.DISPLAY_NAME,
								"Mobility Management Report");
						mobilityManagementReportTBS.put(
								DxtPerformanceConstants.DESCRIPTION,
								"Traffic Measurements : Mobility Management");
						mobilityManagementReportTBS.put(
								DxtPerformanceConstants.FORMAT_VERSION,
								formatVersion);
						mobilityManagementReportTBS.put(
								DxtPerformanceConstants.REPORT_TYPE,
								MOBILITY_MANAGEMENT_REPORT);
						mobilityManagementReportTBS.put(
								DxtPerformanceConstants.CUSTOM_CLASS_NAME,
								"UIM_DXTPMTBSMobility");

						mobilityManagementReportTBS
								.put(DxtPerformanceConstants.COUNTER_TIME,
										startTime);
						mobilityManagementReportTBS.put(
								DxtPerformanceConstants.START_TIME, startTime);
						mobilityManagementReportTBS.put(
								DxtPerformanceConstants.END_TIME, endTime);

						mobilityManagementReportTBS.put(
								DxtPerformanceConstants.TBS_IDENTIFIER,
								tbsIdentifier);
						mobilityManagementReportTBS.put("ITSIAttachedCount",
								dp.createLongValue(4, meDataValues, recDiff));
						mobilityManagementReportTBS.put(
								"LocationUpdatesInCount",
								dp.createLongValue(8, meDataValues, recDiff));
						mobilityManagementReportTBS.put(
								"LocationUpdatesOutCount",
								dp.createLongValue(12, meDataValues, recDiff));
						mobilityManagementReportTBS.put("ITSIDeattachedCount",
								dp.createLongValue(16, meDataValues, recDiff));
						mobilityManagementReportTBS.put("SubscriberCount",
								dp.createLongValue(20, meDataValues, recDiff));
						mobilityManagementReportTBS.put("PeakValue",
								dp.createLongValue(24, meDataValues, recDiff));

						if (formatVersion == 3) {

							mobilityManagementReportTBS.put(
									"PeriodicRegOperationsCount", dp
											.createLongValue(28, meDataValues,
													recDiff));

							mobilityManagementReportTBS
									.put(DxtPerformanceConstants.LOCATION_AREA_ID,
											dp.createIntValue(32, meDataValues,
													recDiff));
						}

						mobilityManagementReports.put(tbsId,
								mobilityManagementReportTBS);
					}
				} catch (final Exception e) {
					System.err.println("Can't get value for record : "
							+ e.getMessage());
				}
			}

			mobilityManagementReports.put(
					DxtPerformanceConstants.DXT_IDENTIFIER,
					mobilityManagementReportDXT);

		}

		return mobilityManagementReports;
	}

	/**
	 * The Packet Data report provides DXT and TBS scale measurement data of
	 * packet data transferring. Thus there are two different record types in
	 * this report, the DXT Packet Data record and the TBS Packet Data record.
	 * They are organized so that the DXT Packet Data record appears first,
	 * followed by one or more TBS Packet Data records. In the DXT Packet Data
	 * Record Format the counters from byte 12 to byte 35 appear only if the
	 * version of format in the header is 2. In the TBS Packet Data Record
	 * Format the counters from byte 16 to byte 35 appear only if the version of
	 * format in the header is 2.
	 *
	 * @param meDataValues
	 * @return map for report creation
	 */
	private Map<String, Map<String, Object>> createPacketDataReport(
			final List<String> meDataValues) {

		final Date startTime = dp.getCounterTime(meDataValues, START_TIME_BYTE);
		final Date endTime = createEndTime(startTime, meDataValues);

		final int formatVersion = dp.getFormatVersion(meDataValues);

		final Map<String, Object> packetDataDXTReport = new HashMap<String, Object>();

		final Map<String, Map<String, Object>> packetDataReports = new HashMap<String, Map<String, Object>>();

		packetDataDXTReport.put(DxtPerformanceConstants.DISPLAY_NAME,
				"Packet Data Report");
		packetDataDXTReport.put(DxtPerformanceConstants.FORMAT_VERSION,
				formatVersion);
		packetDataDXTReport.put(DxtPerformanceConstants.REPORT_TYPE,
				PACKET_DATA_REPORT);
		packetDataDXTReport.put(DxtPerformanceConstants.CUSTOM_CLASS_NAME,
				"UIM_DXTPMPacketData");

		packetDataDXTReport
				.put(DxtPerformanceConstants.COUNTER_TIME, startTime);
		packetDataDXTReport.put(DxtPerformanceConstants.START_TIME, startTime);
		packetDataDXTReport.put(DxtPerformanceConstants.END_TIME, endTime);

		packetDataDXTReport.put("ContextCount",
				dp.createLongValue(0, meDataValues, usefulDataCounter));
		packetDataDXTReport.put("SuccessfulActivationCount",
				dp.createLongValue(4, meDataValues, usefulDataCounter));
		packetDataDXTReport.put("FailedGTPCount",
				dp.createLongValue(8, meDataValues, usefulDataCounter));
		packetDataDXTReport.put(DxtPerformanceConstants.DESCRIPTION,
				"Traffic Measurements : Packet Data");

		int recLength = 16;

		if (formatVersion == 2) {
			recLength = 36;
		} else if (formatVersion == 3) {
			recLength = 38;
		}

		if (formatVersion == 1) {

			final int tbs1FirstByte = usefulDataCounter + 12;

			for (int i = 0; i < getMeasurementsNumber(meDataValues); i++) {

				final int recDiff = tbs1FirstByte + i * recLength;
				final Map<String, Object> packetDataTBSReport = new HashMap<String, Object>();

				try {
					final int tbsIdentifier = dp.createIntValue(2,
							meDataValues, recDiff);

					if (checkTbsNumberValidity(tbsIdentifier, meDataValues)) {
						final String tbsId = tbsFirstPart + tbsIdentifier;

						packetDataTBSReport.put(
								DxtPerformanceConstants.DISPLAY_NAME,
								"Packet Data Report");
						packetDataTBSReport.put(
								DxtPerformanceConstants.DESCRIPTION,
								"Traffic Measurements : Packet Data");
						packetDataTBSReport.put(
								DxtPerformanceConstants.FORMAT_VERSION,
								formatVersion);
						packetDataTBSReport.put(
								DxtPerformanceConstants.REPORT_TYPE,
								PACKET_DATA_REPORT);
						packetDataTBSReport.put(
								DxtPerformanceConstants.CUSTOM_CLASS_NAME,
								"UIM_DXTPMTBSPacketData");

						packetDataTBSReport
								.put(DxtPerformanceConstants.COUNTER_TIME,
										startTime);
						packetDataTBSReport.put(
								DxtPerformanceConstants.START_TIME, startTime);
						packetDataTBSReport.put(
								DxtPerformanceConstants.END_TIME, endTime);

						packetDataTBSReport.put(
								DxtPerformanceConstants.TBS_IDENTIFIER,
								tbsIdentifier);
						packetDataTBSReport.put("TBSPacketsCount",
								dp.createLongValue(4, meDataValues, recDiff));
						packetDataTBSReport.put("PacketLenghtCount",
								dp.createLongValue(8, meDataValues, recDiff));
						packetDataTBSReport.put("UnsentPacketsCount",
								dp.createLongValue(12, meDataValues, recDiff));

						packetDataReports.put(tbsId, packetDataTBSReport);
					}
				} catch (final Exception e) {
					DXTPerformanceTestingTool.printLine("Can't get values for "
							+ i + " record : " + e.getMessage() + "\n");
				}
			}
		} else if (formatVersion >= 2) {
			packetDataDXTReport.put("RadioDeactivationCount",
					dp.createLongValue(12, meDataValues, usefulDataCounter));
			packetDataDXTReport.put("GGSNDeactivationCount",
					dp.createLongValue(16, meDataValues, usefulDataCounter));
			packetDataDXTReport.put("DXTDeactivationCount",
					dp.createLongValue(20, meDataValues, usefulDataCounter));
			packetDataDXTReport.put("PeakNumber",
					dp.createLongValue(24, meDataValues, usefulDataCounter));
			packetDataDXTReport.put("PeakTransmittingNumber",
					dp.createLongValue(28, meDataValues, usefulDataCounter));
			packetDataDXTReport.put("ActiveContextCount",
					dp.createLongValue(32, meDataValues, usefulDataCounter));

			final int dxtPartSize = 36;
			// 36 is the DXT part size
			final int tbs2FirstByte = usefulDataCounter + dxtPartSize;

			for (int i = 0; i < getMeasurementsNumber(meDataValues); i++) {

				final int recDiff = tbs2FirstByte + i * recLength;

				try {
					final Map<String, Object> packetDataTBSReport = new HashMap<String, Object>();

					final int tbsIdentifier = dp.createIntValue(2,
							meDataValues, recDiff);

					if (checkTbsNumberValidity(tbsIdentifier, meDataValues)) {
						final String tbsId = tbsFirstPart + tbsIdentifier;

						packetDataTBSReport.put(
								DxtPerformanceConstants.DISPLAY_NAME,
								"Packet Data Report");
						packetDataTBSReport.put(
								DxtPerformanceConstants.DESCRIPTION,
								"Traffic Measurements : Packet Data");
						packetDataTBSReport.put(
								DxtPerformanceConstants.FORMAT_VERSION,
								formatVersion);
						packetDataTBSReport.put(
								DxtPerformanceConstants.REPORT_TYPE,
								PACKET_DATA_REPORT);
						packetDataTBSReport.put(
								DxtPerformanceConstants.CUSTOM_CLASS_NAME,
								"UIM_DXTPMTBSPacketData");
						packetDataTBSReport.put(
								DxtPerformanceConstants.TBS_IDENTIFIER,
								tbsIdentifier);

						packetDataTBSReport
								.put(DxtPerformanceConstants.COUNTER_TIME,
										startTime);
						packetDataTBSReport.put(
								DxtPerformanceConstants.START_TIME, startTime);
						packetDataTBSReport.put(
								DxtPerformanceConstants.END_TIME, endTime);

						packetDataTBSReport.put(
								DxtPerformanceConstants.TBS_IDENTIFIER,
								tbsIdentifier);
						packetDataTBSReport.put("TBSPacketsCount",
								dp.createLongValue(4, meDataValues, recDiff));
						packetDataTBSReport.put("PacketLenghtCount",
								dp.createLongValue(8, meDataValues, recDiff));
						packetDataTBSReport.put("FailedPacketsCount",
								dp.createLongValue(12, meDataValues, recDiff));
						packetDataTBSReport.put("TransferredSentPacketsCount",
								dp.createLongValue(16, meDataValues, recDiff));
						packetDataTBSReport.put("TransferredBytesCount",
								dp.createLongValue(20, meDataValues, recDiff));
						packetDataTBSReport.put("TransferredRecPacketsCount",
								dp.createLongValue(24, meDataValues, recDiff));
						packetDataTBSReport.put("RecievedBytesCount",
								dp.createLongValue(28, meDataValues, recDiff));
						packetDataTBSReport.put("UnsentPacketsCount",
								dp.createLongValue(32, meDataValues, recDiff));

						if (formatVersion == 3) {
							packetDataTBSReport
									.put(DxtPerformanceConstants.LOCATION_AREA_ID,
											dp.createIntValue(36, meDataValues,
													recDiff));
						}

						packetDataReports.put(tbsId, packetDataTBSReport);
					}
				} catch (final Exception e) {
					DXTPerformanceTestingTool.printLine("Can't get values for "
							+ i + " record : " + e.getMessage() + "\n");
				}
			}

		}

		packetDataReports.put(DxtPerformanceConstants.DXT_IDENTIFIER,
				packetDataDXTReport);

		return packetDataReports;
	}

	/**
	 * This report provides additional TBS scale measurement data of the usage
	 * of radio resources
	 *
	 * @param meDataValues
	 * @return map for report creation
	 */

	private Map<String, Map<String, Object>> createRadioNetwork2Report(
			final List<String> meDataValues) {

		final int formatVersion = dp.getFormatVersion(meDataValues);

		final Map<String, Map<String, Object>> radioNetwork2Reports = new HashMap<String, Map<String, Object>>();

		final Date startTime = dp.getCounterTime(meDataValues, START_TIME_BYTE);
		final Date endTime = createEndTime(startTime, meDataValues);

		int recLength = 52;

		if (formatVersion == 2) {
			recLength = 54;
		}

		for (int i = 0; i < getMeasurementsNumber(meDataValues); i++) {

			final int recDiff = usefulDataCounter + recLength * i;

			try {
				final Map<String, Object> radioNetworkReport = new HashMap<String, Object>();

				final int tbsIdentifier = dp.createIntValue(2, meDataValues,
						recDiff);

				if (checkTbsNumberValidity(tbsIdentifier, meDataValues)) {
					final String tbsId = tbsFirstPart + tbsIdentifier;

					radioNetworkReport.put(
							DxtPerformanceConstants.DISPLAY_NAME,
							"Radio Network 2 Report");
					radioNetworkReport.put(DxtPerformanceConstants.DESCRIPTION,
							"Traffic Measurements : Radio Network 2");
					radioNetworkReport.put(
							DxtPerformanceConstants.FORMAT_VERSION,
							formatVersion);
					radioNetworkReport.put(DxtPerformanceConstants.REPORT_TYPE,
							RADIO_NETWORK2_REPORT);
					radioNetworkReport.put(
							DxtPerformanceConstants.CUSTOM_CLASS_NAME,
							"UIM_DXTPMTBSRadioNetwork2");

					radioNetworkReport.put(
							DxtPerformanceConstants.COUNTER_TIME, startTime);
					radioNetworkReport.put(DxtPerformanceConstants.START_TIME,
							startTime);
					radioNetworkReport.put(DxtPerformanceConstants.END_TIME,
							endTime);

					radioNetworkReport.put(
							DxtPerformanceConstants.TBS_IDENTIFIER,
							tbsIdentifier);
					radioNetworkReport.put("PacketDataReqCount",
							dp.createLongValue(4, meDataValues, recDiff));
					radioNetworkReport.put("IndividualCallReqCount",
							dp.createLongValue(8, meDataValues, recDiff));
					radioNetworkReport.put("GroupCallReqCount",
							dp.createLongValue(12, meDataValues, recDiff));
					radioNetworkReport.put("PacketDataResReqCount",
							dp.createLongValue(16, meDataValues, recDiff));
					radioNetworkReport.put("IndividualCallResReqCount",
							dp.createLongValue(20, meDataValues, recDiff));
					radioNetworkReport.put("GroupCallResReqCount",
							dp.createLongValue(24, meDataValues, recDiff));
					radioNetworkReport.put("DroppedPacketDataCount",
							dp.createLongValue(28, meDataValues, recDiff));
					radioNetworkReport.put("DroppedIndividualCallCount",
							dp.createLongValue(32, meDataValues, recDiff));
					radioNetworkReport.put("DroppedGroupCallCount",
							dp.createLongValue(36, meDataValues, recDiff));
					radioNetworkReport.put("DDCHCount",
							dp.createLongValue(40, meDataValues, recDiff));
					radioNetworkReport.put("DDCHQueuedCount",
							dp.createLongValue(44, meDataValues, recDiff));
					radioNetworkReport.put("DDCHDroppedCount",
							dp.createLongValue(48, meDataValues, recDiff));

					if (formatVersion == 2) {
						radioNetworkReport.put(
								DxtPerformanceConstants.LOCATION_AREA_ID,
								dp.createIntValue(52, meDataValues, recDiff));
					}

					radioNetwork2Reports.put(tbsId, radioNetworkReport);
				}
			} catch (final Exception e) {
				DXTPerformanceTestingTool
						.printLine("Can't get values for record : "
								+ e.getMessage() + "\n");
			}
		}

		return radioNetwork2Reports;
	}

	/**
	 * This report provides TBS scale measurement data of the usage of radio
	 * resources. One Radio Network record contains channel usage information of
	 * one TBS and the report can contain one or several such records. One Radio
	 * Network record contains channel usage information of one TBS and the
	 * report can contain one or several such records.
	 *
	 * @param meDataValues
	 * @return map for report creation
	 */
	private Map<String, Map<String, Object>> createRadioNetworkReport(
			final List<String> meDataValues) {

		final Date startTime = dp.getCounterTime(meDataValues, START_TIME_BYTE);
		final Date endTime = createEndTime(startTime, meDataValues);

		final int formatVersion = dp.getFormatVersion(meDataValues);

		final Map<String, Map<String, Object>> radioNetworkReports = new HashMap<String, Map<String, Object>>();

		int recLength = 68;

		if (formatVersion == 1) {
			recLength = 74;
		}

		if (formatVersion == 3) {
			recLength = 72;
		} else if (formatVersion == 4) {
			recLength = 74;
		}

		for (int i = 0; i < getMeasurementsNumber(meDataValues); i++) {

			final int recDiff = usefulDataCounter + recLength * i;
			final Map<String, Object> radioNetworkReport = new HashMap<String, Object>();

			try {
				final int tbsIdentifier = dp.createIntValue(2, meDataValues,
						recDiff);

				if (checkTbsNumberValidity(tbsIdentifier, meDataValues)) {
					final String tbsId = tbsFirstPart + tbsIdentifier;

					radioNetworkReport.put(
							DxtPerformanceConstants.FORMAT_VERSION,
							formatVersion);
					radioNetworkReport.put(DxtPerformanceConstants.REPORT_TYPE,
							RADIO_NETWORK_REPORT);
					radioNetworkReport.put(
							DxtPerformanceConstants.DISPLAY_NAME,
							"Radio Network Report");
					radioNetworkReport.put(DxtPerformanceConstants.DESCRIPTION,
							"Traffic Measurements : Radio Network");
					radioNetworkReport.put(
							DxtPerformanceConstants.CUSTOM_CLASS_NAME,
							"UIM_DXTPMTBSRadioNetwork");

					radioNetworkReport.put(
							DxtPerformanceConstants.COUNTER_TIME, startTime);
					radioNetworkReport.put(DxtPerformanceConstants.START_TIME,
							startTime);
					radioNetworkReport.put(DxtPerformanceConstants.END_TIME,
							endTime);

					radioNetworkReport.put(
							DxtPerformanceConstants.TBS_IDENTIFIER,
							tbsIdentifier);
					radioNetworkReport.put("UsableChannelsCount",
							dp.createLongValue(4, meDataValues, recDiff));
					radioNetworkReport.put("UpdatedUsableChannelsCount",
							dp.createLongValue(8, meDataValues, recDiff));
					radioNetworkReport.put("GroupCallsBusyTime",
							dp.createLongValue(12, meDataValues, recDiff));
					radioNetworkReport.put("IndividualCallsBusyTime",
							dp.createLongValue(16, meDataValues, recDiff));
					radioNetworkReport.put("DataTrafficBusyTime",
							dp.createLongValue(20, meDataValues, recDiff));
					radioNetworkReport.put("ChannelMainSlotsCount",
							dp.createLongValue(24, meDataValues, recDiff));
					radioNetworkReport.put("ChannelUplinkSlotsCount",
							dp.createLongValue(28, meDataValues, recDiff));
					radioNetworkReport.put("ChannelDownlinkSlotsCount",
							dp.createLongValue(32, meDataValues, recDiff));
					radioNetworkReport.put("ChannelRAUplinkSlots",
							dp.createLongValue(36, meDataValues, recDiff));
					radioNetworkReport.put("ReservationRequestCount",
							dp.createLongValue(40, meDataValues, recDiff));
					radioNetworkReport.put("QueuedReservationRequestCount",
							dp.createLongValue(44, meDataValues, recDiff));
					radioNetworkReport.put("SACCHCount",
							dp.createLongValue(48, meDataValues, recDiff));
					radioNetworkReport.put("FACCHCount",
							dp.createLongValue(52, meDataValues, recDiff));
					radioNetworkReport.put("RACollisionsCount",
							dp.createLongValue(56, meDataValues, recDiff));
					radioNetworkReport.put("QueuingTimeSum",
							dp.createLongValue(60, meDataValues, recDiff));
					radioNetworkReport.put("QueuingPeakValue",
							dp.createLongValue(64, meDataValues, recDiff));

					if (formatVersion >= 3) {
						radioNetworkReport.put("DDCHTime",
								dp.createLongValue(68, meDataValues, recDiff));

						if (formatVersion == 4) {
							radioNetworkReport
									.put(DxtPerformanceConstants.LOCATION_AREA_ID,
											dp.createIntValue(72, meDataValues,
													recDiff));
						}
					}

					radioNetworkReports.put(tbsId, radioNetworkReport);
				}
			} catch (final Exception e) {
				DXTPerformanceTestingTool.printLine("Can't get values for " + i
						+ " record : " + e.getMessage() + "\n");
			}
		}
		return radioNetworkReports;
	}

	/**
	 * @param meDataValues
	 * @param type
	 * @return measurement report map
	 */
	public Map<String, Map<String, Object>> createReport(
			final List<String> meDataValues, final String type) {
		DXTPerformanceTestingTool.printLine("Measurements number is : "
				+ getMeasurementsNumber(meDataValues) + "\n");

		dp.printReportLogInHex(meDataValues);

		Map<String, Map<String, Object>> report = new HashMap<String, Map<String, Object>>();

		if (isReportValid(meDataValues, type)) {
			if (type.equals(RADIO_NETWORK_REPORT)
					|| type.equals(RADIO_NETWORK_REPORT_128)) {
				report = createRadioNetworkReport(meDataValues);
			} else if (type.equals(INDIVIDUAL_CALL_REPORT)) {
				report = createIndividualCallReport(meDataValues);
			} else if (type.equals(GROUP_CALL_REPORT)) {
				report = createGroupCallReport(meDataValues);
			} else if (type.equals(GROUP_MANAGEMENT_REPORT)
					|| type.equals(GROUP_MANAGEMENT_REPORT_128)) {
				report = createGroupManagementReport(meDataValues);
			} else if (type.equals(HANDOVERS_REPORT)
					|| type.equals(HANDOVERS_REPORT_128)) {
				report = createHandoversRecordReport(meDataValues);
			} else if (type.equals(PACKET_DATA_REPORT)
					|| type.equals(PACKET_DATA_REPORT_128)) {
				report = createPacketDataReport(meDataValues);
			} else if (type.equals(SDS1_REPORT) || type.equals(SDS1_REPORT_128)) {
				report = createSDSReport(meDataValues);
			} else if (type.equals(SPEECH_LINE_REPORT)) {
				report = createSpeechLineReport(meDataValues);
			} else if (type.equals(MOBILITY_MANAGEMENT_REPORT)
					|| type.equals(MOBILITY_MANAGEMENT_REPORT_128)) {
				report = createMobilityManagementReport(meDataValues);
			} else if (type.equals(ISDN_REPORT)) {
				report = createISDNReport(meDataValues);
			} else if (type.equals(FNIM_REPORT)) {
				report = createFNIMReport(meDataValues);
			} else if (type.equals(HLR_REPORT)) {
				report = createHLRReport(meDataValues);
			} else if (type.equals(DXT_TBS_REPORT)
					|| type.equals(DXT_TBS_REPORT_128)) {
				report = createTBSLinkLoadReport(meDataValues);
			} else if (type.equals(RADIO_NETWORK2_REPORT)
					|| type.equals(RADIO_NETWORK2_REPORT_128)) {
				report = createRadioNetwork2Report(meDataValues);
			} else if (type.equals(SCCH_REPORT) || type.equals(SCCH_REPORT_128)) {
				report = createSCCHReport(meDataValues);
			} else if (type.equals(SDS2_REPORT)) {
				report = createSDS2Report(meDataValues);
			} else if (type.equals(SDS3_REPORT) || type.equals(SDS3_REPORT_128)) {
				report = createSDS3Report(meDataValues);
			} else if (type.equals(TEDS25_REPORT)
					|| type.equals(TEDS25_REPORT_128)) {
				report = createTEDS25Report(meDataValues);
			} else if (type.equals(TEDS50_REPORT)
					|| type.equals(TEDS50_REPORT_128)) {
				report = createTEDS50Report(meDataValues);
			}
		} else {
			System.err.println("Report " + type
					+ " is not valid, skipping it..");
		}
		return report;
	}

	/**
	 * This report provides TBS scale measurement data of the usage of SCCH
	 * resources.
	 *
	 * @param meDataValues
	 * @return map for report creation
	 */
	private Map<String, Map<String, Object>> createSCCHReport(
			final List<String> meDataValues) {

		final Map<String, Map<String, Object>> scchReports = new HashMap<String, Map<String, Object>>();

		final int formatVersion = dp.getFormatVersion(meDataValues);

		final Date startTime = dp.getCounterTime(meDataValues, START_TIME_BYTE);
		final Date endTime = createEndTime(startTime, meDataValues);

		// TODO doesn't match to req spec (length = 64). Suppose to be fixed on
		// DXT device
		int recLength = 76;

		if (formatVersion == 2) {
			recLength = 78;
		}

		for (int i = 0; i < getMeasurementsNumber(meDataValues); i++) {

			final int recDiff = i * recLength + usefulDataCounter;

			try {
				final Map<String, Object> scchReport = new HashMap<String, Object>();

				final int tbsIdentifier = dp.createIntValue(2, meDataValues,
						recDiff);

				if (checkTbsNumberValidity(tbsIdentifier, meDataValues)) {
					final String tbsId = tbsFirstPart + tbsIdentifier;

					scchReport.put(DxtPerformanceConstants.DISPLAY_NAME,
							"SCCH Report");
					scchReport.put(DxtPerformanceConstants.DESCRIPTION,
							"Traffic Measurements : SCCH");
					scchReport.put(DxtPerformanceConstants.FORMAT_VERSION,
							formatVersion);
					scchReport.put(DxtPerformanceConstants.REPORT_TYPE,
							SCCH_REPORT);
					scchReport.put(DxtPerformanceConstants.CUSTOM_CLASS_NAME,
							"UIM_DXTPMTBSSCCH");

					scchReport.put(DxtPerformanceConstants.COUNTER_TIME,
							startTime);
					scchReport.put(DxtPerformanceConstants.START_TIME,
							startTime);
					scchReport.put(DxtPerformanceConstants.END_TIME, endTime);

					scchReport.put(DxtPerformanceConstants.TBS_IDENTIFIER,
							tbsIdentifier);
					scchReport.put("SCCH1SentDownlinkSlotsCount",
							dp.createLongValue(4, meDataValues, recDiff));
					scchReport.put("SCCH1ReservedUplinkSlotsCount",
							dp.createLongValue(8, meDataValues, recDiff));
					scchReport.put("SCCH1UsedDownlinkSlotsCount",
							dp.createLongValue(12, meDataValues, recDiff));
					scchReport.put("SCCH1UsedUplinkSlotsCount",
							dp.createLongValue(16, meDataValues, recDiff));
					scchReport.put("SCCH1ColissionsCount",
							dp.createLongValue(20, meDataValues, recDiff));
					scchReport.put("SCCH2SentDownlinkSlotsCount",
							dp.createLongValue(24, meDataValues, recDiff));
					scchReport.put("SCCH2ReservedUplinkSlotsCount",
							dp.createLongValue(28, meDataValues, recDiff));
					scchReport.put("SCCH2UsedDownlinkSlotsCount",
							dp.createLongValue(32, meDataValues, recDiff));
					scchReport.put("SCCH2UsedUplinkSlotsCount",
							dp.createLongValue(36, meDataValues, recDiff));
					scchReport.put("SCCH2ColissionsCount",
							dp.createLongValue(40, meDataValues, recDiff));
					scchReport.put("SCCH3SentDownlinkSlotsCount",
							dp.createLongValue(44, meDataValues, recDiff));
					scchReport.put("SCCH3ReservedUplinkSlotsCount",
							dp.createLongValue(48, meDataValues, recDiff));
					scchReport.put("SCCH3UsedDownlinkSlotsCount",
							dp.createLongValue(52, meDataValues, recDiff));
					scchReport.put("SCCH3UsedUplinkSlotsCount",
							dp.createLongValue(56, meDataValues, recDiff));
					scchReport.put("SCCH3ColissionsCount",
							dp.createLongValue(60, meDataValues, recDiff));

					if (formatVersion == 2) {
						scchReport.put(
								DxtPerformanceConstants.LOCATION_AREA_ID,
								dp.createIntValue(64, meDataValues, recDiff));
					}

					scchReports.put(tbsId, scchReport);
				}
			} catch (final Exception e) {
				DXTPerformanceTestingTool
						.printLine("Can't get values for record : "
								+ e.getMessage() + "\n");
			}
		}

		return scchReports;
	}

	/**
	 * The Status and SDS Messages 2 report provides DXT scale measurement data
	 * of status and SDS message transferring
	 *
	 * @param meDataValues
	 * @return map for report creation
	 */
	private Map<String, Map<String, Object>> createSDS2Report(
			final List<String> meDataValues) {

		final Map<String, Object> sds2Report = new HashMap<String, Object>();
		final Map<String, Map<String, Object>> sds2Reports = new HashMap<String, Map<String, Object>>();

		final Date startTime = dp.getCounterTime(meDataValues, START_TIME_BYTE);
		final Date endTime = createEndTime(startTime, meDataValues);

		sds2Report.put(DxtPerformanceConstants.DISPLAY_NAME,
				"SDS2 Messages Report");
		sds2Report.put(DxtPerformanceConstants.DESCRIPTION,
				"Traffic Measurements : Status and SDS Messages 2");
		sds2Report.put(DxtPerformanceConstants.FORMAT_VERSION, 1);
		sds2Report.put(DxtPerformanceConstants.REPORT_TYPE, SDS2_REPORT);
		sds2Report.put(DxtPerformanceConstants.CUSTOM_CLASS_NAME,
				"UIM_DXTPMMessage2");

		sds2Report.put(DxtPerformanceConstants.COUNTER_TIME, startTime);
		sds2Report.put(DxtPerformanceConstants.START_TIME, startTime);
		sds2Report.put(DxtPerformanceConstants.END_TIME, endTime);

		sds2Report.put("GroupSDS1DataCount",
				dp.createLongValue(0, meDataValues, usefulDataCounter));
		sds2Report.put("IndividualSDS1DataCount",
				dp.createLongValue(4, meDataValues, usefulDataCounter));
		sds2Report.put("GroupSDS2DataCount",
				dp.createLongValue(8, meDataValues, usefulDataCounter));
		sds2Report.put("IndividualSDS2DataCount",
				dp.createLongValue(12, meDataValues, usefulDataCounter));
		sds2Report.put("GroupSDS1DataOtherCount",
				dp.createLongValue(16, meDataValues, usefulDataCounter));
		sds2Report.put("IndividualSDS1DataOtherCount",
				dp.createLongValue(20, meDataValues, usefulDataCounter));
		sds2Report.put("GroupSDS2DataOtherCount",
				dp.createLongValue(24, meDataValues, usefulDataCounter));
		sds2Report.put("IndividualSDS2DataOtherCount",
				dp.createLongValue(28, meDataValues, usefulDataCounter));
		sds2Report.put("FailedSDS1GroupDataCount",
				dp.createLongValue(32, meDataValues, usefulDataCounter));
		sds2Report.put("FailedSDS1IndividualDataCount",
				dp.createLongValue(36, meDataValues, usefulDataCounter));
		sds2Report.put("FailedSDS2GroupDataCount",
				dp.createLongValue(40, meDataValues, usefulDataCounter));
		sds2Report.put("FailedSDS2IndividualDataCount",
				dp.createLongValue(44, meDataValues, usefulDataCounter));
		sds2Report.put("DWSClientSDS1GroupCount",
				dp.createLongValue(48, meDataValues, usefulDataCounter));
		sds2Report.put("DWSClientSDS1IndivCount",
				dp.createLongValue(52, meDataValues, usefulDataCounter));
		sds2Report.put("DWSClientSDS2GroupCount",
				dp.createLongValue(56, meDataValues, usefulDataCounter));
		sds2Report.put("DWSClientSDS2IndivCount",
				dp.createLongValue(60, meDataValues, usefulDataCounter));
		sds2Report.put("FailedDWSClientSDS1GroupCount",
				dp.createLongValue(64, meDataValues, usefulDataCounter));
		sds2Report.put("FailedDWSClientSDS1IndivCount",
				dp.createLongValue(68, meDataValues, usefulDataCounter));
		sds2Report.put("FailedDWSClientSDS2GroupCount",
				dp.createLongValue(72, meDataValues, usefulDataCounter));
		sds2Report.put("FailedDWSClientSDS2IndivCount",
				dp.createLongValue(76, meDataValues, usefulDataCounter));
		sds2Report.put("OtherSDS1GroupDataCount",
				dp.createLongValue(80, meDataValues, usefulDataCounter));
		sds2Report.put("OtherSDS1IndivDataCount",
				dp.createLongValue(84, meDataValues, usefulDataCounter));
		sds2Report.put("OtherSDS2GroupDataCount",
				dp.createLongValue(88, meDataValues, usefulDataCounter));
		sds2Report.put("OtherSDS2IndivDataCount",
				dp.createLongValue(92, meDataValues, usefulDataCounter));

		sds2Reports.put(DxtPerformanceConstants.DXT_IDENTIFIER, sds2Report);

		return sds2Reports;
	}

	/**
	 * @param meDataValues
	 * @return map for report creation
	 */
	private Map<String, Map<String, Object>> createSDS3Report(
			final List<String> meDataValues) {

		final Map<String, Map<String, Object>> sds3Reports = new HashMap<String, Map<String, Object>>();

		final int formatVersion = dp.getFormatVersion(meDataValues);

		final Date startTime = dp.getCounterTime(meDataValues, START_TIME_BYTE);
		final Date endTime = createEndTime(startTime, meDataValues);

		int recLength = 56;

		if (formatVersion == 2) {
			recLength = 58;
		}

		for (int i = 0; i < getMeasurementsNumber(meDataValues); i++) {

			final int recDiff = i * recLength + usefulDataCounter;

			try {
				final Map<String, Object> sds3Report = new HashMap<String, Object>();

				final int tbsIdentifier = dp.createIntValue(2, meDataValues,
						recDiff);

				if (checkTbsNumberValidity(tbsIdentifier, meDataValues)) {
					final String tbsId = tbsFirstPart + tbsIdentifier;

					sds3Report.put(DxtPerformanceConstants.DISPLAY_NAME,
							"SDS3 Messages Report");
					sds3Report.put(DxtPerformanceConstants.FORMAT_VERSION,
							formatVersion);
					sds3Report.put(DxtPerformanceConstants.REPORT_TYPE,
							SDS3_REPORT);
					sds3Report.put(DxtPerformanceConstants.DESCRIPTION,
							"Traffic Measurements : Status and SDS Messages 3");
					sds3Report.put(DxtPerformanceConstants.CUSTOM_CLASS_NAME,
							"UIM_DXTPMTBSMessage3");

					sds3Report.put(DxtPerformanceConstants.COUNTER_TIME,
							startTime);
					sds3Report.put(DxtPerformanceConstants.START_TIME,
							startTime);
					sds3Report.put(DxtPerformanceConstants.END_TIME, endTime);

					sds3Report.put(DxtPerformanceConstants.TBS_IDENTIFIER,
							tbsIdentifier);
					sds3Report.put("SDS1GroupFromCount",
							dp.createLongValue(4, meDataValues, recDiff));
					sds3Report.put("SDS1IndividualFromCount",
							dp.createLongValue(8, meDataValues, recDiff));
					sds3Report.put("SDS2GroupFromCount",
							dp.createLongValue(12, meDataValues, recDiff));
					sds3Report.put("SDS2IndividualFromCount",
							dp.createLongValue(16, meDataValues, recDiff));
					sds3Report.put("SDS1GroupToCount",
							dp.createLongValue(20, meDataValues, recDiff));
					sds3Report.put("SDS1IndividualToCount",
							dp.createLongValue(24, meDataValues, recDiff));
					sds3Report.put("SDS2GroupToCount",
							dp.createLongValue(28, meDataValues, recDiff));
					sds3Report.put("SDS2IndividualToCount",
							dp.createLongValue(32, meDataValues, recDiff));
					sds3Report.put("SDS1FailedGrouplCount",
							dp.createLongValue(36, meDataValues, recDiff));
					sds3Report.put("SDS1FailedIndivCount",
							dp.createLongValue(40, meDataValues, recDiff));
					sds3Report.put("SDS2FailedGroupCount",
							dp.createLongValue(44, meDataValues, recDiff));
					sds3Report.put("SDS2FailedIndivGroupCount",
							dp.createLongValue(48, meDataValues, recDiff));
					sds3Report.put("AVLMessagedCount",
							dp.createLongValue(52, meDataValues, recDiff));

					if (formatVersion == 2) {
						sds3Report.put(
								DxtPerformanceConstants.LOCATION_AREA_ID,
								dp.createIntValue(56, meDataValues, recDiff));
					}

					sds3Reports.put(tbsId, sds3Report);
				}
			} catch (final Exception e) {
				DXTPerformanceTestingTool
						.printLine("Can't get values for record : "
								+ e.getMessage() + "\n");
			}
		}
		return sds3Reports;
	}

	/**
	 * The Status and SDS Messages report provides DXT and TBS scale measurement
	 * data of status and SDS message transferring. Thus there are two different
	 * record types in this report, the DXT Status and SDS Messages record and
	 * the TBS Status and SDS Messages record. The TBS record consists of two
	 * different sub-records and they (up to 128) are located after the DXT
	 * record.
	 *
	 * @param meDataValues
	 * @return map for report creation
	 */

	private Map<String, Map<String, Object>> createSDSReport(
			final List<String> meDataValues) {

		final Map<String, Map<String, Object>> sdsReports = new HashMap<String, Map<String, Object>>();

		final Map<String, Object> sdsReportDXT = new HashMap<String, Object>();

		final int formatVersion = dp.getFormatVersion(meDataValues);

		final Date startTime = dp.getCounterTime(meDataValues, START_TIME_BYTE);
		final Date endTime = createEndTime(startTime, meDataValues);

		sdsReportDXT.put(DxtPerformanceConstants.DISPLAY_NAME,
				"SDS Messages Report");
		sdsReportDXT.put(DxtPerformanceConstants.DESCRIPTION,
				"Traffic Measurements : Status and SDS Messages");
		sdsReportDXT.put(DxtPerformanceConstants.FORMAT_VERSION, formatVersion);
		sdsReportDXT.put(DxtPerformanceConstants.REPORT_TYPE, SDS1_REPORT);
		sdsReportDXT.put(DxtPerformanceConstants.CUSTOM_CLASS_NAME,
				"UIM_DXTPMMessage");

		sdsReportDXT.put(DxtPerformanceConstants.COUNTER_TIME, startTime);
		sdsReportDXT.put(DxtPerformanceConstants.START_TIME, startTime);
		sdsReportDXT.put(DxtPerformanceConstants.END_TIME, endTime);

		sdsReportDXT.put("SDS1Count",
				dp.createLongValue(0, meDataValues, usefulDataCounter));
		sdsReportDXT.put("SDS2Count",
				dp.createLongValue(4, meDataValues, usefulDataCounter));
		sdsReportDXT.put("SDS1FailedIndividualCount",
				dp.createLongValue(8, meDataValues, usefulDataCounter));
		sdsReportDXT.put("SDS1FailedGroupCount",
				dp.createLongValue(12, meDataValues, usefulDataCounter));
		sdsReportDXT.put("SDS2FailedIndividualCount",
				dp.createLongValue(16, meDataValues, usefulDataCounter));
		sdsReportDXT.put("SDS2FailedGroupCount",
				dp.createLongValue(20, meDataValues, usefulDataCounter));

		final int dxtPartLength = 24;

		int recLength = 28;

		if (formatVersion == 2) {
			recLength = 30;
		}

		for (int i = 0; i < getMeasurementsNumber(meDataValues); i++) {

			final int recDiff = i * recLength + usefulDataCounter
					+ dxtPartLength;

			try {
				final Map<String, Object> sdsReportTBS = new HashMap<String, Object>();

				final int tbsIdentifier = dp.createIntValue(2, meDataValues,
						recDiff);
				if (checkTbsNumberValidity(tbsIdentifier, meDataValues)) {
					final String tbsId = tbsFirstPart + tbsIdentifier;

					sdsReportTBS.put(DxtPerformanceConstants.DISPLAY_NAME,
							"SDS Messages Report");
					sdsReportTBS.put(DxtPerformanceConstants.FORMAT_VERSION, 1);
					sdsReportTBS.put(DxtPerformanceConstants.DESCRIPTION,
							"Traffic Measurements : Status and SDS Messages");
					sdsReportTBS.put(DxtPerformanceConstants.REPORT_TYPE,
							SDS1_REPORT);
					sdsReportTBS.put(DxtPerformanceConstants.CUSTOM_CLASS_NAME,
							"UIM_DXTPMTBSMessage");

					sdsReportTBS.put(DxtPerformanceConstants.COUNTER_TIME,
							startTime);
					sdsReportTBS.put(DxtPerformanceConstants.START_TIME,
							startTime);
					sdsReportTBS.put(DxtPerformanceConstants.END_TIME, endTime);

					sdsReportTBS.put(DxtPerformanceConstants.TBS_IDENTIFIER,
							tbsIdentifier);
					sdsReportTBS.put("SDS1Count",
							dp.createLongValue(4, meDataValues, recDiff));
					sdsReportTBS.put("SDS2Count",
							dp.createLongValue(8, meDataValues, recDiff));
					sdsReportTBS.put("SDS1FailedIndividualCount",
							dp.createLongValue(12, meDataValues, recDiff));
					sdsReportTBS.put("SDS1FailedGroupCount",
							dp.createLongValue(16, meDataValues, recDiff));
					sdsReportTBS.put("SDS2FailedIndividualCount",
							dp.createLongValue(20, meDataValues, recDiff));
					sdsReportTBS.put("SDS2FailedGroupCount",
							dp.createLongValue(24, meDataValues, recDiff));

					if (formatVersion == 2) {
						sdsReportTBS.put(
								DxtPerformanceConstants.LOCATION_AREA_ID,
								dp.createIntValue(28, meDataValues, recDiff));
					}

					sdsReports.put(tbsId, sdsReportTBS);
				}
			} catch (final Exception e) {
				DXTPerformanceTestingTool
						.printLine("Can't get values for record : "
								+ e.getMessage() + "\n");
			}
		}

		sdsReports.put(DxtPerformanceConstants.DXT_IDENTIFIER, sdsReportDXT);
		return sdsReports;
	}

	/**
	 * This report provides DXT-DXT scale measurement data of speech line
	 * reservations. The measurement in this report provides the operator with
	 * information on the load of the inter-DXT speech lines. The counters from
	 * byte 24 to byte 31 appear only if the version of format in the header is
	 * 2.
	 *
	 * @param meDataValues
	 * @return map for report creation
	 */
	private Map<String, Map<String, Object>> createSpeechLineReport(
			final List<String> meDataValues) {

		final Map<String, Map<String, Object>> speechLineReports = new HashMap<String, Map<String, Object>>();

		final Date startTime = dp.getCounterTime(meDataValues, START_TIME_BYTE);
		final Date endTime = createEndTime(startTime, meDataValues);

		final int formatVersion = dp.getFormatVersion(meDataValues);

		Integer recordLength = 24;

		if (formatVersion == 2) {
			recordLength = 32;
		}

		for (int i = 0; i < getMeasurementsNumber(meDataValues); i++) {

			final Map<String, Object> speechLineReport = new HashMap<String, Object>();

			final int recDiff = usefulDataCounter + recordLength * i;

			final String dxtId = dp.createHexValue(0, meDataValues, recDiff);
			speechLineReport.put(DxtPerformanceConstants.DISPLAY_NAME, dxtId);
			speechLineReport
					.put(DxtPerformanceConstants.DESCRIPTION,
							"Traffic Measurements : Speech Line Reservations Between DXTs.Hex representation of dxt identifier");
			speechLineReport.put(DxtPerformanceConstants.FORMAT_VERSION,
					formatVersion);
			speechLineReport.put(DxtPerformanceConstants.REPORT_TYPE,
					SPEECH_LINE_REPORT);
			speechLineReport.put(DxtPerformanceConstants.CUSTOM_CLASS_NAME,
					"UIM_DXTPMSpeechLine");

			speechLineReport.put(DxtPerformanceConstants.COUNTER_TIME,
					startTime);
			speechLineReport.put(DxtPerformanceConstants.START_TIME, startTime);
			speechLineReport.put(DxtPerformanceConstants.END_TIME, endTime);

			speechLineReport.put("DXTIdentifier", dxtId);
			speechLineReport.put("SucReservationsCount",
					dp.createLongValue(4, meDataValues, recDiff));
			speechLineReport.put("UnsucReservReservedCount",
					dp.createLongValue(8, meDataValues, recDiff));
			speechLineReport.put("UnsucReservFaultCount",
					dp.createLongValue(12, meDataValues, recDiff));
			speechLineReport.put("SpeechLinesCount",
					dp.createLongValue(16, meDataValues, recDiff));
			speechLineReport.put("ReservationTime",
					dp.createLongValue(20, meDataValues, recDiff));

			if (formatVersion == 2) {
				speechLineReport.put("CongestionTime",
						dp.createLongValue(24, meDataValues, recDiff));
				speechLineReport.put("LowestLinesCount",
						dp.createLongValue(28, meDataValues, recDiff));
			}

			speechLineReports.put(dxtId, speechLineReport);
		}

		return speechLineReports;
	}

	/**
	 * The DXT-TBS link load report provides TBS scale measurement data of usage
	 * of DXT-TBS link resources.
	 *
	 * @param meDataValues
	 * @return map for report creation
	 */
	private Map<String, Map<String, Object>> createTBSLinkLoadReport(
			final List<String> meDataValues) {

		final Map<String, Map<String, Object>> dxtTbsReports = new HashMap<String, Map<String, Object>>();

		final Date startTime = dp.getCounterTime(meDataValues, START_TIME_BYTE);
		final Date endTime = createEndTime(startTime, meDataValues);

		final int formatVersion = dp.getFormatVersion(meDataValues);

		int dxtTbsRecordLength = 36;

		if (formatVersion == 2) {
			dxtTbsRecordLength = 38;
		}

		for (int i = 0; i < getMeasurementsNumber(meDataValues); i++) {

			final Map<String, Object> dxtTbsReport = new HashMap<String, Object>();

			final int recDiff = usefulDataCounter + dxtTbsRecordLength * i;

			dxtTbsReport.put(DxtPerformanceConstants.DISPLAY_NAME,
					"Link Load Report");
			dxtTbsReport.put(DxtPerformanceConstants.DESCRIPTION,
					"Traffic Measurements : DXT-TBS Link Load");
			dxtTbsReport.put(DxtPerformanceConstants.FORMAT_VERSION,
					formatVersion);
			dxtTbsReport.put(DxtPerformanceConstants.REPORT_TYPE,
					DXT_TBS_REPORT);
			dxtTbsReport.put(DxtPerformanceConstants.CUSTOM_CLASS_NAME,
					"UIM_DXTPMTBSLinkLoad");

			dxtTbsReport.put(DxtPerformanceConstants.COUNTER_TIME, startTime);
			dxtTbsReport.put(DxtPerformanceConstants.START_TIME, startTime);
			dxtTbsReport.put(DxtPerformanceConstants.END_TIME, endTime);

			try {
				final int tbsIdentifier = dp.createIntValue(2, meDataValues,
						recDiff);
				if (checkTbsNumberValidity(tbsIdentifier, meDataValues)) {
					final String tbsId = tbsFirstPart + tbsIdentifier;

					dxtTbsReport.put(DxtPerformanceConstants.TBS_IDENTIFIER,
							tbsIdentifier);

					dxtTbsReport.put("TransmittedFramesCount",
							dp.createLongValue(4, meDataValues, recDiff));
					dxtTbsReport.put("ReceivedFramesCount",
							dp.createLongValue(8, meDataValues, recDiff));
					dxtTbsReport.put("TransmittedFrameOctetsCount",
							dp.createLongValue(12, meDataValues, recDiff));
					dxtTbsReport.put("ReceivedFrameOctetsCount",
							dp.createLongValue(16, meDataValues, recDiff));
					dxtTbsReport.put("TotalTransmittedOctetsCount",
							dp.createLongValue(20, meDataValues, recDiff));
					dxtTbsReport.put("TotalReceivedOctetsCount",
							dp.createLongValue(24, meDataValues, recDiff));
					dxtTbsReport.put("EstablishmentFramesCount",
							dp.createLongValue(28, meDataValues, recDiff));
					dxtTbsReport.put("ReleaseFramesCount",
							dp.createLongValue(32, meDataValues, recDiff));

					if (formatVersion == 2) {
						dxtTbsReport.put(
								DxtPerformanceConstants.LOCATION_AREA_ID,
								dp.createIntValue(36, meDataValues, recDiff));
					}

					dxtTbsReports.put(tbsId, dxtTbsReport);
				}
			} catch (final Exception e) {
				DXTPerformanceTestingTool.printLine("Can't get values for " + i
						+ "record : " + e.getMessage() + "\n");
			}
		}

		return dxtTbsReports;
	}

	/**
	 * This report provides TBS scale measurement data of the usage of TEDS 25
	 * kHz resources. One TEDS Statistics 25 kHz record contains channel usage
	 * information of one TBS and the report can contain one or several such
	 * records.
	 *
	 * @param meDataValues
	 * @return map for report creation
	 */
	private Map<String, Map<String, Object>> createTEDS25Report(
			final List<String> meDataValues) {

		final Map<String, Map<String, Object>> teds25Reports = new HashMap<String, Map<String, Object>>();
		final Map<String, Object> teds25Report = new HashMap<String, Object>();

		final Date startTime = dp.getCounterTime(meDataValues, START_TIME_BYTE);
		final Date endTime = createEndTime(startTime, meDataValues);

		final int formatVersion = dp.getFormatVersion(meDataValues);

		try {
			final int tbsIdentifier = dp.createIntValue(2, meDataValues,
					usefulDataCounter);
			if (checkTbsNumberValidity(tbsIdentifier, meDataValues)) {
				final String tbsId = tbsFirstPart + tbsIdentifier;

				teds25Report.put(DxtPerformanceConstants.DISPLAY_NAME,
						"TEDS 25Khz Report");
				teds25Report.put(DxtPerformanceConstants.DESCRIPTION,
						"Traffic Measurements : TEDS Statistics Report 25 KHz");
				teds25Report.put(DxtPerformanceConstants.FORMAT_VERSION,
						formatVersion);
				teds25Report.put(DxtPerformanceConstants.REPORT_TYPE,
						TEDS25_REPORT);
				teds25Report.put(DxtPerformanceConstants.CUSTOM_CLASS_NAME,
						"UIM_DXTPMTBSTEDS25Khz");

				teds25Report.put(DxtPerformanceConstants.COUNTER_TIME,
						startTime);
				teds25Report.put(DxtPerformanceConstants.START_TIME, startTime);
				teds25Report.put(DxtPerformanceConstants.END_TIME, endTime);

				teds25Report.put(DxtPerformanceConstants.TBS_IDENTIFIER,
						tbsIdentifier);
				teds25Report.put("SentDownlinkHalfslotsCoount",
						dp.createLongValue(4, meDataValues, usefulDataCounter));
				teds25Report.put("QAM4HUsedCount",
						dp.createLongValue(8, meDataValues, usefulDataCounter));
				teds25Report
						.put("QAM16HUsedCount", dp.createLongValue(12,
								meDataValues, usefulDataCounter));
				teds25Report
						.put("QAM64HUsedCount", dp.createLongValue(16,
								meDataValues, usefulDataCounter));
				teds25Report
						.put("QAM64MUsedCount", dp.createLongValue(20,
								meDataValues, usefulDataCounter));
				teds25Report
						.put("BroadcastHalfslotsCount", dp.createLongValue(24,
								meDataValues, usefulDataCounter));
				teds25Report
						.put("RandomAcessCount", dp.createLongValue(28,
								meDataValues, usefulDataCounter));
				teds25Report
						.put("CollisionsCount", dp.createLongValue(32,
								meDataValues, usefulDataCounter));
				teds25Report
						.put("QAM4HReceivedCount", dp.createLongValue(36,
								meDataValues, usefulDataCounter));
				teds25Report
						.put("QAM16HReceivedCount", dp.createLongValue(40,
								meDataValues, usefulDataCounter));
				teds25Report
						.put("QAM64HReceivedCount", dp.createLongValue(44,
								meDataValues, usefulDataCounter));
				teds25Report
						.put("QAM64MReceivedCount", dp.createLongValue(48,
								meDataValues, usefulDataCounter));
				teds25Report
						.put("UnusedHalfslotsCount", dp.createLongValue(52,
								meDataValues, usefulDataCounter));

				if (formatVersion == 2) {
					teds25Report.put(DxtPerformanceConstants.LOCATION_AREA_ID,
							dp.createIntValue(54, meDataValues,
									usefulDataCounter));
				}

				teds25Reports.put(tbsId, teds25Report);
			}
		} catch (final Exception e) {
			DXTPerformanceTestingTool
					.printLine("Can't get values for record : "
							+ e.getMessage() + "\n");
		}
		return teds25Reports;
	}

	/**
	 * This report provides TBS scale measurement data of the usage of TEDS 50
	 * kHz resources. One TEDS Statistics 50 kHz record contains channel usage
	 * information of one TBS and the report can contain one or several such
	 * records.
	 *
	 * @param meDataValues
	 * @return map for report creation
	 */
	private Map<String, Map<String, Object>> createTEDS50Report(
			final List<String> meDataValues) {

		final Map<String, Map<String, Object>> teds50Reports = new HashMap<String, Map<String, Object>>();
		final Map<String, Object> teds50Report = new HashMap<String, Object>();

		final int formatVersion = dp.getFormatVersion(meDataValues);

		try {
			final int tbsIdentifier = dp.createIntValue(2, meDataValues,
					usefulDataCounter);
			if (checkTbsNumberValidity(tbsIdentifier, meDataValues)) {
				final String tbsId = tbsFirstPart + tbsIdentifier;

				final Date startTime = dp.getCounterTime(meDataValues,
						START_TIME_BYTE);
				final Date endTime = createEndTime(startTime, meDataValues);

				teds50Report.put(DxtPerformanceConstants.DISPLAY_NAME,
						"TEDS 50Khz Report");
				teds50Report.put(DxtPerformanceConstants.DESCRIPTION,
						"Traffic Measurements : TEDS Statistics Report 50 KHz");
				teds50Report.put(DxtPerformanceConstants.FORMAT_VERSION,
						formatVersion);
				teds50Report.put(DxtPerformanceConstants.REPORT_TYPE,
						TEDS50_REPORT);
				teds50Report.put(DxtPerformanceConstants.CUSTOM_CLASS_NAME,
						"UIM_DXTPMTBSTEDS50Khz");

				teds50Report.put(DxtPerformanceConstants.COUNTER_TIME,
						startTime);
				teds50Report.put(DxtPerformanceConstants.START_TIME, startTime);
				teds50Report.put(DxtPerformanceConstants.END_TIME, endTime);

				teds50Report.put(DxtPerformanceConstants.TBS_IDENTIFIER,
						tbsIdentifier);
				teds50Report.put("SentDownlinkHalfslotsCount",
						dp.createLongValue(4, meDataValues, usefulDataCounter));
				teds50Report.put("QAM4HUsedCount",
						dp.createLongValue(8, meDataValues, usefulDataCounter));
				teds50Report
						.put("QAM16HUsedCount", dp.createLongValue(12,
								meDataValues, usefulDataCounter));
				teds50Report
						.put("QAM64HUsedCount", dp.createLongValue(16,
								meDataValues, usefulDataCounter));
				teds50Report
						.put("QAM64MUsedCount", dp.createLongValue(20,
								meDataValues, usefulDataCounter));
				teds50Report
						.put("BroadcastCount", dp.createLongValue(24,
								meDataValues, usefulDataCounter));
				teds50Report
						.put("RandomAcessCount", dp.createLongValue(28,
								meDataValues, usefulDataCounter));
				teds50Report
						.put("CollisionsCount", dp.createLongValue(32,
								meDataValues, usefulDataCounter));
				teds50Report
						.put("QAM4HReceivedCount", dp.createLongValue(36,
								meDataValues, usefulDataCounter));
				teds50Report
						.put("QAM16HReceivedCount", dp.createLongValue(40,
								meDataValues, usefulDataCounter));
				teds50Report
						.put("QAM64HReceivedCount", dp.createLongValue(44,
								meDataValues, usefulDataCounter));
				teds50Report
						.put("QAM64MReceivedCount", dp.createLongValue(48,
								meDataValues, usefulDataCounter));
				teds50Report
						.put("UnusedHalfslotsCount", dp.createLongValue(52,
								meDataValues, usefulDataCounter));
				teds50Report
						.put("AcceptedLinkCount", dp.createLongValue(56,
								meDataValues, usefulDataCounter));
				teds50Report
						.put("RejectedLinkCount", dp.createLongValue(60,
								meDataValues, usefulDataCounter));
				teds50Report
						.put("PacketDataUsersPeakValue", dp.createLongValue(64,
								meDataValues, usefulDataCounter));

				if (formatVersion == 2) {
					teds50Report.put(DxtPerformanceConstants.LOCATION_AREA_ID,
							dp.createIntValue(68, meDataValues,
									usefulDataCounter));
				}

				teds50Reports.put(tbsId, teds50Report);
			}
		} catch (final Exception e) {
			DXTPerformanceTestingTool
					.printLine("Can't get values for record : "
							+ e.getMessage() + "\n");
		}

		return teds50Reports;
	}

	private Integer getMeasurementsNumber(final List<String> meDataValues) {
		return dp.createIntValue(MEAS_NUMBER, meDataValues, 0);
	}

	/*
	 * Tbs report is not valid if Status byte (56) is FF (Note ISDN report does
	 * not use this field)
	 */
	private Boolean isReportValid(final List<String> meDataValues,
			final String type) {
		if (meDataValues.size() > validityByte) {
			final String value = meDataValues.get(validityByte);

			if (value.equals("FF") && !type.equals(ISDN_REPORT)) {
				return false;
			}

			return true;
		}
		return false;
	}
}
