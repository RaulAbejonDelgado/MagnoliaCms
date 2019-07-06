[#assign allContacts = model.getContacts()]
 
[#list allContacts]
    <h4 class="chapter-head">All Contacts</h4>
    <ul>
        [#items as contact]
            <li>${model.format(contact)}</li>
        [/#items]      
    </ul>
[#else]
    [#if cmsfn.editMode]
        <h4 class="chapter-head">Dear Author</h4>
        <p><b>Problem & Cause:</b> It seems that there are no contacts existing in the contacts app.</p>   
    [/#if]
 
[/#list]