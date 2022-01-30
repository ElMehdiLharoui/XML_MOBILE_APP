package com.casaa.xml;

import android.Manifest;
import android.os.Environment;
import android.util.Xml;

import androidx.core.app.ActivityCompat;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class xml {
    File F=null;
   private InputStream i=null;
    xml(String aa){
        String  xmlFile = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+aa;
        F= new File(xmlFile);
        if(!F.exists()){
            try {
                F.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    xml(String lien,boolean a){

        try {
            URL li = new URL(lien);//recupurer le lien
            HttpURLConnection cnx =(HttpURLConnection) li.openConnection();
            cnx.setRequestMethod("GET");
            i=cnx.getInputStream();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    List<RSS.song> Read_songs()
    {
        List<RSS.song> menu=new ArrayList<RSS.song>();
        String title=null;
        String image=null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder=factory.newDocumentBuilder();//XML to hTML get elemnt

            Document dom=documentBuilder.parse(i);

            Element root=dom.getDocumentElement();

            NodeList Datas=root.getElementsByTagName("entry");

            for (int i = 0; i < Datas.getLength(); i++) {

                Element BigDa=(Element) Datas.item(i);

                 title=BigDa.getElementsByTagName("title").item(0).getTextContent();
                if(title.length()>50){
                    title=BigDa.getElementsByTagName("im:name").item(0).getTextContent();
                }

                Node IMG=BigDa.getElementsByTagName("im:image").item(0);
                if(IMG!=null)image=IMG.getTextContent();
                else image="";

                menu.add(new RSS.song(title,image));

            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
            return  menu;

    }


    void fun_write(String firstname,String Lastname) {

        FileOutputStream Strem;
        try {

            //overture du ficher
            Strem = new FileOutputStream(F, false);//ecriture au debut
            XmlSerializer xmlSerializer = Xml.newSerializer();//construction de l'architecture de xml
            StringWriter writer = new StringWriter();//xml ecriture en chaine de caractere buffer
            xmlSerializer.setOutput(writer);// ecriture (buffer)
            xmlSerializer.startDocument("UTF-8", true);
            xmlSerializer.startTag(null, "Root");// tag de debut xml
            //ecriture de l'object
            xmlSerializer.startTag(null,"Data");
            xmlSerializer.startTag(null,"FirstName").text(firstname).endTag(null,"FirstName");
            xmlSerializer.startTag(null,"LastName").text(Lastname).endTag(null,"LastName");
            xmlSerializer.endTag(null,"Data");//end ecriture de lobject
            xmlSerializer.endTag(null,"Root");
            xmlSerializer.flush();//transfere au buffer normal
            Strem.write(writer.toString().getBytes(StandardCharsets.UTF_8));//ecriture dans le fichier
            Strem.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
