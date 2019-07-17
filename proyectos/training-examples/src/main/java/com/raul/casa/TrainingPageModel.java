package com.raul.casa;

import info.magnolia.context.MgnlContext;
import info.magnolia.rendering.model.RenderingModel;
import info.magnolia.rendering.model.RenderingModelImpl;
import info.magnolia.rendering.template.configured.ConfiguredTemplateDefinition;
import info.magnolia.templating.functions.TemplatingFunctions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.jcr.*;
import java.util.ArrayList;
import java.util.List;

public class TrainingPageModel<RD extends ConfiguredTemplateDefinition> extends RenderingModelImpl<RD> {
    final private TemplatingFunctions templatingFunctions;

    private static final Logger log = LoggerFactory.getLogger(TrainingPageModel.class);
    Session session = null;

    List<String> categoriesList = new ArrayList<>();


    @Inject
    public TrainingPageModel(Node content, RD definition, RenderingModel<?> parent, TemplatingFunctions templatingFunctions) {
        super(content, definition, parent);
        this.templatingFunctions = templatingFunctions;
    }

    @Override
    public String execute() {

        // In edit mode we want to render the template, so in NOT-Edit mode we do the redirect.
        if (!templatingFunctions.isEditMode()) {

            String redirectTo = "";

            System.out.println("Info: Here the redirect will be made.");

            // Tell the renderer to skip the rendering after triggering the redirect on the response.
            return RenderingModel.SKIP_RENDERING;
        }

        return super.execute();
    }

    public String getCustomFunction() {
        return "For you mother fucker!!!!!!!!!!!!";
    }

    public List getCategories() {


        try {
            session = MgnlContext.getJCRSession("workspaceName");
            Node categories = session.getNodeByIdentifier("category");
            PropertyIterator pi = categories.getProperties();

            if(pi != null){
                while(pi.hasNext()){
                    Property p = pi.nextProperty().getProperty();

                    log.info(p.getProperty() +":"+ p.getValue());
                }
            }

        } catch (RepositoryException e) {
            e.printStackTrace();
        }

        categoriesList.add(">HeadHache<");

        return categoriesList;


    }


}
