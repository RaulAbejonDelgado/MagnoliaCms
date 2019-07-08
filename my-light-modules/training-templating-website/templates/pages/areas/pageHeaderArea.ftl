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