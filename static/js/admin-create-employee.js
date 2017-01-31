new Vue({
    el : '.admin-create-employee',
    data : {
        name : '',
        email : '',
        personalId : '',
        phone : '',
        password : '',
        role : ''
    },
    created: function () {
    },
    methods : {
        create : function() {
            $.ajax({
                type: 'POST',
                url: '/admin/employee',
                contentType: 'application/json',
                data: JSON.stringify({
                    name : this.name,
                    email : this.email,
                    personalId : this.personalId,
                    phone : this.phone,
                    password : this.password,
                    role : this.role
                })
            }).done(function(xhr){
                window.location.href = "/home";
            }).fail(function(data){
                alert('Invalid Data');
            });
        }
    }
});