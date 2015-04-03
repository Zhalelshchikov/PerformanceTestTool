package com.harris.netboss.DXTPerformanceTestingTool.recordscreator.parsers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.harris.netboss.dxtPerformanceTestingTool.DXTPerformanceTestingTool;
import com.harris.netboss.recordscreator.DxtPerformanceConstants;

public class XmlReaderParser {

	private static boolean isElementNode(final Node node) {
		if (node == null) {
			return false;
		}
		return node.getNodeType() == Node.ELEMENT_NODE;
	}

	public static final String LAN_SWITCH_TRAFFIC_REPORT_NUMBER = "M288";

	public static final String LAN_HOST_TRAFFIC_REPORT_NUMBER = "M289";

	public static final String LAN_SWITCH_AVAIL_REPORT_NUMBER = "M290";

	public static final String LAN_SWITCH_ERROR_REPORT_NUMBER = "M291";

	public static final String LAN_HOST_UNIT_ERROR_REPORT_NUMBER = "M292";

	public static final String LAN_EMB_LOAD_REPORT_NUMBER = "M293";

	public static final String M3UA_ASSOCIATION_SET_NUMBER = "M661";
	static public Map<String, String> m3UAAssociationSetReportsNaming;

	static {
		m3UAAssociationSetReportsNaming = new HashMap<String, String>();
		{
			m3UAAssociationSetReportsNaming.put(M3UA_ASSOCIATION_SET_NUMBER
					+ "B2C1", "SetUnavailability");
			m3UAAssociationSetReportsNaming.put(M3UA_ASSOCIATION_SET_NUMBER
					+ "B2C2", "SetUnavailabilityTimes");
		}
	}
	static public Map<String, String> m3UAAssociationIndexReportsNaming;

	static {
		m3UAAssociationIndexReportsNaming = new HashMap<String, String>();
		{

			m3UAAssociationIndexReportsNaming.put(M3UA_ASSOCIATION_SET_NUMBER
					+ "B3C1", "AssociationUnavail");
			m3UAAssociationIndexReportsNaming.put(M3UA_ASSOCIATION_SET_NUMBER
					+ "B3C2", "AssociationUnavailTimes");
			m3UAAssociationIndexReportsNaming.put(M3UA_ASSOCIATION_SET_NUMBER
					+ "B3C3", "MessagesReceivedNumber");
			m3UAAssociationIndexReportsNaming.put(M3UA_ASSOCIATION_SET_NUMBER
					+ "B3C4", "MessagesSentNumber");
			m3UAAssociationIndexReportsNaming.put(M3UA_ASSOCIATION_SET_NUMBER
					+ "B3C5", "OctetsReceivedNumber");
			m3UAAssociationIndexReportsNaming.put(M3UA_ASSOCIATION_SET_NUMBER
					+ "B3C6", "OctetsSentNumber");
			m3UAAssociationIndexReportsNaming.put(M3UA_ASSOCIATION_SET_NUMBER
					+ "B3C7", "MessagesDiscardedNumber");
			m3UAAssociationIndexReportsNaming.put(M3UA_ASSOCIATION_SET_NUMBER
					+ "B4C1", "DataPacketReceivedNumber");
			m3UAAssociationIndexReportsNaming.put(M3UA_ASSOCIATION_SET_NUMBER
					+ "B4C2", "DataPacketSentNumber");
			m3UAAssociationIndexReportsNaming.put(M3UA_ASSOCIATION_SET_NUMBER
					+ "B4C3", "OctetsSCTPReceivedNumber");
			m3UAAssociationIndexReportsNaming.put(M3UA_ASSOCIATION_SET_NUMBER
					+ "B4C4", "OctetsSCTPSentNumber");
			m3UAAssociationIndexReportsNaming.put(M3UA_ASSOCIATION_SET_NUMBER
					+ "B4C5", "PacketsRetransmittedNumber");
			m3UAAssociationIndexReportsNaming.put(M3UA_ASSOCIATION_SET_NUMBER
					+ "B4C6", "TSNReceivedNumber");
		}
	}
	static public Map<String, String> lanEMBLoadReportsNaming;

	static {
		lanEMBLoadReportsNaming = new HashMap<String, String>();
		{
			lanEMBLoadReportsNaming.put(LAN_EMB_LOAD_REPORT_NUMBER + "B2C2",
					"AverageLoadRate");
			lanEMBLoadReportsNaming.put(LAN_EMB_LOAD_REPORT_NUMBER + "B2C3",
					"HighestPeakLoadRate");
			lanEMBLoadReportsNaming.put(LAN_EMB_LOAD_REPORT_NUMBER + "B2C4",
					"HighestPeakLoadRateTime");
			lanEMBLoadReportsNaming.put(LAN_EMB_LOAD_REPORT_NUMBER + "B3C1",
					"ErrorSituation1");
			lanEMBLoadReportsNaming.put(LAN_EMB_LOAD_REPORT_NUMBER + "B3C2",
					"ErrorSituation2");
		}
	}
	static public Map<String, String> lanSwitchAvailReportsNaming;

	static {
		lanSwitchAvailReportsNaming = new HashMap<String, String>();
		{
			lanSwitchAvailReportsNaming.put(LAN_SWITCH_AVAIL_REPORT_NUMBER
					+ "B2C2", "UnitWarmRestartsNumber");
			lanSwitchAvailReportsNaming.put(LAN_SWITCH_AVAIL_REPORT_NUMBER
					+ "B2C3", "UnitColdRestartsNumber");
			lanSwitchAvailReportsNaming.put(LAN_SWITCH_AVAIL_REPORT_NUMBER
					+ "B2C4", "LastRestartTime");
			lanSwitchAvailReportsNaming.put(LAN_SWITCH_AVAIL_REPORT_NUMBER
					+ "B2C5", "TopologyChangesNumber");
		}
	}
	static public Map<String, String> lanSwitchErrorReportsNaming;

	static {
		lanSwitchErrorReportsNaming = new HashMap<String, String>();
		{
			lanSwitchErrorReportsNaming.put(LAN_SWITCH_ERROR_REPORT_NUMBER
					+ "B2C2", "CollisionsNumber");
			lanSwitchErrorReportsNaming.put(LAN_SWITCH_ERROR_REPORT_NUMBER
					+ "B2C3", "CRCAlignErrorsNumber");
			lanSwitchErrorReportsNaming.put(LAN_SWITCH_ERROR_REPORT_NUMBER
					+ "B2C4", "UndersizePacketsNumber");
			lanSwitchErrorReportsNaming.put(LAN_SWITCH_ERROR_REPORT_NUMBER
					+ "B2C5", "OversizePacketsNumber");
			lanSwitchErrorReportsNaming.put(LAN_SWITCH_ERROR_REPORT_NUMBER
					+ "B2C6", "FIFOOverrunsCount");
			lanSwitchErrorReportsNaming.put(LAN_SWITCH_ERROR_REPORT_NUMBER
					+ "B2C7", "LineNotReadyCount");
			lanSwitchErrorReportsNaming.put(LAN_SWITCH_ERROR_REPORT_NUMBER
					+ "B2C8", "UnknownSAPPacketsNumber");
			lanSwitchErrorReportsNaming.put(LAN_SWITCH_ERROR_REPORT_NUMBER
					+ "B2C9", "RecPAUSEONNumber");
			lanSwitchErrorReportsNaming.put(LAN_SWITCH_ERROR_REPORT_NUMBER
					+ "B2C10", "RecPAUSEOFFNumber");
			lanSwitchErrorReportsNaming.put(LAN_SWITCH_ERROR_REPORT_NUMBER
					+ "B2C11", "TransmPAUSEONNumber");
			lanSwitchErrorReportsNaming.put(LAN_SWITCH_ERROR_REPORT_NUMBER
					+ "B2C12", "TransmPAUSEOFFNumber");

			lanSwitchErrorReportsNaming.put(LAN_SWITCH_ERROR_REPORT_NUMBER
					+ "B3C1", "CollisNumberValidity");
			lanSwitchErrorReportsNaming.put(LAN_SWITCH_ERROR_REPORT_NUMBER
					+ "B3C2", "CRCAlignValidity");
			lanSwitchErrorReportsNaming.put(LAN_SWITCH_ERROR_REPORT_NUMBER
					+ "B3C3", "UndersizePackValidity");
			lanSwitchErrorReportsNaming.put(LAN_SWITCH_ERROR_REPORT_NUMBER
					+ "B3C4", "OversizePackValidity");
			lanSwitchErrorReportsNaming.put(LAN_SWITCH_ERROR_REPORT_NUMBER
					+ "B3C5", "FIFOOverrunsValidity");
			lanSwitchErrorReportsNaming.put(LAN_SWITCH_ERROR_REPORT_NUMBER
					+ "B3C6", "LineNotReadyValidity");
			lanSwitchErrorReportsNaming.put(LAN_SWITCH_ERROR_REPORT_NUMBER
					+ "B3C7", "UnknownSAPPackValidity");

			lanSwitchErrorReportsNaming.put(LAN_SWITCH_ERROR_REPORT_NUMBER
					+ "B4C1", "RecPAUSEONValidity");
			lanSwitchErrorReportsNaming.put(LAN_SWITCH_ERROR_REPORT_NUMBER
					+ "B4C2", "RecPAUSEOFFValidity");
			lanSwitchErrorReportsNaming.put(LAN_SWITCH_ERROR_REPORT_NUMBER
					+ "B4C3", "TransmPAUSEONValidity");
			lanSwitchErrorReportsNaming.put(LAN_SWITCH_ERROR_REPORT_NUMBER
					+ "B4C4", "TransmPAUSEOFFValidity");
		}
	}
	static public Map<String, String> lanHostUnitErrorReportsNaming;

	static {
		lanHostUnitErrorReportsNaming = new HashMap<String, String>();
		{
			lanHostUnitErrorReportsNaming.put(LAN_HOST_UNIT_ERROR_REPORT_NUMBER
					+ "B2C2", "CollisionsNumber");
			lanHostUnitErrorReportsNaming.put(LAN_HOST_UNIT_ERROR_REPORT_NUMBER
					+ "B2C3", "CRCAlignErrorsNumber");
			lanHostUnitErrorReportsNaming.put(LAN_HOST_UNIT_ERROR_REPORT_NUMBER
					+ "B2C4", "UndersizePacketsNumber");
			lanHostUnitErrorReportsNaming.put(LAN_HOST_UNIT_ERROR_REPORT_NUMBER
					+ "B2C5", "OversizePacketsNumber");
			lanHostUnitErrorReportsNaming.put(LAN_HOST_UNIT_ERROR_REPORT_NUMBER
					+ "B2C6", "FIFOOverrunsCount");
			lanHostUnitErrorReportsNaming.put(LAN_HOST_UNIT_ERROR_REPORT_NUMBER
					+ "B2C7", "LineNotReadyCount");
			lanHostUnitErrorReportsNaming.put(LAN_HOST_UNIT_ERROR_REPORT_NUMBER
					+ "B2C8", "UnknownSAPPacketsNumber");
			lanHostUnitErrorReportsNaming.put(LAN_HOST_UNIT_ERROR_REPORT_NUMBER
					+ "B2C9", "RecPAUSEONNumber");
			lanHostUnitErrorReportsNaming.put(LAN_HOST_UNIT_ERROR_REPORT_NUMBER
					+ "B2C10", "RecPAUSEOFFNumber");
			lanHostUnitErrorReportsNaming.put(LAN_HOST_UNIT_ERROR_REPORT_NUMBER
					+ "B2C11", "TransmPAUSEONNumber");
			lanHostUnitErrorReportsNaming.put(LAN_HOST_UNIT_ERROR_REPORT_NUMBER
					+ "B2C12", "TransmPAUSEOFFNumber");

			lanHostUnitErrorReportsNaming.put(LAN_HOST_UNIT_ERROR_REPORT_NUMBER
					+ "B3C1", "CollisNumberValidity");
			lanHostUnitErrorReportsNaming.put(LAN_HOST_UNIT_ERROR_REPORT_NUMBER
					+ "B3C2", "CRCAlignValidity");
			lanHostUnitErrorReportsNaming.put(LAN_HOST_UNIT_ERROR_REPORT_NUMBER
					+ "B3C3", "UndersizePackValidity");
			lanHostUnitErrorReportsNaming.put(LAN_HOST_UNIT_ERROR_REPORT_NUMBER
					+ "B3C4", "OversizePackValidity");
			lanHostUnitErrorReportsNaming.put(LAN_HOST_UNIT_ERROR_REPORT_NUMBER
					+ "B3C5", "FIFOOverrunsValidity");
			lanHostUnitErrorReportsNaming.put(LAN_HOST_UNIT_ERROR_REPORT_NUMBER
					+ "B3C6", "LineNotReadyValidity");
			lanHostUnitErrorReportsNaming.put(LAN_HOST_UNIT_ERROR_REPORT_NUMBER
					+ "B3C7", "UnknownSAPPackValidity");

			lanHostUnitErrorReportsNaming.put(LAN_HOST_UNIT_ERROR_REPORT_NUMBER
					+ "B4C1", "RecPAUSEONValidity");
			lanHostUnitErrorReportsNaming.put(LAN_HOST_UNIT_ERROR_REPORT_NUMBER
					+ "B4C2", "RecPAUSEOFFValidity");
			lanHostUnitErrorReportsNaming.put(LAN_HOST_UNIT_ERROR_REPORT_NUMBER
					+ "B4C3", "TransmPAUSEONValidity");
			lanHostUnitErrorReportsNaming.put(LAN_HOST_UNIT_ERROR_REPORT_NUMBER
					+ "B4C4", "TransmPAUSEOFFValidity");
		}
	}
	static public Map<String, String> lanSwitchTrafficReportsNaming;

	static {
		lanSwitchTrafficReportsNaming = new HashMap<String, String>();
		{
			lanSwitchTrafficReportsNaming.put(LAN_SWITCH_TRAFFIC_REPORT_NUMBER
					+ "B2C2", DxtPerformanceConstants.LINK_TYPE);
			lanSwitchTrafficReportsNaming.put(LAN_SWITCH_TRAFFIC_REPORT_NUMBER
					+ "B2C3", DxtPerformanceConstants.CONNECTED_UNIT_TYPE);
			lanSwitchTrafficReportsNaming.put(LAN_SWITCH_TRAFFIC_REPORT_NUMBER
					+ "B2C4", DxtPerformanceConstants.CONNECTED_UNIT_INDEX);
			lanSwitchTrafficReportsNaming.put(LAN_SWITCH_TRAFFIC_REPORT_NUMBER
					+ "B2C5", DxtPerformanceConstants.INTERFACE_STATUS);

			lanSwitchTrafficReportsNaming.put(LAN_SWITCH_TRAFFIC_REPORT_NUMBER
					+ "B2C6", "TransmPacketsNumber");
			lanSwitchTrafficReportsNaming.put(LAN_SWITCH_TRAFFIC_REPORT_NUMBER
					+ "B2C7", "TransmOctetsNumber");
			lanSwitchTrafficReportsNaming.put(LAN_SWITCH_TRAFFIC_REPORT_NUMBER
					+ "B2C8", "TransmBroadPacketsNumber");
			lanSwitchTrafficReportsNaming.put(LAN_SWITCH_TRAFFIC_REPORT_NUMBER
					+ "B2C9", "TransmMultPacketsNumber");

			lanSwitchTrafficReportsNaming.put(LAN_SWITCH_TRAFFIC_REPORT_NUMBER
					+ "B2C10", "RecPacketsNumber");
			lanSwitchTrafficReportsNaming.put(LAN_SWITCH_TRAFFIC_REPORT_NUMBER
					+ "B2C11", "RecOctetsNumber");
			lanSwitchTrafficReportsNaming.put(LAN_SWITCH_TRAFFIC_REPORT_NUMBER
					+ "B2C12", "RecBroadPacketsNumber");
			lanSwitchTrafficReportsNaming.put(LAN_SWITCH_TRAFFIC_REPORT_NUMBER
					+ "B2C13", "RecMultPacketsNumber");

			lanSwitchTrafficReportsNaming.put(LAN_SWITCH_TRAFFIC_REPORT_NUMBER
					+ "B2C14", "TransmSizeUpTo64PackNumber");
			lanSwitchTrafficReportsNaming.put(LAN_SWITCH_TRAFFIC_REPORT_NUMBER
					+ "B2C15", "TransmSize65To127PackNumber");
			lanSwitchTrafficReportsNaming.put(LAN_SWITCH_TRAFFIC_REPORT_NUMBER
					+ "B2C16", "TransmSize128To255PackNumber");
			lanSwitchTrafficReportsNaming.put(LAN_SWITCH_TRAFFIC_REPORT_NUMBER
					+ "B2C17", "TransmSize256To511PackNumber");
			lanSwitchTrafficReportsNaming.put(LAN_SWITCH_TRAFFIC_REPORT_NUMBER
					+ "B2C18", "TransmSize512To1023PackNumber");
			lanSwitchTrafficReportsNaming.put(LAN_SWITCH_TRAFFIC_REPORT_NUMBER
					+ "B2C19", "TransmSize1024To1518PackNumber");

			lanSwitchTrafficReportsNaming.put(LAN_SWITCH_TRAFFIC_REPORT_NUMBER
					+ "B2C20", "RecSizeUpTo64PackNumber");
			lanSwitchTrafficReportsNaming.put(LAN_SWITCH_TRAFFIC_REPORT_NUMBER
					+ "B2C21", "RecSize65To127PackNumber");
			lanSwitchTrafficReportsNaming.put(LAN_SWITCH_TRAFFIC_REPORT_NUMBER
					+ "B2C22", "RecSize128To255PackNumber");
			lanSwitchTrafficReportsNaming.put(LAN_SWITCH_TRAFFIC_REPORT_NUMBER
					+ "B2C23", "RecSize256To511PackNumber");
			lanSwitchTrafficReportsNaming.put(LAN_SWITCH_TRAFFIC_REPORT_NUMBER
					+ "B2C24", "RecSize512To1023PackNumber");
			lanSwitchTrafficReportsNaming.put(LAN_SWITCH_TRAFFIC_REPORT_NUMBER
					+ "B2C25", "RecSize1024To1518PackNumber");

			lanSwitchTrafficReportsNaming.put(LAN_SWITCH_TRAFFIC_REPORT_NUMBER
					+ "B2C26", "TransmAverageLoadRate");
			lanSwitchTrafficReportsNaming.put(LAN_SWITCH_TRAFFIC_REPORT_NUMBER
					+ "B2C27", "TransmPeakLoadRate");
			lanSwitchTrafficReportsNaming.put(LAN_SWITCH_TRAFFIC_REPORT_NUMBER
					+ "B2C28", "RecAverageLoadRate");
			lanSwitchTrafficReportsNaming.put(LAN_SWITCH_TRAFFIC_REPORT_NUMBER
					+ "B2C29", "RecPeakLoadRate");

			lanSwitchTrafficReportsNaming.put(LAN_SWITCH_TRAFFIC_REPORT_NUMBER
					+ "B3C1", "TransmBroadPacketsValidity");
			lanSwitchTrafficReportsNaming.put(LAN_SWITCH_TRAFFIC_REPORT_NUMBER
					+ "B3C2", "RecBroadPacketsValidity");
			lanSwitchTrafficReportsNaming.put(LAN_SWITCH_TRAFFIC_REPORT_NUMBER
					+ "B3C3", "TransmPacketsValidity");
			lanSwitchTrafficReportsNaming.put(LAN_SWITCH_TRAFFIC_REPORT_NUMBER
					+ "B3C4", "RecPacketsValidity");
			lanSwitchTrafficReportsNaming.put(LAN_SWITCH_TRAFFIC_REPORT_NUMBER
					+ "B3C5", "TransmMultPacketsValidity");
			lanSwitchTrafficReportsNaming.put(LAN_SWITCH_TRAFFIC_REPORT_NUMBER
					+ "B3C6", "RecMultPacketsValidity");
		}
	}
	static public Map<String, String> lanHostTrafficReportsNaming;

	static {
		lanHostTrafficReportsNaming = new HashMap<String, String>();
		{
			lanHostTrafficReportsNaming.put(LAN_HOST_TRAFFIC_REPORT_NUMBER
					+ "B2C2", DxtPerformanceConstants.LINK_TYPE);
			lanHostTrafficReportsNaming.put(LAN_HOST_TRAFFIC_REPORT_NUMBER
					+ "B2C3", DxtPerformanceConstants.CONNECTED_UNIT_TYPE);
			lanHostTrafficReportsNaming.put(LAN_HOST_TRAFFIC_REPORT_NUMBER
					+ "B2C4", DxtPerformanceConstants.CONNECTED_UNIT_INDEX);
			lanHostTrafficReportsNaming.put(LAN_HOST_TRAFFIC_REPORT_NUMBER
					+ "B2C5", DxtPerformanceConstants.INTERFACE_STATUS);

			lanHostTrafficReportsNaming.put(LAN_HOST_TRAFFIC_REPORT_NUMBER
					+ "B2C6", "TransmPacketsNumber");
			lanHostTrafficReportsNaming.put(LAN_HOST_TRAFFIC_REPORT_NUMBER
					+ "B2C7", "TransmOctetsNumber");
			lanHostTrafficReportsNaming.put(LAN_HOST_TRAFFIC_REPORT_NUMBER
					+ "B2C8", "TransmBroadPacketsNumber");
			lanHostTrafficReportsNaming.put(LAN_HOST_TRAFFIC_REPORT_NUMBER
					+ "B2C9", "TransmMultPacketsNumber");

			lanHostTrafficReportsNaming.put(LAN_HOST_TRAFFIC_REPORT_NUMBER
					+ "B2C10", "RecPacketsNumber");
			lanHostTrafficReportsNaming.put(LAN_HOST_TRAFFIC_REPORT_NUMBER
					+ "B2C11", "RecOctetsNumber");
			lanHostTrafficReportsNaming.put(LAN_HOST_TRAFFIC_REPORT_NUMBER
					+ "B2C12", "RecBroadPacketsNumber");
			lanHostTrafficReportsNaming.put(LAN_HOST_TRAFFIC_REPORT_NUMBER
					+ "B2C13", "RecMultPacketsNumber");

			lanHostTrafficReportsNaming.put(LAN_HOST_TRAFFIC_REPORT_NUMBER
					+ "B2C14", "TransmSizeUpTo64PackNumber");
			lanHostTrafficReportsNaming.put(LAN_HOST_TRAFFIC_REPORT_NUMBER
					+ "B2C15", "TransmSize65To127PackNumber");
			lanHostTrafficReportsNaming.put(LAN_HOST_TRAFFIC_REPORT_NUMBER
					+ "B2C16", "TransmSize128To255PackNumber");
			lanHostTrafficReportsNaming.put(LAN_HOST_TRAFFIC_REPORT_NUMBER
					+ "B2C17", "TransmSize256To511PackNumber");
			lanHostTrafficReportsNaming.put(LAN_HOST_TRAFFIC_REPORT_NUMBER
					+ "B2C18", "TransmSize512To1023PackNumber");
			lanHostTrafficReportsNaming.put(LAN_HOST_TRAFFIC_REPORT_NUMBER
					+ "B2C19", "TransmSize1024To1518PackNumber");

			lanHostTrafficReportsNaming.put(LAN_HOST_TRAFFIC_REPORT_NUMBER
					+ "B2C20", "RecSizeUpTo64PackNumber");
			lanHostTrafficReportsNaming.put(LAN_HOST_TRAFFIC_REPORT_NUMBER
					+ "B2C21", "RecSize65To127PackNumber");
			lanHostTrafficReportsNaming.put(LAN_HOST_TRAFFIC_REPORT_NUMBER
					+ "B2C22", "RecSize128To255PackNumber");
			lanHostTrafficReportsNaming.put(LAN_HOST_TRAFFIC_REPORT_NUMBER
					+ "B2C23", "RecSize256To511PackNumber");
			lanHostTrafficReportsNaming.put(LAN_HOST_TRAFFIC_REPORT_NUMBER
					+ "B2C24", "RecSize512To1023PackNumber");
			lanHostTrafficReportsNaming.put(LAN_HOST_TRAFFIC_REPORT_NUMBER
					+ "B2C25", "RecSize1024To1518PackNumber");

			lanHostTrafficReportsNaming.put(LAN_HOST_TRAFFIC_REPORT_NUMBER
					+ "B2C26", "TransmAverageLoadRate");
			lanHostTrafficReportsNaming.put(LAN_HOST_TRAFFIC_REPORT_NUMBER
					+ "B2C27", "TransmPeakLoadRate");
			lanHostTrafficReportsNaming.put(LAN_HOST_TRAFFIC_REPORT_NUMBER
					+ "B2C28", "RecAverageLoadRate");
			lanHostTrafficReportsNaming.put(LAN_HOST_TRAFFIC_REPORT_NUMBER
					+ "B2C29", "RecPeakLoadRate");

			lanHostTrafficReportsNaming.put(LAN_HOST_TRAFFIC_REPORT_NUMBER
					+ "B3C1", "TransmBroadPacketsValidity");
			lanHostTrafficReportsNaming.put(LAN_HOST_TRAFFIC_REPORT_NUMBER
					+ "B3C2", "RecBroadPacketsValidity");
			lanHostTrafficReportsNaming.put(LAN_HOST_TRAFFIC_REPORT_NUMBER
					+ "B3C3", "TransmPacketsValidity");
			lanHostTrafficReportsNaming.put(LAN_HOST_TRAFFIC_REPORT_NUMBER
					+ "B3C4", "RecPacketsValidity");
			lanHostTrafficReportsNaming.put(LAN_HOST_TRAFFIC_REPORT_NUMBER
					+ "B3C5", "TransmMultPacketsValidity");
			lanHostTrafficReportsNaming.put(LAN_HOST_TRAFFIC_REPORT_NUMBER
					+ "B3C6", "RecMultPacketsValidity");
		}
	}
	static public Map<String, Map<String, String>> repNumberToPropertiesMap;

	static {
		repNumberToPropertiesMap = new HashMap<String, Map<String, String>>();
		{
			repNumberToPropertiesMap.put(LAN_HOST_TRAFFIC_REPORT_NUMBER,
					lanHostTrafficReportsNaming);
			repNumberToPropertiesMap.put(LAN_SWITCH_TRAFFIC_REPORT_NUMBER,
					lanSwitchTrafficReportsNaming);
			repNumberToPropertiesMap.put(LAN_HOST_UNIT_ERROR_REPORT_NUMBER,
					lanHostUnitErrorReportsNaming);
			repNumberToPropertiesMap.put(LAN_SWITCH_ERROR_REPORT_NUMBER,
					lanSwitchErrorReportsNaming);
			repNumberToPropertiesMap.put(LAN_SWITCH_AVAIL_REPORT_NUMBER,
					lanSwitchAvailReportsNaming);
			repNumberToPropertiesMap.put(LAN_EMB_LOAD_REPORT_NUMBER,
					lanEMBLoadReportsNaming);
			repNumberToPropertiesMap.put(M3UA_ASSOCIATION_SET_NUMBER,
					m3UAAssociationSetReportsNaming);

		}
	}
	static public Map<String, String> repNumberToTableName;

	static {
		repNumberToTableName = new HashMap<String, String>();
		{
			repNumberToTableName.put(LAN_HOST_TRAFFIC_REPORT_NUMBER,
					"LanHostTraffic");
			repNumberToTableName.put(LAN_SWITCH_TRAFFIC_REPORT_NUMBER,
					"LanSwitchTraffic");
			repNumberToTableName.put(LAN_HOST_UNIT_ERROR_REPORT_NUMBER,
					"LanHostError");
			repNumberToTableName.put(LAN_SWITCH_ERROR_REPORT_NUMBER,
					"LanSwitchError");
			repNumberToTableName.put(LAN_SWITCH_AVAIL_REPORT_NUMBER,
					"LanSwitchAvail");
			repNumberToTableName.put(LAN_EMB_LOAD_REPORT_NUMBER, "LanEMBLoad");
			repNumberToTableName.put(M3UA_ASSOCIATION_SET_NUMBER,
					"TableNameStub");
		}
	}

	private final static Pattern START_TIME_PATTERN = Pattern
			.compile("(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}).\\d{2}0\\+(\\d{2}:\\d{2}:\\d{2})");

	public XmlReaderParser() {

	}

	private File createMxFileFromString(final String trim, final File file,
			final int fileCounter) {

		final String fileName = file.getPath() + File.separator
				+ file.getName() + "_" + fileCounter
				+ DxtPerformanceConstants.XML_ENDING;

		PrintWriter out = null;

		try {
			out = new PrintWriter(fileName);
			out.println(trim);
		} catch (final FileNotFoundException e) {
			DXTPerformanceTestingTool.printLine("Can't find file " + fileName + e.getMessage() + "\n");
			return null;
		} finally {
			if (out != null) {
				out.close();
			}
		}

		return new File(fileName);
	}

	private void deleteDir(final File dir) {

		final File[] files = dir.listFiles();

		if (files != null) {
			for (final File myFile : files) {
				if (myFile.isDirectory()) {
					deleteDir(myFile);
				}
				myFile.delete();

			}
		}
	}

	private String generateM3UAClassName(final String localMoid) {

		if (localMoid.contains(DxtPerformanceConstants.ASSO_INDEX_TO_SPLIT)) {
			return "M3UAIndex";
		}

		return "M3UASet";
	}

	private String generateM3UADisplayName(final String localMoid) {

		if (localMoid.contains(DxtPerformanceConstants.ASSO_INDEX_TO_SPLIT)) {

			final String[] splittedName = localMoid.split("/");

			String setName = "";
			String indexNumber = "";

			for (final String name : splittedName) {

				if (name.contains(DxtPerformanceConstants.ASSO_SETNAME_TO_SPLIT)) {
					setName = name
							.replaceAll(
									DxtPerformanceConstants.PART_NAME_TO_SPLIT
											+ DxtPerformanceConstants.ASSO_SETNAME_TO_SPLIT,
									"");
				} else if (name
						.contains(DxtPerformanceConstants.ASSO_INDEX_TO_SPLIT)) {
					indexNumber = name.replaceAll(
							DxtPerformanceConstants.ASSO_INDEX_TO_SPLIT, "");
				}
			}

			return setName + "-" + indexNumber;
		}

		return localMoid.replaceAll(DxtPerformanceConstants.PART_NAME_TO_SPLIT
				+ DxtPerformanceConstants.ASSO_SETNAME_TO_SPLIT, "");
	}

	private Map<String, String> generateM3UAProperties(final String localMoid) {

		if (localMoid.contains(DxtPerformanceConstants.ASSO_INDEX_TO_SPLIT)) {
			return m3UAAssociationIndexReportsNaming;
		}

		return m3UAAssociationSetReportsNaming;
	}

	public Map<String, Date> generatePerformanceTimeMap(final String startTime,
			final String intervalString) {

		final Map<String, Date> timeMap = new HashMap<String, Date>(2, 1);

		final Matcher timeMatcher = START_TIME_PATTERN.matcher(startTime);

		if (timeMatcher.find() && timeMatcher.groupCount() > 0) {
			try {
				final Calendar cal = Calendar.getInstance();

				final SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd'T'HH:mm:ss");
				final Date d = sdf.parse(timeMatcher.group(1));

				cal.setTime(d);
				timeMap.put(DxtPerformanceConstants.START_TIME, d);
				timeMap.put(DxtPerformanceConstants.COUNTER_TIME, d);

				Integer interval = null;
				try {
					interval = Integer.parseInt(intervalString);
				} catch (final Exception e) {
					DXTPerformanceTestingTool.printLine("Can't parse interval value " + e.getMessage() + "\n");
					return null;
				}

				cal.add(Calendar.MINUTE, interval);
				timeMap.put(DxtPerformanceConstants.END_TIME, cal.getTime());

			} catch (final ParseException e) {
				DXTPerformanceTestingTool.printLine("Can't parse : " + timeMatcher.group(1) + " :  " + e.getMessage() + "\n");
			}
		}

		return timeMap;
	}

	private List<File> generateSeparatedMxFilesList(final File file) {

		final List<File> separatedMxFiles = new ArrayList<File>();

		final String dirName = (file.getName()).replace(
				DxtPerformanceConstants.XML_ENDING, "").replace(
				DxtPerformanceConstants.BIG_XML_ENDING, "");

		final String trainingDir = file.getParent() + File.separator + dirName;

		final File newFile = new File(trainingDir);
		final boolean isDirectoryCreated = newFile.mkdir();

		if (!isDirectoryCreated) {
			deleteDir(newFile); // Invoke recursive method
			file.mkdir();
		}

		// Directory is cleaned generating files.
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));

			try {
				final StringBuilder sb = new StringBuilder();
				String line = null;
				line = br.readLine();
				int counter = 0;
				while (line != null) {

					if (line.contains("<?xml version=")) {
						if (counter != 0) {
							separatedMxFiles.add(createMxFileFromString(sb
									.toString().trim(), newFile, counter));
							sb.setLength(0);
						}
						counter++;
					}

					// For some reason some xml elements looks like <-Something>
					else if (line.contains("<-")) {
						line = line.replace("<-", "<");
					} else if (line.contains("</-")) {
						line = line.replace("</-", "</");
					}

					sb.append(line);
					sb.append('\n');
					line = br.readLine();

				}
				separatedMxFiles.add(createMxFileFromString(sb.toString()
						.trim(), newFile, counter));
			} catch (final FileNotFoundException e1) {
				DXTPerformanceTestingTool.printLine("Can't find file : " + e1.getMessage() + "\n");
			} catch (final IOException e) {
				DXTPerformanceTestingTool.printLine("General IO exception : " + e.getMessage() + "\n");
			}
		} catch (final FileNotFoundException e) {
			DXTPerformanceTestingTool.printLine("Can't find file : " + e.getMessage() + "\n");
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (final IOException e) {
				DXTPerformanceTestingTool.printLine("General IO exception : " + e.getMessage() + "\n");
			}
		}

		return separatedMxFiles;
	}

	private Map<String, Object> getAdditionalMeasurementProps(
			final NamedNodeMap perfNodeAttributes) {

		final Map<String, Object> additionalMeasProps = new HashMap<String, Object>();

		for (int i = 0; i < perfNodeAttributes.getLength(); i++) {

			if (perfNodeAttributes.item(i).getNodeName()
					.contains(DxtPerformanceConstants.LOCAL_MO_ID)) {
				additionalMeasProps.put(DxtPerformanceConstants.LOCAL_MO_ID,
						perfNodeAttributes.item(i).getNodeValue());
			} else if (perfNodeAttributes.item(i).getNodeName()
					.contains(DxtPerformanceConstants.MEASUREMENT_TYPE)) {
				additionalMeasProps.put(
						DxtPerformanceConstants.MEASUREMENT_TYPE,
						perfNodeAttributes.item(i).getNodeValue());
			}
		}

		return additionalMeasProps;
	}

	private Map<String, Object> getAllPerformancesFromDoc(final Document doc) {

		final Node mainNode = doc.getElementsByTagName(
				DxtPerformanceConstants.PM_SETUP).item(0);

		final Map<String, Object> perfFileValueMap = new HashMap<String, Object>();

		Boolean reportSupported = false;

		// Can contain data for different localMoids
		final Map<String, Object> perfValuesMap = new HashMap<String, Object>();

		if (isElementNode(mainNode)) {

			final NamedNodeMap topLevelAttributes = mainNode.getAttributes();

			if (topLevelAttributes == null) {
				return Collections.emptyMap();
			}

			// topLevelAttributes contains 3 basic elements. Don't need to
			// change nothing
			for (int i = 0; i < topLevelAttributes.getLength(); i++) {
				perfFileValueMap.put(topLevelAttributes.item(i).getNodeName(),
						topLevelAttributes.item(i).getNodeValue());
			}

			// recurse on each child
			final NodeList nodes = mainNode.getChildNodes();

			if (nodes != null) {

				String measureName = "";

				for (int i = 0; i < nodes.getLength(); i++) {

					if (isElementNode(nodes.item(i))) {
						if (nodes
								.item(i)
								.getNodeName()
								.contains(
										DxtPerformanceConstants.EXT_INFO_SETUP)) {

							if (nodes.item(i).hasChildNodes()) {
								// Get the measurements name
								measureName = getMeasurementName(nodes.item(i));

								perfFileValueMap
										.put(DxtPerformanceConstants.MEASUREMENT_NAME,
												measureName);
							}
						} else {
							final NamedNodeMap perfNodeAttributes = nodes.item(
									i).getAttributes();

							final Map<String, Object> perfValueMap = new HashMap<String, Object>();

							perfValueMap
									.putAll(getAdditionalMeasurementProps(perfNodeAttributes));

							final String measurementType = perfValueMap.get(
									DxtPerformanceConstants.MEASUREMENT_TYPE)
									.toString();

							final String localMoid = perfValueMap.get(
									DxtPerformanceConstants.LOCAL_MO_ID)
									.toString();

							// Checking is measurementType is supported
							// No need to create 'UNKNOWN' reports because they
							// are empty
							if (repNumberToTableName
									.containsKey(measurementType)
									&& repNumberToPropertiesMap
											.containsKey(measurementType)) {

								if (!localMoid
										.contains(DxtPerformanceConstants.UNKNOWN)) {

									perfValueMap
											.putAll(getXmlPropertiesFromNodetoMap(
													nodes.item(i),
													measurementType, localMoid));

									if (measurementType
											.equals(M3UA_ASSOCIATION_SET_NUMBER)) {
										perfValueMap
												.put(DxtPerformanceConstants.CUSTOM_CLASS_NAME,
														"UIM_DXTPM"
																+ generateM3UAClassName(localMoid));
										perfValueMap
												.put(DxtPerformanceConstants.DISPLAY_NAME,
														generateM3UADisplayName(localMoid));

									} else {
										perfValueMap
												.put(DxtPerformanceConstants.CUSTOM_CLASS_NAME,
														"UIM_DXTPM"
																+ repNumberToTableName
																		.get(measurementType));
										perfValueMap
												.put(DxtPerformanceConstants.DISPLAY_NAME,
														localMoid
																.substring(localMoid
																		.indexOf("/") + 1));
									}

									perfValueMap
											.put(DxtPerformanceConstants.DESCRIPTION,
													measureName);

									perfValueMap
											.putAll(parseLocalMoId(localMoid));

									perfValuesMap.put(localMoid, perfValueMap);

									reportSupported = true;
								} else {
									DXTPerformanceTestingTool.printLine("Local mo id is UNKNOWN : " + localMoid + "\n");
								}
							} else {
								DXTPerformanceTestingTool.printLine("Unsupported measurement type : " + measurementType + "\n");
							}
						}
					}
				}
			}
		}

		if (!reportSupported) {
			return Collections.emptyMap();
		}

		perfFileValueMap.put(DxtPerformanceConstants.PERFORMANCE_MAP,
				perfValuesMap);

		return perfFileValueMap;
	}

	private Document getDocument(final File file) {

		final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		DocumentBuilder db;
		Document doc = null;

		try {
			db = dbf.newDocumentBuilder();
			try {
				doc = db.parse(file);
				doc.getDocumentElement().normalize();
			} catch (final SAXException e) {
				DXTPerformanceTestingTool.printLine("General SAX exception for : " + file.getName() + e.getMessage() + "\n");
			} catch (final IOException e) {
				DXTPerformanceTestingTool.printLine("Exception while reading xml document for : " + file.getName() + e.getMessage() + "\n");
			}
		} catch (final ParserConfigurationException e) {
			DXTPerformanceTestingTool.printLine("Exception while parsing xml document for : " + file.getName() + e.getMessage() + "\n");
		}

		return doc;
	}

	private final String getMeasurementName(final Node node) {

		final NodeList measName = node.getChildNodes();

		String measureName = "";

		for (int x = 0; x < measName.getLength(); x++) {

			if (measName.item(x).getNodeName()
					.contains(DxtPerformanceConstants.MEASUREMENT_NAME)) {
				measureName = measName.item(x).getFirstChild().getNodeValue();

				break;
			}
		}

		return measureName;
	}

	private Map<String, Object> getXmlPropertiesFromNodetoMap(final Node node,
			final String measurementType, final String localMoid) {

		final Map<String, Object> props = new HashMap<String, Object>();

		final NodeList childs = node.getChildNodes();

		Map<String, String> reportMap = new HashMap<String, String>();

		if (measurementType.equals(M3UA_ASSOCIATION_SET_NUMBER)) {
			reportMap = generateM3UAProperties(localMoid);
		} else {
			reportMap = repNumberToPropertiesMap.get(measurementType);
		}

		if (childs != null) {
			for (int x = 0; x < childs.getLength(); x++) {

				if (isElementNode(childs.item(x))) {
					if (childs.item(x).getNodeName().contains(measurementType)) {

						final String propertyName = childs.item(x)
								.getNodeName();
						if (reportMap.containsKey(propertyName)) {
							props.put(reportMap.get(propertyName),
									nodeValueToLong(childs.item(x)));
						}
					}
				}
			}
		}

		return props;
	}

	private Long nodeValueToLong(final Node node) {

		Long nodeValue = null;
		final String value = node.getFirstChild().getNodeValue();

		try {
			nodeValue = Long.parseLong(value);
		} catch (final NumberFormatException exc) {
			DXTPerformanceTestingTool.printLine("Can't parse " + value + " setting it to empty value" + "\n");
		}

		return nodeValue;
	}

	private Map<String, Object> parseLocalMoId(final String localMoId) {

		final Map<String, Object> localMoIdProps = new HashMap<String, Object>();

		final String localIdClean = localMoId.replaceAll(
				DxtPerformanceConstants.PART_NAME_TO_SPLIT, "");

		final String[] splittedName = localIdClean.split("/");

		for (final String propName : splittedName) {

			if (propName.contains(DxtPerformanceConstants.UNIT_NAME_TO_SPLIT)) {
				final String unit = propName.replaceAll(
						DxtPerformanceConstants.UNIT_NAME_TO_SPLIT, "");

				if (unit.equals(DxtPerformanceConstants.OMU_TYPE)) {
					localMoIdProps.put(DxtPerformanceConstants.UNIT_NAME,
							DxtPerformanceConstants.OMU_TYPE);
				} else {
					final String[] unitSplitted = unit.split("_");
					if (unitSplitted.length > 1) {
						localMoIdProps.put(DxtPerformanceConstants.UNIT_NAME,
								unitSplitted[0]);

						if (unit.contains(DxtPerformanceConstants.SIPU_TYPE)) {
							if (unitSplitted.length > 2) {
								localMoIdProps
										.put(DxtPerformanceConstants.UNIT_INDEX,
												unitSplitted[1] + "-"
														+ unitSplitted[2]);
							}
						} else {
							localMoIdProps.put(
									DxtPerformanceConstants.UNIT_INDEX,
									unitSplitted[1]);
						}
					}
				}

			} else if (propName
					.contains(DxtPerformanceConstants.PLUGIN_NAME_TO_SPLIT)) {
				final String plugin = propName.replaceAll(
						DxtPerformanceConstants.PLUGIN_NAME_TO_SPLIT, "");

				final String[] pluginSplitted = plugin.split("_");

				if (pluginSplitted.length > 2) {
					localMoIdProps.put(DxtPerformanceConstants.PLUGIN_TYPE,
							pluginSplitted[0] + "-" + pluginSplitted[1]);
					localMoIdProps.put(DxtPerformanceConstants.PLUGIN_INDEX,
							pluginSplitted[2]);
				}

			} else if (propName
					.contains(DxtPerformanceConstants.INTERFACE_NAME_TO_SPLIT)) {
				final String inter = propName.replaceAll(
						DxtPerformanceConstants.INTERFACE_NAME_TO_SPLIT, "");
				localMoIdProps.put(DxtPerformanceConstants.INTERFACE_NAME,
						inter);
			} else if (propName
					.contains(DxtPerformanceConstants.ASSO_SETNAME_TO_SPLIT)) {
				// Parent is m3ua inside performances top level
				localMoIdProps.put(DxtPerformanceConstants.UNIT_NAME,
						DxtPerformanceConstants.M3UA);
				localMoIdProps.put(DxtPerformanceConstants.ASSOCIATION_NAME,
						propName.replaceAll(
								DxtPerformanceConstants.ASSO_SETNAME_TO_SPLIT,
								""));
			} else if (propName
					.contains(DxtPerformanceConstants.ASSO_INDEX_TO_SPLIT)) {
				localMoIdProps
						.put(DxtPerformanceConstants.ASSOCIATION_INDEX,
								propName.replaceAll(
										DxtPerformanceConstants.ASSO_INDEX_TO_SPLIT,
										""));
			}
		}

		return localMoIdProps;

	}

	public List<Map<String, Object>> parseXmlPerformanceFile(
			final String fileName) {

		final List<Map<String, Object>> perfFileValueMap = new ArrayList<Map<String, Object>>();
		final File file = new File(fileName);

		final List<File> mxFiles = generateSeparatedMxFilesList(file);

		for (final File mxFile : mxFiles) {

			// Need to add information about all subFiles
			if (mxFile != null) {
				try {
					final Document doc = getDocument(mxFile);
					perfFileValueMap.add(getAllPerformancesFromDoc(doc));
				} catch (final Exception e) {
					DXTPerformanceTestingTool.printLine("General exception : " + e.getMessage() + "\n");
				}
			}
		}
		DXTPerformanceTestingTool.printLine("XML map for : " + fileName + " recieved" + "\n");
		
		return perfFileValueMap;
	}
}
