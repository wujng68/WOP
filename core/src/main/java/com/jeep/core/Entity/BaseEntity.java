package com.jeep.core.Entity;

import com.jeep.core.annotation.ModelAttr;
import com.jeep.core.annotation.RenderIgnore;
import com.jeep.core.log.WopLogger;
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
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;
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
    
    public BaseEntity(){
        EntityMetaData.addMetaData(this);
    }

    public List<String> getSearchProperties(){
        List<String> list = new ArrayList<String>();
        List<Field> fields = Refle
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
    
    
    
    public abstract String getMetaData();
}
