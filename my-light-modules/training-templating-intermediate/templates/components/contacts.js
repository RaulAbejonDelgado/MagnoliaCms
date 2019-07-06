var MyModel = function () {
   
    this.getContacts = function () {
        var result = new Array();
        var nodes = ctx.getJCRSession("contacts").getRootNode().getNodes();
        while (nodes.hasNext()) {
            node = nodes.next();
            if (node.getPrimaryNodeType().getName() == 'mgnl:contact') {
                result.push(node);
            }
        }
        return result;
    };
   
    this.format = function (contact) {
        return contact.getProperty("firstName").getString() + ' ' + contact.getProperty("lastName").getString();
    };
};
   
new MyModel();