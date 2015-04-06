package com.harris.netboss.dxtPerformanceTestingTool;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

class Reports {

	Date reportDate;

	public Reports(final Map<String, Map<String, Object>> reportMap) {

		Date startTime = null;

		final Iterator<String> itr = reportMap.keySet().iterator();
		if (itr.hasNext()) {
			startTime = (Date) reportMap.get(itr.next()).get(
					DxtPerformanceConstants.START_TIME);
		}

		this.setReportDate(startTime);
	}

	public void setReportDate(final Date reportDate) {
		this.reportDate = reportDate;
	}

}