/**
 * This file Copyright (c) 2018 Magnolia International
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
package info.magnolia.training.developer.templating.templates.availability;

import info.magnolia.jcr.util.NodeTypes;
import info.magnolia.module.site.templates.ConfiguredSiteTemplateAvailability;
import info.magnolia.objectfactory.Components;
import info.magnolia.rendering.template.TemplateDefinition;
import info.magnolia.rendering.template.registry.TemplateDefinitionRegistry;

import java.util.HashMap;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A custom templates availability allowing to define in two ways available templates:
 * 1. Per level/depth in the website hierarchy:
 * - level 1 : allow template X & Y
 * - level 2 : allow template Y & Z
 * - level 3-10 : allow template D
 * 2. Depending on the used parent node's template:
 * - template Y can be added if parent page uses template X
 * - template X can be added if parent page uses template X or ROOT
 * - template D can be added if parent page uses template X or Y
 */
public class HierarchicalConfiguredSiteTemplateAvailability extends ConfiguredSiteTemplateAvailability {

    private static final Logger log = LoggerFactory.getLogger(HierarchicalConfiguredSiteTemplateAvailability.class);

    public static String PARENT_ROOT_VALUE = "ROOT";
    public static String VALUES_SEPARATOR = ";";
    public static String KEYS_WILDCARD = "-";
    public static String SLASH_REPLACEMENT = "+";
    public static String COLON_REPLACEMENT = "@";
    
    private Hierarchy hierarchy = new Hierarchy();

    public Hierarchy getHierarchy() {
        return hierarchy;
    }
    public void setHierarchy(Hierarchy hierarchy) {
        this.hierarchy = hierarchy;
    }

    /**
     * Checks if the templateDefinition can be used/made available for the current page node.
     * 
     * @param node Page node to check if the templateDefinition is available for it.
     * @param templateDefinition the TemplateDefinition to check if should it should be available for the page node.
     * @return true if template is available.
     */
    @Override
    public boolean isAvailable(Node node, TemplateDefinition templateDefinition) {
        // Proceed logic on super=false so only returning on true
        if (super.isAvailable(node, templateDefinition)) {
            return true;
        }
        // Proceed custom logic only if hierarchical based configuration is enabled.
        if (hierarchy.isEnabled()) {
            if (hierarchy.isLevelsEnabled() && checkLevelCompliance(hierarchy.getLevels(), node, templateDefinition)) {
                return true;
            }

            if (hierarchy.isParentsEnabled() && checkParentsCompliance(hierarchy.getParents(), node, templateDefinition)) {
                return true;
            }
        }
        return false;
    };

    /**
     * Checks the compliance of the TemplateDefinition with the levels availability configuration
     * 
     * @param levels the level based template availability configuration
     * @param pageNode the node the template would be used for
     * @param templateDefinition to check against the levels configuration
     * @return true fulfilling the compliance
     */
    protected boolean checkLevelCompliance(Map<String, String> levels, Node pageNode, TemplateDefinition templateDefinition) {
        String depthAsString = StringUtils.EMPTY;
        int depth = -1;
        try {
            depth = pageNode.getDepth();
            depthAsString = Integer.toString(depth);
        } catch (RepositoryException e) {
            log.error("An error occurred while trying to get the depth of the jcr node {}.", pageNode, e);
        }
        // Specific level value is the key in the map, returning its value or null
        String allowedValueForLevel = levels.get(depthAsString);

        // Checking for wildcard levels configuration as example '4-10' levels
        String wildcardKey = StringUtils.EMPTY;
        for (String key : levels.keySet()) {
            if(key.contains(KEYS_WILDCARD)){
                wildcardKey = key;
            }
        }
        if (allowedValueForLevel == null && wildcardKey.contains(KEYS_WILDCARD)) {
            String[] fromToLevels = StringUtils.split(wildcardKey, KEYS_WILDCARD);
            int fromLevel;
            int toLevel;
            try {
                fromLevel = Integer.valueOf(fromToLevels[0]);
                toLevel = Integer.valueOf(fromToLevels[1]);
            } catch (NumberFormatException e) {
                log.error("The used wildcard value {} could not be tanformed into two in values. The wildcard value should be alike alike '4-10' getting transformed into the two ints 4 and 10.", wildcardKey, e);
                return false;
            }
            if (depth >= fromLevel && depth <= toLevel) {
                allowedValueForLevel = levels.get(wildcardKey);
            }
        }
        return hasValueInTemplateTypeOrSubtypeOrId(templateDefinition, allowedValueForLevel);
    }

    /**
     * Checks the compliance of the TemplateDefinition with the parents availability configuration
     * 
     * @param parents the parents based template availability configuration
     * @param pageNode the node the template would be used for
     * @param templateDefinition to check against the levels configuration
     * @return true fulfilling the compliance
     */
    protected boolean checkParentsCompliance(Map<String, String> parents, Node pageNode, TemplateDefinition templateDefinition) {
        try {

            Node parentNode = pageNode.getParent();

            for (Map.Entry<String, String> levelEntry : parents.entrySet()) {
                String possibleTemplate = levelEntry.getKey();
                String allowedParentTemplates = levelEntry.getValue();
                // System.out.println("Key = " + checkingTemplate + ", Value = " + allowedParentTemplates);

                possibleTemplate = replaceInvalidProperyNameCharacters(possibleTemplate);

                // Checking if the templateDefinition contains the current key/template value of the availability configuration
                boolean isPossibleTemplate = hasValueInTemplateTypeOrSubtypeOrId(templateDefinition, possibleTemplate);
                if (isPossibleTemplate) {
                    // Parent is root node AND the ROOT replacement character value is defined as allowed parent.
                    if (parentNode.getPath().equals("/") && allowedParentTemplates.contains(PARENT_ROOT_VALUE)) {
                        return true;
                    }

                    // Parent has no template attached
                    String parentTemplateId = NodeTypes.Renderable.getTemplate(parentNode);
                    if (parentTemplateId == null) {
                        return false;
                    }

                    TemplateDefinition parentTemplateDefinition = Components.getComponent(TemplateDefinitionRegistry.class).getProvider(parentTemplateId).get();
                    boolean isAllowedParentTemplate = hasValueInTemplateTypeOrSubtypeOrId(parentTemplateDefinition, allowedParentTemplates);
                    if (isAllowedParentTemplate) {
                        return true;
                    }
                }
            }
        } catch (RepositoryException e) {
            log.error("An error occurred while trying to access the parent node of the jcr node {}.", pageNode, e);
        }

        return false;
    }

    /**
     * This method replaces in a property using a template id as the propertie's name invalid characters and returns a working and valid template id.
     * Using template ids for JCR property names do not allow the columns and slash character.
     * As the template id is used for defining the key of the map following characters have to be replaced ": -> @" and "/ -> +'
     * Input example: training-templating-website@pages+default
     * Output example: training-templating-website:pages/default
     * 
     * @param templateIdPropertyName the property name of a template id containing the '@' and '+' characters.
     * @return the property name of a template with the valid and replaced characters ':' and '/'.
     */
    protected String replaceInvalidProperyNameCharacters(String templateIdPropertyName) {
        templateIdPropertyName = StringUtils.replace(templateIdPropertyName, COLON_REPLACEMENT, ":");
        templateIdPropertyName = StringUtils.replace(templateIdPropertyName, SLASH_REPLACEMENT, "/");
        return templateIdPropertyName;
    }

    /**
     * Checking if the TemplateDefinition contains the 'value' either in the template's type or template's subtype or the template's id.
     * 
     * @param templateDefinition the TemplateDefinition currently checked for availability
     * @param value the value to check if the TemplateDefinition contains it
     * @return contains the value either as template type or template subtype or the template id itself.
     */
    protected boolean hasValueInTemplateTypeOrSubtypeOrId(TemplateDefinition templateDefinition, String value) {
        String templateId = templateDefinition.getId();
        if (value != null && templateId.contains(":pages/")) {
            String templateType = templateDefinition.getType();
            String templateSubtype = templateDefinition.getSubtype();
            
            String[] values = StringUtils.split(value, VALUES_SEPARATOR);

            for (int i = 0; i < values.length; i++) {
                if (StringUtils.isNotBlank(templateType) && values[i].trim().equals(templateType)) {
                    return true;
                }
                if (StringUtils.isNotBlank(templateSubtype) && values[i].trim().equals(templateSubtype)) {
                    return true;
                }
                if (StringUtils.isNotBlank(templateId) && values[i].trim().equals(templateId)) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * The sub bean of the @class HierarchicalConfiguredSiteTemplateAvailability used for the 'hierarchy' node to populate.
     * The actual and complete configuration for the hierarchical templates availability.
     */
    public static class Hierarchy {
        private boolean enabled = true;
        private boolean levelsEnabled = true;
        private boolean parentsEnabled = true;

        private Map<String, String> levels = new HashMap<String, String>();
        private Map<String, String> parents = new HashMap<String, String>();

        public Map<String, String> getLevels() {
            return levels;
        }

        public void setLevels(Map<String, String> levels) {
            this.levels = levels;
        }

        public void addLevel(String name, String value) {
            this.levels.put(name, value);
        }

        public Map<String, String> getParents() {
            return parents;
        }

        public void setParents(Map<String, String> parents) {
            this.parents = parents;
        }

        public void addParent(String name, String value) {
            this.parents.put(name, value);
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public boolean isLevelsEnabled() {
            return levelsEnabled;
        }

        public void setLevelsEnabled(boolean levelsEnabled) {
            this.levelsEnabled = levelsEnabled;
        }

        public boolean isParentsEnabled() {
            return parentsEnabled;
        }

        public void setParentsEnabled(boolean parentsEnabled) {
            this.parentsEnabled = parentsEnabled;
        }
    }
}
