/**
 * This file Copyright (c) 2017 Magnolia International
 * Ltd.  (http://www.magnolia-cms.com). All rights reserved.
 *
 *
 * This file is dual-licensed under both the Magnolia
 * Network Agreement and the GNU General Public License.
 * You may elect to use one or the other of these licenses.
 *
 * This file is distributed in the hope that it will be
 * useful, but AS-IS and WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE, TITLE, or NONINFRINGEMENT.
 * Redistribution, except as permitted by whichever of the GPL
 * or MNA you select, is prohibited.
 *
 * 1. For the GPL license (GPL), you can redistribute and/or
 * modify this file under the terms of the GNU General
 * Public License, Version 3, as published by the Free Software
 * Foundation.  You should have received a copy of the GNU
 * General Public License, Version 3 along with this program;
 * if not, write to the Free Software Foundation, Inc., 51
 * Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * 2. For the Magnolia Network Agreement (MNA), this file
 * and the accompanying materials are made available under the
 * terms of the MNA which accompanies this distribution, and
 * is available at http://www.magnolia-cms.com/mna.html
 *
 * Any modifications to this file must keep this entire header
 * intact.
 *
 */
package info.magnolia.training.developer.templating.advanced.templates.components;
 
import info.magnolia.context.MgnlContext;
import info.magnolia.context.WebContext;
import info.magnolia.jcr.util.NodeTypes;
import info.magnolia.jcr.util.NodeUtil;
import info.magnolia.jcr.util.PropertyUtil;
import info.magnolia.rendering.model.RenderingModel;
import info.magnolia.rendering.model.RenderingModelImpl;
import info.magnolia.rendering.template.configured.ConfiguredTemplateDefinition;
import info.magnolia.templating.functions.TemplatingFunctions;
 
import java.io.IOException;
import java.util.Iterator;
 
import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.http.HttpServletResponse;
 
import org.apache.commons.lang3.StringUtils;
 
 
/**
 * Model of the Redirect Template. 2 Redirect mode are available (depending of
 * the 'path' value)
 * <ul>
 * <li>Empty path: This will redirect to the first child page found. This is useful to directly display a page defined in a second or third level of a redirect tree</li>
 * <li>Internal page link: Redirect directly to the selected page</li>
 * </ul>
 * If the redirect is define (not empty path) the rendering is skipped avoiding
 * the writing of the response (not allowed for response.redirect instruction).
 */
public class RedirectPageModel<RD extends ConfiguredTemplateDefinition> extends RenderingModelImpl<RD> {
 
    protected static final String PATH_PROPERTY_NAME = "path";
 
    final private TemplatingFunctions templatingFunctions;
 
    @Inject
    public RedirectPageModel(Node content, RD definition, RenderingModel<?> parent, TemplatingFunctions templatingFunctions) {
        super(content, definition, parent);
        this.templatingFunctions = templatingFunctions;
    }
 
    @Override
    public String execute() {
 
        // In edit mode we want to render the template, so in NOT-Edit mode we do the redirect.
        if (!templatingFunctions.isEditMode()) {
 
            String redirectTo = "";
 
            try {
                redirectTo = getRedirectPathManuallyDefined();
             
                if (StringUtils.isBlank(redirectTo)) {
                    redirectTo = getRedirectPathToFirstChild();
                }
             
                final WebContext webContext = MgnlContext.getWebContext();
                if (StringUtils.isNotBlank(redirectTo)) {
                    webContext.getResponse().sendRedirect(redirectTo);
                } else {
                    webContext.getResponse().sendError(HttpServletResponse.SC_NOT_FOUND);
                }
             
            } catch (RepositoryException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
 
            // Tell the renderer to skip the rendering after triggering the redirect on the response.
            return RenderingModel.SKIP_RENDERING;
        }
        return super.execute();
    }
 
    public String getTest() {
        return "test succeeded!";
    }
    
    public String getRedirectPathManuallyDefined() throws RepositoryException {
    	// Little starting help: - check if 'content' has a property 'path'
    	if (content.hasProperty(PATH_PROPERTY_NAME) && StringUtils.isNoneBlank(PropertyUtil.getString(content, PATH_PROPERTY_NAME))) {
    	    String path = content.getProperty(PATH_PROPERTY_NAME).getString();
    	    Session session = content.getSession();
    	    if (session.nodeExists(path)) {
    	        Node targetNode = session.getNode(path);
    	        return templatingFunctions.link(targetNode);
    	    }
    	}
    	return null;
    }
     
    public String getRedirectPathToFirstChild() throws RepositoryException {
    	// Little starting help: - collect all children of the current page of type 'mgnl:page'
        Iterable<Node> allChildren = NodeUtil.getNodes(content, NodeTypes.Page.NAME);
        Iterator<Node> iterator = allChildren.iterator();
        if (iterator.hasNext()) {
            Node firstChildNode = iterator.next();
            return templatingFunctions.link(firstChildNode);
        }
        return null;
    }
 
}