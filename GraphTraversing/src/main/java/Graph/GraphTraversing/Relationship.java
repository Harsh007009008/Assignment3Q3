package Graph.GraphTraversing;

import com.fasterxml.jackson.annotation.JsonProperty;

class Relationship {
    @JsonProperty("parentId")
    private String parentId;
    @JsonProperty("childId")
    private String childId;

    public Relationship() {}

    public Relationship(String parentId, String childId) {
        this.parentId = parentId;
        this.childId = childId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }

    @Override
    public String toString() {
        return "Relationship{" +
                "parentId='" + parentId + '\'' +
                ", childId='" + childId + '\'' +
                '}';
    }
}

