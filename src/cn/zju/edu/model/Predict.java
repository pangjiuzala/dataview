package cn.zju.edu.model;

public class Predict {
	private int id;
	private double truevalue;
	private double predictvalue;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getTruevalue() {
		return truevalue;
	}

	public void setTruevalue(double truevalue) {
		this.truevalue = truevalue;
	}

	public double getPredictvalue() {
		return predictvalue;
	}

	public void setPredictvalue(double predictvalue) {
		this.predictvalue = predictvalue;
	}
}
