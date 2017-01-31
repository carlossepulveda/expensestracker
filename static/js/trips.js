new Vue({
    el : '.trip-list',
    data : {
    	trips : [] 
    },
    created: function () {
    	var that = this;
    	$.ajax({
                type: 'GET',
                url: '/trip',
                contentType: 'application/json'
            }).done(function(xhr){
            	console.log(xhr);
                that.trips = xhr;
            });
    },
    methods : {
        view : function(data) {
            window.location.href = '/trip/' + data;
        },
        getTotal : function(trip) {
        	if (!trip.expenses) return '';
        	var total = 0;
        	for (var i in trip.expenses) {
        		total += trip.expenses[i].amount;
        	}
        	return total;
        }
    }
});