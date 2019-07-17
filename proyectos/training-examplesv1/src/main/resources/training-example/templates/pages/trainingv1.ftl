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
        <link rel="stylesheet" href="${ctx.contextPath}/.resources/training-example/webresources/bootstrap.css" media="screen">
    </head>
     
    <body>

        [#if cmsfn.editMode]
        
            
            <div class="panel-heading">
                <h1 class="panel-title">Dear Author</h1>
                <h3 class="panel-title">${model.getCustomFunction()!"did not work!"}</h3>
                 <h4>OFICINAS</h4>
                [#assign myResource = cmsfn.contentByPath("/", "oficinas")]
                ${myResource!}

                <ul>
                   
                </ul>

                <div class="d-flex justify-content-around flex-wrap">
                     [#list cmsfn.children(myResource, "oficina") as childNode]
                   
                        [#-- <a href="${cmsfn.link(childNode)!}">${childNode.title!childNode.@name}</a> --]
                        <div class="card" style="width: 18rem;">
                            <div class="card-body">
                                <h5 class="card-title">${childNode.title!}</h5>
                                
                               [#--  <p class="card-text">${childNode.postal!childNode.@postal}</p>  --]
                              
                            </div>
                            <ul class="list-group list-group-flush">
                                <li class="list-group-item">${childNode.@name}</li>
                                <li class="list-group-item">${childNode.@postal!}</li>
                                <li class="list-group-item">${childNode.@prueba!}</li>
                            </ul>
                            <div class="card-body">
                                <p>${childNode.@latitud!}</p>
                                <p>${childNode.@longitud!}</p>
                            </div>
                        </div>
                    
                    [/#list]
                </div>

                [#list model.getOficinas() ]
                     //empty collection save
                    [#items as elem]
                        <li>${elem!}</li>
                    [/#items]
                    [#else]
                      //executed on empty collection (else is optional)
                [/#list]

                <br>

            </div>
        [/#if]

    </body>
     
</html>