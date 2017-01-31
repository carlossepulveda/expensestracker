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

<div class="trip-list container-section col-md-10 col-xs-10 col-md-offset-1 col-xs-offset-1" style="padding:4%;">
    
	<hr/>
    <div class="row" style="padding:2em">
        <div class="col-md-8">
            <h4>My Trips</h4>
        </div>
    </div>

    <div class="col-md-12 content-container">

        <table class="table table-striped">
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Status</th>
                    <th>Total</th>
                </tr>
            </thead>

            <tbody>
            <tr class="clickable" v-for="trip in trips" @click="view(trip.id)">
                <td>{{trip.name}}</td>
                <td>{{trip.status}}</td>
                <td>{{getTotal(trip)}}</td>
            </tr>
            </tbody>
        </table>


    </div>

</div>

</body>
<script type="text/javascript" src="/static/js/trips.js"></script>
</html>