new Vue({
    el : '.admin-create-trip',
    data : {
        employee : '',
        name : ''
    },
    created: function () {
    },
    methods : {
        create : function() {
            $.ajax({
                type: 'POST',
                url: '/admin/trip',
                contentType: 'application/json',
                data: JSON.stringify({employeePersonalId : this.employee, name : this.name})
            }).done(function(xhr){
                window.location.href = "/home";
            }).fail(function(data){
                alert('Invalid Data');
            });
        }
    }
});