package main.by.intexsoft.testBilling.model;

public class CallRecord {
	public String callId;
	public int subscriberId;
	public long startTime;
	public long endTime;
	public long duration;
	public boolean type;
	public double price;
	public CallRecord() {
	}

	public CallRecord(String callId, int subscriberId, long startTime, long endTime, long duration, boolean type,
			double price) {
		this.callId = callId;
		this.subscriberId = subscriberId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.duration = duration;
		this.type = type;
		this.price = price;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null)
			return false;
		if (getClass() != o.getClass())
			return false;
		CallRecord other = (CallRecord) o;
		if (callId != other.callId)
			return false;
		if (subscriberId != other.subscriberId)
			return false;
		if (duration != other.duration)
			return false;
		if (endTime != other.endTime)
			return false;
		if (startTime != other.startTime)
			return false;
		if (type != other.type)
			return false;
		if (price != other.price)
			return false;
		return true;
	}
}
