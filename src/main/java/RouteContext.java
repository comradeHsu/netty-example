import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.*;

import java.util.*;

public class RouteContext {

    private HttpRequest request;

    private Map<String,String> params = this.initQueryParams();

    public RouteContext(HttpRequest request){
        this.request = request;
    }

    public Optional<String> queryParam(String name){
        return Optional.of(this.params.get(name));
    }

    private Map<String,String> initQueryParams(){
        String url = request.uri();
        if(url.indexOf("?") == -1){
            return Collections.EMPTY_MAP;
        }
        String[] params = url.split("[?]")[1].split("&");
        Map<String,String> paramsMap = new HashMap<>(params.length);
        for(String param : params){
            String[] parameter = param.split("=");
            paramsMap.put(parameter[0],parameter[1]);
        }
        return paramsMap;
    }

    public Map<String,String> queryParams(){
        return this.params;
    }
}
