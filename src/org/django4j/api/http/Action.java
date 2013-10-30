package org.django4j.api.http;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: neverend
 * Date: 13-6-7
 * Time: 下午8:36
 * To change this template use File | Settings | File Templates.
 */
public class Action {
    public static Action GET = new Action("get");
    public static Action POST = new Action("post");
    private static Map<String, Action> actionMap = new HashMap<String, Action>();

    static {
        actionMap.put(GET.getActStr(), GET);
        actionMap.put(POST.getActStr(), POST);
    }

    private final String _actStr;

    private Action(String actStr) {
        _actStr = actStr;
    }

    public static Action build(String actStr) {
        if (actStr.contains(actStr.toLowerCase())) {
            return actionMap.get(actStr.toLowerCase());
        }
        return new Action(actStr);
    }

    public String getActStr() {
        return _actStr;
    }

    public boolean isGet()
    {
        return "get".equals(_actStr);
    }
    public boolean isPost()
    {
        return "post".equals(_actStr);
    }
}
