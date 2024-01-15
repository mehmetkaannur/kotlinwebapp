<html lang="EN">
<body>
<table>
    <tr>
        <th>Word</th>
        <th>Score</th>
    </tr>
    <#if words?size != 0>
        <#list 0..words?size-1 as index>
            <tr>
                <td>${words[index]}</td>
                <td>${scores[index]}</td>
            </tr>
        </#list>
    </#if>
</table>

</body>
</html>