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

<div class="admin-trip-view container-section col-md-10 col-xs-10 col-md-offset-1 col-xs-offset-1" style="padding:4%;">

	<hr/>
    <div class="row" style="padding:2em">
        <div class="col-md-8">
            <h4>Trip {{trip.id}}</h4>
            <h5>Status : {{trip.status}}</h5>
        </div>
        <div class="pull-right">
            <a type="button" class="btn btn-lg btn-success" href="/home">Back</a>
        </div>
        <div class="pull-right" style="padding-right:1em">
            <a type="button" class="btn btn-lg btn-warning" v-if="trip.status == 'open'" @click="close">Close</a>
        </div>
        <div class="pull-right" style="padding-right:1em">
            <a type="button" class="btn btn-lg btn-success" v-if="trip.status == 'open'" @click="showExpenseView = true" v-if="!showExpenseView">Add Expense</a>
        </div>


    </div>

    <div class="col-md-12 content-container">

    	<div class="col-md-4" v-if="showExpenseView" style="padding-bottom:2em">
    		<div class="col col-md-12">
	            <input type="text" id="inputEmail" class="form-control" placeholder="Amount" v-model="amount" required="" autofocus="">
	        </div>
	        <div class="col col-md-12">
	            <input type="text" id="inputEmail" class="form-control" placeholder="Tag" v-model="tag" required="" autofocus="">
        	</div>
        	<div class="col col-md-12">
	            <a type="button" class="btn btn-lg btn-success" @click="addExpense">Add</a>
        	</div>
    	</div>


        <table class="table table-striped">
            <thead>
                <tr>
                    <th>Tag</th>
                    <th>Amount</th>
                </tr>
            </thead>

            <tbody>
            <tr class="clickable" v-for="expense in trip.expenses" >
                <td>{{expense.tag}}</td>
                <td>{{expense.amount}}</td>
            </tr>
            <tr>
                <td><b>TOTAL</b></td>
                <td>{{getTotal()}}</td>
            </tr>
            </tbody>
        </table>


    </div>

</div>

</body>
<script type="text/javascript" src="/static/js/trip-view.js"></script>
</html>