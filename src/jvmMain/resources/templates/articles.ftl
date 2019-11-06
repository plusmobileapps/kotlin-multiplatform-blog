<#import "common/bootstrap.ftl" as b>

<@b.page>
    <#if articles?? && (articles?size > 0)>
        <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>Title</th>
                            <th>Author</th>
                            <th>Minutes to Read</th>
                            <th>Body</th>
                        </tr>
                    </thead>
                    <tbody>
                    <#list articles as article>
                        <tr>
                            <td style="vertical-align:middle"><h3>${article.title}</h3></td>
                            <td style="vertical-align:middle"><h3>${article.author}</h3></td>
                            <td style="vertical-align:middle"><h3>${article.minRead}</h3></td>
                            <td style="vertical-align:middle"><h3>${article.body}</h3></td>

                            <td class="col-md-1" style="text-align:center;vertical-align:middle;">
                                <form method="post" action="/articles">
                                    <input type="hidden" name="id" value="${article.id}">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="image" src="/static/delete.png" width="24" height="24" border="0 alt="Delete" />
                                </form>
                            </td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
    </#if>

    <div class="panel-body">
        <form method="post" action="/articles">
            <input type="hidden" name="action" value="add">
            Title:<br>
            <input type="text" name="title" /><br>
            Author:<br>
            <input type="text" name="author" /><br>
            Minutes to Read: <br>
            <input type="text" name="minRead" /><br>
            Body:<br>
            <input type="text" name="body" /><br>
            <input type="submit" value="Submit" />
        </form>
        </div>
</@b.page>