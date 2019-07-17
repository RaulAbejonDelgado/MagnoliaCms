package com.raul.casa;

import info.magnolia.context.MgnlContext;
import info.magnolia.jcr.util.NodeUtil;
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
    List<String> oficinas = new ArrayList<>();


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
        return "MESSAGE FROM CUSTOM METHOD !!!!!!!!!!!!";
    }

 //   public List getCategories() {


//        try {
//            session = MgnlContext.getJCRSession("category");
//            Node categories = session.getNode("/Comunidades-Autonomas");
//            PropertyIterator pi = categories.getProperties();
//
//            if(pi != null){
//                while(pi.hasNext()){
//                    Property p = pi.nextProperty().getProperty();
//
//                    log.info(p.getProperty().getString() +":"+ p.getValue());
//                    categoriesList.add(p.getProperty().getString() +":"+ p.getValue());
//                }
//            }else{
//                log.info("Category Nodes is empty");
//            }
//
//        } catch (RepositoryException e) {
//            e.printStackTrace();
//        }
//
//        if(categoriesList.isEmpty()){
//            categoriesList.add(">HeadHache<");
//        }
//
//
//        return categoriesList;
//
//
//    }

    public List getOficinas() {

        try{
            session = MgnlContext.getJCRSession("oficinas");
            Node oficinasNode = session.getNode("/Prueba");
            log.info(oficinasNode.getIdentifier());
            //NodeUtil.getNodeByIdentifier("oficinas","")
            PropertyIterator pi = oficinasNode.getProperties();
//
//            if(pi != null){
//                while(pi.hasNext()){
//                    log.info("PROPERTY FOUND");
//                    Property p = pi.nextProperty().getProperty();
//
//                    oficinas.add("Property:"+p.getProperty().getString()+" "+" value:"+p.getValue());
//                }
//            }else{
//                log.info("OFICINAS NODE IS EMPTY");
//            }
//            log.info(oficinas.toString());
        }catch (RepositoryException e) {
            e.printStackTrace();
        }

        if(oficinas.isEmpty()) {
            oficinas.add("Oficina harcodeada");
        }

        return oficinas;


    }


}
