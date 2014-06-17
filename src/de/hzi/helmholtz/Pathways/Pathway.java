/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hzi.helmholtz.Pathways;

import de.hzi.helmholtz.Genes.Gene;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author skondred
 */
public class Pathway {

    /*Pathway ID*/
    private int pathwayId;
    /*Pathway name*/
    private String pathwayName;
    /*Ordered list of genes*/
    private List<Gene> genes;
    /*Number of genes*/
    private int size;

    public Pathway() {
    }

    public Pathway(int pathwayId,String pathwayName, List<Gene> m) {
        this.pathwayId = pathwayId;
        this.pathwayName = pathwayName;
        this.genes = m;
        this.size = m.size();
    }

    /**
     * @return the modules
     */
    public List<Gene> getGenes() {
        return genes;
    }

    /**
     * @param genes the modules to set
     */
    public void setGenes(List<Gene> genes) {
        this.genes = genes;
    }

    public Iterator<Gene> geneIterator() {
        return this.genes.iterator();
    }

    /**
     * @return the pathwayName
     */
    public String getPathwayName() {
        return pathwayName;
    }

    /**
     * @param pathwayName the pathwayName to set
     */
    public void setPathwayName(String pathwayName) {
        this.pathwayName = pathwayName;
    }

    /**
     * @return the pathwayId
     */
    public int getPathwayId() {
        return pathwayId;
    }

    /**
     * @param pathwayId the pathwayId to set
     */
    public void setPathwayId(int pathwayId) {
        this.pathwayId = pathwayId;
    }

    /**
     * @return the size
     */
    public int size() {
        return size;
    }
}
