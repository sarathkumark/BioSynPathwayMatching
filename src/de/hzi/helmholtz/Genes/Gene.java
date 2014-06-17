/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hzi.helmholtz.Modules;

import de.hzi.helmholtz.Domains.Domain;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author skondred
 */
public class Module {

    /*Ordered list of domains*/
    private List<Domain> domains;

    /**
     * @param domains the domains to set
     */
    public void setDomains(List<Domain> domains) {
        this.domains = domains;
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
}
