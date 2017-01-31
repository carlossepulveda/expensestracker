new Vue({
    el : '.expenses-list',
    data : {
    	expenses : [] 
    },
    created: function () {
    	var that = this;
    	$.ajax({
                type: 'GET',
                url: '/admin/expense',
                contentType: 'application/json'
            }).done(function(xhr){
                that.expenses = xhr;
            });
    },
    methods : {
    }
});