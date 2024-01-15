<html lang="EN">
    <head>
        <link rel="stylesheet" href="/assets/style.css">
        <title>Kotlin WebApp</title>
    </head>
    <body>
        <h1>Hello ${name}!</h1>
        <p>This is a very simple Kotlin webapp.</p>
        <ul>
            <#list list as item>
                <#if item != "b">
                    <li>${item}</li>
                <#else>
                    <li>d</li>
                </#if>

            </#list>
        </ul>
        <form action="/submit" method="post">
            <label for="namefield">Words:</label>
            <input type="text" id="namefield" name="words">
            <input type="submit" value="Submit">
        </form>
    </body>
</html>