package com.bcauction.domain;

import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.User;
import org.hyperledger.fabric.sdk.security.CryptoSuite;

import java.io.Serializable;
import java.util.Set;

/**
 * TODO 클래스를 완성한다.
 * 패브릭 네트워크 초기 세팅을 위해
 * org.hyperledger.fabric.sdk.User를 implements한 클래스가 필요하다.
 */
public class FabricUser implements User, Serializable
{
	private static final long serialVersionUID = 8077132186383604355L;

	protected String name;
	protected Set<String> roles;
	protected String account;
	protected String affiliation;
	protected Enrollment enrollment;
	protected String mspId;

	public void setName(String name) {
		this.name = name;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
	}

	public void setEnrollment(Enrollment enrollment) {
		this.enrollment = enrollment;
	}

	public void setMspId(String mspId) {
		this.mspId = mspId;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Set<String> getRoles() {
		return roles;
	}

	@Override
	public String getAccount() {
		return account;
	}

	@Override
	public String getAffiliation() {
		return affiliation;
	}

	@Override
	public Enrollment getEnrollment() {
		return enrollment;
	}

	@Override
	public String getMspId() {
		return mspId;
	}

}
