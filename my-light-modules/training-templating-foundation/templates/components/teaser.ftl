[#assign targetPageLink = content.targetPageLink!]
 
[#if targetPageLink?has_content]
    [#assign targetPage = cmsfn.contentById(content.targetPageLink)!]
 
    [#if targetPage?has_content]
        <div class="default-teaser">
     
            [#-- Create target page link here. Use its title or page name as its text. --]
            <h4 class="chapter-head">
                <a href="${cmsfn.link(targetPage)!}">${targetPage.title!targetPage.@name}</a>
            </h4>
             
            [#-- Access abstract content of the target page. --]
            ${targetPage.abstract!}
             
            [#-- An optional possible extends: Trying also to get an image from the target page. --]
             
            <a href="${cmsfn.link(targetPage)!}">Read On</a>
             
        </div>
    [#elseif cmsfn.editMode]
        <div>Target Page could not be resolved.</div>
    [/#if]
 
 
[#elseif cmsfn.editMode]
    <div>No target page has been chosen, please open dialog and do so.</div>
[/#if]