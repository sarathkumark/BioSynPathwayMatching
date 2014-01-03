/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hzi.helmholtz.Domains;

/**
 *
 * @author skondred
 */
public class DomainSimilarity {

    /* Return 0 for equivalence 
     * -SUBDOMAINPENALTY for sub-domain
     * -SUBSTRATEMISMATCHPENALTY for substrate mismatch
     */
    public static double getDomainSimilarity(Domain d1, Domain d2) {
        return getSubDomainSimilarity(d1, d2) + getSubstrateSimilarity(d1, d2);
    }

    public static double getSubDomainSimilarity(Domain d1, Domain d2) {
        return 0.0;
    }

    /*
     * If set of substrates are equivalent return 0
     * If set of substrates are disjoint return high
     * If set of substrates have intersection return medium
     */
    public static double getSubstrateSimilarity(Domain d1, Domain d2) {
        return 0.0;
    }
}
