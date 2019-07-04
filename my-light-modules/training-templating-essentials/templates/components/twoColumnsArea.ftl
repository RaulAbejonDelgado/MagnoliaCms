[#list components ]
<ul class="list-group">
    [#items as component ]
        <li class="list-group-item">
            <h5>Component Number: '${component?index + 1!}'; Is Last Component: '${(component?is_last)?string}'</h5>
            [@cms.component content=component /]
        </li>
    [/#items]
</ul>
[/#list]