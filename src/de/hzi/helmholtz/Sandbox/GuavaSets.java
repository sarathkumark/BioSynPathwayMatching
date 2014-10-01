/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hzi.helmholtz.Sandbox;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import java.util.Set;

/**
 *
 * @author skondred
 */
public class GuavaSets {
    public static void main(String[] args) {
       for(Set s: Sets.powerSet(ImmutableSet.of(1, 2, 3, 4, 5))){
           System.out.println(s);
       }
    }
}
