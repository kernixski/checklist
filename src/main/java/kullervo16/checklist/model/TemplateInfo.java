/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kullervo16.checklist.model;

import java.util.List;

/**
 * Data object specifying the info for a template
 * @author jef
 */
public class TemplateInfo implements Comparable<TemplateInfo>{
    
    private String description;
    
    private List<String> tags;
    
    private List<Milestone> milestones;
    
    private String id;
    
    private boolean subchecklistOnly;

    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<Milestone> getMilestones() {
        return milestones;
    }

    public void setMilestones(List<Milestone> milestones) {
        this.milestones = milestones;
    }

    public boolean isSubchecklistOnly() {
        return subchecklistOnly;
    }

    public void setSubchecklistOnly(boolean subchecklistOnly) {
        this.subchecklistOnly = subchecklistOnly;
    }
    
    

    @Override
    public int compareTo(TemplateInfo t) {
        if(t == null) {
            return 1;
        }
        if(this.isSubchecklistOnly() && !t.isSubchecklistOnly()) {
            return 1;
        } else if (!this.isSubchecklistOnly() && t.isSubchecklistOnly()) {
            return -1;
        }
        return this.getId().compareTo(t.getId());
    }
    
    
}
