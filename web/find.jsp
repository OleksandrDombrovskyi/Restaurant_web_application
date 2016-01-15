<%-- 
    Document   : find
    Created on : 11.01.2016, 17:45:50
    Author     : Sasha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Find meal by id</title>
    </head>
    <body>
        <div>
            <form action="servlet">
                Find meal by id : <input type="text" name="mealId">
                <input type="submit" name="findMeal" value="Search">
            </form>
        </div>
    </body>
</html>
