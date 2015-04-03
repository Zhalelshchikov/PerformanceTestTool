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
import com.harris.netboss.dxtPerformanceTestingTool.parsers.MTPReportsParser;
import com.harris.netboss.dxtPerformanceTestingTool.parsers.MeasurementsReportsParser;
import com.harris.netboss.dxtPerformanceTestingTool.recordscreator.DxtPerformanceConstants;

public class DXTPerformanceTestingTool extends javax.swing.JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private void initComponents() {

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("DXT performance testing tool");
		setBounds(new java.awt.Rectangle(300, 200, 0, 0));
		setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
		setResizable(false);

		DxtPerformanceConstants.previousVersionButton.setText("Browse...");
		DxtPerformanceConstants.previousVersionButton
				.addMouseListener(new java.awt.event.MouseAdapter() {
					public void mouseClicked(java.awt.event.MouseEvent evt) {
						previousVersionButtonMouseClicked(evt);
					}
				});

		DxtPerformanceConstants.previousVersionButton
				.addKeyListener(new java.awt.event.KeyAdapter() {
					public void keyPressed(java.awt.event.KeyEvent evt) {
						previousVersionButtonKeyPressed(evt);
					}
				});

		DxtPerformanceConstants.previousVersionLabel
				.setText("Previous version:");

		DxtPerformanceConstants.startButton.setText("Start");
		DxtPerformanceConstants.startButton
				.addMouseListener(new java.awt.event.MouseAdapter() {
					public void mouseClicked(java.awt.event.MouseEvent evt) {
						startButtonMouseClicked(evt);
					}
				});

		DxtPerformanceConstants.textArea.setColumns(20);
		DxtPerformanceConstants.textArea.setRows(5);
		DxtPerformanceConstants.textArea.setEnabled(false);
		DxtPerformanceConstants.jScrollPane1
				.setViewportView(DxtPerformanceConstants.textArea);

		DxtPerformanceConstants.outputLabel.setText("Output:");

		DxtPerformanceConstants.outputFileLabel.setText("Output file:");

		DxtPerformanceConstants.outputFileButton.setText("Browse...");
		DxtPerformanceConstants.outputFileButton
				.addMouseListener(new java.awt.event.MouseAdapter() {
					public void mouseClicked(java.awt.event.MouseEvent evt) {
						outputFileButtonMouseClicked(evt);
					}
				});
		DxtPerformanceConstants.outputFileButton
				.addKeyListener(new java.awt.event.KeyAdapter() {
					public void keyPressed(java.awt.event.KeyEvent evt) {
						outputFileButtonKeyPressed(evt);
					}
				});

		DxtPerformanceConstants.perfomanceFileStateCheckerRadioButton
				.setSelected(true);
		DxtPerformanceConstants.perfomanceFileStateCheckerRadioButton
				.setText("Performance File State Checker");
		DxtPerformanceConstants.perfomanceFileStateCheckerRadioButton
				.addMouseListener(new java.awt.event.MouseAdapter() {
					public void mouseClicked(java.awt.event.MouseEvent evt) {
						perfomanceFileStateCheckerRadioButtonMouseClicked(evt);
					}
				});

		DxtPerformanceConstants.perfomanceFileReaderRadioButton
				.setText("Performance File Reader");
		DxtPerformanceConstants.perfomanceFileReaderRadioButton
				.addMouseListener(new java.awt.event.MouseAdapter() {
					public void mouseClicked(java.awt.event.MouseEvent evt) {
						perfomanceFileReaderRadioButtonMouseClicked(evt);
					}
				});

		DxtPerformanceConstants.fileMenu.setText("File");

		DxtPerformanceConstants.exitMenu.setText("Exit");
		DxtPerformanceConstants.exitMenu
				.addMouseListener(new java.awt.event.MouseAdapter() {
					public void mousePressed(java.awt.event.MouseEvent evt) {
						exitMenuMousePressed(evt);
					}
				});
		DxtPerformanceConstants.fileMenu.add(DxtPerformanceConstants.exitMenu);

		DxtPerformanceConstants.menuBar.add(DxtPerformanceConstants.fileMenu);

		DxtPerformanceConstants.helpMenu.setText("Help");
		DxtPerformanceConstants.helpMenu.setToolTipText("");

		DxtPerformanceConstants.aboutMenu.setText("About");
		DxtPerformanceConstants.aboutMenu
				.addMouseListener(new java.awt.event.MouseAdapter() {
					public void mousePressed(java.awt.event.MouseEvent evt) {
						aboutMenuMousePressed(evt);
					}
				});
		DxtPerformanceConstants.helpMenu.add(DxtPerformanceConstants.aboutMenu);

		DxtPerformanceConstants.menuBar.add(DxtPerformanceConstants.helpMenu);

		setJMenuBar(DxtPerformanceConstants.menuBar);

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
																						DxtPerformanceConstants.previousVersionLabel,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE)
																				.addComponent(
																						DxtPerformanceConstants.outputFileLabel,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE)
																				.addGroup(
																						layout.createSequentialGroup()
																								.addComponent(
																										DxtPerformanceConstants.perfomanceFileStateCheckerRadioButton)
																								.addGap(0,
																										0,
																										Short.MAX_VALUE))
																				.addGroup(
																						layout.createSequentialGroup()
																								.addGroup(
																										layout.createParallelGroup(
																												javax.swing.GroupLayout.Alignment.LEADING)
																												.addComponent(
																														DxtPerformanceConstants.perfomanceFileReaderRadioButton)
																												.addComponent(
																														DxtPerformanceConstants.outputLabel))
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
																										DxtPerformanceConstants.outputFileTextField,
																										javax.swing.GroupLayout.PREFERRED_SIZE,
																										308,
																										javax.swing.GroupLayout.PREFERRED_SIZE)
																								.addPreferredGap(
																										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																								.addComponent(
																										DxtPerformanceConstants.outputFileButton))
																				.addGroup(
																						layout.createSequentialGroup()
																								.addComponent(
																										DxtPerformanceConstants.previousVersionTextField,
																										javax.swing.GroupLayout.PREFERRED_SIZE,
																										308,
																										javax.swing.GroupLayout.PREFERRED_SIZE)
																								.addPreferredGap(
																										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																								.addComponent(
																										DxtPerformanceConstants.previousVersionButton))
																				.addComponent(
																						DxtPerformanceConstants.startButton,
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
																						DxtPerformanceConstants.jScrollPane1)
																				.addComponent(
																						DxtPerformanceConstants.separator,
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
														DxtPerformanceConstants.previousVersionButton)
												.addComponent(
														DxtPerformanceConstants.previousVersionTextField,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(
														DxtPerformanceConstants.previousVersionLabel))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(
														DxtPerformanceConstants.outputFileButton)
												.addComponent(
														DxtPerformanceConstants.outputFileTextField,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(
														DxtPerformanceConstants.outputFileLabel))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING,
												false)
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		DxtPerformanceConstants.perfomanceFileStateCheckerRadioButton)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		DxtPerformanceConstants.perfomanceFileReaderRadioButton))
												.addComponent(
														DxtPerformanceConstants.startButton,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														48, Short.MAX_VALUE))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(
										DxtPerformanceConstants.separator,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										10,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(
										DxtPerformanceConstants.outputLabel)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(
										DxtPerformanceConstants.jScrollPane1,
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
			DxtPerformanceConstants.previousVersionTextField
					.setText(currentDirectoryPreviousVersion.toString());
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
			DxtPerformanceConstants.outputFileTextField
					.setText(currentDirectoryPreviousVersion.toString());
			setDir(currentDirectoryPreviousVersion);
		}
	}

	@SuppressWarnings("unchecked")
	private void startButtonMouseClicked(java.awt.event.MouseEvent evt) {
		setCursor(new Cursor(Cursor.WAIT_CURSOR));
		DxtPerformanceConstants.textArea.setText(null);

		File f1 = new File(
				DxtPerformanceConstants.previousVersionTextField.getText());
		File f2 = new File(
				DxtPerformanceConstants.outputFileTextField.getText());

		Pattern pf = Pattern.compile("(.*):");
		Matcher mf = pf.matcher(DxtPerformanceConstants.outputFileTextField
				.getText());

		if (mf.find()) {
			try {
				f2.createNewFile();
			} catch (IOException ex) {
				Logger.getLogger(DXTPerformanceTestingTool.class.getName())
						.log(Level.SEVERE, null, ex);
			}
		}

		if (f1.exists()) {
			if (!DxtPerformanceConstants.previousVersionTextField.getText()
					.isEmpty()) {
				if (DxtPerformanceConstants.outputFileTextField.getText()
						.isEmpty() || f2.exists()) {
					if (DxtPerformanceConstants.perfomanceFileStateCheckerRadioButton
							.isSelected()) {
						final File directory = new File(
								DxtPerformanceConstants.previousVersionTextField
										.getText());
						final File[] files = directory.listFiles();

						File ttscFile = null;

						File ttscFileXml = null;

						if (files != null) {
							for (final File file : files) {

								if (file.getName().contains(
										DxtPerformanceConstants.fileNameTTSC)) {
									ttscFile = file;
								} else if (file
										.getName()
										.contains(
												DxtPerformanceConstants.fileNameTTSC_XML)) {
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
					if (DxtPerformanceConstants.perfomanceFileReaderRadioButton
							.isSelected()) {
						printLine("Started\n");

						final File directory = new File(
								DxtPerformanceConstants.previousVersionTextField
										.getText());

						final Map<Integer, File> mxFilesSim = getMFilesFromSimFolder(
								"MX", directory);
						final Map<Integer, File> meFilesSim = getMFilesFromSimFolder(
								"ME", directory);

						createAllReports(meFilesSim);

						for (final File fileName : mxFilesSim.values()) {

							if (fileName != null && fileName.isFile()) {
								final List<Map<String, Object>> xmlProps = DxtPerformanceConstants.xmlParser
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
									DxtPerformanceConstants.outputFileTextField
											.getText());
							writerList.println(DxtPerformanceConstants.line
									.toString());
							writerList.close();

							@SuppressWarnings("rawtypes")
							List<String> list = new ArrayList<String>();
							try (BufferedReader reader = new BufferedReader(
									new FileReader(
											DxtPerformanceConstants.outputFileTextField
													.getText()))) {
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
									DxtPerformanceConstants.outputFileTextField
											.getText());

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

		DxtPerformanceConstants.textArea.setText(DxtPerformanceConstants.line
				.toString());
		DxtPerformanceConstants.line.delete(0,
				DxtPerformanceConstants.line.length());
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	private void previousVersionButtonKeyPressed(java.awt.event.KeyEvent evt) {
		JFileChooser fileopenPreviousVersion = new JFileChooser();
		fileopenPreviousVersion.showDialog(null, "Open file");
		File currentDirectoryPreviousVersion;
		currentDirectoryPreviousVersion = fileopenPreviousVersion
				.getCurrentDirectory();
		DxtPerformanceConstants.previousVersionTextField
				.setText(currentDirectoryPreviousVersion.toString());
	}

	private void outputFileButtonKeyPressed(java.awt.event.KeyEvent evt) {
		JFileChooser fileopenPreviousVersion = new JFileChooser();
		fileopenPreviousVersion.showDialog(null, "Open file");
		File currentDirectoryPreviousVersion;
		currentDirectoryPreviousVersion = fileopenPreviousVersion
				.getSelectedFile();
		DxtPerformanceConstants.outputFileTextField
				.setText(currentDirectoryPreviousVersion.toString());
	}

	private void perfomanceFileStateCheckerRadioButtonMouseClicked(
			java.awt.event.MouseEvent evt) {
		DxtPerformanceConstants.perfomanceFileReaderRadioButton
				.setSelected(false);
	}

	private void perfomanceFileReaderRadioButtonMouseClicked(
			java.awt.event.MouseEvent evt) {
		DxtPerformanceConstants.perfomanceFileStateCheckerRadioButton
				.setSelected(false);
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

		final int recordsCount = fileDataInHex.size()
				/ DxtPerformanceConstants.ttscofRecordBytes;

		final Map<Integer, List<String>> valuesToChange = new HashMap<>();

		int countElement;

		for (int i = 1; i < recordsCount; i++) {

			final List<String> reportData = new ArrayList<>();
			List<String> resultData = new ArrayList<>();

			for (int j = 0; j < DxtPerformanceConstants.ttscofRecordBytes; j++) {

				countElement = i * (DxtPerformanceConstants.ttscofRecordBytes)
						+ j;

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

					report = DxtPerformanceConstants.measParser.createReport(
							meDataValues, type);

				} else if (com.harris.netboss.dxtPerformanceTestingTool.parsers.MTPReportsParser.mtpReportsTypes
						.keySet().contains(type)) {
					printLine("MTP report found, type : " + type + "\n");

					report = DxtPerformanceConstants.mtpParser.createReport(
							meDataValues, type);

				} else if (com.harris.netboss.dxtPerformanceTestingTool.parsers.CommonReportsParser.commonReportsTypes
						.keySet().contains(type)) {
					printLine("Common report found, type : " + type + "\n");

					report = DxtPerformanceConstants.commonParser.createReport(
							meDataValues, type);
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
			final int c = DxtPerformanceConstants.dp.createIntValue(index,
					meDataValues, difference);
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

		final int formatVersion = DxtPerformanceConstants.dp
				.getFormatVersion(meDataValues);

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

	public DXTPerformanceTestingTool() {
		initComponents();
		setIconImage(getImage());
	}

	protected static Image getImage() {
		URL imgURL = DXTPerformanceTestingTool.class
				.getResource(DxtPerformanceConstants.ICON);
		if (imgURL != null) {
			return new ImageIcon(imgURL).getImage();
		} else {
			return null;
		}
	}

	public static void printLine(String string) {
		DxtPerformanceConstants.line.append(string);

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

		java.awt.EventQueue.invokeLater(() -> {
			new DXTPerformanceTestingTool().setVisible(true);
		});

	}

}
