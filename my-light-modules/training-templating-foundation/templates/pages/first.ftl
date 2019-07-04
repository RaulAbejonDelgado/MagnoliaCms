<!DOCTYPE html>
<html xml:lang="en" lang="en" class="no-js">
 
    <head>
        [@cms.page /]
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
         
        <title>${content.title!content.@name}</title>
    
        ${resfn.css("/training-templating-foundation.*css")}
    </head>
     
    <body>
        <div class="container">
            <div class="panel panel-info">
                <div class="panel-heading">
                    <h1 class="panel-title">${content.title!content.@name}</h1>
                </div>
                 
                [#if content.abstract?has_content]
                <div class="panel-body">
                    <p>${content.abstract}</p>
                </div>
                [/#if]
            </div>
             
            [#-- ****** main area ****** --]
            [@cms.area name="main" /]
        </div>
    </body>
     
</html>