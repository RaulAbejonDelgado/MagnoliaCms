package info.magnolia.training.developer.templating.advanced.templates.components;
 
import info.magnolia.context.MgnlContext;
import info.magnolia.jcr.util.ContentMap;
import info.magnolia.jcr.util.NodeUtil;
import info.magnolia.jcr.util.PropertyUtil;
import info.magnolia.rendering.model.RenderingModel;
import info.magnolia.rendering.model.RenderingModelImpl;
import info.magnolia.rendering.template.configured.ConfiguredTemplateDefinition;
import info.magnolia.repository.RepositoryConstants;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
 
import org.apache.commons.lang3.StringUtils;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
/**
 * Model class for Navigation component.
 *
 * @param <RD>
 */
public class AutoLinkListModel<RD extends ConfiguredTemplateDefinition> extends RenderingModelImpl<RD> {
 
    private static final Logger log = LoggerFactory.getLogger(AutoLinkListModel.class);
 
    public AutoLinkListModel(Node content, RD definition, RenderingModel<?> parent) {
        super(content, definition, parent);
    }
 
    private Node targetNode = null;
     
    @Override
    public String execute() {
        try {
            this.resolveTarget();
        } catch (RepositoryException e) {
            log.error("Was not able to resolve target node: " + e);
            return null;
        }
         
        return "success";
    }
 
    public String getTest() {
        return "test succeeded!";
    }
 
    // Using our PropertyUtil & NodeUtil for convenience coding
    private void resolveTarget() throws RepositoryException {
        String idOfChosenTargetNode = PropertyUtil.getString(content, "targetNode");
        if (StringUtils.isNotEmpty(idOfChosenTargetNode)) {
            String workspaceName = getWorkspaceName();
            Node nodeByIdentifier = NodeUtil.getNodeByIdentifier(workspaceName, idOfChosenTargetNode);
            this.targetNode = nodeByIdentifier;
        }
    }
 
 
    public String getWorkspaceName() {
        /*
         * TODO 2:
         * Do:
         * - Change in the class declaration the 'definition' to the new created type 'AutoLinkListDefinition'. (Generics)
         * - Read here from the 'definition' object the workspace.
         * - If the workspace is empty, then return 'website' workspace. Hint: 'RepositoryConstants' interface provides workspace names.
         */
    	AutoLinkListDefinition ad = new AutoLinkListDefinition();
    			
		String workspace = ad.getWorkspace();
        if (StringUtils.isEmpty(workspace)) {
            workspace = RepositoryConstants.WEBSITE;
        }
        return workspace;

    }
 
    public ContentMap getTargetNode() {
        if (targetNode != null) {
            return new ContentMap(targetNode);
        }
        return null;
    }
}