package app.project.gamestart.web.controllers;

import org.springframework.web.servlet.ModelAndView;

public abstract class BaseController {

    private static final String BASE_PAGE_LAYOUT = "/base/base-layout";
    private static final String PROPERTY_VIEW_NAME = "viewName";
    private static final String PROPERTY_VIEW_MODEL = "viewModel";
    private static final String PROPERTY_TITLE = "title";
    private static final String REDIRECT_KEYWORD = "redirect:";

    public ModelAndView view(String viewName, Object viewModel, String title){

        ModelAndView mv = new ModelAndView();
        mv.setViewName(BASE_PAGE_LAYOUT);
        mv.addObject(PROPERTY_VIEW_NAME, viewName);
        mv.addObject(PROPERTY_VIEW_MODEL, viewModel);
        mv.addObject(PROPERTY_TITLE, title);

        return mv;
    }

    final ModelAndView view(String viewName, Object viewModel){
        return this.view(viewName,viewModel, null);
    }

    final ModelAndView view(String viewName){
        return this.view(viewName,null, null);
    }

    final ModelAndView redirect(String redirectUrl, Object viewModel, String title) {
        final ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(PROPERTY_VIEW_MODEL, viewModel);
        modelAndView.addObject(PROPERTY_TITLE, title);
        modelAndView.setViewName(REDIRECT_KEYWORD + redirectUrl);
        return modelAndView;
    }

    final ModelAndView redirect(String redirectUrl, Object viewModel){
        return this.redirect(redirectUrl,viewModel,null);
    }

    final ModelAndView redirect(String redirectUrl){
        return this.redirect(redirectUrl,null,null);
    }
}
