<!DOCTYPE html>
<html xml:lang="${cmsfn.language()}" lang="${cmsfn.language()}">
<head>
    [@cms.page /]
      
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="${content.description!content.abstract!}">
    <meta name="keywords" content="${content.keywords!}">
    <meta name="author" content="">
      
    <title>${content.windowTitle!content.navigationTitle!content.title!content.@name}</title>
  
    ${resfn.css("/training-templating-website.*css")}
</head>
  
<body>

    <!-- Navigation -->
    [@cms.area name="navigation"/]
    <!-- End: Navigation -->
    <div class="container">
               
        [#-- ****** page content ****** --]
        [@cms.area name="pageHeader" /]
          
        <!-- Main Area -->
        [@cms.area name="main" /]
        <!-- End: Main Area-->
         
    </div>
      
    ${resfn.cachedJs(["/training-templating-website.*jquery.*js", "/training-templating-website.*bootstrap.*js"]) }

    <!-- Footer -->
    [@cms.area name="footer"/]
    <!-- End: Footer -->
</body>
</html>