[#-- example of pure manual code achieving the same
[#if content.@depth == 1]
    [#assign rootPage = content /]
[#else]
    [#assign rootPage = cmsfn.root(content, "mgnl:page") /]
[/#if]
--]
 
 
[#assign rootPage = cmsfn.siteRoot(content, "training-home") /]
 
 
<nav class="navbar navbar-default">
  <div class="container-fluid">
    <div class="navbar-header">
      <button aria-controls="navbar-collapse-1" type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar-collapse-1" aria-expanded="false">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a href="${cmsfn.link(rootPage)}" class="navbar-brand">${rootPage.title!rootPage.@name}</a>
    </div>
   
    <div class="collapse navbar-collapse" id="navbar-collapse-1"> 
      <div class="navbar-left">
        [@linkChildren node=rootPage /]
      </div> <!-- /navbar-left -->
    </div><!-- /.navbar-collapse -->   
  </div><!-- /.container-fluid -->
</nav>
 
 
 
[#macro linkChildren node ulClass="nav navbar-nav"]
<ul class="${ulClass}">
    [#list cmsfn.children(node, "mgnl:page") as child]
        [#assign hasChildren = (cmsfn.children(child, "mgnl:page")?size)>0 /]
        [#if hasChildren]
            <li class="dropdown">
                <a href="${cmsfn.link(child)!}" > ${child.title!child.@name}</a>
                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false" >
                    <span class="caret"></span>
                </a>
                 [#if child.@depth <= 3]
                    [@linkChildren node=child ulClass="dropdown-menu"/]
                [/#if]
            </li>
        [#else]
            <li><a href="${cmsfn.link(child)!}">${child.title!child.@name}</a></li>       
        [/#if]           
    [/#list]
</ul>   
[/#macro]