/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hzi.helmholtz.Pathways;

import de.hzi.helmholtz.Modules.Module;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author skondred
 */
public class Pathway {

    /*Ordered list of modules*/
    private List<Module> modules;

    public Pathway() {
    }

    public Pathway(List<Module> m) {
        this.modules = m;
    }

    /**
     * @return the modules
     */
    public List<Module> getModules() {
        return modules;
    }

    /**
     * @param modules the modules to set
     */
    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    public Iterator<Module> moduleIterator() {
        return this.modules.iterator();
    }
}
