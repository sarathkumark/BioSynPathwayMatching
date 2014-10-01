/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hzi.helmholtz.ModularGenes;

import de.hzi.helholtz.Modules.Module;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author skondred
 */
public class ModularGene {

    /*Id of the gene*/
    private int modularGeneId;
    /*Name of the gene*/
    private String modularGeneName;
    /*Ordered list of domains*/
    private List<Module> modules;
    /*Number of domains in this gene*/
    private int size;

    public ModularGene(int id, String name, List<Module> modules) {
        this.modularGeneId = id;
        this.modularGeneName = name;
        this.modules = modules;
        this.size = modules.size();
    }

    /**
     * @param modules the domains to set
     */
    public void setDomains(List<Module> modules) {
        this.modules = modules;
        this.size = modules.size();
    }

    public Iterator<Module> moduleIterator() {
        return this.getModules().iterator();
    }

    /**
     * @return the domains
     */
    public List<Module> getModules() {
        return modules;
    }

    /**
     * @return the moduleName
     */
    public String getModularGeneName() {
        return modularGeneName;
    }

    /**
     * @param modularGeneName the moduleName to set
     */
    public void setModularGeneName(String modularGeneName) {
        this.modularGeneName = modularGeneName;
    }

    /**
     * @return the modularGeneId
     */
    public int getModularGeneId() {
        return modularGeneId;
    }

    /**
     * @param modularGeneId the modularGeneId to set
     */
    public void setModularGeneId(int modularGeneId) {
        this.modularGeneId = modularGeneId;
    }

    /**
     * @return the size
     */
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        String toReturn = "";

        toReturn += modularGeneId;
        toReturn += ":";
        toReturn += modules.toString();

        return toReturn;
    }
}
