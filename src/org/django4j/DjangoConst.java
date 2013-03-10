package org.django4j;

public interface DjangoConst {
    String $CUR_URL              = "$cur_url";

    String $DEFAULT_VAR          = "$_";

    String $POJO_CALSS           = "$pojo_calss";

    String $POJO_PARAM           = "$pojo_param";

    String $PRE_RESULT           = "$pre_result";

    String $TMPL_PATH            = "$templ_path";

    String APP_NAME_ENGINE       = "org.django4j.engine";

    String APP_NAME_ROUTER       = "org.django4j.app.handlerouter";

    String APP_NAME_TEMPLATE     = "org.django4j.app.template";

    String APP_PACKAGE           = "app_page_package";

    String TEMPLATE_FILE_CHARSET = "template.file.charset";

    String TEMPLATE_FILTER       = "org.django4j.app.template.filter";

    String TEMPLATE_TAG          = "org.django4j.app.template.tag";

    void empty();
}
