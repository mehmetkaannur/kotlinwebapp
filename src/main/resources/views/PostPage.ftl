<html lang="EN">
<body>
<ul>
    <#list 0..titles?size-1 as index>
        <li>${titles[index]}</li>
        <p>${bodies[index]}</p>
            <br>
    </#list>
</ul>
<form action="/posts" method="post">
    <label for="namefield">Post Title:</label>
    <input type="text" id="namefield" name="title">
    <label for="namefield">Post Body:</label>
    <input type="text" id="namefield" name="body">
    <input type="submit" value="Submit">
</form>
</body>
</html>