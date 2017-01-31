new Vue({
    el : '.admin-trip-view',
    data : {
        trip : {},
        tag : '',
        amount : '',
        showExpenseView : false
    },
    created: function () {
    	var that = this;
    	var data = window.location.pathname.split('/');
    	var id = data[data.length - 1];
        this.id = id;
    	
    	$.ajax({
                type: 'GET',
                url: '/trip/' + id,
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
    	},
    	close : function() {
    		$.ajax({
                type: 'PUT',
                url: '/trip/' + this.id,
                contentType: 'application/json'
            }).done(function(xhr){
                window.location.href = "/home";
            }).fail(function(data){
            	alert('Error');
                window.location.href = "/home";
            });
    	},
    	addExpense : function() {
    		$.ajax({
                type: 'POST',
                url: '/trip/' + this.id + '/expense',
                contentType: 'application/json',
                data: JSON.stringify({
                    tag : this.tag,
                    amount : this.amount
                })
            }).done(function(xhr){
                window.location.href = "/home";
            }).fail(function(data){
            	alert('Error');
                window.location.href = "/home";
            });
    	}
    }
});