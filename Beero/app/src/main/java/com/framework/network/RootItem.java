package com.framework.network;

import java.io.Serializable;

public class RootItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String mId;
	protected String mName;

	public RootItem() {

	}

	public RootItem(String id, String name) {
		mId = id;
		mName = name;
	}

	/**
	 * @return the mId
	 */
	public String getId() {
		return mId;
	}

	/**
	 * @param mId
	 *            the mId to set
	 */
	public void setId(String id) {
		mId = id;
	}

	/**
	 * @return the mName
	 */
	public String getName() {
		return mName;
	}

	/**
	 * @param mName
	 *            the mName to set
	 */
	public void setName(String name) {
		mName = name;
	}

}
