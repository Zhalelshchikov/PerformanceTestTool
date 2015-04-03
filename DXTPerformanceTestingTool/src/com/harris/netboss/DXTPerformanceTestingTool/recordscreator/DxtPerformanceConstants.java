package com.harris.netboss.dxtPerformanceTestingTool.recordscreator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.harris.netboss.dxtPerformanceTestingTool.parsers.CommonReportsParser;
import com.harris.netboss.dxtPerformanceTestingTool.parsers.DataParser;
import com.harris.netboss.dxtPerformanceTestingTool.parsers.MTPReportsParser;
import com.harris.netboss.dxtPerformanceTestingTool.parsers.MeasurementsReportsParser;
import com.harris.netboss.dxtPerformanceTestingTool.parsers.XmlReaderParser;

/**
 * @author zheltukhin
 * @since 24.02.2012
 */

public class DxtPerformanceConstants {

	/** */
	public static final Map<Integer, String> FILE_STATES_MAP = new HashMap<Integer, String>() {

		/**  */
		private static final long serialVersionUID = 4850215773755592007L;

		{
			put(0, "OPEN");
			put(1, "FULL");
			put(2, "TRANSFERRED");
			put(3, "WAITING");
			put(4, "COMPRESSING");
			put(5, "UNUSEABLE");
		}
	};

	/**  */
	public final static String PERF_POLLING_FREQUENCY = "PerformancePollingFrequency";

	/**  */
	public final static String PERF_POLLING_TIME = "PerformancePollingTime";

	/**  */
	public static final String DXT_IDENTIFIER = "DXT";

	/**  */
	public static final String FORMAT_VERSION = "FormatVersion";

	/**  */
	public static final String REPORT_TYPE = "ReportType";

	/**  */
	public static final String NETWORK = "Network";

	/**  */
	public static final String OBJECT_NAME = "ObjectName";

	/**  */
	public static final String TBS_IDENTIFIER = "TbsIdentifier";

	/**  */
	public static final String LOCATION_AREA_ID = "LocationAreaId";

	/**  */
	public static final String COUNTER_TIME = "CounterTime";

	/**  */
	public static final String START_TIME = "StartTime";

	/**  */
	public static final String END_TIME = "EndTime";

	/**  */
	public static final String LINK_TYPE = "LinkType";

	/**  */
	public static final String UNIT_NAME = "UnitName";

	/**  */
	public static final String UNIT_INDEX = "UnitIndex";

	/**  */
	public static final String CONNECTED_UNIT_INDEX = "ConnectedUnitIndex";

	/**  */
	public static final String CONNECTED_UNIT_TYPE = "ConnectedUnitType";

	/**  */
	public static final String PLUGIN_TYPE = "PluginType";

	/**  */
	public static final String PLUGIN_INDEX = "PluginIndex";

	/**  */
	public static final String INTERFACE_NAME = "InterfaceName";

	/**  */
	public static final String INTERFACE_STATUS = "InterfaceStatus";

	/**  */
	public static final String ASSOCIATION_NAME = "AssociationName";

	/**  */
	public static final String ASSOCIATION_INDEX = "AssociationIndex";

	/**  */
	public static final String BASE_ID = "baseId";

	public static final String DESTINATION_ID = "DXTId";

	public static final String UNKNOWN = "UNKNOWN";

	public static final String REPORT = "report";

	public static final String RECORD = "record";

	/**  */
	public static final int CLEAR_ALARM = 3;

	/**  */
	public static final int ACTIVE_ALARM = 1;

	/**  */
	public static final String meFileFirstPart = "ME0";

	/**  */
	public static final String meFileLastPart = ".D01";

	/**  */
	public static final String mxFileFirstPart = "MX0";

	/**  */
	public static final String mxFileLastPart = ".XML";

	public static final DataParser dp = new DataParser();

	public static final CommonReportsParser commonParser = new CommonReportsParser();

	public static final MeasurementsReportsParser measParser = new MeasurementsReportsParser();

	public static final MTPReportsParser mtpParser = new MTPReportsParser();

	public static final XmlReaderParser xmlParser = new XmlReaderParser();

	public static final StringBuilder line = new StringBuilder();

	public static final JButton previousVersionButton = new javax.swing.JButton();

	public static final JTextField previousVersionTextField = new javax.swing.JTextField();

	public static final JLabel previousVersionLabel = new javax.swing.JLabel();

	public static final JButton startButton = new javax.swing.JButton();

	public static final JScrollPane jScrollPane1 = new javax.swing.JScrollPane();

	public static final JTextArea textArea = new javax.swing.JTextArea();

	public static final JSeparator separator = new javax.swing.JSeparator();

	public static final JLabel outputLabel = new javax.swing.JLabel();

	public static final JLabel outputFileLabel = new javax.swing.JLabel();

	public static final JTextField outputFileTextField = new javax.swing.JTextField();

	public static final JButton outputFileButton = new javax.swing.JButton();

	public static final JRadioButton perfomanceFileStateCheckerRadioButton = new javax.swing.JRadioButton();

	public static final JRadioButton perfomanceFileReaderRadioButton = new javax.swing.JRadioButton();

	public static final JMenuBar menuBar = new javax.swing.JMenuBar();

	public static final JMenu fileMenu = new javax.swing.JMenu();

	public static final JMenuItem exitMenu = new javax.swing.JMenuItem();

	public static final JMenu helpMenu = new javax.swing.JMenu();

	public static final JMenuItem aboutMenu = new javax.swing.JMenuItem();

	public static final String fileNameTTSC = "TTSCOF01.IMG";

	public static final String fileNameTTTC = "TTTCOF01.IMG";

	// for .xml measurements
	public static final String fileNameTTSC_XML = "TTSCOF04.IMG";
	public static final String fileNameTTTC_XML = "TTTCOF04.IMG";

	public static final String MEAXML = "MEAXML";

	// Number of bytes for each record in the appropriate files
	public static final int ttscofRecordBytes = 9;
	public static final int ttccofRecordBytes = 7;

	public static final int fullStateOfRecord = 1;
	public static final int lastDateByteNumberTtSC = 7;

	public static final String ICON = "res\\AirbusDSDXT.gif";

	/**
	 * Format version are supported for measurements reports and couple of
	 * observation reports only. MTP reports are not defining format version.
	 *
	 */

	public static final Integer NOT_DEFINED_FORMAT = 0;

	/**  */
	static public List<Integer> format1;
	static {
		format1 = new ArrayList<Integer>();
		{
			format1.add(1);
		}
	}

	/**  */
	static public List<Integer> format2;
	static {
		format2 = new ArrayList<Integer>();
		{
			format2.addAll(format1);
			format2.add(2);
		}
	}

	/**  */
	static public List<Integer> format3;
	static {
		format3 = new ArrayList<Integer>();
		{
			format3.addAll(format2);
			format3.add(3);
		}
	}

	/**  */
	static public List<Integer> format4;
	static {
		format4 = new ArrayList<Integer>();
		{
			format4.addAll(format3);
			format4.add(4);
		}
	}

	/**
	 * Format 0 means that format is not defined for such report
	 * */
	static public List<Integer> format0;
	static {
		format0 = new ArrayList<Integer>();
		{
			format0.add(NOT_DEFINED_FORMAT);
		}
	}

	public static final String TBS_REPORTS_NAME = "TBS reports";

	public static final String DXT_REPORTS_NAME = "DXT reports";

	public static final String MTP_REPORTS_NAME = "MTP reports";

	public static final String SPEECH_LINE_REPORTS_NAME = "Speech Line Reports";

	public static final String ISDN_REPORTS_NAME = "ISDN Reports";

	public static final String MESSAGE_BUS_REPORTS_NAME = "Message Bus Report";

	public static final String COMMON_REPORTS_NAME = "Common reports";

	public static final String UNKNOWN_REPORTS_NAME = "Unknown Element Reports";

	public static final String LAN_REPORTS_NAME = "Lan reports";

	public static final String M3UA_REPORTS_NAME = "M3UA reports";

	// Main node for performance files
	public static final String PM_SETUP = "PMSetup";

	public static final String INTERVAL = "interval";

	public static final String START_TIME_XML_PROPERTY = "startTime";

	public static final String LOCAL_MO_ID = "localMoid";

	public static final String MEASUREMENT_TYPE = "measurementType";

	public static final String MEASUREMENT_NAME = "measurementName";

	public static final String PART_NAME_TO_SPLIT = "DN:";

	public static final String UNIT_NAME_TO_SPLIT = "UNIT-";

	public static final String PLUGIN_NAME_TO_SPLIT = "PIU-";

	public static final String INTERFACE_NAME_TO_SPLIT = "INTERFACE-";

	public static final String ASSO_SETNAME_TO_SPLIT = "ASSO_SETNAME-";

	public static final String ASSO_INDEX_TO_SPLIT = "ASSO_INDEX-";

	public static final String M3UA = "M3UA";

	public static final String XML_ENDING = ".xml";

	public static final String BIG_XML_ENDING = ".XML";

	// Key for the main Map with the all performance inside
	public static final String PERFORMANCE_MAP = "perfMap";

	public static final String EXT_INFO_SETUP = "extraInfoSetup";

	public static final String PARENT = "Parent";

	public static final Integer MAX_TBS_NUMBER = 255;

	public static final String MB = "MB";

	public static final String DESCRIPTION = "Description";

	public static final String DISPLAY_NAME = "DisplayName";

	public static final String CUSTOM_CLASS_NAME = "CustomClassName";

	public static final String OMU_TYPE = "Omu";

	public static final String SIPU_TYPE = "Sipu";

	public static final String NAME = "Name";

}
