var walletService = {
    findAddressById: function(id, callback) {
        $.get(API_BASE_URL + "/api/wallets/of/" + id, function(data) {
            callback(data['주소']);
        });
    },
    findById: function(id, callback) {
        // TODO 지갑 조회 API를 호출합니다.
        $.ajax({
            type: "get",
            url: API_BASE_URL + "/api/wallets/of/" + id,
            headers: { 'Content-Type': 'application/json' },
            success: function(response) {
                response.status = 200;
                callback(response)
            },
            error: function(response) {
                response.status = 204;
                callback(response)
            }
        });
    },
    isValidPrivateKey: function(id, privateKey, callback) {
        var web3 = new Web3(new Web3.providers.HttpProvider(BLOCKCHAIN_URL));
        var account = web3.eth.accounts.privateKeyToAccount(privateKey);
        this.findById(id, function(data) {
            var address = data['주소'];
            callback(account && account.address == address);
        });
    },
    registerWallet: function(userId, walletAddress, callback) {
        // TODO 지갑 등록 API를 호출합니다. 
        console.log(userId)
        var body = {
            "소유자id": userId,
            "주소": walletAddress
        }
        $.ajax({
            type: "POST",
            url: API_BASE_URL + "/api/wallets",
            data: JSON.stringify(body),
            headers: { 'Content-Type': 'application/json' },
            success: function(response) {
                callback(response)
            }
        });
    },
    chargeEther: function(walletAddress, callback) {
        // TODO 코인 충전 API를 호출합니다.
        var web3 = new Web3(new Web3.providers.HttpProvider(BLOCKCHAIN_URL));
        web3.eth.personal.unlockAccount("0xe5ed3d107634891df48642e3ad73f0ef7d65b456", "Seokwoo", 600)
            .then(console.log('Account unlocked!'));
        web3.eth.sendTransaction({ from: "0xe5ed3d107634891df48642e3ad73f0ef7d65b456", to: walletAddress, value: web3.utils.toWei('5', "ether") },
            function(error, hash) {
                if (error) {
                    console.log(error);
                } else {
                    callback(hash);
                }
            }
        )

    },
    createWallet() {
        var web3 = new Web3(new Web3.providers.HttpProvider(BLOCKCHAIN_URL));
        return web3.eth.accounts.wallet.create(1);

    },
    getBalance(id) {
        var web3 = new Web3(new Web3.providers.HttpProvider(BLOCKCHAIN_URL));
        return web3.eth.getBalance(id);

    }
}