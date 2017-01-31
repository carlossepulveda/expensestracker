<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script
  src="https://code.jquery.com/jquery-1.12.4.min.js"
  integrity="sha256-ZosEbRLbNQzLpnKIkEdrPv7lOy9C27hHQ+Xp8a4MxAQ="
  crossorigin="anonymous"></script>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/vue/1.0.24/vue.min.js"></script>
    <link rel="stylesheet" href="/static/css/base.css">
</head>
<body>

<div class="expenses-list container-section col-md-10 col-xs-10 col-md-offset-1 col-xs-offset-1" style="padding:4%;">
    

    <div class="pull-right">
        <a href="/logout">Logout</a>
    </div>
	<div class="row col-md-12 main-menu">
		<a href="/admin/trip" class="col-md-2"><h4>Trips</h4></a>
		<a href="/admin/employee" class="col-md-2"><h4>Employees</h4></a>
        <a href="/admin/expense" class="col-md-2"><h4>ExpensesByTag</h4></a>
	</div>
	<hr/>
    <div class="row" style="padding:2em">
        <div class="col-md-8">
            <h4>List Expenses by Tag</h4>
        </div>
    </div>

    <div class="col-md-12 content-container">

        <table class="table table-striped">
            <thead>
                <tr>
                    <th>Tag</th>
                    <th>Amount</th>
                </tr>
            </thead>

            <tbody>
            <tr class="clickable" v-for="expense in expenses">
                <td>{{expense[0]}}</td>
                <td>{{expense[1]}}</td>
            </tr>
            </tbody>
        </table>


    </div>

</div>

</body>
<script type="text/javascript" src="/static/js/admin-list-expenses.js"></script>
</html>