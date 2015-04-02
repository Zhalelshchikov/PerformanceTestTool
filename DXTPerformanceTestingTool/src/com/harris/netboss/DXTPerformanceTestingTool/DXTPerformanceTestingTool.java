package com.harris.netboss.dxtPerformanceTestingTool;

import java.awt.Cursor;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

import com.harris.netboss.dxtPerformanceTestingTool.parsers.CommonReportsParser;
import com.harris.netboss.dxtPerformanceTestingTool.parsers.DataParser;
import com.harris.netboss.dxtPerformanceTestingTool.parsers.MTPReportsParser;
import com.harris.netboss.dxtPerformanceTestingTool.parsers.MeasurementsReportsParser;
import com.harris.netboss.dxtPerformanceTestingTool.parsers.XmlReaderParser;
import com.harris.netboss.dxtPerformanceTestingTool.recordscreator.DxtPerformanceConstants;

public class DXTPerformanceTestingTool extends javax.swing.JFrame {

	@SuppressWarnings("deprecation")
	private void initComponents() {

		previousVersionButton = new javax.swing.JButton();
		previousVersionTextField = new javax.swing.JTextField();
		previousVersionLabel = new javax.swing.JLabel();
		startButton = new javax.swing.JButton();
		jScrollPane1 = new javax.swing.JScrollPane();
		textArea = new javax.swing.JTextArea();
		separator = new javax.swing.JSeparator();
		outputLabel = new javax.swing.JLabel();
		outputFileLabel = new javax.swing.JLabel();
		outputFileTextField = new javax.swing.JTextField();
		outputFileButton = new javax.swing.JButton();
		perfomanceFileStateCheckerRadioButton = new javax.swing.JRadioButton();
		perfomanceFileReaderRadioButton = new javax.swing.JRadioButton();
		menuBar = new javax.swing.JMenuBar();
		fileMenu = new javax.swing.JMenu();
		exitMenu = new javax.swing.JMenuItem();
		helpMenu = new javax.swing.JMenu();
		aboutMenu = new javax.swing.JMenuItem();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("DXT performance testing tool");
		setBounds(new java.awt.Rectangle(300, 200, 0, 0));
		setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
		setName("Agent Migration Tool"); // NOI18N
		setResizable(false);

		previousVersionButton.setText("Browse...");
		previousVersionButton
				.addMouseListener(new java.awt.event.MouseAdapter() {
					public void mouseClicked(java.awt.event.MouseEvent evt) {
						previousVersionButtonMouseClicked(evt);
					}
				});

		previousVersionButton.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				previousVersionButtonKeyPressed(evt);
			}
		});

		previousVersionLabel.setText("Previous version:");

		startButton.setText("Start");
		startButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				startButtonMouseClicked(evt);
			}
		});

		textArea.setColumns(20);
		textArea.setRows(5);
		textArea.setEnabled(false);
		jScrollPane1.setViewportView(textArea);

		outputLabel.setText("Output:");

		outputFileLabel.setText("Output file:");

		outputFileButton.setText("Browse...");
		outputFileButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				outputFileButtonMouseClicked(evt);
			}
		});
		outputFileButton.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				outputFileButtonKeyPressed(evt);
			}
		});

		perfomanceFileStateCheckerRadioButton.setSelected(true);
		perfomanceFileStateCheckerRadioButton
				.setLabel("Performance File State Checker");
		perfomanceFileStateCheckerRadioButton
				.addMouseListener(new java.awt.event.MouseAdapter() {
					public void mouseClicked(java.awt.event.MouseEvent evt) {
						perfomanceFileStateCheckerRadioButtonMouseClicked(evt);
					}
				});

		perfomanceFileReaderRadioButton.setText("Performance File Reader");
		perfomanceFileReaderRadioButton
				.addMouseListener(new java.awt.event.MouseAdapter() {
					public void mouseClicked(java.awt.event.MouseEvent evt) {
						perfomanceFileReaderRadioButtonMouseClicked(evt);
					}
				});

		fileMenu.setText("File");

		exitMenu.setText("Exit");
		exitMenu.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				exitMenuMousePressed(evt);
			}
		});
		fileMenu.add(exitMenu);

		menuBar.add(fileMenu);

		helpMenu.setText("Help");
		helpMenu.setToolTipText("");

		aboutMenu.setText("About");
		aboutMenu.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				aboutMenuMousePressed(evt);
			}
		});
		helpMenu.add(aboutMenu);

		menuBar.add(helpMenu);

		setJMenuBar(menuBar);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																				.addComponent(
																						previousVersionLabel,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE)
																				.addComponent(
																						outputFileLabel,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE)
																				.addGroup(
																						layout.createSequentialGroup()
																								.addComponent(
																										perfomanceFileStateCheckerRadioButton)
																								.addGap(0,
																										0,
																										Short.MAX_VALUE))
																				.addGroup(
																						layout.createSequentialGroup()
																								.addGroup(
																										layout.createParallelGroup(
																												javax.swing.GroupLayout.Alignment.LEADING)
																												.addComponent(
																														perfomanceFileReaderRadioButton)
																												.addComponent(
																														outputLabel))
																								.addGap(38,
																										38,
																										38)))
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																				.addGroup(
																						layout.createSequentialGroup()
																								.addComponent(
																										outputFileTextField,
																										javax.swing.GroupLayout.PREFERRED_SIZE,
																										308,
																										javax.swing.GroupLayout.PREFERRED_SIZE)
																								.addPreferredGap(
																										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																								.addComponent(
																										outputFileButton))
																				.addGroup(
																						layout.createSequentialGroup()
																								.addComponent(
																										previousVersionTextField,
																										javax.swing.GroupLayout.PREFERRED_SIZE,
																										308,
																										javax.swing.GroupLayout.PREFERRED_SIZE)
																								.addPreferredGap(
																										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																								.addComponent(
																										previousVersionButton))
																				.addComponent(
																						startButton,
																						javax.swing.GroupLayout.PREFERRED_SIZE,
																						308,
																						javax.swing.GroupLayout.PREFERRED_SIZE))
																.addGap(12, 12,
																		12))
												.addGroup(
														layout.createSequentialGroup()
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																				.addComponent(
																						jScrollPane1)
																				.addComponent(
																						separator,
																						javax.swing.GroupLayout.Alignment.TRAILING))
																.addContainerGap()))));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGap(22, 22, 22)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(
														previousVersionButton)
												.addComponent(
														previousVersionTextField,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(
														previousVersionLabel))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(outputFileButton)
												.addComponent(
														outputFileTextField,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(outputFileLabel))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING,
												false)
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		perfomanceFileStateCheckerRadioButton)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		perfomanceFileReaderRadioButton))
												.addComponent(
														startButton,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														48, Short.MAX_VALUE))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(separator,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										10,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(outputLabel)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jScrollPane1,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										169,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap()));

		pack();
		setLocationRelativeTo(null);
	}// </editor-fold>

	static File dir;

	public static void setDir(File file) {
		dir = file;

	}

	private void previousVersionButtonMouseClicked(java.awt.event.MouseEvent evt) {
		JFileChooser fileopenPreviousVersion = new JFileChooser();
		fileopenPreviousVersion
				.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileopenPreviousVersion.setCurrentDirectory(dir);
		fileopenPreviousVersion.showDialog(null, "Open folder");
		File currentDirectoryPreviousVersion;
		currentDirectoryPreviousVersion = fileopenPreviousVersion
				.getSelectedFile();

		if (fileopenPreviousVersion.getSelectedFile() != null) {
			previousVersionTextField.setText(currentDirectoryPreviousVersion
					.toString());
			setDir(currentDirectoryPreviousVersion);
		}
	}

	private void aboutMenuMousePressed(java.awt.event.MouseEvent evt) {
		About frm;
		frm = new About();
		frm.setVisible(true);
	}

	private void exitMenuMousePressed(java.awt.event.MouseEvent evt) {
		System.exit(0);
	}

	private void outputFileButtonMouseClicked(java.awt.event.MouseEvent evt) {
		JFileChooser fileopenPreviousVersion = new JFileChooser();
		fileopenPreviousVersion.setCurrentDirectory(dir);
		fileopenPreviousVersion.showDialog(null, "Save file");
		File currentDirectoryPreviousVersion;

		currentDirectoryPreviousVersion = fileopenPreviousVersion
				.getSelectedFile();
		if (fileopenPreviousVersion.getSelectedFile() != null) {
			outputFileTextField.setText(currentDirectoryPreviousVersion
					.toString());
			setDir(currentDirectoryPreviousVersion);
		}
	}

	@SuppressWarnings("unchecked")
	private void startButtonMouseClicked(java.awt.event.MouseEvent evt) {
		setCursor(new Cursor(Cursor.WAIT_CURSOR));
		textArea.setText(null);

		File f1 = new File(previousVersionTextField.getText());
		File f2 = new File(outputFileTextField.getText());

		Pattern pf = Pattern.compile("(.*):");
		Matcher mf = pf.matcher(outputFileTextField.getText());

		if (mf.find()) {
			try {
				f2.createNewFile();
			} catch (IOException ex) {
				Logger.getLogger(DXTPerformanceTestingTool.class.getName())
						.log(Level.SEVERE, null, ex);
			}
		}

		if (f1.exists()) {
			if (!previousVersionTextField.getText().isEmpty()) {
				if (outputFileTextField.getText().isEmpty() || f2.exists()) {
					if (perfomanceFileStateCheckerRadioButton.isSelected()) {
						final File directory = new File(
								previousVersionTextField.getText());
						final File[] files = directory.listFiles();

						File ttscFile = null;

						File ttscFileXml = null;

						if (files != null) {
							for (final File file : files) {

								if (file.getName().contains(fileNameTTSC)) {
									ttscFile = file;
								} else if (file.getName().contains(
										fileNameTTSC_XML)) {
									ttscFileXml = file;
								}
							}
						}

						try {
							if (ttscFile != null) {
								printLine("Check " + ttscFile.getName() + "\n");
								generateRecordsToUpdateFromTTSCFile(ttscFile);
							} else {
								printLine("TTSC binary file was not found.\n");
							}

							if (ttscFileXml != null) {
								printLine("Check " + ttscFileXml.getName()
										+ "\n");
								generateRecordsToUpdateFromTTSCFile(ttscFileXml);
							} else {
								printLine("TTSC xml file was not found.\n");
							}
						} catch (final IOException e) {
							printLine("Error during loading : "
									+ e.getMessage() + "\n");
						}
					}
					if (perfomanceFileReaderRadioButton.isSelected()) {
						printLine("Started\n");

						final File directory = new File(
								previousVersionTextField.getText());

						final Map<Integer, File> mxFilesSim = getMFilesFromSimFolder(
								"MX", directory);
						final Map<Integer, File> meFilesSim = getMFilesFromSimFolder(
								"ME", directory);

						createAllReports(meFilesSim);

						for (final File fileName : mxFilesSim.values()) {

							if (fileName != null && fileName.isFile()) {
								final List<Map<String, Object>> xmlProps = xmlParser
										.parseXmlPerformanceFile(fileName
												.getPath());
								printLine("Properties : " + xmlProps + "\n");
							}
						}
						printLine("Finished\n");

					}
					PrintWriter writerList;
					if (f2.exists()) {

						try {
							writerList = new PrintWriter(
									outputFileTextField.getText());
							writerList.println(line.toString());
							writerList.close();

							@SuppressWarnings("rawtypes")
							List<String> list = new ArrayList<String>();
							try (BufferedReader reader = new BufferedReader(
									new FileReader(
											outputFileTextField.getText()))) {
								list = new ArrayList<>();
								String line = null;
								while ((line = reader.readLine()) != null) {
									Pattern p = Pattern
											.compile(".* = .*||.*[.||.IMG||: '\n'||: [0-9]]");
									Matcher m = p.matcher(line);

									if (m.matches()) {
										String g2 = m.group();
										list.add(g2);
									}
								}
							}

							writerList = new PrintWriter(
									outputFileTextField.getText());

							for (String word : list) {
								writerList.println(word);
							}

							writerList.close();

						} catch (FileNotFoundException ex) {
							Logger.getLogger(
									DXTPerformanceTestingTool.class.getName())
									.log(Level.SEVERE, null, ex);

						} catch (IOException ex) {
							Logger.getLogger(
									DXTPerformanceTestingTool.class.getName())
									.log(Level.SEVERE, null, ex);
						}
					}

				} else {
					Error frm;
					frm = new Error();
					frm.setVisible(true);

				}
			}
		} else {
			Error frm;
			frm = new Error();
			frm.setVisible(true);

		}

		textArea.setText(line.toString());
		line.delete(0, line.length());
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	private void previousVersionButtonKeyPressed(java.awt.event.KeyEvent evt) {
		JFileChooser fileopenPreviousVersion = new JFileChooser();
		fileopenPreviousVersion.showDialog(null, "Open file");
		File currentDirectoryPreviousVersion;
		currentDirectoryPreviousVersion = fileopenPreviousVersion
				.getCurrentDirectory();
		previousVersionTextField.setText(currentDirectoryPreviousVersion
				.toString());
	}

	private void outputFileButtonKeyPressed(java.awt.event.KeyEvent evt) {
		JFileChooser fileopenPreviousVersion = new JFileChooser();
		fileopenPreviousVersion.showDialog(null, "Open file");
		File currentDirectoryPreviousVersion;
		currentDirectoryPreviousVersion = fileopenPreviousVersion
				.getSelectedFile();
		outputFileTextField.setText(currentDirectoryPreviousVersion.toString());
	}

	private void perfomanceFileStateCheckerRadioButtonMouseClicked(
			java.awt.event.MouseEvent evt) {
		perfomanceFileReaderRadioButton.setSelected(false);
	}

	private void perfomanceFileReaderRadioButtonMouseClicked(
			java.awt.event.MouseEvent evt) {
		perfomanceFileStateCheckerRadioButton.setSelected(false);
	}

	public List<String> printRecordStates(final List<String> hexString,
			final int recordNumber, final int tTFileType) {

		try {
			final int fileState = Integer.parseInt(hexString.get(0), 16);
			printLine("Record " + recordNumber + " is in the "
					+ FILE_STATES_MAP.get(fileState) + " state.\n");
		} catch (final Exception e) {
			printLine("Can't get file state value for " + hexString.get(0)
					+ "\n");
		}

		return Collections.emptyList();
	}

	private void readAllRecordsData(final List<String> fileDataInHex) {

		final int recordsCount = fileDataInHex.size() / ttscofRecordBytes;

		final Map<Integer, List<String>> valuesToChange = new HashMap<>();

		int countElement;

		for (int i = 1; i < recordsCount; i++) {

			final List<String> reportData = new ArrayList<>();
			List<String> resultData = new ArrayList<>();

			for (int j = 0; j < ttscofRecordBytes; j++) {

				countElement = i * (ttscofRecordBytes) + j;

				if (countElement > fileDataInHex.size()) {
					printLine("Wrong file bytes format.\n");
					return;
				} else {
					reportData.add(j, fileDataInHex.get(countElement));
				}
			}

			resultData = printRecordStates(reportData, i, 0);
			if (!resultData.isEmpty()) {
				valuesToChange.put(i, resultData);
			}
		}
	}

	public List<String> convertToHex(final File file) throws IOException {

		final List<String> hexValues = new ArrayList<>();

		InputStream is = null;

		if (file == null) {
			final List<String> emptyHex = Collections.emptyList();
			printLine("File is null, return EmptyList.\n");
			return emptyHex;
		}

		try {
			is = new FileInputStream(file);

			int value = 0;

			while ((value = is.read()) != -1) {

				hexValues.add(String.format("%02X", value));
			}
		} catch (final IOException e) {
			printLine("Error converting file to hex : " + e.getMessage() + "\n");
			return new ArrayList<>(0);
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (final Exception e) {
				printLine("Can't close file input stream " + file.getName()
						+ " : " + e.getMessage() + "\n");
			}
		}
		return hexValues;
	}

	private void generateRecordsToUpdateFromTTSCFile(final File ttscFile)
			throws IOException {

		final List<String> hexValues = convertToHex(ttscFile);

		if (!hexValues.isEmpty()) {
			readAllRecordsData(hexValues);
		} else {
			printLine("Hex data is empty. Nothing to update.Exiting.\n");
		}

	}

	public static final Map<Integer, String> FILE_STATES_MAP = new HashMap<Integer, String>() {

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

	private void createAllReports(final Map<Integer, File> mFiles) {

		final List<Reports> reports = new ArrayList<Reports>();

		for (final Integer id : mFiles.keySet()) {

			final File mFile = mFiles.get(id);

			try {
				final List<String> hexValues = convertToHex(mFile);
				Map<String, Map<String, Object>> reportMap = new HashMap<String, Map<String, Object>>();

				if (!hexValues.isEmpty()) {

					final List<List<String>> splittedData = splitHexDataFile(
							hexValues, mFile.getName());

					if (splittedData != null) {

						for (final List<String> singleReport : splittedData) {

							final String reportType = singleReport.get(5)
									+ singleReport.get(4);
							printLine("Report type : " + reportType + "\n");

							reportMap = createReportMap(singleReport, id);
							printLine("Report map : " + reportMap + "\n");

							if (!reportMap.isEmpty()) {
								final Reports report = new Reports(reportMap);
								reports.add(report);
							}
						}
					}

				} else {
					printLine(createMFileName(id,
							DxtPerformanceConstants.meFileFirstPart)
							+ " is empty.. \n");
				}

			} catch (final IOException e) {
				printLine("Can't convert to hex "
						+ createMFileName(id,
								DxtPerformanceConstants.meFileFirstPart)
						+ " : " + e.getMessage() + "\n");
			}
		}

		Collections.sort(reports, new Comparator<Reports>() {
			@Override
			public int compare(final Reports o1, final Reports o2) {
				return o1.reportDate.compareTo(o2.reportDate);
			}
		});

		return;
	}

	public String createMFileName(final int meNumber, final String fileType) {

		String lastPart = "";

		if (fileType.equals(DxtPerformanceConstants.meFileFirstPart)) {
			lastPart = DxtPerformanceConstants.meFileLastPart;
		} else {
			lastPart = DxtPerformanceConstants.mxFileLastPart;
		}

		if (meNumber < 10) {
			return (fileType + "00" + meNumber + lastPart);
		} else if (meNumber < 100) {
			return (fileType + "0" + meNumber + lastPart);
		} else {
			return (fileType + meNumber + lastPart);
		}

	}

	private Map<String, Map<String, Object>> createReportMap(
			final List<String> meDataValues, final Integer fileId) {

		final String type = getReportType(meDataValues);

		Map<String, Map<String, Object>> report = new HashMap<String, Map<String, Object>>();

		try {
			if (validateFormatVersion(meDataValues)) {
				if (com.harris.netboss.dxtPerformanceTestingTool.parsers.MeasurementsReportsParser.measReportsTypes
						.keySet().contains(type)) {
					printLine("Measurement report found, type : " + type + "\n");

					report = measParser.createReport(meDataValues, type);

				} else if (com.harris.netboss.dxtPerformanceTestingTool.parsers.MTPReportsParser.mtpReportsTypes
						.keySet().contains(type)) {
					printLine("MTP report found, type : " + type + "\n");

					report = mtpParser.createReport(meDataValues, type);

				} else if (com.harris.netboss.dxtPerformanceTestingTool.parsers.CommonReportsParser.commonReportsTypes
						.keySet().contains(type)) {
					printLine("Common report found, type : " + type + "\n");

					report = commonParser.createReport(meDataValues, type);
				} else {
					printLine("Unknown report type : " + type + "\n");
				}
			}
		} catch (final Exception e) {
			printLine("Can't create report from " + fileId + " ME file.\n");
		}
		return report;
	}

	private List<Integer> getLenghtForEachReport(final List<String> meDataValues) {

		final List<Integer> result = new ArrayList<Integer>();
		int difference = 0;
		final int index = 0;

		while (difference < meDataValues.size()) {
			final int c = dp.createIntValue(index, meDataValues, difference);
			difference += c;
			result.add(c);
		}

		return result;
	}

	private static Map<Integer, File> getMFilesFromSimFolder(
			final String fileTemplate, final File directory) {

		final File[] files = directory.listFiles();

		final Map<Integer, File> mFilesSim = new HashMap<Integer, File>();
		if (directory.exists()) {
			if (files.length != 0) {

				for (int fileNumber = 0; fileNumber < files.length; fileNumber++) {

					// Skip reading not ME files (don't read folders also)
					if (files[fileNumber].getName().contains(fileTemplate)
							&& files[fileNumber].isFile()) {
						mFilesSim.put(fileNumber, files[fileNumber]);
					}
				}
			}
		}
		return mFilesSim;

	}

	private String getReportType(final List<String> meDataValues) {

		String type = "";
		try {
			type = meDataValues.get(5) + meDataValues.get(4);
		} catch (final Exception e) {
			printLine("Can't get report type\n");
			System.err.println("Can't get report type");
		}

		return type;
	}

	private List<List<String>> splitHexDataFile(
			final List<String> meDataValues, final String fileName) {

		final List<Integer> lengths = getLenghtForEachReport(meDataValues);
		final List<List<String>> splittedReport = new ArrayList<List<String>>();
		int startIndex = 0;

		if (lengths != null && !lengths.isEmpty()) {
			for (final int length : lengths) {

				if (startIndex + length > meDataValues.size()) {
					printLine("Wrong index in file " + fileName + "\n");
					return null;
				} else {
					splittedReport.add(meDataValues.subList(startIndex,
							startIndex + length));
					startIndex += length;
				}
			}
		}

		return splittedReport;
	}

	private boolean validateFormatVersion(final List<String> meDataValues) {

		final int formatVersion = dp.getFormatVersion(meDataValues);

		final Map<String, List<Integer>> allReportsVersionTypes = new HashMap<String, List<Integer>>();

		// Add all possible reports
		allReportsVersionTypes
				.putAll(MeasurementsReportsParser.measReportsTypes);
		allReportsVersionTypes.putAll(MTPReportsParser.mtpReportsTypes);
		allReportsVersionTypes.putAll(CommonReportsParser.commonReportsTypes);

		final List<Integer> possibleFormatVersions = allReportsVersionTypes
				.get(getReportType(meDataValues));

		if (!possibleFormatVersions.equals(DxtPerformanceConstants.format0)) {
			if (!possibleFormatVersions.contains(formatVersion)) {
				return false;
			}
		}

		return true;
	}

	private final static DataParser dp = new DataParser();

	private final static CommonReportsParser commonParser = new CommonReportsParser();

	private final static MeasurementsReportsParser measParser = new MeasurementsReportsParser();

	private final static MTPReportsParser mtpParser = new MTPReportsParser();

	private final static XmlReaderParser xmlParser = new XmlReaderParser();

	static int ttscofRecordBytes = 9;

	static final String fileNameTTSC = "TTSCOF01.IMG";

	static final String fileNameTTTC = "TTTCOF01.IMG";

	static final String fileNameTTSC_XML = "TTSCOF04.IMG";

	static final String fileNameTTTC_XML = "TTTCOF04.IMG";

	static final String MEAXML = "MEAXML";

	static final String ICON = "res\\AirbusDSDXT.gif";

	static int ttccofRecordBytes = 7;

	static int fullStateOfRecord = 1;

	static int lastDateByteNumberTtSC = 7;

	public final static String PERF_POLLING_FREQUENCY = "PerformancePollingFrequency";

	public final static String PERF_POLLING_TIME = "PerformancePollingTime";

	public static final String DXT_IDENTIFIER = "DXT";

	public static final String FORMAT_VERSION = "FormatVersion";

	public static final String REPORT_TYPE = "ReportType";

	public static final String NETWORK = "Network";

	public static final String OBJECT_NAME = "ObjectName";

	public static final String TBS_IDENTIFIER = "TbsIdentifier";

	public static final String LOCATION_AREA_ID = "LocationAreaId";

	public static final String COUNTER_TIME = "CounterTime";

	public static final String START_TIME = "StartTime";

	public static final String END_TIME = "EndTime";

	public static final String LINK_TYPE = "LinkType";

	public static final String UNIT_NAME = "UnitName";

	public static final String UNIT_INDEX = "UnitIndex";

	public static final String CONNECTED_UNIT_INDEX = "ConnectedUnitIndex";

	public static final String CONNECTED_UNIT_TYPE = "ConnectedUnitType";

	public static final String PLUGIN_TYPE = "PluginType";

	public static final String PLUGIN_INDEX = "PluginIndex";

	public static final String INTERFACE_NAME = "InterfaceName";

	public static final String INTERFACE_STATUS = "InterfaceStatus";

	public static final String ASSOCIATION_NAME = "AssociationName";

	public static final String ASSOCIATION_INDEX = "AssociationIndex";

	public static final String BASE_ID = "baseId";

	public static final String DESTINATION_ID = "DXTId";

	public static final String UNKNOWN = "UNKNOWN";

	static final String REPORT = "report";

	static final String RECORD = "record";

	public static final int CLEAR_ALARM = 3;

	public static final int ACTIVE_ALARM = 1;

	public static final String meFileFirstPart = "ME0";

	public static final String meFileLastPart = ".D01";

	public static final String mxFileFirstPart = "MX0";

	public static final String mxFileLastPart = ".XML";

	public static final Integer NOT_DEFINED_FORMAT = 0;

	static public List<Integer> format1;

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

	static {
		format1 = new ArrayList<>();
		{
			format1.add(1);
		}
	}

	static public List<Integer> format2;
	static {
		format2 = new ArrayList<>();
		{
			format2.addAll(format1);
			format2.add(2);
		}
	}

	static public List<Integer> format3;
	static {
		format3 = new ArrayList<Integer>();
		{
			format3.addAll(format2);
			format3.add(3);
		}
	}

	static public List<Integer> format4;
	static {
		format4 = new ArrayList<Integer>();
		{
			format4.addAll(format3);
			format4.add(4);
		}
	}

	static public List<Integer> format0;
	static {
		format0 = new ArrayList<>();
		{
			format0.add(NOT_DEFINED_FORMAT);
		}
	}

	static StringBuilder line = new StringBuilder();

	public static void printLine(String string) {
		line.append(string);

	}

	private static final long serialVersionUID = 1L;

	public DXTPerformanceTestingTool() {
		initComponents();
		setIconImage(getImage());
	}

	protected static Image getImage() {
		URL imgURL = DXTPerformanceTestingTool.class.getResource(ICON);
		if (imgURL != null) {
			return new ImageIcon(imgURL).getImage();
		} else {
			return null;
		}
	}

	public static void main(final String args[]) {

		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
					.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(
					DXTPerformanceTestingTool.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(
					DXTPerformanceTestingTool.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(
					DXTPerformanceTestingTool.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(
					DXTPerformanceTestingTool.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		}
		// </editor-fold>

		java.awt.EventQueue.invokeLater(() -> {
			new DXTPerformanceTestingTool().setVisible(true);
		});

	}

	private javax.swing.JButton previousVersionButton;
	private javax.swing.JButton outputFileButton;
	private javax.swing.JButton startButton;
	private javax.swing.JLabel previousVersionLabel;
	private javax.swing.JLabel outputFileLabel;
	private javax.swing.JLabel outputLabel;
	private javax.swing.JMenu fileMenu;
	private javax.swing.JMenu helpMenu;
	private javax.swing.JMenuBar menuBar;
	private javax.swing.JMenuItem exitMenu;
	private javax.swing.JMenuItem aboutMenu;
	private javax.swing.JRadioButton perfomanceFileStateCheckerRadioButton;
	private javax.swing.JRadioButton perfomanceFileReaderRadioButton;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JSeparator separator;
	private static javax.swing.JTextArea textArea;
	private javax.swing.JTextField previousVersionTextField;
	private javax.swing.JTextField outputFileTextField;
	// End of variables declaration

}
