package com.jeep.core.Entity;

/**
 * 功能描述：
 *
 * @日期：2013-12-31 17:55:01
 * @作者：wujng
 * @版本：1.0
 */
public class EntityFieldData {

    private String english;
    private String chinese;
    private String type;
    private String simpleDic;
    private String treeDic;
    private boolean manyToOne;
    private String manyToOneRef;

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSimpleDic() {
        return simpleDic;
    }

    public void setSimpleDic(String simpleDic) {
        this.simpleDic = simpleDic;
    }

    public String getTreeDic() {
        return treeDic;
    }

    public void setTreeDic(String treeDic) {
        this.treeDic = treeDic;
    }

    public boolean isManyToOne() {
        return manyToOne;
    }

    public void setManyToOne(boolean manyToOne) {
        this.manyToOne = manyToOne;
    }

    public String getManyToOneRef() {
        return manyToOneRef;
    }

    public void setManyToOneRef(String manyToOneRef) {
        this.manyToOneRef = manyToOneRef;
    }
    
    
}
