[#if content.headline?has_content || content.text?has_content || content.imageLink?has_content]
 
    <div class="default-text-image">
     
        [#if content.headline?has_content]
            <h4 class="chapter-head">${content.headline!}</h4>
        [/#if]
         
        ${cmsfn.decode(content).text!}
         
        [#-- Resolve the 'imageLink' property to the asset node and create a link to it. For more detailed comments see the 'defaultPageScript.ftl' script.--]         
        [#if content.imageLink?has_content]
            [#assign asset = damfn.getAsset(content.imageLink)!]
            [#if asset?has_content && asset.link?has_content]
                <img class="img-responsive" src="${asset.link}" alt="">
            [/#if]
        [/#if]
         
    </div> 
 
[#elseif cmsfn.editMode]
    <div>No Content defined for this text & image component.</div>
[/#if]
