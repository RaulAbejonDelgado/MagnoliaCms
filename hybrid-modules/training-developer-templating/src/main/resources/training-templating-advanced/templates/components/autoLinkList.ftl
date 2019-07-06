<br/>
The model test: ${model.test!"did not work!"}
<br/><br/>
 
[#assign targetNode = model.targetNode!]
 
[#if targetNode?has_content]
<div>
    <a href="${cmsfn.link(targetNode)!}">${targetNode.title!targetNode.@name}</a>
 
	[#list cmsfn.children(targetNode, "mgnl:page")]
	<ul>
	    [#items as childNode]
	        <li><a href="${cmsfn.link(childNode)!}">${childNode.title!childNode.@name}</a></li>
	    [/#items]      
	</ul>
	[/#list]
</div>
      
[#elseif cmsfn.editMode]
    <div>Target node could not be found.</div>
[/#if]