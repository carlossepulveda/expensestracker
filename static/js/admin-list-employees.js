new Vue({
    el : '.employees-list',
    data : {
    	employees : [] 
    },
    created: function () {
    	var that = this;
    	$.ajax({
                type: 'GET',
                url: '/admin/employee',
                contentType: 'application/json'
            }).done(function(xhr){
            	console.log(xhr);
                that.employees = xhr;
            });
    },
    methods : {
    }
});