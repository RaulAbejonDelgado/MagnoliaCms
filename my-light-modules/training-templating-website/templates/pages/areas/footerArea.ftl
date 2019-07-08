[#-------------- ASSIGNMENTS --------------]
[#switch components?size]
    [#case 2]
        [#assign divColClass = "col-sm-6" /]
        [#break]
    [#case 3]
        [#assign divColClass = "col-sm-4" /]
        [#break]
    [#case 4]
        [#assign divColClass = "col-sm-3" /]
        [#break]
    [#default]
        [#assign divColClass = "col-sm-12" /]
[/#switch]
  
[#-------------- RENDERING --------------]
<div class="row">
 
    [#-- The system provides automatically to every Area script: All components of the area as a list 'components'. Therefore looping over each component. --]
    [#list components as component ]
    <div class="${divColClass}">
     
        [#-- Triggering the rendering of each component node. --]
        [@cms.component content=component /]
    </div>
    [/#list]
</div>