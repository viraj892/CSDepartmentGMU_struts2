package services;

import beans.ResultBean;

public class DataProcessor {

	// Calculate mean
	public double mean(double[] data) {
		double sum = 0.0d;
		for (int i = 0; i < data.length; i++)
			sum = sum + data[i];
		double meanVal = (double) (sum / data.length);
		return meanVal;
	}

	// Calculate Standard deviation
	public double standardDev(double[] data) {
		double mean = mean(data);
		double summation = 0.0d;
		double sd = 0.0d;

		for (int i = 0; i < data.length; i++)
			summation = summation + Math.pow((data[i] - mean), 2);

		sd = Math.sqrt(summation / (double) data.length);
		return sd;
	}
	// Set the values into ResultBean
	public ResultBean calculate(double[] data) {
		ResultBean db = new ResultBean();
		db.setMean(mean(data));
		db.setSd(standardDev(data));
		return db;
	}

}
