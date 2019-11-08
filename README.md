# 블록체인 idle 위키 🖥🖥

### 저희 팀은 p2p 경매 시스템을 구축 하고 있습니다. 

사용자가 시스템에 등록한 자산인 디지털 작품을 경매를 통해 판매하고 그 소유권을 이전하는 기능과 경매기능은 서버를 거치지 않고 [이더리움 스마트 컨트랙트](https://opentutorials.org/course/2869/19273)를 통해 직접 진행합니다. 이때 이더리움 네트워크 상에 작성한 스마트 컨트랙트를 사용하고, 이더리움 네트워크의 통화인 이더가 매개 디지털 작품의 소유권을 프라이빗 혹은 허가형 블록체인의 대표인 [하이퍼레저 패브릭](https://medium.com/landingblock-korea/26-%ED%95%98%EC%9D%B4%ED%8D%BC%EB%A0%88%EC%A0%80-%ED%8C%A8%EB%B8%8C%EB%A6%AD-hyperledger-fabric-%EC%9D%B4%ED%95%B4-a64e8fccd357)에 기록하게 됩니다. 디지털 작품 소유권의 이해관계를 가지는 가상의 컨소시엄이 존재하며 이해관계자에 의해 구성된 패브릭 네트워크 채널과 자산을 관리할수 있는 체인코드로, 시스템을 통한 작품 등록, 삭제 , 경매 완료 등 소유권에 변경이 발생하는 이벤트가 있을 때 체인코드를 호출하여 소유권 이력을 기록하게 됩니다.

# AWS EC2 활용 이더리움 네트워크 구축 (담당자 : 장석우) 

본 프로젝트에는 2대의 EC2가 있고 이중 1대의 EC2에 이더리움 사설 블록체인 네트워크 인프라를 구축한다.
우리는 이더리움 사설 네트워크 구축을 위해 Geth 프로그램을 사용한다.
Geth는 Go Ethereum의 줄임말이며 Ethereum Foundation에서 개발하고 있는 이더리움 메인 클라이언트이다. 

### Step 1 : Geth 설치

AWS는 Ubuntu OS를 제공하며 명령어를 통해 Geth를 설치한다.  
  
AWS EC2에 접속하기 위해 putty 프로그램을 사용했고  
  
퍼블릭 도메인을 통해 해당 ec2에 접속하여 unbuntu OS 위에서 작업하도록 한다.  
  
~~~ 
sudo apt-get install software-properies-common  
sudo add-apt-repository -y ppa:ethereum/ethereum   
sudo apt-get update  
sudo apt-get install ethereum  
~~~

### Step 2 : genesis block 정의 (JSON)
블록체인 네트워크를 구성하기 위해 필요한 사전 단계이다.  
  
최초로 생성되며 시작되는 블록의 옵션을 정의한다.  
  
주의깊게 봐야할 옵션은 chainID와 difficulty이며
    
chainID는 이 블록체인의 고유값을 지정해주는 것이 좋고 외부에서 이 체인에 접속하기 위한 ID값이다.   
  
difficulty는 마이너가 다음 블록을 생성할 때 블록이 생성되는 속도를 제어할 수 있는 난이도 값이다. 
   
~~~
genesis.json
{
	"config": {
	"chainId":15150,
	"homesteadBlock":0,
	"eip155Block":0,
	"eip158Block":0
	},
	"nonce":"0xdeadbeefdeadbeef",
	"timestamp":"0x00",
	"parentHash":"0x0000000000000000000000000000000000000000000000000000000000000000",
	"extraData":"0x00",
	"gasLimit":"9999999",
	"difficulty":"0x200",
	"mixhash": "0x0000000000000000000000000000000000000000000000000000000000000000",
	"alloc":{}
}
~~~
### Step 3 : Genesis 블록 생성  
~~~
geth --datadir "서버 구동 디렉터리" init "genesis파일 디렉토리/genesis.json"  
~~~

### Step 4 : Genesis 블록을 통해 이더리움 사설 네트워크 구동

geth 명령어 뒤에 붙는 옵션들은 구글링을 통해 기능을 확인할 수 있다.
~~~
포그라운드에서 구동할 때 

geth --datadir "서버 구동 디렉터리" --port "30303" --rpc --rpcaddr "0.0.0.0" --rpcport "8545"  --rpccorsdomain "*" --networkid "15150" --rpcapi "db,eth,net,web3,miner,personal"  --allow-insecure-unlock  console

##### 서버 종료 명령
exit
~~~  

본 프로젝트에서 구축하는 이더리움 사설네트워크에서는 백그라운드에 상주하며 계속해서 마이닝을 하게끔 --mine 과 --minderthreads "1" 이라는 옵션을 추가하였다.  
  
마이닝이 되고 있는 상태여야 트랜젝션 전송이 가능하고 충분한 이더량을 보유하기 위함이다.  
  
마이닝, 즉 채굴이 너무 빨리 되어 부담스럽다면 genesis.json 블록에서 difficulty값을 재정의 해주어 새로운 체인을 구축하면 된다.  
  
만약 이미 구축된 다른 사설네트워크에 접속하고 싶다면 --rpcaddr 옵션을 통해 접속하면 된다.  
  
--networkid는 genesis.json에서 정의한 chainID와 동일하게 정의한다.  


~~~ 
백그라운드에서 구동할 때
nohup geth --datadir "서버 구동 디렉터리" --nodiscover --port "30303" --rpc --rpcaddr "0.0.0.0" --rpcport "8545"  --rpccorsdomain "*" --networkid "15150" --rpcapi "db,eth,net,web3,miner,personal"  --allow-insecure-unlock --mine --minerthreads "1" 2>> ~/dev/eth_localdata/geth.log &

##### 서버 종료 명령
백그라운드로 돌렸다면 백그라운드에서 구동되고 있는 프로세스 ID 값을 종료시켜야한다.
터미널에 ps -ef |grep geth 또는 netstat -ntlp로 프로세스 id 확인 후

kill -15 프로세스id

~~~

### Step 5 : 구동 확인
다음 명령으로 --port와 --rpcport로 개방한 포트 번호를 확인해본다. 
~~~
netstat -ntlp
~~~

### Step 6 : 구축된 사설 네트워크에 노드로 참여 (멀티노드)
Step1부터 Step5까지 동일하게 수행하되 다른 작업 디렉토리를 만들고  
  
genesis.json도 새로 정의해주어야한다.  
  
genesis.json의 블록 내용은 구축된 사설 네트워크의 genesis.json 내용과 반 드 시 동일해야한다.  
  
구축및 구동까지 완료했다면  
  
네트워크 터미널에서 다음 명령을 통해 enode값을 확인한다. 

~~~
admin.nodeInfo.enode
~~~

확인된 enode값을  접속하고자 하는 이더리움 네트워크 터미널에서 다음 명령을 통해 노드로서 추가해준다.  
~~~
admin.addPeer("확인된 enode값 그대로 복사 붙여넣기")
~~~

또는 처음으로 만든 이더리움 네트워크를 구동하기 전에 Json 형태로 미리 peer로 할당하는 방법이 있다.  
서버를 구동하는 디렉토리에 enode값을 미리 Json을 만들어 놓는다.
~~~
stable-nodes.json 에시
 [
"enode://51a985b5da0699560c82b8bb639feef32275de9cd79672156537073daf4187873d1567047ac209bc2a06554d730d7d08bdb6db82e50bfdac738198191b7772d7@127.0.0.1:30304?discport=0",
"enode://7c5e5ca2f0e625bdd9a0917dbe127ad8ec6e89bfc50c4bc99dbb3f29adc4d967995c271fc6641e62ca30f803b195b28022b89c308aa64af7052c93b065b5ac11@127.0.0.1:30305?discport=0"
]
~~~
이렇게 JSON파일을 정의 해놓은 후 서버를 구동하면 자동으로 구동된 서버에 위 두 enode가 peer로 잡힌다.  
  
매번 포그라운드에서 작업하고 admin.addPeer로 피어를 추가해줄 수 없으니 백그라운드로 서버를 구동할 때 이 방법을 사용한다.  
  
주로 실질적인 블록체인 네트워크에서는  
  
참여된 노드의 역할은 트랜잭션풀에 트랜잭션을 알리고 전파하는 역할을 하며  
  
마이너들이 트랜잭션풀에 있는 트랜잭션을 확인하고 블록에 데이터를 쓰는 시스템으로 돌아간다.  
 
본 프로젝트에서는 한 EC2 내에서 프로세스를 통해 노드를 구성하는 것이여서 거시적, 물리적으로는 하나의 노드에 불과하다. 
   
즉 한 은행에서 창구를 여러개 둔 것 뿐이지 실제로 다른 은행 지점을 둔 것이 아닌 형태다.  
  
하지만 블록체인 시스템이라는 의미를 더하기 위해 프로세스 레벨로 노드를 구성하여 참여시켰다. 


### Step 7 : 포그라운드에서 이더리움 사설 네트워크 터미널의 명령어들  
  
본 프로젝트에서 자주 썼던 명령어들 몇개의 예시이다.
~~~
마이닝에 대한 이더 받을 계정 확인 : eth.coinbase

계정 리스트 확인 : eth.accounts

personal.listWallets[0].status // 계정 lock or unlock 상태 확인

unlock 걸려있을 때 : personal.unlockAccount(eth.accounts[0]) //unlock (비밀번호는 아이디랑 똑같이)

또는 web3.personal.unlockAccount(eth.accounts[0])

계정 금액 확인 :eth.getBalance(eth.accounts[0])

마이닝 시작 : miner.start(1) //파라미터 1 값은 스레드 번호

마이닝중인가 확인 : web3.eth.mining

마이닝 종료 : miner.stop()

송금 : eth.sendTransaction({from:송신자 account,to:수신자 account,value:web3.toWei("금액","ether")})  

~~~  

### Step 8 : 서버 상태확인 및 WEB3  
  
Chrome 브라우저에서 MetaMask 브라우저 앱을 설치한 후  
  
ec2 퍼블릭 도메인 IP: 이더리움 네트워크 RPC PORT 번호로 연동하여 서버에 접속 가능 여부를 확인할 수 있다.  
  
또한 Remix에서 Web3 Provider를 통해 접속한 이더리움 네트워크 서버에 속한 계정의 잔액, 마이닝 상태를 확인할 수 있다.  
  
WEB3는 이더리움 블록체인 네트워크와 데이터를 주고받을 수 있게끔 하는 API이며  
  
자바스크립트 환경에서는 web3.js, 자바 환경에서는 web3.j를 사용하면 된다.  


* 스켈레톤 프로젝트 중 Frontend  확인 ( 담당자 : 한재현) 
  * DB와 연동 되어 회원 가입시 등록 되는지 확인 후 지갑 생성 시작. 
  * 지갑 관리 

    * 지갑 생성

      [web3 js](https://web3js.readthedocs.io/en/v1.2.1/index.html#)에 web3.eth.accounts.wallet.create() 함수 활용하여 지갑을 생성할 수 있다.
발생 이슈 : 회원 마다 account를 생성 해주어야 할지 아니면 account 하나에 여러개의 wallet을 사용하는지에 대한 논의가 있었으나 지갑 주소로 account 를 대체 할 수 있기 때문에 account를 생성해주지 않기로 했다. 
    
    * 지갑 열람

      DB에 유저ID로 검색하여 지갑 정보를 가져오면[web3 js](https://web3js.readthedocs.io/en/v1.2.1/index.html#) getBalance() 함수를 이용해서 지갑에 있는 이더량을 화면에 가져온다. 

    
     * 코인 충전 구현

        web3 j credentials 클래스를 이용해서 miner에게 권한을 요청했다. transactionReceipt() 함수를 이용해 트랜잭션을 작성하여 5이더 충전하도록 하였다.


* 스켈레톤 프로젝트 중 Backend 확인 ( 담당자 : 김용민, 박진수)
    * 명세서 참고하여 MySQL DB 도커 이미지 다운로드 및 테이블 구조 확인
    * 이더리움 네트워크 연동, 이더리움 관련 Service 확인.
    * 구조 파악 후 기능 나누기 및 git, jira, scrum 정리.

* EthereumService 구현 ( 담당자 : 박진수 )
    * web3j를 이용해 블록과 트랜젝션 조회 기능 구현
    * ethGetBlockByNumber를 통해 블록을 호출하고 ethGetTransactionByHash을 통해 최근 블록과 트랜젝션을 조회하고 해당 블록의 넘버와 트랜젝션 Hash값을 통해 검색을 하는 기능 구현.
    * 해당 하는 데이터를 호출하고 그 데이터를 DB구조에 맞게 재가공.

* git 관리자 ( 담당자 : 김용민, 박진수 )
    * develop branch 에서 작업. 
    * 각각 개인의 branch 작업 후 하루 작업 종료 후 develop branch에 merge 후 conflict 조정

* Jira 관리자 ( 담당자 : 박진수 )
    * epic과 story 별로 각각 명세에서 구현해야 하는 기능 및 담당자 취합.
    * 각각의 기능을 구현하기 위해, 팀원에게 담당 기능을 나누었으며 git에서 충돌이 나지 않도록 겹치는 부분을 최소화 시키고 기능 별로 나누어 담당자 배분.

* DigitalWorkService 작품 등록 및 작품 삭제 ( 담당자 : 박진수 )
    * Frontend에선 작품 삭제 시 DB에는 해당 로우가 Delete 되는 것이 아닌 공개 여부를 N으로 바꾸어 작품의 이력은 남아있음.