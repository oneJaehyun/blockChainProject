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



	@Override
	public String getName() {
		return null;
	}

	@Override
	public Set<String> getRoles() {
		return null;
	}

	@Override
	public String getAccount() {
		return null;
	}

	@Override
	public String getAffiliation() {
		return null;
	}

	@Override
	public Enrollment getEnrollment() {
		return null;
	}

	@Override
	public String getMspId() {
		return null;
	}
}
