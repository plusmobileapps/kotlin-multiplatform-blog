<html>
	<body>
	    <div>${displayName}</div>
		<ul>
		<#list articles as article>
			<li>${article}</li>
		</#list>
		</ul>

		<form method="post" action"/articles">

		Title:<br>
        <input type="text" name="title" /><br>

		Author:<br>
		<input type="text" name="author" /><br>

		Minutes Read:<br>
        <input type="text" name="minRead" /><br>

		Body:<br>
        <input type="text" name="body" /><br>

        <input type="submit" value="Submit" />

		</form>
	</body>
</html>