/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.duzieblo.ewdapp.xml;

import com.duzieblo.ewdapp.model.People;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Serializer;

/**
 *
 * @author Damian
 */
public class PeopleXmlParser {
    
    private static final String ELEMENT_PEOPLES = "peoples";
    private static final String ELEMENT_PEOPLE  = "people";
    private static final String ELEMENT_PESEL   = "pesel";
    private static final String ELEMENT_NAME    = "name";
    private static final String ELEMENT_SURNAME = "surname";
    private static final String ELEMENT_CITY    = "city";
    
    private static final int SPACES = 3;
    
    private String xml;
    private ArrayList<People> peopleList;
    private Document doc;

    public PeopleXmlParser(ArrayList<People> peopleList) {
        if (peopleList == null) {
            throw new NullPointerException("People list is null!!");
        }
        this.peopleList = peopleList;
        generateXml();
    }
    
    public String getXml() {
        return xml;
    }
    
    private void generateXml() {
        Element root = new Element(ELEMENT_PEOPLES);
        peopleList.forEach(people -> {
            Element elPeople  = new Element(ELEMENT_PEOPLE);
            Element elPesel   = new Element(ELEMENT_PESEL);
            Element elName    = new Element(ELEMENT_NAME);
            Element elSurname = new Element(ELEMENT_SURNAME);
            Element elCity    = new Element(ELEMENT_CITY);
            elPesel.appendChild(people.getPesel());
            elName.appendChild(people.getName());
            elSurname.appendChild(people.getSurname());
            elCity.appendChild(people.getCity());
            elPeople.appendChild(elPesel);
            elPeople.appendChild(elName);
            elPeople.appendChild(elSurname);
            elPeople.appendChild(elCity);
            root.appendChild(elPeople);
        });
        
        doc = new Document(root);
        xml = doc.toXML();
        
    }
    
    public void saveXmlToFile(String file) throws FileNotFoundException, IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
        Serializer serialize = new Serializer(bos);
        serialize.setIndent(SPACES);
        serialize.write(doc);
    }

    
}
