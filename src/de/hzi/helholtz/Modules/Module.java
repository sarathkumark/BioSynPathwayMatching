/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hzi.helholtz.Modules;

import de.hzi.helmholtz.Domains.Domain;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author skondred
 */
public class Module {

    /*Id of the module*/
    private int moduleId;
    /*Name of the module*/
    private String moduleName;
    /*Ordered list of domains*/
    private List<Domain> domains;
    /*Number of domains in this module*/
    private int size;

    public Module(int id, String name, List<Domain> domains) {
        this.moduleId = id;
        this.moduleName = name;
        this.domains = domains;
        this.size = domains.size();
    }

    /**
     * @param domains the domains to set
     */
    public void setDomains(List<Domain> domains) {
        this.domains = domains;
        this.size = domains.size();
    }

    public Iterator<Domain> domainIterator() {
        return this.getDomains().iterator();
    }

    /**
     * @return the domains
     */
    public List<Domain> getDomains() {
        return domains;
    }

    /**
     * @return the moduleName
     */
    public String getModuleName() {
        return moduleName;
    }

    /**
     * @param moduleName the moduleName to set
     */
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    /**
     * @return the moduleId
     */
    public int getModuleId() {
        return moduleId;
    }

    /**
     * @param moduleId the moduleId to set
     */
    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
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

        toReturn += moduleId;
        toReturn += ":";
        toReturn += domains.toString();

        return toReturn;
    }
}

