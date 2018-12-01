package logistics.work.common;

import java.util.HashMap;
import java.util.Map;

public class ParamUtils {
    public static Map<String,Object> setPageInfo(Integer pageNumber,Integer pageSize){
        Map<String,Object> map=new HashMap<>();
        if(pageNumber==null)pageNumber=1;
        if(pageSize==null)pageSize=10;
        map.put("pageStart",(pageNumber-1)*pageSize);
        map.put("pageSize",pageSize);
        return map;
    }
}
