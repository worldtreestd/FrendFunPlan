package com.legend.ffplan.common.http;

import java.io.File;
import java.util.Map;

/**
 * @author Legend
 * @data by on 2018/1/3.
 * @description
 */

public interface IHttpClient {
    IResponse get(IRequest request);
    IResponse post(IRequest request);
    IResponse upload_image_post(IRequest request, Map<String,Object> map, File file);
    IResponse delete(IRequest request);
    IResponse put(IRequest request);
}
