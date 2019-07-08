// create workspace
Components.getSingleton(RepositoryManager.class).createWorkspace(app_repository, app_workspace)
 
// check we registered all right
appSession = ctx.getJCRSession(app_workspace)
 
// register node type
nodeTypeManager = appSession.getWorkspace().getNodeTypeManager()
type = NodeTypeTemplateUtil.createSimpleNodeType(nodeTypeManager, app_node_type, Arrays.asList(NodeType.NT_HIERARCHY_NODE, NodeType.MIX_REFERENCEABLE, NodeTypes.Created.NAME, NodeTypes.Activatable.NAME, NodeTypes.LastModified.NAME, NodeTypes.Renderable.NAME))
nodeTypeManager.registerNodeType(type, true)
appSession.save()
 
// double check it registered all right
nodeTypeManager.getNodeType(app_node_type)