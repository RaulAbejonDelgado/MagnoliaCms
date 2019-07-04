[#if content.targetNode?has_content]
    [#assign targetNode = cmsfn.contentById(content.targetNode, "dam")! /]
     
    [#if targetNode?has_content]
    <div>       
     
     
        <!-- TODO 1 -->           
        [#-- The Author has correctly chosen a folder as target node --]
        [#if targetNode.@nodeType.name == "mgnl:folder"]
             
            <br/>
            <h5>${targetNode.title!targetNode.@name}</h5>
 
            [#list cmsfn.children(targetNode, "mgnl:asset")]
            <ul>
                [#items as childNode]
                    <li><img src="${cmsfn.link(childNode)!}"></li>
                [/#items]      
            </ul>
            [/#list]
            <br/><br/>
         
        [#-- The Author has incorrectly chosen an asset as target node --]   
        [#else]
            <br/>NodeType helper test for TODO1: ${content.@nodeType.name!}<br/><br/>
 
            TODO 3: Inside ELSE<br/><br/>
             
            [#if cmsfn.editMode]
                <div>You have chosen an asset and not a folder. To get a download list you should choose a folder instead.</div>
            [/#if]
        [/#if]
         
                                           
    </div>   
    [#elseif cmsfn.editMode]
        <div>Target node could not be found.</div>
    [/#if]
[#elseif cmsfn.editMode]
    <div>No target node defined.</div>
[/#if]