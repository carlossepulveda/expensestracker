new Vue({
    el : '.trip-list',
    data : {
    	trips : [] 
    },
    created: function () {
    	var that = this;
    	$.ajax({
                type: 'GET',
                url: '/admin/trip',
                contentType: 'application/json'
            }).done(function(xhr){
            	console.log(xhr);
                that.trips = xhr;
            });
    },
    methods : {
        view : function(data) {
            window.location.href = '/admin/trip/' + data;
        }
    }
});