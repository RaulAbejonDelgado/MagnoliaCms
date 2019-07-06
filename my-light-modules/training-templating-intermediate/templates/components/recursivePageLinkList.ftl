[#if content.targetNode?has_content]
    [#assign targetNode = cmsfn.contentById(content.targetNode, "website")! /]
 
    [#if targetNode?has_content]
    <div>
        <a href="${cmsfn.link(targetNode)!}">${targetNode.title!targetNode.@name}</a>
 
        [#assign maxLevels = content.maxLevels!3]
        [#assign targetNode = cmsfn.contentById(content.targetNode, "website")! /]
        [#assign targetNodeDepth = targetNode.@depth!1]
 
        [@linkChildren targetNode targetNodeDepth maxLevels /]
 
        [#--  The macro getting all the childNode nodes and calling itself recursive. --]
        [#macro linkChildren node startNodeDepth maxLevels]
            [#list cmsfn.children(node, "mgnl:page")]
            <ul>
                [#items as childNode]
                    <li><a href="${cmsfn.link(childNode)!}">${childNode.title!childNode.@name}</a></li>
                
                    [#assign childNodeDepth = childNode.@depth!2]
                    [#assign doRecursion = (childNodeDepth - startNodeDepth) < maxLevels]
                    [#if doRecursion!false]
                        [#--  The recursive call on the childNode again. --]
                        [@linkChildren childNode startNodeDepth maxLevels /]
                    [/#if]
                [/#items]      
            </ul>
            [/#list]
        [/#macro]
 
    [#elseif cmsfn.editMode]
        <div>Target node could not be found.</div>
    [/#if]
[#elseif cmsfn.editMode]
    <div>No target node defined.</div>
[/#if]