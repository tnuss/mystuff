package com.orcasdev.equity;

public class Status implements StatusIF {

	private String description = null;
	private String status = null ;

	@Override
	public void  setActive() {
		description = StatusIF.ACTIVE_STATUS;
		status = StatusIF.SHORT_ACTIVE;
	}

	@Override
	public void setInactive() {
		description = StatusIF.INACTIVE_STATUS;
		status = StatusIF.SORT_INACTIVE;
	}
	
	public boolean isActive() {
		boolean bf = false;
		if (status.equals(SHORT_ACTIVE))
			bf =true;
		return bf;
	}
	
	public String getStatusCode() {
		return status;
	}
	public String getStatusDescription() {
		return description;
	}
	
}
