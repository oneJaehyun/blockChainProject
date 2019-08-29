package com.bcauction.application.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthGetBalance;

import com.bcauction.application.IEthereumService;
import com.bcauction.domain.Address;
import com.bcauction.domain.exception.ApplicationException;
import com.bcauction.domain.repository.ITransactionRepository;
import com.bcauction.domain.wrapper.Block;
import com.bcauction.domain.wrapper.EthereumTransaction;

@Service
public class EthereumService implements IEthereumService {

	private static final Logger log = LoggerFactory.getLogger(EthereumService.class);

	public static final BigInteger GAS_PRICE = BigInteger.valueOf(1L);
	public static final BigInteger GAS_LIMIT = BigInteger.valueOf(21_000L);

	@Value("${eth.admin.address}")
	private String ADMIN_ADDRESS;
	@Value("${eth.encrypted.password}")
	private String PASSWORD;
	@Value("${eth.admin.wallet.filename}")
	private String ADMIN_WALLET_FILE;

	private ITransactionRepository transactionRepository;

	@Autowired
	private Web3j web3j;


	@Autowired
	public EthereumService(ITransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

	private EthBlock.Block 최근블록(final boolean fullFetched)
	{
		try {
			EthBlock latestBlockResponse;
			latestBlockResponse
			= web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, fullFetched).sendAsync().get();

			return latestBlockResponse.getBlock();
		}catch (ExecutionException | InterruptedException e){
			throw new ApplicationException(e.getMessage());
		}
	}

	/**
	 * 최근 블록 조회
	 * 예) 최근 20개의 블록 조회
	 * @return List<Block>
	 */
	@Override
	public List<Block> 최근블록조회()
	{
		// TODO
		return null;
	}

	/**
	 * 최근 생성된 블록에 포함된 트랜잭션 조회
	 * 이더리움 트랜잭션을 EthereumTransaction으로 변환해야 한다.
	 * @return List<EthereumTransaction>
	 */
	@Override
	public List<EthereumTransaction> 최근트랜잭션조회()
	{
		// TODO
		return null;
	}

	/**
	 * 특정 블록 검색
	 * 조회한 블록을 Block으로 변환해야 한다.
	 * @param 블록No
	 * @return Block
	 */
	@Override
	public Block 블록검색(String 블록No)
	{
		// TODO
		return null;
	}

	/**
	 * 특정 hash 값을 갖는 트랜잭션 검색
	 * 조회한 트랜잭션을 EthereumTransaction으로 변환해야 한다.
	 * @param 트랜잭션Hash
	 * @return EthereumTransaction
	 */
	@Override
	public EthereumTransaction 트랜잭션검색(String 트랜잭션Hash)
	{
		// TODO
		return null;
	}

	/**
	 * 이더리움으로부터 해당 주소의 잔액을 조회하고
	 * 동기화한 트랜잭션 테이블로부터 Address 정보의 trans 필드를 완성하여
	 * 정보를 반환한다.
	 * @param 주소
	 * @return Address
	 */
	@Override
	public Address 주소검색(String 주소)
	{
		// TODO
		return null;
	}

	/**
	 * [주소]로 시스템에서 정한 양 만큼 이더를 송금한다.
	 * 이더를 송금하는 트랜잭션을 생성, 전송한 후 결과인
	 * String형의 트랜잭션 hash 값을 반환한다.
	 * @param 주소
	 * @return String 생성된 트랜잭션의 hash 반환 (참고, TransactionReceipt)
	 */
	@Override
	public String 충전(final String 주소) // 특정 주소로 테스트 특정 양(5Eth) 만큼 충전해준다.
	{
		// TODO
		return null;
	}

	@Override
	public BigInteger getEthereumInfromation(String 지갑주소) throws Exception {
		try {
			 EthGetBalance a = web3j.ethGetBalance(지갑주소, DefaultBlockParameterName.LATEST).sendAsync().get();
			 BigInteger tmp = new BigInteger("1000000000000000000");
			 return a.getBalance().divide(tmp);
		} catch (Exception ex) {
			throw ex;
		}
	}

}
