var walletService = {
    findAddressById: function(id, callback){
        $.get(API_BASE_URL + "/api/wallets/of/" + id, function(data){
            callback(data['주소']);
        });
    },
    findById: function(id, callback){
        // TODO 지갑 조회 API를 호출합니다. 
    },
    isValidPrivateKey: function(id, privateKey, callback){
        var web3 = new Web3(new Web3.providers.HttpProvider(BLOCKCHAIN_URL));
        var account = web3.eth.accounts.privateKeyToAccount(privateKey);

        this.findById(id, function(data){
            var address = data['주소'];
            callback(account && account.address == address);
        });
    },
    registerWallet: function(userId, walletAddress, callback){
        // TODO 지갑 등록 API를 호출합니다.      
        
    },
    chargeEther: function(walletAddress, callback){
        // TODO 코인 충전 API를 호출합니다.
    }
}