<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script
  src="https://code.jquery.com/jquery-1.12.4.min.js"
  integrity="sha256-ZosEbRLbNQzLpnKIkEdrPv7lOy9C27hHQ+Xp8a4MxAQ="
  crossorigin="anonymous"></script>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/vue/1.0.24/vue.min.js"></script>
</head>
<body>
<div class="container-section admin-create-employee col-md-10 col-xs-10 col-md-offset-1 col-xs-offset-1" style="padding:4%;">

    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <h2>Agregar viaje</h2>
        </div>
    </div>

    <div class="col-md-6 col-md-offset-3 content-container">

        <div class="col col-md-12">
            <input type="text" id="inputEmail" class="form-control" placeholder="Name" v-model="name" required="" autofocus="">
        </div>
        <div class="col col-md-12">
            <input type="text" id="inputEmail" class="form-control" placeholder="Email" v-model="email" required="" autofocus="">
        </div>
        <div class="col col-md-12">
            <input type="text" id="inputEmail" class="form-control" placeholder="Personal Id" v-model="personalId" required="" autofocus="">
        </div>
        <div class="col col-md-12">
            <input type="text" id="inputEmail" class="form-control" placeholder="Phone" v-model="phone" required="" autofocus="">
        </div>
        <div class="col col-md-12">
            <input type="text" id="inputEmail" class="form-control" placeholder="Password" v-model="password" required="" autofocus="">
        </div>
        <div class="col col-md-12">
            <input type="text" id="inputEmail" class="form-control" placeholder="Role (admin or employee)" v-model="role" required="" autofocus="">
        </div>
        
        

        <br>
        <button class="invite-button btn btn-lg btn-primary btn-block" type="submit" @click="create" analytics-on>Create</button>


    </div>

</div>
<style type="text/css">
    .content-container .col {
        padding: 0;
    }
    .content-container input {
        height: 4em;
        margin-bottom: 0.1em;
    }
    .invite-button {
        margin-top: 1em;
        display: inline-block;
    }
</style>
<script type="text/javascript" src="/static/js/admin-create-employee.js"></script>
</body>
</html>
