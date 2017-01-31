var LoginForm = new Vue({
    el : '.login',
    data : {
        username : '',
        password : ''
    },
    created: function () {
    },
    methods : {
        login : function() {
            $.ajax({
                type: 'POST',
                url: '/login',
                contentType: 'application/json',
                data: JSON.stringify({username : this.username, password : this.password})
            }).done(function(xhr){
                window.location.href = "/home";
            }).fail(function(data){
                alert('Invalid Data');
            });
        }
    }
});