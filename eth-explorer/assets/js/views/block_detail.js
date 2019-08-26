// 실제 Vue 템플릿 코드 작성 부분
$(function(){
    var blockNumber = parseQueryString()['blockNumber'];
    
    var detailView = new Vue({
        el: '#block-detail',
        data: {
            isValid: true,
            block: {
                number: 0
            }
        },
        mounted: async function(){
            if(blockNumber) {
                var block = await getBlock(blockNumber);
                this.block = block;
                this.block.timestamp = new Date(this.block.timestamp * 1000);
            } else {
                this.isValid = false;
                
            }
        }
    });
});