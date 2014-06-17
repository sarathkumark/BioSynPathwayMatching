/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hzi.helmholtz.Compare;

import java.util.List;
import java.util.Map;

/**
 *
 * @author skondred
 */
public class DBPathway {

    private String pathwayID;
    private Map<Integer, List<String>> pathway;

    public DBPathway() {
    }

    public DBPathway(String pid, Map<Integer, List<String>> p) {
        this.pathwayID = pid;
        this.pathway = p;
    }

    /**
     * @return the pathwayID
     */
    public String getPathwayID() {
        return pathwayID;
    }

    /**
     * @param pathwayID the pathwayID to set
     */
    public void setPathwayID(String pathwayID) {
        this.pathwayID = pathwayID;
    }

    /**
     * @return the pathway
     */
    public Map<Integer, List<String>> getPathway() {
        return pathway;
    }

    /**
     * @param pathway the pathway to set
     */
    public void setPathway(Map<Integer, List<String>> pathway) {
        this.pathway = pathway;
    }

    public void printPathway() {
        System.out.println(this.pathwayID);
        for (Map.Entry<Integer, List<String>> entry : this.pathway.entrySet()) {
            int key = entry.getKey();
            List<String> values = entry.getValue();
            System.out.print("\t"+key+": ");
            System.out.println(values);
        }
    }
}
