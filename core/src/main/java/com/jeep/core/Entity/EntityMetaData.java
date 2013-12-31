/**
 * Copyright (c) 2007-2025 西安未来国际信息股份有限公司
 */
package com.jeep.core.Entity;

import com.jeep.core.log.WopLogger;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述：
 *
 *    @日期：2013-12-31 16:21:22
 *    @作者：wujng
 *    @版本：1.0
 */
public class EntityMetaData {
    private static final WopLogger logger = new WopLogger(EntityMetaData.class);

    private static Map<String,String> des=new HashMap<String,String>();
    private static Map<String,Class<? extends BaseEntity>> metaData=new HashMap<String,Class<? extends BaseEntity>>();
    public static Map<String,String> getModelDes(){
        return Collections.unmodifiableMap(des);
    }
    public static void addMetaData(BaseEntity baseEntity){
        String modelName=baseEntity.getClass().getSimpleName().toLowerCase();
        if(des.get(modelName)!=null){
            return ;
        }
        logger.info("注册模型元数据(Register model metadata)，"+modelName+"="+baseEntity.getMetaData());
        des.put(modelName, baseEntity.getMetaData());
        metaData.put(modelName, baseEntity.getClass());
    }
    public static String getMetaData(String modelName){
        modelName=modelName.toLowerCase();
        String value = des.get(modelName);
        if(value==null){
           logger.info("没有找到(Not find model metadata) "+modelName+" 的模型元数据"); 
           return "";
        }
        return value;
    }
}
