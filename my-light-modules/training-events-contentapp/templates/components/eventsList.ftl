<div class='events-list'>
    [#assign page = cmsfn.page(content)]
    [#assign pageLink = cmsfn.link(page)]
    [#assign eventName = state.getSelector()]
    [#assign eventNode = cmsfn.contentByPath("/"+(eventName!), "events")!]
      
    [#if eventName?has_content && eventNode?has_content]
    
        [#if eventNode.name?has_content]
            <h3>${eventNode.name}</h3>
        [/#if]
        [#if eventNode.location?has_content]
            <h4>[#if eventNode.date?has_content] ${eventNode.date?string["dd.MM.YYYY"]!} - [/#if]${eventNode.location}</h4>
        [/#if]
        
        ${eventNode.description!}
        
        [#if pageLink?has_content]
            <a href="${pageLink!}">Show all events</a>
        [/#if]

    [#else] [#-- IF THERE IS NO SELECTOR, LIST OF EVENTS WILL BE DISPLAYED --]
        

        [#assign eventsRoot = cmsfn.contentByPath("/", "events")]
        [#assign events = cmsfn.children(eventsRoot,"event")!]
        
        [#list events]
            <ul class="list-group">
                <li class="list-group-item active">Total events: ${events?size} </li>
                [#items as event]
                    [#if event.name?has_content]
                        <li class="list-group-item"><small>Event #${event_index+1}: </small> ${event.name} [#if event.date?has_content][${event.date?string["dd.MM.YY"]}][/#if]</li>
                    [/#if]
                [/#items]
            </ul>
        [/#list]

    [/#if]

</div>
