const NUMBER_OF_CONTENTS_TO_SHOW = 10;           // 한 번에 보여줄 정보의 개수
const REFRESH_TIMES_OF_TRANSACTIONS = 3000;     // 트랜잭션 정보 갱신 시간 3초

// 실제 Vue 템플릿 코드 작성 부분
$(function(){
    var txesView = new Vue({
        el: '#transactions',
        data: {
            blocknum : 0,
            transactions: [],
            BlockTimeStamp:""
        },
        methods: {
            fetchTxes: async function(){
                // TODO 
                this.blocknum = await fetchLatestBlock();
                var block = await getBlock(this.blocknum);
                this.BlockTimeStamp = block.timestamp;
                var ts = await getBlockTransactionCount(this.blocknum)
                if(ts >= 10){
                for (let i = 0; i < 10; i++) {
                   var d = await getTransaction(block.transactions[i]);
                    this.transactions.unshift(d);
            }
        }else{
            for (let i = 0; i < ts; i++) {
                var d = await getTransaction(block.transactions[i]);
                 this.transactions.unshift(d);
        }
    }

            },
            fetchTxe: async function(){
                var blocknum = await fetchLatestBlock();
                var block = await getBlock(blocknum);
                this.BlockTimeStamp = block.timestamp;
                var ts = await getBlockTransactionCount(blocknum)
                if( this.blocknum != blocknum){
                    this.blocknum = blocknum;
                if(ts >= 10){
                for (let i = 0; i < 10; i++) {
                   var d = await getTransaction(block.transactions[i]);
                    this.transactions.unshift(d);
            }
        }else{
            for (let i = 0; i < ts; i++) {
                var d = await getTransaction(block.transactions[i]);
                 this.transactions.unshift(d);
        }
    }
    }

        }
        },
        mounted: function(){
            this.fetchTxes();

            this.$nextTick(function () {
                window.setInterval(() => {
                    this.fetchTxe();
                }, REFRESH_TIMES_OF_TRANSACTIONS);
            })
        }
    });
});