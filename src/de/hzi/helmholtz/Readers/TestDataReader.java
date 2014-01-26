/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hzi.helmholtz.Readers;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.io.Files;
import java.io.File;

/**
 *
 * @author skondred
 */
public class TestDataReader {

    String genesFile;
    String geneDomainsFile;

    public TestDataReader() {
    }

    public TestDataReader(String genesFile, String domainsFile) {
        this.genesFile = genesFile;
        this.geneDomainsFile = domainsFile;
    }

    public void read() {
        try {
            File genesf = new File(genesFile);
            ImmutableList<String> lines = Files.asCharSource(genesf, Charsets.UTF_8).readLines();
            for (int i = 0; i < lines.size(); i++) {
                System.out.println(lines.get(i));
            }
            
            File genedomainsf = new File(geneDomainsFile);
            ImmutableList<String> domainlines = Files.asCharSource(genedomainsf, Charsets.UTF_8).readLines();
            for (int i = 0; i < domainlines.size(); i++) {
                System.out.println(domainlines.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        TestDataReader reader = new TestDataReader("./data/small/genes.txt", "./data/small/genes_domains.txt");
        reader.read();
    }
}
