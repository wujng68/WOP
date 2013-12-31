package com.jeep.core.Entity;

import com.jeep.core.annotation.ModelAttr;
import com.jeep.core.annotation.ModelAttrRef;
import com.jeep.core.annotation.RenderIgnore;
import com.jeep.core.annotation.SimpleDic;
import com.jeep.core.annotation.TreeDic;
import com.jeep.core.log.WopLogger;
import com.jeep.core.util.ReflectionUtils;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlTransient;
import org.compass.annotations.SearchableComponent;
import org.compass.annotations.SearchableId;
import org.compass.annotations.SearchableProperty;

/**
 * 功能描述：实体基类
 *
 * @日期：2013-12-31 15:28:59
 * @作者：wujng
 * @版本：1.0
 */
@MappedSuperclass
@EntityListeners(value = EntityListeners.class)
public abstract class BaseEntity implements Serializable {

    @Transient
    @RenderIgnore
    private static final long serialVersionUID = 1L;

    @Transient
    @RenderIgnore
    protected final WopLogger logger = new WopLogger(getClass());

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @SearchableId
    @ModelAttr("编号")
    protected Long id;

    @SearchableProperty(format = "yyyy-MM-dd")
    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @ModelAttr("创建时间")
    protected Date createTime;

    @SearchableProperty(format = "yyyy-MM-dd")
    @Column(insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @ModelAttr("上一次更新时间")
    protected Date updateTime;

    @Version
    @ModelAttr("更新次数")
    protected Integer version;

    public BaseEntity() {
        EntityMetaData.addMetaData(this);
    }

    public List<String> getSearchProperties() {
        List<String> list = new ArrayList<String>();
        List<Field> fields = ReflectionUtils.getDeclaredFields(this);
        for (Field field : fields) {
            if (field.isAnnotationPresent(SearchableProperty.class)) {
                String fieldName = field.getName();
                list.add(fieldName);
            }
        }
        return list;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @XmlTransient
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BaseEntity)) {
            return false;
        }
        BaseEntity baseEntity = (BaseEntity) obj;
        return baseEntity.getId() == this.getId();
    }

    @Override
    public int hashCode() {
        if (id == null) {
            id = Long.parseLong("-1");
        }
        return Long.valueOf(id + 1000).hashCode();
    }

    @Override
    public String toString() {
        return this.getMetaData() + this.getId();
    }

    public List<EntityFieldData> getAllRenderModelAttr(){
        List<EntityFieldData> list=new ArrayList<>();
        //获取所有字段，包括继承的
        List<Field> fields = ReflectionUtils.getDeclaredFields(this);
        for (Field field : fields) {
            if(field.isAnnotationPresent(RenderIgnore.class)){
                continue;
            }
            EntityFieldData data=getFieldData(field);
            if(data!=null){
                list.add(data);
            }
        }
        return list;
    }
    public List<EntityFieldData> getAllModelAttr(){
        List<EntityFieldData> list=new ArrayList<>();
        //获取所有字段，包括继承的
        List<Field> fields = ReflectionUtils.getDeclaredFields(this);
        for (Field field : fields) {
            EntityFieldData data=getFieldData(field);
            if(data!=null){
                list.add(data);
            }
        }
        return list;
    }
    public List<EntityFieldData> getModelAttr(){
        List<EntityFieldData> list=new ArrayList<>();
        //获取所有字段，不包括继承的
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            EntityFieldData data=getFieldData(field);
            if(data!=null){
                list.add(data);
            }
        }
        return list;
    }
    public List<EntityFieldData> getAllModelSearchableAttr(){
        List<EntityFieldData> list=new ArrayList<>();
        //获取所有字段，包括继承的
        List<Field> fields = ReflectionUtils.getDeclaredFields(this);
        for (Field field : fields) {
            EntityFieldData data=getSearchableFieldData(field);
            if(data!=null){
                list.add(data);
            }
        }
        return list;
    }
    public List<EntityFieldData> getModelSearchableAttr(){
        List<EntityFieldData> list=new ArrayList<>();
        //获取所有字段，不包括继承的
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            EntityFieldData data=getSearchableFieldData(field);
            if(data!=null){
                list.add(data);
            }
        }
        return list;
    }
    private EntityFieldData getSearchableFieldData(Field field){
        if(field.isAnnotationPresent(SearchableProperty.class) || field.isAnnotationPresent(SearchableComponent.class)){
            String prefix="";
            if(field.isAnnotationPresent(SearchableComponent.class)){
                prefix=field.getAnnotation(SearchableComponent.class).prefix();
            }
            String english=field.getName();
            //处理下拉项
            if(field.getType().getSimpleName().equals("DicItem")){
                english="name";
            }
            english=prefix+english;
            
            return getModelFieldData(english,field);
        }
        return null;
    }
    private EntityFieldData getFieldData(Field field){
        if(field.isAnnotationPresent(ModelAttr.class)){
            String english=field.getName();
            return getModelFieldData(english,field);
        }
        return null;
    }
    private EntityFieldData getModelFieldData(String english, Field field){
        String chinese=field.getAnnotation(ModelAttr.class).value();
        EntityFieldData data=new EntityFieldData();
        data.setChinese(chinese);
        data.setEnglish(english);
        data.setSimpleDic("");
        data.setTreeDic("");
        data.setManyToOneRef("");
        if(field.isAnnotationPresent(SimpleDic.class)){
            String dic=field.getAnnotation(SimpleDic.class).value();
            data.setSimpleDic(dic);
        }
        if(field.isAnnotationPresent(TreeDic.class)){
            String dic=field.getAnnotation(TreeDic.class).value();
            data.setTreeDic(dic);
        }
        if(field.isAnnotationPresent(ManyToOne.class)){
            data.setManyToOne(true);
            if(field.isAnnotationPresent(ModelAttrRef.class)){
                String manyToOneRef=field.getAnnotation(ModelAttrRef.class).value();
                data.setManyToOneRef(manyToOneRef);
            }
        }
        String valueClass=field.getType().getSimpleName();
        if("Timestamp".equals(valueClass) || "Date".equals(valueClass)){
            data.setType("Date");
        }else{
            data.setType(valueClass);
        }
        return data;
    }
    
    public abstract String getMetaData();
}
