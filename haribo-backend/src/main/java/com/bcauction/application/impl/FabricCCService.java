package com.bcauction.application.impl;

import com.bcauction.application.IFabricCCService;
import com.bcauction.domain.CommonUtil;
import com.bcauction.domain.FabricAsset;
import com.bcauction.domain.FabricUser;
import com.bcauction.domain.exception.ApplicationException;
import com.google.protobuf.ByteString;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.exception.EnrollmentException;
import org.hyperledger.fabric_ca.sdk.exception.InvalidArgumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.ByteArrayInputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class FabricCCService implements IFabricCCService
{
	private static final Logger logger = LoggerFactory.getLogger(FabricCCService.class);

	private HFClient hfClient;
	private Channel channel;

	/**
	 * 패브릭 네트워크를 이용하기 위한 정보
	 */
	@Value("${fabric.ca-server.url}")
	private String CA_SERVER_URL;
	@Value("${fabric.ca-server.admin.name}")
	private String CA_SERVER_ADMIN_NAME;
	@Value("${fabric.ca-server.pem.file}")
	private String CA_SERVER_PEM_FILE;
	@Value("${fabric.org.name}")
	private String ORG_NAME;
	@Value("${fabric.org.msp.name}")
	private String ORG_MSP_NAME;
	@Value("${fabric.org.admin.name}")
	private String ORG_ADMIN_NAME;
	@Value("${fabric.peer.name}")
	private String PEER_NAME;
	@Value("${fabric.peer.url}")
	private String PEER_URL;
	@Value("${fabric.peer.pem.file}")
	private String PEER_PEM_FILE;
	@Value("${fabric.orderer.name}")
	private String ORDERER_NAME;
	@Value("${fabric.orderer.url}")
	private String ORDERER_URL;
	@Value("${fabric.orderer.pem.file}")
	private String ORDERER_PEM_FILE;
	@Value("${fabric.org.user.name}")
	private String USER_NAME;
	@Value("${fabric.org.user.secret}")
	private String USER_SECRET;
	@Value("${fabric.channel.name}")
	private String CHANNEL_NAME;


	/**
	 * 체인코드를 이용하기 위하여
	 * 구축해놓은 패브릭 네트워크의 채널을 가져오는
	 * 기능을 구현한다.
	 * 여기에서 this.channel의 값을 초기화 한다
	 */
	private void loadChannel(){
		// TODO
	}

	private Properties getPropertiesWith(String filename) {
		Properties properties = new Properties();
		properties.put("pemBytes", CommonUtil.readString(filename).getBytes());
		properties.setProperty("sslProvider", "openSSL");
		properties.setProperty("negotiationType", "TLS");
		return properties;
	}

	/**
	 * 소유권 등록을 위해 체인코드 함수를 차례대로 호출한다.
	 * @param 소유자
	 * @param 작품id
	 * @return FabricAsset
	 */
	@Override
	public FabricAsset registerOwnership(final long 소유자, final long 작품id){
		if(this.channel == null)
			loadChannel();

		boolean res = registerAsset(작품id, 소유자);
		if(!res)
			return null;
		res = confirmTimestamp(작품id);
		if(!res)
			return null;

		return query(작품id);
	}

	/**
	 * 소유권 이전을 위해 체인코드 함수를 차례대로 호출한다.
	 * @param from
	 * @param to
	 * @param 작품id
	 * @return List<FabricAsset
	 */
	@Override
	public List<FabricAsset> transferOwnership(final long from, final long to, final long 작품id) {
		if(this.channel == null)
			loadChannel();

		List<FabricAsset> assets = new ArrayList<>();
		boolean res = this.expireAssetOwnership(작품id, from);
		if(!res) return null;
		FabricAsset expired = query(작품id);
		if(expired == null) return null;
		assets.add(expired);

		res = this.updateAssetOwnership(작품id, to);
		if(!res) return null;
		FabricAsset transferred = query(작품id);
		if(transferred == null) return null;
		assets.add(transferred);

		return assets;
	}

	/**
	 * 소유권 소멸을 위해 체인코드 함수를 호출한다.
	 * @param 작품id
	 * @param 소유자id
	 * @return FabricAsset
	 */
	@Override
	public FabricAsset expireOwnership(final long 작품id, final long 소유자id) {
		if(this.channel == null)
			loadChannel();

		boolean res = this.expireAssetOwnership(작품id, 소유자id);
		if(!res) return null;

		return query(작품id);
	}

	/**
	 * 체인코드 registerAsset 함수를 호출하는 메소드
	 * @param 작품id
	 * @param 소유자
	 * @return boolean
	 */
	private boolean registerAsset(final long 작품id, final long 소유자) {
		// TODO
		return false;
	}

	/**
	 * 체인코드 confirmTimestamp 함수를 호출하는 메소드
	 * @param 작품id
	 * @return
	 */
	private boolean confirmTimestamp(final long 작품id){
		// TODO
		return false;
	}

	/**
	 * 체인코드 expireAssetOwnership를 호출하는 메소드
	 * @param 작품id
	 * @param 소유자
	 * @return
	 */
	private boolean expireAssetOwnership(final long 작품id, final long 소유자) {
		// TODO
		return false;
	}

	/**
	 * 체인코드 updateAssetOwnership를 호출하는 메소드
	 * @param 작품id
	 * @param to
	 * @return
	 */
	private boolean updateAssetOwnership(final long 작품id, final long to) {
		// TODO
		return false;
	}

	/**
	 * 체인코드 queryHistory 함수를 호출하는 메소드
	 * @param 작품id
	 * @return
	 */
	@Override
	public List<FabricAsset> queryHistory(final long 작품id){
		if(this.hfClient == null || this.channel == null)
			loadChannel();

		return null;
	}

	/**
	 * 체인코드 query 함수를 호출하는 메소드
	 * @param 작품id
	 * @return
	 */
	@Override
	public FabricAsset query(final long 작품id){
		if(this.hfClient == null || this.channel == null)
			loadChannel();

		return null;
	}

	private static FabricAsset getAssetRecord(final JsonObject rec)
	{
		FabricAsset asset = new FabricAsset();

		asset.setAssetId(rec.getString("assetID"));
		asset.setOwner(rec.getString("owner"));
		asset.setCreatedAt(rec.getString("createdAt"));
		asset.setExpiredAt(rec.getString("expiredAt"));

		logger.info("Work " + rec.getString("assetID") + " by Owner " + rec.getString("owner") + ": "+
				            rec.getString("createdAt") + " ~ " + rec.getString("expiredAt"));

		return asset;
	}

}
