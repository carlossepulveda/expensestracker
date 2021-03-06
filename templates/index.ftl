<html>
<head>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script
  src="https://code.jquery.com/jquery-1.12.4.min.js"
  integrity="sha256-ZosEbRLbNQzLpnKIkEdrPv7lOy9C27hHQ+Xp8a4MxAQ="
  crossorigin="anonymous"></script>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/vue/1.0.24/vue.min.js"></script>
</head>
<body>

  <div class="container col-md-2 col-md-offset-5 login">

    <div class="form-signin" style="padding-top:3em">
      <h4 class="form-signin-heading">Log in</h4>
      <label for="inputEmail" class="sr-only">PersonalId</label>
      <input type="text" id="inputEmail" class="form-control" placeholder="Personal Id" required="" autofocus="" v-model="username">
      <label for="inputPassword" class="sr-only">Password</label>
      <input type="password" id="inputPassword" class="form-control" placeholder="Password" required="" v-model="password" style="margin-top:0.3em">
      <br/>
      <button class="btn btn-lg btn-primary btn-block" type="submit" @click="login">Sign in</button>
    </div>

  </div> <!-- /container -->


</body>
<script type="text/javascript" src="/static/js/index.js"></script>
</html>
