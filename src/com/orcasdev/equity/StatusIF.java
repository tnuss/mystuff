package com.orcasdev.equity;

interface StatusIF {

	static final String ACTIVE_STATUS = "Active";
	static final String INACTIVE_STATUS = "Inactive";
	static final String SHORT_ACTIVE = "A";
	static final String SORT_INACTIVE = "I";
	
	public void setActive();
	
	public void setInactive();

}
