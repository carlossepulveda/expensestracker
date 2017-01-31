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

<div class="employees-list container-section col-md-10 col-xs-10 col-md-offset-1 col-xs-offset-1" style="padding:4%;">
    

    <div class="pull-right">
        <a href="/logout">Logout</a>
    </div>
	<div class="row col-md-12">
		<a href="/admin/trip" class="col-md-2"><h4>Trips</h4></a>
		<a href="/admin/employee" class="col-md-2"><h4>Employees</h4></a>
	</div>
	<hr/>
    <div class="row" style="padding:2em">
        <div class="col-md-8">
            <h4>List Employees</h4>
        </div>

        <div class="pull-right">
            <a type="button" class="btn btn-lg btn-success" href="/admin/employee/new/form">Create</a>
        </div>
    </div>

    <div class="col-md-12 content-container">

        <table class="table table-striped">
            <thead>
                <tr>
                    <th>Id</th>
                    <th>PersonalId</th>
                    <th>Name</th>
                    <th>Phone</th>
                    <th>Role</th>
                </tr>
            </thead>

            <tbody>
            <tr class="clickable" v-for="employee in employees">
                <td>{{employee.id}}</td>
                <td>{{employee.personalId}}</td>
                <td>{{employee.name}}</td>
                <td>{{employee.phone}}</td>
                <td>{{employee.role}}</td>
            </tr>
            </tbody>
        </table>


    </div>

</div>

</body>
<script type="text/javascript" src="/static/js/admin-list-employees.js"></script>
</html>