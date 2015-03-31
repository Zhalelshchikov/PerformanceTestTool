package com.harris.netboss.recordscreator.parsers;

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
public class MTPReportsParser {

	private static int START_TIME_BYTE = 36;

	private static int END_TIME_BYTE = 44;

	// If report contain linkType, it's currently on 2 byte of the report value
	// (without block headers)
	private static int LINK_TYPE_BYTE = 2;

	private final DataParser dp;

	private static final String TDM_LINK_TYPE = "TDM";

	private static final String ATM_LINK_TYPE = "ATM";

	private static final String UNKNOWN_LINK_TYPE = "UNKNOWN";

	private static final Integer MEAS_NUMBER_INDEX = 32;

	private static final Integer LINK_REPORT_START_INDEX = 52;

	private static final Integer POINTS_REPORT_START_INDEX = 57;

	private static final Integer MATRIX_REPORT_START_INDEX = 67;

	private static final String SIGLINK = "SIGLINK";

	private static final String LINKTYPE = "LinkType";

	private static final String USERPART = "UserPart";

	private static final String SIGPOINT = "SignallingPoint";

	// MTP Statistics
	static final String MTP_AVAILABILITY_LINKS = "00D0";
	static final String MTP_PERFORMANCE_LINKS = "00D1";
	static final String MTP_UTILIZATION_LINKS = "00D2";
	static final String MTP_STATUS_POINTS = "00D3";
	static final String MTP_AVAILABILITY_POINTS = "00D4";
	static final String MTP_SIGNALING_POINTS = "00D5";
	static final String MTP_SIGNALING_USERPARTS = "00D6";
	static final String MTP_SIGNALING_MATRIX = "00D7";

	/** Set of all possible mtp reports */
	static public Map<String, List<Integer>> mtpReportsTypes;
	static {
		mtpReportsTypes = new HashMap<String, List<Integer>>();
		{
			mtpReportsTypes.put(MTP_AVAILABILITY_LINKS,
					DxtPerformanceConstants.format0);
			mtpReportsTypes.put(MTP_PERFORMANCE_LINKS,
					DxtPerformanceConstants.format0);
			mtpReportsTypes.put(MTP_UTILIZATION_LINKS,
					DxtPerformanceConstants.format0);
			mtpReportsTypes.put(MTP_STATUS_POINTS,
					DxtPerformanceConstants.format0);
			mtpReportsTypes.put(MTP_AVAILABILITY_POINTS,
					DxtPerformanceConstants.format0);
			mtpReportsTypes.put(MTP_SIGNALING_POINTS,
					DxtPerformanceConstants.format0);
			mtpReportsTypes.put(MTP_SIGNALING_USERPARTS,
					DxtPerformanceConstants.format0);
			mtpReportsTypes.put(MTP_SIGNALING_MATRIX,
					DxtPerformanceConstants.format0);
		}
	}

	static public Map<String, String> repNumberToReportFolderMap;

	static {
		repNumberToReportFolderMap = new HashMap<String, String>();
		{
			repNumberToReportFolderMap.put(MTP_AVAILABILITY_LINKS,
					"Availability Of Signalling Links");
			repNumberToReportFolderMap.put(MTP_UTILIZATION_LINKS,
					"Utilization Of Signalling Links");
			repNumberToReportFolderMap.put(MTP_PERFORMANCE_LINKS,
					"Performance Of Signalling Links");
			repNumberToReportFolderMap.put(MTP_AVAILABILITY_POINTS,
					"Availability Of Signalling Points");
			repNumberToReportFolderMap.put(MTP_SIGNALING_POINTS,
					"Signalling Traffic Of Signalling Points");
			repNumberToReportFolderMap.put(MTP_STATUS_POINTS,
					"Status Of Signalling Points");
			repNumberToReportFolderMap.put(MTP_SIGNALING_USERPARTS,
					"Signalling Traffic Of User Parts");
			repNumberToReportFolderMap.put(MTP_SIGNALING_MATRIX,
					"Signalling Traffic Matrix");

		}
	}

	/**
	 * @param manager
	 */
	public MTPReportsParser() {
		this.dp = new DataParser();
	}

	/**
	 *
	 * This report shows how long (days, hours, minutes and seconds) a signaling
	 * link has been unavailable due to various causes.
	 *
	 * @param meDataValues
	 * @return map for creating mtp report
	 */
	public Map<String, Map<String, Object>> createMTPLinkAvailabilityReport(
			final List<String> meDataValues) {

		final Map<String, Map<String, Object>> availabilityReports = new HashMap<String, Map<String, Object>>();

		final Date startTime = dp.getCounterTime(meDataValues, START_TIME_BYTE);
		final Date endTime = dp.getCounterTime(meDataValues, END_TIME_BYTE);

		for (Integer i = 0; i < getMeasurementsNumber(meDataValues); i++) {

			final int reportDiff = LINK_REPORT_START_INDEX + i * 67;

			final Map<String, Object> availabilityReport = new HashMap<String, Object>();
			final long sigLink = dp.createIntValue(0, meDataValues, reportDiff);

			availabilityReport.put(DxtPerformanceConstants.DISPLAY_NAME,
					SIGLINK + sigLink);

			availabilityReport.put(LINKTYPE,
					getLinkType(meDataValues, reportDiff));

			availabilityReport.put(DxtPerformanceConstants.DESCRIPTION,
					"MTP Statistics : MTP Availability of Signalling Links");
			availabilityReport.put(DxtPerformanceConstants.REPORT_TYPE,
					MTP_AVAILABILITY_LINKS);
			availabilityReport.put(DxtPerformanceConstants.CUSTOM_CLASS_NAME,
					"UIM_DXTPMMTPLinkAvailability");

			availabilityReport.put(DxtPerformanceConstants.COUNTER_TIME,
					startTime);
			availabilityReport.put(DxtPerformanceConstants.START_TIME,
					startTime);
			availabilityReport.put(DxtPerformanceConstants.END_TIME, endTime);

			availabilityReport.put("LinkName", sigLink);
			availabilityReport.put("DurationAnyReason",
					dp.createFormattedDate(3, 6, meDataValues, reportDiff));
			availabilityReport.put("DurationLinkFailure",
					dp.createFormattedDate(7, 10, meDataValues, reportDiff));
			availabilityReport.put("DurationLocalBlocking",
					dp.createFormattedDate(11, 14, meDataValues, reportDiff));
			availabilityReport.put("DurationProcessorOutage",
					dp.createFormattedDate(15, 18, meDataValues, reportDiff));
			availabilityReport.put("DurationLocalManageActions",
					dp.createFormattedDate(19, 22, meDataValues, reportDiff));
			availabilityReport.put("DurationRemoteManageActions",
					dp.createFormattedDate(23, 26, meDataValues, reportDiff));
			availabilityReport.put("DurationLocalBusy",
					dp.createFormattedDate(27, 30, meDataValues, reportDiff));
			availabilityReport.put("LocalChangeovers",
					dp.createLongValue(31, meDataValues, reportDiff));
			availabilityReport.put("RemoteChangeovers",
					dp.createLongValue(35, meDataValues, reportDiff));
			availabilityReport.put("StartOutage",
					dp.createLongValue(39, meDataValues, reportDiff));
			availabilityReport.put("StopOutage",
					dp.createLongValue(43, meDataValues, reportDiff));
			availabilityReport.put("LocalInhibit",
					dp.createLongValue(47, meDataValues, reportDiff));
			availabilityReport.put("LocalUninhibit",
					dp.createLongValue(51, meDataValues, reportDiff));
			availabilityReport.put("RemoteInhibitionStart",
					dp.createLongValue(55, meDataValues, reportDiff));
			availabilityReport.put("RemoteInhibitionEnd",
					dp.createLongValue(59, meDataValues, reportDiff));
			availabilityReport.put("LocalBusyDuration",
					dp.createLongValue(63, meDataValues, reportDiff));

			availabilityReports.put(i.toString(), availabilityReport);
		}

		return availabilityReports;
	}

	/**
	 * This report shows how long (days, hours, minutes and seconds) a signaling
	 * link has been in in-service state. It also shows the number of signaling
	 * link failures and their reasons.
	 *
	 * @param meDataValues
	 * @return map for creating mtp report
	 */
	public Map<String, Map<String, Object>> createMTPLinkPerformanceReport(
			final List<String> meDataValues) {

		final Map<String, Map<String, Object>> perfLinksReports = new HashMap<String, Map<String, Object>>();

		final Date startTime = dp.getCounterTime(meDataValues, START_TIME_BYTE);
		final Date endTime = dp.getCounterTime(meDataValues, END_TIME_BYTE);

		for (Integer i = 0; i < getMeasurementsNumber(meDataValues); i++) {

			final int reportDiff = LINK_REPORT_START_INDEX + i * 55;

			final Map<String, Object> perfReport = new HashMap<String, Object>();
			final long sigLink = dp.createIntValue(0, meDataValues, reportDiff);

			perfReport.put(DxtPerformanceConstants.DISPLAY_NAME, SIGLINK
					+ sigLink);
			perfReport.put(DxtPerformanceConstants.DESCRIPTION,
					"MTP Statistics : MTP Performance of Signalling Links ");
			perfReport.put(DxtPerformanceConstants.REPORT_TYPE,
					MTP_PERFORMANCE_LINKS);
			perfReport.put(DxtPerformanceConstants.CUSTOM_CLASS_NAME,
					"UIM_DXTPMMTPLinkPerformance");

			perfReport.put(DxtPerformanceConstants.COUNTER_TIME, startTime);
			perfReport.put(DxtPerformanceConstants.START_TIME, startTime);
			perfReport.put(DxtPerformanceConstants.END_TIME, endTime);

			perfReport.put("LinkName", sigLink);
			perfReport.put(LINKTYPE, getLinkType(meDataValues, reportDiff));

			perfReport.put("InServiceStateDuration",
					dp.createFormattedDate(3, 6, meDataValues, reportDiff));
			perfReport.put("SignallingFailuresAllReasons",
					dp.createLongValue(7, meDataValues, reportDiff));
			perfReport.put("SignallingFailuresAbnormal",
					dp.createLongValue(11, meDataValues, reportDiff));
			perfReport.put("SignallingFailuresAckDelay",
					dp.createLongValue(15, meDataValues, reportDiff));
			perfReport.put("SignallingFailuresErrorRate",
					dp.createLongValue(19, meDataValues, reportDiff));
			perfReport.put("SignalFailExcessiveDuration",
					dp.createLongValue(23, meDataValues, reportDiff));
			perfReport.put("SignallingProvingFailure",
					dp.createLongValue(27, meDataValues, reportDiff));
			perfReport.put("ReceivedErroredSignallingUnits",
					dp.createLongValue(31, meDataValues, reportDiff));
			perfReport.put("ReceivedNegAck",
					dp.createLongValue(35, meDataValues, reportDiff));
			perfReport.put("Changeover",
					dp.createLongValue(39, meDataValues, reportDiff));
			perfReport.put("Changeback",
					dp.createLongValue(43, meDataValues, reportDiff));
			perfReport.put("LinkRestoration",
					dp.createLongValue(47, meDataValues, reportDiff));
			perfReport.put("SDLossError",
					dp.createLongValue(51, meDataValues, reportDiff));

			perfLinksReports.put(i.toString(), perfReport);
		}

		return perfLinksReports;
	}

	/**
	 * This report contains the number of transmitted and received message
	 * signal units, the number of octets retransmitted, and various
	 * measurements having to do with signaling link congestion.
	 *
	 * @param meDataValues
	 * @return map for creating mtp report
	 */
	public Map<String, Map<String, Object>> createMTPLinkUtilizationReport(
			final List<String> meDataValues) {

		final Map<String, Map<String, Object>> utilLinksReports = new HashMap<String, Map<String, Object>>();

		final Date startTime = dp.getCounterTime(meDataValues, START_TIME_BYTE);
		final Date endTime = dp.getCounterTime(meDataValues, END_TIME_BYTE);

		for (Integer i = 0; i < getMeasurementsNumber(meDataValues); i++) {

			final int reportDiff = LINK_REPORT_START_INDEX + i * 113;

			final Map<String, Object> utilReport = new HashMap<String, Object>();
			final long sigLink = dp.createIntValue(0, meDataValues, reportDiff);
			utilReport.put("LinkName", sigLink);
			utilReport.put(DxtPerformanceConstants.DISPLAY_NAME, SIGLINK
					+ sigLink);
			utilReport.put(DxtPerformanceConstants.CUSTOM_CLASS_NAME,
					"UIM_DXTPMMTPLinkUtilization");
			utilReport.put(DxtPerformanceConstants.DESCRIPTION,
					"MTP Statistics : MTP Utilization of Signaling Links");
			utilReport.put(DxtPerformanceConstants.REPORT_TYPE,
					MTP_UTILIZATION_LINKS);
			utilReport.put(LINKTYPE, getLinkType(meDataValues, reportDiff));
			utilReport.put(DxtPerformanceConstants.COUNTER_TIME, startTime);
			utilReport.put(DxtPerformanceConstants.START_TIME, startTime);
			utilReport.put(DxtPerformanceConstants.END_TIME, endTime);

			utilReport.put("SignallingLinkRate",
					dp.createIntValue(3, meDataValues, reportDiff));
			utilReport.put("TransmittedOctets",
					dp.createLongValue(5, meDataValues, reportDiff));
			utilReport.put("ReceivedOctets",
					dp.createLongValue(9, meDataValues, reportDiff));
			utilReport.put("MSUTx",
					dp.createLongValue(13, meDataValues, reportDiff));
			utilReport.put("MSURx",
					dp.createLongValue(17, meDataValues, reportDiff));
			utilReport.put("OctetsRetransmitted",
					dp.createLongValue(21, meDataValues, reportDiff));
			utilReport.put("CongestionL1",
					dp.createLongValue(25, meDataValues, reportDiff));
			utilReport.put("CongestionL2",
					dp.createLongValue(29, meDataValues, reportDiff));
			utilReport.put("CongestionL3",
					dp.createLongValue(33, meDataValues, reportDiff));
			utilReport.put("CongestionDurationL1",
					dp.createFormattedDate(37, 40, meDataValues, reportDiff));
			utilReport.put("CongestionDurationL2",
					dp.createFormattedDate(41, 44, meDataValues, reportDiff));
			utilReport.put("CongestionDurationL3",
					dp.createFormattedDate(45, 48, meDataValues, reportDiff));
			utilReport.put("MSUDiscardedL1",
					dp.createLongValue(49, meDataValues, reportDiff));
			utilReport.put("MSUDiscardedL2",
					dp.createLongValue(53, meDataValues, reportDiff));
			utilReport.put("MSUDiscardedL3",
					dp.createLongValue(57, meDataValues, reportDiff));
			utilReport.put("MSULossL1",
					dp.createLongValue(61, meDataValues, reportDiff));
			utilReport.put("MSULossL2",
					dp.createLongValue(65, meDataValues, reportDiff));
			utilReport.put("MSULossL3",
					dp.createLongValue(69, meDataValues, reportDiff));
			utilReport.put("PeakLoadIn30",
					dp.createIntValue(73, meDataValues, reportDiff));
			utilReport.put("FreezeStartTimeIn30",
					dp.parseTimeStamp(75, meDataValues, reportDiff));
			utilReport.put("PeakLoadOut30",
					dp.createIntValue(83, meDataValues, reportDiff));
			utilReport.put("FreezeStartTimeOut30",
					dp.parseTimeStamp(85, meDataValues, reportDiff));
			utilReport.put("PeakLoadIn5",
					dp.createIntValue(93, meDataValues, reportDiff));
			utilReport.put("FreezeStartTimeIn5",
					dp.parseTimeStamp(95, meDataValues, reportDiff));
			utilReport.put("PeakLoadOut5",
					dp.createIntValue(103, meDataValues, reportDiff));
			utilReport.put("FreezeStartTimeOut5",
					dp.parseTimeStamp(105, meDataValues, reportDiff));

			utilLinksReports.put(i.toString(), utilReport);
		}

		return utilLinksReports;
	}

	/**
	 *
	 * This report contains information regarding the availability of signaling
	 * link sets including the number of times a failure stopped/started in the
	 * signaling link set, the duration of the signaling link set
	 * unavailability, the number of times a transfer-prohibited message has
	 * been sent as a result of a link set failure, the number of times a
	 * transfer-allowed message has been sent as a result of a link set
	 * recovery, and signaling route set unavailability.
	 *
	 * @param meDataValues
	 * @return map for creating mtp report
	 */
	public Map<String, Map<String, Object>> createMTPSignalPointAvailReport(
			final List<String> meDataValues) {

		final Map<String, Map<String, Object>> avalPointsReports = new HashMap<String, Map<String, Object>>();

		final String networkName = getNetworkName(meDataValues);

		final Date startTime = dp.getCounterTime(meDataValues, START_TIME_BYTE);
		final Date endTime = dp.getCounterTime(meDataValues, END_TIME_BYTE);

		for (Integer i = 0; i < getMeasurementsNumber(meDataValues); i++) {

			final int recDiff = POINTS_REPORT_START_INDEX + i * 40;

			final Map<String, Object> avalPointReport = new HashMap<String, Object>();

			final String sigPoint = dp.createHexValue(0, meDataValues, recDiff);
			avalPointReport.put(DxtPerformanceConstants.DISPLAY_NAME, sigPoint);
			avalPointReport
					.put(DxtPerformanceConstants.DESCRIPTION,
							"MTP Statistics : MTP Availability of Signaling Points.Hex representation of the Point Code");
			avalPointReport.put(DxtPerformanceConstants.NETWORK, networkName);
			avalPointReport.put(DxtPerformanceConstants.REPORT_TYPE,
					MTP_AVAILABILITY_POINTS);
			avalPointReport.put(DxtPerformanceConstants.CUSTOM_CLASS_NAME,
					"UIM_DXTPMMTPSignalPointAvail");

			avalPointReport
					.put(DxtPerformanceConstants.COUNTER_TIME, startTime);
			avalPointReport.put(DxtPerformanceConstants.START_TIME, startTime);
			avalPointReport.put(DxtPerformanceConstants.END_TIME, endTime);

			avalPointReport.put(SIGPOINT, sigPoint);
			avalPointReport.put("UnavailableLinkSetDuration",
					dp.createFormattedDate(4, 7, meDataValues, recDiff));
			avalPointReport.put("StartLinkFailure",
					dp.createLongValue(8, meDataValues, recDiff));
			avalPointReport.put("EndLinkFailure",
					dp.createLongValue(12, meDataValues, recDiff));
			avalPointReport.put("LinkTransmissionFailure",
					dp.createLongValue(16, meDataValues, recDiff));
			avalPointReport.put("LinkTransmissionRestore",
					dp.createLongValue(20, meDataValues, recDiff));
			avalPointReport.put("RouteSetUnavailabilityMessage",
					dp.createLongValue(24, meDataValues, recDiff));
			avalPointReport.put("RouteSetUnavailMessageDuration",
					dp.createFormattedDate(28, 31, meDataValues, recDiff));
			avalPointReport.put("RouteSetUnavailability",
					dp.createLongValue(32, meDataValues, recDiff));
			avalPointReport.put("RouteSetUnavailabilityDuration",
					dp.createFormattedDate(36, 38, meDataValues, recDiff));

			avalPointsReports.put(i.toString(), avalPointReport);
		}

		return avalPointsReports;
	}

	/**
	 * This report contains availability information on adjacent signaling
	 * links, the number of received transfer controlled messages, the number of
	 * discarded signaling information messages, the number of unauthorized STP
	 * signaling information messages, and the number of of transmitted/received
	 * UPU
	 *
	 * (User Part Unavailable) messages.
	 *
	 * @param meDataValues
	 * @return map for creating mtp report
	 */
	public Map<String, Map<String, Object>> createMTPSignalPointStatReport(
			final List<String> meDataValues) {

		final Map<String, Map<String, Object>> statusPointsReports = new HashMap<String, Map<String, Object>>();

		final String networkName = getNetworkName(meDataValues);

		final Date startTime = dp.getCounterTime(meDataValues, START_TIME_BYTE);
		final Date endTime = dp.getCounterTime(meDataValues, END_TIME_BYTE);

		for (Integer i = 0; i < getMeasurementsNumber(meDataValues); i++) {

			final int recDiff = POINTS_REPORT_START_INDEX + i * 44;
			final Map<String, Object> statusPointReport = new HashMap<String, Object>();

			final String sigPoint = dp.createHexValue(0, meDataValues, recDiff);

			statusPointReport.put(DxtPerformanceConstants.DISPLAY_NAME,
					sigPoint);
			statusPointReport
					.put(DxtPerformanceConstants.DESCRIPTION,
							"MTP Statistics : MTP Status of Signaling Points.Hex representation of the Point Code");
			statusPointReport.put(DxtPerformanceConstants.REPORT_TYPE,
					MTP_STATUS_POINTS);
			statusPointReport.put(DxtPerformanceConstants.CUSTOM_CLASS_NAME,
					"UIM_DXTPMMTPSignalPointStat");
			statusPointReport.put(DxtPerformanceConstants.NETWORK, networkName);
			statusPointReport.put(SIGPOINT, sigPoint);

			statusPointReport.put(DxtPerformanceConstants.COUNTER_TIME,
					startTime);
			statusPointReport
					.put(DxtPerformanceConstants.START_TIME, startTime);
			statusPointReport.put(DxtPerformanceConstants.END_TIME, endTime);

			statusPointReport.put("AdjacentSPInaccessibleDuration",
					dp.createFormattedDate(4, 7, meDataValues, recDiff));
			statusPointReport.put("AdjacentSPInaccessible",
					dp.createLongValue(8, meDataValues, recDiff));
			statusPointReport.put("TCMessagesReceived",
					dp.createLongValue(12, meDataValues, recDiff));
			statusPointReport.put("MSUDiscardedTx",
					dp.createLongValue(16, meDataValues, recDiff));
			statusPointReport.put("MSUDiscardedRx",
					dp.createLongValue(20, meDataValues, recDiff));
			statusPointReport.put("MSUDPCCount",
					dp.createLongValue(24, meDataValues, recDiff));
			statusPointReport.put("MSUOPCCount",
					dp.createLongValue(28, meDataValues, recDiff));
			statusPointReport.put("MSUSTPCount",
					dp.createLongValue(32, meDataValues, recDiff));
			statusPointReport.put("UnavailableMSUTx",
					dp.createLongValue(36, meDataValues, recDiff));
			statusPointReport.put("UnavailableMSURx",
					dp.createLongValue(40, meDataValues, recDiff));

			statusPointsReports.put(i.toString(), statusPointReport);
		}

		return statusPointsReports;
	}

	/**
	 *
	 * This report contains information concerning signaling traffic of
	 * signaling points including the number of received SIF and SIO octets
	 * according to the originating point code, the number of SIF and SIO octets
	 * according to the signaling destination points, and the number of
	 * signaling octets.
	 *
	 * @param meDataValues
	 * @return map for creating mtp report
	 */

	public Map<String, Map<String, Object>> createMTPSigPointTrafficReport(
			final List<String> meDataValues) {

		final Map<String, Map<String, Object>> sigPointsReports = new HashMap<String, Map<String, Object>>();

		final String networkName = getNetworkName(meDataValues);

		final Date startTime = dp.getCounterTime(meDataValues, START_TIME_BYTE);
		final Date endTime = dp.getCounterTime(meDataValues, END_TIME_BYTE);

		for (Integer i = 0; i < getMeasurementsNumber(meDataValues); i++) {

			final int recDiff = POINTS_REPORT_START_INDEX + i * 77;

			final Map<String, Object> sigPointReport = new HashMap<String, Object>();

			final String sigPoint = dp.createHexValue(0, meDataValues, recDiff);
			sigPointReport.put(DxtPerformanceConstants.DISPLAY_NAME, sigPoint);
			meDataValues.get(57);
			sigPointReport
					.put(DxtPerformanceConstants.DESCRIPTION,
							"MTP Statistics : MTP Signalling Traffic of Signalling Points.Hex representation of the Point Code");
			sigPointReport.put(DxtPerformanceConstants.REPORT_TYPE,
					MTP_SIGNALING_POINTS);
			sigPointReport.put(DxtPerformanceConstants.CUSTOM_CLASS_NAME,
					"UIM_DXTPMMTPSigPointTraffic");
			sigPointReport.put(DxtPerformanceConstants.NETWORK, networkName);
			sigPointReport.put(SIGPOINT, sigPoint);

			sigPointReport.put("SifSioOctetsRx",
					dp.createLongValue(4, meDataValues, recDiff));
			sigPointReport.put("SifSioOctetsTx",
					dp.createLongValue(8, meDataValues, recDiff));

			sigPointReport.put(DxtPerformanceConstants.COUNTER_TIME, startTime);
			sigPointReport.put(DxtPerformanceConstants.START_TIME, startTime);
			sigPointReport.put(DxtPerformanceConstants.END_TIME, endTime);

			final int routeCount = Integer.parseInt(meDataValues
					.get(12 + recDiff));

			sigPointReport.put("RouteCount", routeCount);
			for (int c = 0; c < routeCount; c++) {

				final int subRecDiff = recDiff + c * 8;

				final String route = "Route" + (c + 1);

				sigPointReport.put(route + "TransferPoint",
						dp.createHexValue(13, meDataValues, subRecDiff));
				sigPointReport.put(route + "TransmittedOctets",
						dp.createLongValue(17, meDataValues, subRecDiff));

			}

			sigPointsReports.put(i.toString(), sigPointReport);
		}

		return sigPointsReports;
	}

	/**
	 *
	 * This report contains the number of handled octets and the number of
	 * handled signaling messages for each Destination Point Code (DPC).
	 *
	 * @param meDataValues
	 * @return map for creating mtp report
	 */
	public Map<String, Map<String, Object>> createMTPTrafficMatrixReport(
			final List<String> meDataValues) {

		final Map<String, Map<String, Object>> sigMatrixReports = new HashMap<String, Map<String, Object>>();

		final String networkName = getNetworkName(meDataValues);
		final String infOctet = meDataValues.get(57);

		final String userPart = dp.createAsciiName(58, 62, meDataValues, 0);
		final String sigPoint = dp.createHexValue(63, meDataValues, 0);

		final Date startTime = dp.getCounterTime(meDataValues, START_TIME_BYTE);
		final Date endTime = dp.getCounterTime(meDataValues, END_TIME_BYTE);
		DXTPerformanceTestingTool.printLine("Measurement Reports Count : " + getMeasurementsNumber(meDataValues) + "\n");
		//System.out.println("Measurement Reports Count : " + getMeasurementsNumber(meDataValues));

		for (Integer i = 0; i < getMeasurementsNumber(meDataValues); i++) {

			final int recDiff = MATRIX_REPORT_START_INDEX + i * 14;

			final Map<String, Object> sigMatrixReport = new HashMap<String, Object>();

			sigMatrixReport.put(DxtPerformanceConstants.DISPLAY_NAME, userPart);

			sigMatrixReport
					.put(DxtPerformanceConstants.DESCRIPTION,
							"MTP Statistics : MTP Signalling Traffic Matrix.Hex representation of the Point Code");
			sigMatrixReport.put(DxtPerformanceConstants.NETWORK, networkName);
			sigMatrixReport.put(USERPART, userPart);
			sigMatrixReport.put("InformationOctet", infOctet);
			sigMatrixReport.put("OPC", sigPoint);
			sigMatrixReport.put(DxtPerformanceConstants.REPORT_TYPE,
					MTP_SIGNALING_MATRIX);
			sigMatrixReport.put(DxtPerformanceConstants.CUSTOM_CLASS_NAME,
					"UIM_DXTPMMTPTrafficMatrix");

			sigMatrixReport
					.put(DxtPerformanceConstants.COUNTER_TIME, startTime);
			sigMatrixReport.put(DxtPerformanceConstants.START_TIME, startTime);
			sigMatrixReport.put(DxtPerformanceConstants.END_TIME, endTime);

			final String dpc = dp.createHexValue(0, meDataValues, recDiff);
			sigMatrixReport.put("DPC", dpc);
			sigMatrixReport.put("SifSioHandled",
					dp.createLongValue(4, meDataValues, recDiff));
			sigMatrixReport.put("MSUHandled",
					dp.createLongValue(8, meDataValues, recDiff));

			sigMatrixReports
					.put(userPart + "_" + i.toString(), sigMatrixReport);

		}

		return sigMatrixReports;
	}

	/**
	 *
	 * This report contains information concerning signaling traffic of user
	 * parts including the number of received signaling octets and the number of
	 * transmitted signaling octets.
	 *
	 * @param meDataValues
	 * @return map for creating mtp report
	 */
	public Map<String, Map<String, Object>> createMTPUserPartTrafficReport(
			final List<String> meDataValues) {

		final Map<String, Map<String, Object>> userPartsReports = new HashMap<String, Map<String, Object>>();

		final String networkName = getNetworkName(meDataValues);

		final Date startTime = dp.getCounterTime(meDataValues, START_TIME_BYTE);
		final Date endTime = dp.getCounterTime(meDataValues, END_TIME_BYTE);

		for (int i = 0; i < getMeasurementsNumber(meDataValues); i++) {

			final int recDiff = POINTS_REPORT_START_INDEX + i * 14;

			final Map<String, Object> userPartReport = new HashMap<String, Object>();

			/*
			 * Basically UserPart is the name of the each UserPartTraffic
			 * report. It's different for each element via req.
			 */
			final String userPart = dp.createAsciiName(0, 4, meDataValues,
					recDiff);

			userPartReport.put(DxtPerformanceConstants.REPORT_TYPE,
					MTP_SIGNALING_USERPARTS);
			userPartReport.put(DxtPerformanceConstants.CUSTOM_CLASS_NAME,
					"UIM_DXTPMMTPUserPartTraffic");
			userPartReport.put(DxtPerformanceConstants.DISPLAY_NAME, userPart);
			userPartReport.put(USERPART, userPart);
			userPartReport.put(DxtPerformanceConstants.DESCRIPTION,
					"MTP Statistics : MTP Signalling Traffic of User Parts");
			userPartReport.put(DxtPerformanceConstants.NETWORK, networkName);

			userPartReport.put(DxtPerformanceConstants.COUNTER_TIME, startTime);
			userPartReport.put(DxtPerformanceConstants.START_TIME, startTime);
			userPartReport.put(DxtPerformanceConstants.END_TIME, endTime);

			userPartReport.put("SIO", meDataValues.get(5 + recDiff));
			userPartReport.put("SifSioRx",
					dp.createLongValue(6, meDataValues, recDiff));
			userPartReport.put("SifSioTx",
					dp.createLongValue(10, meDataValues, recDiff));

			userPartsReports.put(userPart, userPartReport);
		}

		return userPartsReports;
	}

	/**
	 * @param meDataValues
	 * @param type
	 * @return report
	 */
	public Map<String, Map<String, Object>> createReport(
			final List<String> meDataValues, final String type) {

		dp.printReportLogInHex(meDataValues);

		Map<String, Map<String, Object>> report = new HashMap<String, Map<String, Object>>();

		try {

			if (type.equals(MTP_AVAILABILITY_LINKS)) {

				report = createMTPLinkAvailabilityReport(meDataValues);

			} else if (type.equals(MTP_PERFORMANCE_LINKS)) {

				report = createMTPLinkPerformanceReport(meDataValues);

			} else if (type.equals(MTP_UTILIZATION_LINKS)) {

				report = createMTPLinkUtilizationReport(meDataValues);

			} else if (type.equals(MTP_STATUS_POINTS)) {

				report = createMTPSignalPointStatReport(meDataValues);

			} else if (type.equals(MTP_AVAILABILITY_POINTS)) {

				report = createMTPSignalPointAvailReport(meDataValues);

			} else if (type.equals(MTP_SIGNALING_POINTS)) {

				report = createMTPSigPointTrafficReport(meDataValues);

			} else if (type.equals(MTP_SIGNALING_USERPARTS)) {

				report = createMTPUserPartTrafficReport(meDataValues);

			} else if (type.equals(MTP_SIGNALING_MATRIX)) {

				report = createMTPTrafficMatrixReport(meDataValues);
			}
		} catch (final Exception e) {
			DXTPerformanceTestingTool.printLine("Can't parse report " + type + " : "	+ e.getMessage() + "\n");
			//System.out.println("Can't parse report " + type + " : "	+ e.getMessage());
		}

		return report;
	}

	private String getLinkType(final int type) {
		if (type == 0) {
			return TDM_LINK_TYPE;
		} else if (type == 1) {
			return ATM_LINK_TYPE;
		}
		return UNKNOWN_LINK_TYPE;
	}

	private String getLinkType(final List<String> meDataValues, final int diff) {

		String linkType = "";

		try {
			final int linkTypeInt = Integer.parseInt(meDataValues
					.get(LINK_TYPE_BYTE + diff));
			linkType = getLinkType(linkTypeInt);
		} catch (final Exception e) {
			DXTPerformanceTestingTool.printLine("Can't parse linkType " + (meDataValues.get(LINK_TYPE_BYTE + diff)) + "\n");
			//System.out.println("Can't parse linkType " + (meDataValues.get(LINK_TYPE_BYTE + diff)));
		}

		return linkType;
	}

	private Integer getMeasurementsNumber(final List<String> meDataValues) {
		return dp.createIntValue(MEAS_NUMBER_INDEX, meDataValues, 0);
	}

	private String getNetworkName(final List<String> meDataValues) {
		return dp.createAsciiName(52, 56, meDataValues, 0);
	}
}
