// 실제 Vue 템플릿 코드 작성 부분
$(function(){
    var hash = parseQueryString()['hash'];
    
    var detailView = new Vue({
        el: '#tx-detail',
        data: {
            isValid: true,
            tx :{

            },
            BlocktimeStamp : "시간"
        },
        mounted: async function(){
            if(hash) {
               // TODO
               var Txe = await getTransaction(hash)
               this.tx = Txe;
               var d = await getBlock(this.tx.blockNumber);
               this.BlocktimeStamp = new Date(d.timestamp*1000);
            } else {
                this.isValid = false;
            }
        }
    });
});