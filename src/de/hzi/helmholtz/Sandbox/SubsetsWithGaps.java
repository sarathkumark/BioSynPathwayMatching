/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hzi.helmholtz.Sandbox;

import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *
 * @author skondred
 */
public class SubsetsWithGaps {

    public static void printPowerSet(Set<Set> power) {
        for (Set s : power) {
            System.out.println(s);
        }
    }

    public static List<List<Integer>> generateGap(List<Integer> input, int gap) {
        List<List<Integer>> output = new ArrayList<List<Integer>>();
        for (int i = 0; i < input.size(); i++) {
            List<Integer> listWithAGap = new ArrayList<Integer>(input);
            List<Integer> toBeRemoved = new ArrayList<Integer>();

            int removed = 0;
            for (int j = i; j < input.size() && removed < gap; j++) {
                toBeRemoved.add(j);
                removed++;
            }
            listWithAGap.removeAll(toBeRemoved);
            output.add(listWithAGap);
        }
        return output;
    }

    public static void main(String[] args) {

        List<Set<Integer>> finalResultList = new ArrayList<Set<Integer>>();

        List<Integer> input = new ArrayList<Integer>(); //Arrays.asList(1, 2, 3, 4, 5);
        for (int i = 1; i <= 20; i++) {
            input.add(i);
        }

        int maxgap = 18; //2;
        //printPowerSet(Sets.powerSet(new HashSet(input)));   // gap = 0
        finalResultList.addAll(Sets.powerSet(new HashSet(input)));
        for (int i = 1; i <= maxgap; i++) {
            List<List<Integer>> gapoutput = generateGap(input, i);
            for (int j = 0; j < gapoutput.size(); j++) {
                Set s = new HashSet();
                s.addAll(gapoutput.get(j));
                //printPowerSet(Sets.powerSet(s));
                finalResultList.addAll(Sets.powerSet(s));
            }
        }

        System.out.println(finalResultList.size());
        
        Set<String> finalResultSet = new HashSet<String>();
        for (int i = 0; i < finalResultList.size(); i++) {
            finalResultSet.add(finalResultList.get(i).toString());
        }
        System.out.println(finalResultSet.size());
    }
}
