new Vue({
    el : '.admin-trip-view',
    data : {
        trip : {}
    },
    created: function () {
    	var that = this;
    	var data = window.location.pathname.split('/');
    	var id = data[data.length - 1];
        this.id = id;
    	
    	$.ajax({
                type: 'GET',
                url: '/admin/trip/' + id,
                contentType: 'application/json'
            }).done(function(xhr){
                that.trip = xhr;
            }).fail(function(data){
                window.location.href = "/home";
            });
    },
    methods : {
    	getTotal : function(){
    		if (!this.trip.expenses) return '';
    		var total = 0;
    		for (var i in this.trip.expenses) {
    			total += this.trip.expenses[i].amount;
    		}

    		return total;
    	}
    }
});