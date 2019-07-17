<!DOCTYPE html>
<html xml:lang="en" lang="en" class="no-js">
 
    <head>
        [@cms.page /]
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        [#-- Access from the page's content: The page's 'title' OR if non-existing, the page's node name. --]
        <title>${content.title!content.@name}</title>
 
        [#-- A manual defined link to a resource:
        <link rel="stylesheet" href="${ctx.contextPath}/.resources/training-templating-foundation/webresources/bootstrap.css" media="screen">
        --]
        [#-- Using the 'resfn' for a more dynamic approach. --]
        ${resfn.css("/training-examples.*css")}
    </head>
     
    <body>
 
        [#if cmsfn.editMode]
             
            <div class="panel-heading">
                <h1 class="panel-title">Dear Author</h1>
                <h3 class="panel-title">${model.getCustomFunction()!"did not work!"}</h3>
  
               [#assign searchResults = searchfn.searchContent("oficinas", "4", "/", "mgnl:nodeData") /]
 
                [#if searchResults?has_content]
                <p>HAS CONTENT</p>
                    [#list searchResults as item]
                        [#if item.nombre??]
                        <p>ITEM CON NOMBRE</p>
                            <div class="found-component">
                            ${item.nombre}
                            </div>
                        [/#if]
                    [/#list]
                [/#if]
                [#assign rootNode = cmsfn.contentByPath("/home")]
                    <a href="${cmsfn.link(rootNode)}">${rootNode.title!}</a>
                </div>
        [/#if]
    </body>
     
</html>