const NUMBER_OF_CONTENTS_TO_SHOW = 3;           // 한 번에 보여줄 정보의 개수
const REFRESH_TIMES_OF_OVERVIEW = 1000;         // 개요 정보 갱신 시간 1초
const REFRESH_TIMES_OF_BLOCKS = 5000;           // 블록 정보 갱신 시간 5초
const REFRESH_TIMES_OF_TRANSACTIONS = 3000;     // 트랜잭션 정보 갱신 시간 3초

// 실제 Vue 템플릿 코드 작성 부분
$(function(){
    var dashboardOverview = new Vue({
        el: '#dashboard-overview',
        data: {
            latestBlock: 0,
            latestTxCount: 0
        },
        methods: {
            updateLatestBlock: async function(){
                // TODO 
                var block = await fetchLatestBlock()
                this.latestBlock = block;  
            },           
            updateLatestTxCount: async function(){
                // TODO 
                var ts = await getBlockTransactionCount(this.latestBlock)
                this.latestTxCount = ts;
            }
        },
        mounted: function(){
            this.$nextTick(function () {
                window.setInterval(() => {
                    this.updateLatestBlock();
                    this.updateLatestTxCount();
                }, REFRESH_TIMES_OF_OVERVIEW);
            });
        }
    });

    var blocksView = new Vue({
        el: '#blocks',
        data: {
            lastReadBlock: 0,
            blocks: []
        },
        methods: {
            fetchBlocks: async function () {
                // TODO 최근 10개의 블록 정보를 가져와서 계속 업데이트 한다.
                var current = await fetchLatestBlock();
                fetchBlocks(current - 9, current, (val) => {
                    blocksView.blocks.unshift(val);
                    blocksView.lastReadBlock = current;
                })
            },
            fetchBlock: async function () {

                var current = await fetchLatestBlock();

                if (blocksView.lastReadBlock != current) {
                    fetchBlocks(blocksView.lastReadBlock+1, current, (val) => {
                        blocksView.blocks.pop();
                        blocksView.blocks.unshift(val);
                        blocksView.lastReadBlock = current;

                    })
                }
            }
        },
        mounted: function(){
            this.fetchBlocks();

            this.$nextTick(function () {
                window.setInterval(() => {
                    this.fetchBlock();
                }, REFRESH_TIMES_OF_BLOCKS);
            })
        }
    })

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
                   this.transactions.pop();
                    this.transactions.unshift(d);
            }
        }else{
            for (let i = 0; i < ts; i++) {
                var d = await getTransaction(block.transactions[i]);
                this.transactions.pop();
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